package com.springboot.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.sns.AmazonSNS;
import com.springboot.dto.DBConfigurationDTO;
import com.springboot.util.SnsNotificationSender;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class ArchiveCleanUpDAO
{
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	AmazonSNS amazonSNS;

	@Autowired
	SnsNotificationSender snsNotificationSender;

	public void executeArchiveDBJob()
	{
		jdbcTemplate.setResultsMapCaseInsensitive(true);

		List<DBConfigurationDTO> listDBConfiguration = loadDBConfiguration();
		log.debug("listDBConfiguration : " + listDBConfiguration);

		if (CollectionUtils.isNotEmpty(listDBConfiguration))
		{
			for (DBConfigurationDTO dbConfigurationDTO : listDBConfiguration)
			{
				String functionName = dbConfigurationDTO.getFunctionName();
				String tableName = dbConfigurationDTO.getTableName();
				log.debug("functionName : {} ", functionName);
				try
				{
					SqlParameterSource in = new MapSqlParameterSource().addValue("TABLENAME", tableName).addValue("HISTORY_TABLENAME", dbConfigurationDTO.getHistoryTablename()).addValue("archivedays", dbConfigurationDTO.getArchiveDays()).addValue("deletedatadays", dbConfigurationDTO.getDeleteDataDays());

					SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withFunctionName(functionName);
					simpleJdbcCall.executeFunction(String.class, in);
					log.debug("SUCCESS functionName : {} ", functionName);
					log.debug("SUCCESS tableName : {} ", tableName);
				}
				catch (Exception e)
				{

					String message = StringUtils.join("Archive Job Failed for ", functionName, " : ", tableName, " : ", e.getMessage());
					log.error("message : {} ", message);
					log.error("Exception occurred while calling function : {} ", e);
					snsNotificationSender.publishSNSError("ArchiveJob Failed ", message);
				}
			}
		}
	}

	private List<DBConfigurationDTO> loadDBConfiguration()
	{
		log.info("START : ArchiveCleanUpDAO : loadDBConfiguration");
		List<DBConfigurationDTO> listDBConfigurationDTO = new ArrayList<DBConfigurationDTO>();
		try
		{
			List<Map<String, Object>> configdata = jdbcTemplate.queryForList("SELECT * FROM db_configuration");

			for (Map<String, Object> row : configdata)
			{
				Object pGobject = row.get("configdata");
				String configData = pGobject.toString();
				JSONParser parser = new JSONParser();
				JSONObject jsonConfigData = (JSONObject) parser.parse(pGobject.toString());
				DBConfigurationDTO dbconfigurationDTO = new DBConfigurationDTO();
				dbconfigurationDTO.setConfigData(configData);
				dbconfigurationDTO.setConfigId((Integer) row.get("configid"));
				dbconfigurationDTO.setExecuteJob((String) row.get("executejob"));
				dbconfigurationDTO.setHistoryTablename((String) row.get("historytablename"));
				dbconfigurationDTO.setTableName((String) row.get("tablename"));
				dbconfigurationDTO.setFunctionName((String) row.get("functionname"));
				dbconfigurationDTO.setArchiveDays((Long) jsonConfigData.get("archivedays"));
				dbconfigurationDTO.setDeleteDataDays((Long) jsonConfigData.get("deletedatadays"));
				listDBConfigurationDTO.add(dbconfigurationDTO);
			}
		}
		catch (Exception e)
		{
			log.error("Exception occurred while getting configuration : {} ", e);
			String message = StringUtils.join("Archive Job Failed while getting config : ", e.getMessage());
			snsNotificationSender.publishSNSError("ArchiveJob Failed ", message);
		}
		log.info("END : ArchiveCleanUpDAO : loadDBConfiguration");
		return listDBConfigurationDTO;
	}
}

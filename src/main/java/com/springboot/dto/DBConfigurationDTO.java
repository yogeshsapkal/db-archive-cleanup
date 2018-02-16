package com.springboot.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DBConfigurationDTO
{
	private int configId;
	private String configData;
	private String tableName;
	private String historyTablename;
	private String executeJob;
	private String functionName;
	private Long archiveDays;
	private Long deleteDataDays;
}

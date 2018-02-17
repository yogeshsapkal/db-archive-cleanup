package com.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.springboot.dao.ArchiveCleanUpDAO;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class DBArchiveCleanupApplication implements CommandLineRunner
{
	@Autowired
	ArchiveCleanUpDAO archiveCleanUpDAO;

	public static void main(String[] args)
	{
		log.info("START : DBArchiveCleanupApplication : main");
		SpringApplication springApplication = new SpringApplication(DBArchiveCleanupApplication.class);
		springApplication.run(args);
		log.info("END : DBArchiveCleanupApplication : main");
	}

	@Override
	public void run(String... arg0)
	{
		archiveCleanUpDAO.executeArchiveDBJob();
	}
}

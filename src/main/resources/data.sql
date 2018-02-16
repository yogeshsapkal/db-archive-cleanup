INSERT INTO public.db_configuration(
	configid,configdata, tablename, historytablename, executejob,functionname)
	VALUES (1,'{"archivedays":7,"deletedatadays":90}', employee','history_employee','Y','db-archive-cleanup');

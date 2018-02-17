INSERT INTO public.wholesale_integration_configuration(
	configdata, tablename, historytablename, executejob,functionname)
	VALUES ('{"archivedays":1,"deletedatadays":2}', 'employee','history_employee','Y','archive_orderconsolidations');
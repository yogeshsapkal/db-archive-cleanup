INSERT INTO public.wholesale_integration_configuration(
	configdata, tablename, historytablename, executejob,functionname)
	VALUES ('{"archivedays":7,"deletedatadays":90}', 'orderconsolidations','history_orderconsolidations','Y','archive_orderconsolidations');
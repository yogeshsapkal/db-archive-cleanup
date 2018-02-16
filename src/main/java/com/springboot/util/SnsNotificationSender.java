package com.springboot.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate;
import org.springframework.stereotype.Component;

import com.amazonaws.services.sns.AmazonSNS;

@Component
public class SnsNotificationSender
{
	private final NotificationMessagingTemplate notificationMessagingTemplate;

	@Value("${errorTopic}")
	private String errorTopic;

	public SnsNotificationSender(AmazonSNS amazonSns)
	{
		this.notificationMessagingTemplate = new NotificationMessagingTemplate(amazonSns);
	}

	public void publishSNSError(String subject, String message)
	{
		this.notificationMessagingTemplate.sendNotification(errorTopic, message, subject);
	}

}

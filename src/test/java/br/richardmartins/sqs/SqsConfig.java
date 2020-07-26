package br.richardmartins.sqs;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.aws.autoconfigure.context.ContextRegionProviderAutoConfiguration;
import org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration;
import org.springframework.cloud.aws.autoconfigure.messaging.MessagingAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
@EnableAutoConfiguration(exclude = {MessagingAutoConfiguration.class, ContextStackAutoConfiguration.class, ContextRegionProviderAutoConfiguration.class})
public class SqsConfig {
}

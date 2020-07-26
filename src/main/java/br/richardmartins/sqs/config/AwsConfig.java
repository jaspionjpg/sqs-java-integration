package br.richardmartins.sqs.config;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import org.springframework.cloud.aws.messaging.config.SimpleMessageListenerContainerFactory;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AwsConfig {

    public static final int MAX_NUMBER_OF_MESSAGES = 10;

    @Bean
    @Primary
    public AmazonSQSAsync sqsClient(AwsProperties awsProperties) {
        final ClientConfiguration clientConfiguration = new ClientConfiguration();
//        clientConfiguration.setProtocol(Protocol.HTTPS);
//        clientConfiguration.setProxyHost();
//        clientConfiguration.setProxyPort(80);

        return AmazonSQSAsyncClientBuilder
                .standard()
                .withEndpointConfiguration(new AwsClientBuilder
                        .EndpointConfiguration(awsProperties.getUrl(), awsProperties.getRegion()))
                .withCredentials(new AWSStaticCredentialsProvider(
                        new BasicAWSCredentials(awsProperties.getAcessKey(), awsProperties.getSecretKey())))
                .withClientConfiguration(clientConfiguration)
                .build();
    }

    @Bean
    public SimpleMessageListenerContainerFactory simpleMessageListenerContainerFactory(final AmazonSQSAsync amazonSQSAsync) {
        SimpleMessageListenerContainerFactory factory = new SimpleMessageListenerContainerFactory();
        factory.setAmazonSqs(amazonSQSAsync);
        factory.setMaxNumberOfMessages(MAX_NUMBER_OF_MESSAGES);

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(50);
        executor.setMaxPoolSize(75);
        executor.setQueueCapacity(100);
        executor.initialize();
        factory.setTaskExecutor(executor);
        return factory;
    }

    @Bean
    public QueueMessagingTemplate queueMessagingTemplate(AmazonSQSAsync amazonSQSAsync) {
        return new QueueMessagingTemplate(amazonSQSAsync);
    }
}

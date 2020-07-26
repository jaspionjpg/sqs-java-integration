package br.richardmartins.sqs.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "aws.connection")
@Getter
@Setter
public class AwsProperties {

    private String acessKey;
    private String secretKey;
    private String url;
    private String region;
}

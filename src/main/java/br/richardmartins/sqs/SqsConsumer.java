package br.richardmartins.sqs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.aws.messaging.listener.Acknowledgment;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.Visibility;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class SqsConsumer {
    public static final String APPROXIMATE_RECEIVE_COUNT = "ApproximateReceiveCount";
    public static final int secondsByAttempt = 60;

    private final ObjectMapper objectMapper;

    public SqsConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @SqsListener(value = "{aws.queue.namedQueue}", deletionPolicy = SqsMessageDeletionPolicy.NEVER)
    public void consumerMessage(String message, Acknowledgment acknowledgment, Visibility visibility, @Headers Map<String, String> headers){
        int attempts = Integer.parseInt(headers.get(APPROXIMATE_RECEIVE_COUNT));

        try {
            Object object = objectMapper.readValue(message, Object.class);
        } catch (JsonProcessingException e) {
            visibility.extend(attempts);
        }

        try {
            if (10 / 0 == 0) {
                System.out.println("Nao ira entrar aki");
            }
        } catch (Exception e) {
            visibility.extend(attempts * secondsByAttempt);
        }

        acknowledgment.acknowledge();
    }


}

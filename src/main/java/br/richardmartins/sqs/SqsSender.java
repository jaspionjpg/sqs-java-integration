package br.richardmartins.sqs;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class SqsSender {

    private final ObjectMapper objectMapper;
    private final AmazonSQSAsync amazonSQSAsync;

    public SqsSender(ObjectMapper objectMapper, AmazonSQSAsync amazonSQSAsync) {
        this.objectMapper = objectMapper;
        this.amazonSQSAsync = amazonSQSAsync;
    }

    public void sendMessage(Object object, String sqsQueueName) {
        SendMessageRequest sendMessageRequest = new SendMessageRequest()
                .withQueueUrl(sqsQueueName)
                .withMessageBody(convertToJson(object));
        amazonSQSAsync.sendMessage(sendMessageRequest);
    }

    @SneakyThrows
    private String convertToJson(Object object) {
        return objectMapper.writeValueAsString(object);
    }
}

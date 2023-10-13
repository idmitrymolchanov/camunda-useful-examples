package org.example.configuration;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.KafkaExampleModel;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MimeTypeUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProducerConfiguration {

  private final StreamBridge streamBridge;

  private static final String BINDING_NAME = "test-producer-out-0";
  public static final String CORRELATION_HEADER = "correlationIdHeader";

  @Transactional
  public void sendExampleMessage(KafkaExampleModel exampleModel, UUID correlationId) {
    Message<KafkaExampleModel> message =
        MessageBuilder
            .withPayload(exampleModel)
            .setHeader(KafkaHeaders.KEY, exampleModel.getId().toString()
                .getBytes(StandardCharsets.UTF_8))
            .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
            .setHeader(CORRELATION_HEADER, correlationId.toString())
            .build();
    streamBridge.send(BINDING_NAME, message);
    log.debug("message with correlationId: {} sent", correlationId);
  }

}
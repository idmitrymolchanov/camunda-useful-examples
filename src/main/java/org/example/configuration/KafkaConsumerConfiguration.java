package org.example.configuration;

import static org.example.configuration.KafkaProducerConfiguration.CORRELATION_HEADER;

import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.KafkaExampleModel;
import org.example.service.ProcessCorrelationService;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumerConfiguration {

  private final ProcessCorrelationService correlationService;

  @Bean
  public Consumer<Message<KafkaExampleModel>> consumeExampleMessage() {
    return message -> {
      Acknowledgment acknowledgment = message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
      try {
        Object correlationHeader = message.getHeaders().get(CORRELATION_HEADER);
        if (correlationHeader != null) {
          String correlationId = correlationHeader.toString();
          log.debug("received message with correlationId: {}", correlationId);
          KafkaExampleModel model = message.getPayload();
          correlationService.correlateProcessByCorrelationId(correlationId, model);
        }
      } catch (Exception e) {
        log.error("В процессе обработки сообщения возникло исключение.", e);
        throw e;
      }
      if (acknowledgment != null) {
        acknowledgment.acknowledge();
      }
    };
  }

}
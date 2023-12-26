package org.example.bpmn.all_elements_example_pa.listener;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.example.configuration.KafkaProducerConfiguration;
import org.example.model.KafkaExampleModel;
import org.springframework.stereotype.Component;

@Slf4j
@Component("CorrelateFirstTaskListener")
@RequiredArgsConstructor
public class CorrelateFirstTaskListener implements ExecutionListener {

  private final KafkaProducerConfiguration producer;

  @Override
  @SneakyThrows
  public void notify(DelegateExecution execution) {
    Thread.sleep(1000);

    var taskName = execution.getEventName();
    var businessKey = UUID.fromString(execution.getBusinessKey());
    var receiveMessageName = execution.getVariableLocal("receiveMessageName");
    var createResponseError = execution.getVariableLocal("createResponseError");
    var createError = Boolean.parseBoolean(createResponseError.toString());
    var model = new KafkaExampleModel(UUID.randomUUID(), receiveMessageName.toString(), createError);

    log.info("businessKey: {}, sending request for task: {}", businessKey, taskName);
    producer.sendExampleMessage(model, businessKey);
  }

}
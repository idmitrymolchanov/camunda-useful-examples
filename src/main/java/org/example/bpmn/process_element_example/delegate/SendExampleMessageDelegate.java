package org.example.bpmn.process_element_example.delegate;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.example.configuration.KafkaProducerConfiguration;
import org.example.model.KafkaExampleModel;
import org.example.model.ProcessVariables;
import org.springframework.stereotype.Component;

@Slf4j
@Component("SendExampleMessageDelegate")
@RequiredArgsConstructor
public class SendExampleMessageDelegate implements JavaDelegate {

  private final KafkaProducerConfiguration producer;

  @Override
  public void execute(DelegateExecution execution) throws Exception {
    var correlationId = UUID.randomUUID();
    var businessKey = execution.getBusinessKey();
    var taskName = execution.getEventName();
    var receiveMessageName = execution.getVariableLocal("receiveMessageName");
    var createResponseError = execution.getVariableLocal("createResponseError");
    var createError = Boolean.parseBoolean(createResponseError.toString());

    var model = new KafkaExampleModel(UUID.randomUUID(), receiveMessageName.toString(), createError);
    log.info("businessKey: {}, sending request for task: {}", businessKey, taskName);

    execution.setVariableLocal(ProcessVariables.CORRELATION_ID, correlationId.toString());
    producer.sendExampleMessage(model, correlationId);
  }

}
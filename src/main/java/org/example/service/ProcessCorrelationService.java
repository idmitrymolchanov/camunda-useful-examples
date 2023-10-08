package org.example.service;

import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.MismatchingMessageCorrelationException;
import org.camunda.bpm.engine.RuntimeService;
import org.example.model.KafkaExampleModel;
import org.example.model.ProcessVariables;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessCorrelationService {

  private final RuntimeService runtimeService;

  @Transactional
  public void addVariableToProcess(UUID businessKey, Map<String, Object> variables) {
    var processInstanceId = findProcessInstanceId(businessKey);
    runtimeService.setVariables(processInstanceId, variables);
  }

  @Transactional
  public void correlateProcessByKey(UUID businessKey, String messageName) {
    try {
      runtimeService.createMessageCorrelation(messageName)
          .processInstanceBusinessKey(businessKey.toString())
          .correlate();
    } catch (MismatchingMessageCorrelationException e) {
      log.error("process instance with business key {} not found", businessKey);
    }
  }

  public void correlateProcessByCorrelationId(String correlationId, KafkaExampleModel model) {
    runtimeService.createMessageCorrelation(model.getMessageName())
        .localVariableEquals(ProcessVariables.CORRELATION_ID, correlationId)
        .setVariable(ProcessVariables.CREATE_ERROR, model.isCreateError())
        .correlate();
  }

  public void startProcess(String processId, UUID businessKey, Map<String, Object> variables) {
    runtimeService.startProcessInstanceByKey(processId, businessKey.toString(), variables);
    log.info("start process with businessKey: {}", businessKey);
  }


  private String findProcessInstanceId(UUID businessKey) {
    return runtimeService
        .createProcessInstanceQuery()
        .processInstanceBusinessKey(businessKey.toString())
        .singleResult()
        .getProcessInstanceId();
  }

}
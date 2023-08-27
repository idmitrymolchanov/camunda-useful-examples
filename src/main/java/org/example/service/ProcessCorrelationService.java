package org.example.service;

import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.MismatchingMessageCorrelationException;
import org.camunda.bpm.engine.RuntimeService;
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
  public void correlateProcess(UUID businessKey, String messageName) {
    try {
      runtimeService.createMessageCorrelation(messageName)
          .processInstanceBusinessKey(businessKey.toString())
          .correlate();
    } catch (MismatchingMessageCorrelationException e) {
      log.error("process instance with business key {} not found", businessKey);
    }
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
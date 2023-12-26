package org.example.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.MismatchingMessageCorrelationException;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.VariableInstance;
import org.example.controller.dto.ProcessIdEnum;
import org.example.model.KafkaExampleModel;
import org.example.model.ProcessVariables;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BpmnEngineServiceImpl implements BpmnEngineService {

  @Value("${spring.application.name}")
  private String applicationName;

  private final RuntimeService runtimeService;

  private static final String PROCESS_NOT_FOUNT_LOG_TEXT =
      "process instance for businessKey: {} not found";

  @Override
  public String startProcess(ProcessIdEnum processId, UUID businessKey, Map<String, Object> variables) {
    var id = runtimeService
        .startProcessInstanceByKey(processId.getValue(), businessKey.toString(), variables)
        .getProcessInstanceId();
    log.info("start process with businessKey: {}", businessKey);
    return id;
  }

  @Override
  @Transactional
  public void addVariableToProcess(UUID businessKey, Map<String, Object> variables) {
    var processInstancesList = findProcessInstanceIds(businessKey);
    if(!processInstancesList.isEmpty()) {
      var processId = processInstancesList.get(0).getProcessInstanceId();
      runtimeService.setVariables(processId, variables);
    }
    else {
      log.warn(PROCESS_NOT_FOUNT_LOG_TEXT, businessKey);
    }
  }

  @Override
  @Transactional
  public void correlateProcessByKey(UUID businessKey, String messageName) {
    try {
      runtimeService.createMessageCorrelation(messageName)
          .processInstanceBusinessKey(businessKey.toString())
          .correlate();
    } catch (MismatchingMessageCorrelationException e) {
      log.error(PROCESS_NOT_FOUNT_LOG_TEXT, businessKey);
    }
  }

  @Override
  @Transactional
  public void correlateProcessByKeyWithVariables(String businessKey, String messageName, Map<String, Object> variables) {
    try {
      runtimeService
          .createMessageCorrelation(messageName)
          .processInstanceBusinessKey(businessKey)
          .setVariables(variables)
          .correlate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void correlateProcessByBusinessKey(String businessKey, KafkaExampleModel model) {
    runtimeService.createMessageCorrelation(model.getMessageName())
        .processInstanceBusinessKey(businessKey)
        .setVariable(ProcessVariables.CREATE_ERROR, model.isCreateError())
        .correlate();
  }

  @Override
  public VariableInstance getVariableFromMainProcess(String businessKey, String variable) {
    var processInstanceId = getProcessInstanceIdByBusinessKey(businessKey);
    log.debug("applicationId: {}, processInstanceId: {}", businessKey, processInstanceId);

    return runtimeService
        .createVariableInstanceQuery()
        .processInstanceIdIn(processInstanceId)
        .variableName(variable)
        .singleResult();
  }

  @Override
  public String getProcessInstanceIdByBusinessKey(String businessKey) {
    return runtimeService
        .createProcessInstanceQuery()
        .processDefinitionKey(applicationName)
        .processInstanceBusinessKey(businessKey)
        .active()
        .list()
        .get(0)
        .getId();
  }

  private List<ProcessInstance> findProcessInstanceIds(UUID businessKey) {
    return runtimeService
        .createProcessInstanceQuery()
        .processInstanceBusinessKey(businessKey.toString())
        .list();
  }

}
package org.example.service;

import java.util.Map;
import java.util.UUID;
import org.camunda.bpm.engine.runtime.VariableInstance;
import org.example.controller.dto.ProcessIdEnum;
import org.example.model.KafkaExampleModel;

public interface BpmnEngineService {

  String startProcess(ProcessIdEnum processId, UUID businessKey, Map<String, Object> variables);

  void addVariableToProcess(UUID businessKey, Map<String, Object> variables);

  void correlateProcessByKey(UUID businessKey, String messageName);

  void correlateProcessByKeyWithVariables(String businessKey, String messageName, Map<String, Object> variables);

  void correlateProcessByBusinessKey(String businessKey, KafkaExampleModel model);

  VariableInstance getVariableFromMainProcess(String businessKey, String variable);

  String getProcessInstanceIdByBusinessKey(String businessKey);

}

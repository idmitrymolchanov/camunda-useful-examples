package org.example.bpmn.process_element_example.delegate;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.example.model.ProcessVariables;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component("SendMessageToParentDelegate")
@RequiredArgsConstructor
public class SendMessageToParentDelegate implements JavaDelegate {

  @Value("${spring.application.name}")
  private String applicationName;

  private final RuntimeService runtimeService;

  private static final String RECEIVE_MESSAGE = "xx";

  @Override
  public void execute(DelegateExecution execution) throws Exception {
    var businessKey = execution.getBusinessKey();
    log.info("businessKey: {}; sending request to main process: OK", businessKey);

    var mainProcessExampleVar = getBarCodeVariableFromMainProcess(businessKey);
    log.info("businessKey: {}, found variable: {}", businessKey, mainProcessExampleVar);

    correlateMessageToMainProcess(execution.getProcessBusinessKey());
  }

  private Boolean getBarCodeVariableFromMainProcess(String businessKey) {
    var processInstanceId = getMainProcessInstanceId(businessKey);
    log.debug("applicationId: {}, processInstanceId: {}", businessKey, processInstanceId);

    var barCodeVariable = runtimeService
        .createVariableInstanceQuery()
        .processInstanceIdIn(processInstanceId)
        .variableName(ProcessVariables.MAIN_PROCESS_EXAMPLE_VAR)
        .singleResult();

    return (Boolean) barCodeVariable.getValue();
  }

  private String getMainProcessInstanceId(String businessKey) {
    return runtimeService
        .createProcessInstanceQuery()
        .processDefinitionKey(applicationName)
        .processInstanceBusinessKey(businessKey)
        .active()
        .list()
        .get(0)
        .getId();
  }

  private void correlateMessageToMainProcess(String processBusinessKey) {
    try {
      runtimeService
          .createMessageCorrelation(RECEIVE_MESSAGE)
          .processInstanceBusinessKey(processBusinessKey)
          .setVariables(Map.of(ProcessVariables.CORRELATE_DECISION, "OK"))
          .correlate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
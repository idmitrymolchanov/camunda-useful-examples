package org.example.bpmn.all_elements_example_pa.delegate;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.example.model.ProcessVariables;
import org.example.service.BpmnEngineService;
import org.springframework.stereotype.Component;

@Slf4j
@Component("SendMessageToParentDelegate")
@RequiredArgsConstructor
public class SendMessageToParentDelegate implements JavaDelegate {

  private final BpmnEngineService bpmnService;

  private static final String RECEIVE_MESSAGE = "WaitCorrelationFromSubProcessMessageTask";

  @Override
  public void execute(DelegateExecution execution) throws Exception {
    var businessKey = execution.getBusinessKey();
    log.info("businessKey: {}; sending request to main process: OK", businessKey);

    var mainProcessExampleVar = bpmnService.getVariableFromMainProcess(
        businessKey,
        ProcessVariables.MAIN_PROCESS_EXAMPLE_VAR
    ).getValue();
    log.info("businessKey: {}, found variable: {}", businessKey, mainProcessExampleVar);

    bpmnService.correlateProcessByKeyWithVariables(
        businessKey,
        RECEIVE_MESSAGE,
        Map.of(ProcessVariables.VARIABLE_FROM_SUB_PROCESS, "OK")
    );
  }

}
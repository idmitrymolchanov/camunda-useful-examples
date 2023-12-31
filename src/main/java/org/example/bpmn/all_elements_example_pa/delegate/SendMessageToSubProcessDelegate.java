package org.example.bpmn.all_elements_example_pa.delegate;

import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.example.controller.dto.ProcessIdEnum;
import org.example.service.BpmnEngineService;
import org.springframework.stereotype.Component;

@Slf4j
@Component("SendMessageToSubProcessDelegate")
@RequiredArgsConstructor
public class SendMessageToSubProcessDelegate implements JavaDelegate {

  private final BpmnEngineService bpmnService;

  @Override
  public void execute(DelegateExecution execution) throws Exception {
    var businessKey = UUID.fromString(execution.getBusinessKey());
    var processName = ProcessIdEnum.ALL_ELEMENTS_EXAMPLE_EVENT_SUB_PA;
    var id = bpmnService.startProcess(processName, businessKey, Map.of());
    log.info("subprocess {} started with processId: {} and businessKey: {}", processName, id, businessKey);
  }

}
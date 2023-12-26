package org.example.controller;

import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.example.controller.dto.ProcessIdEnum;
import org.example.service.BpmnEngineService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProcessControlController {

  private final BpmnEngineService correlationService;

  @PostMapping(value = "/add-variable")
  public void addVariablesToCurrentProcessInstance(
      @RequestParam UUID businessKey,
      @RequestBody Map<String, Object> variables) {
    correlationService.addVariableToProcess(businessKey, variables);
  }

  @PostMapping(value = "/correlate")
  public void correlateMessageInProcessByBusinessKey(
      @RequestParam UUID businessKey,
      @RequestParam String messageName) {
    correlationService.correlateProcessByKey(businessKey, messageName);
  }

  @PostMapping(value = "/start", consumes = {MediaType.APPLICATION_JSON_VALUE})
  public String startProcessByBusinessKeyAndProcessId(
      @RequestParam ProcessIdEnum processId,
      @RequestParam UUID businessKey,
      @RequestBody Map<String, Object> variables) {
    return correlationService.startProcess(processId, businessKey, variables);
  }

}
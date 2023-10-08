package org.example.controller;

import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.example.service.ProcessCorrelationService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProcessControlController {

  private final ProcessCorrelationService correlationService;

  @PostMapping(value = "/add-variable")
  public void createVariable(
      @RequestParam UUID businessKey,
      @RequestBody Map<String, Object> variables) {
    correlationService.addVariableToProcess(businessKey, variables);
  }

  @PostMapping(value = "/correlate")
  public void correlateByBusinessKey(
      @RequestParam UUID businessKey,
      @RequestParam String messageName) {
    correlationService.correlateProcessByKey(businessKey, messageName);
  }

  @PostMapping(value = "/start/{processId}")
  public void startByBusinessKey(
      @PathVariable String processId,
      @RequestParam UUID businessKey,
      @RequestBody Map<String, Object> variables) {
    correlationService.startProcess(processId, businessKey, variables);
  }

}
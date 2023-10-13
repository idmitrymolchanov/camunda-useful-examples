package org.example.bpmn.external_task_example.external;

import java.util.Collections;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.example.model.ProcessVariables;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@ExternalTaskSubscription(
    topicName = "perform_task",
    processDefinitionKey = "external-task-example",
    variableNames = {"exampleInputVariable"}
)
public class PerformActionExternalTask implements ExternalTaskHandler {

  @Override
  public void execute(ExternalTask externalTask, ExternalTaskService taskService) {
    var isException = (Math.random() <= 0.5);
    var businessKey = externalTask.getBusinessKey();
    var exampleInputVariable = externalTask.getVariable(ProcessVariables.EXAMPLE_INPUT_VARIABLE);
    log.info("businessKey: {}, exampleInputVariable: {}", businessKey, exampleInputVariable.toString());

    if(!isException) {
      taskService.complete(externalTask, Collections.singletonMap(ProcessVariables.IS_PROCESS_EXCEPTION, false));
    }
    else {
      log.error("exception in process with businessKey: {}", businessKey);
      taskService.complete(externalTask, Map.of(
          ProcessVariables.REJECT_REASON, "TECH_ERROR",
          ProcessVariables.IS_PROCESS_EXCEPTION, true)
      );
    }
  }

}
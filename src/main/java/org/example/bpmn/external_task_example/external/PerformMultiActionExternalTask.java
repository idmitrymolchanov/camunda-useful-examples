package org.example.bpmn.external_task_example.external;

import java.util.Collections;
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
    topicName = "perform_multi_task",
    processDefinitionKey = "external-task-example",
    variableNames = {"exampleInputVariable", "actionFromList"}
)
public class PerformMultiActionExternalTask implements ExternalTaskHandler {

  @Override
  public void execute(ExternalTask externalTask, ExternalTaskService taskService) {
    var businessKey = externalTask.getBusinessKey();
    var actionFromList = externalTask.getVariable(ProcessVariables.ACTION_FROM_LIST);
    log.info("businessKey: {}, actionFromList: {}", businessKey, actionFromList.toString());

    taskService.complete(externalTask, Collections.singletonMap(ProcessVariables.IS_PROCESS_EXCEPTION, false));
  }

}
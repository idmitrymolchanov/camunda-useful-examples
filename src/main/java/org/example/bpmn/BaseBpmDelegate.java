package org.example.bpmn;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.example.bpmn.utils.BpmLogActivityTypeEnum;
import org.example.exception.BpmActivityException;

@Slf4j
public abstract class BaseBpmDelegate implements JavaDelegate {

  @Override
  public void execute(DelegateExecution execution) {
    logDelegateActivity(BpmLogActivityTypeEnum.START.toString(), execution);
    try {
      perform(execution);
    } catch (Exception e) {
      log.error("businessKey: {}", execution.getBusinessKey());
      log.error("error message: {}", e.getMessage());
      throw new BpmActivityException(e.getMessage(), execution.getBusinessKey(), execution.getCurrentActivityId());
    }
    logDelegateActivity(BpmLogActivityTypeEnum.COMPLETE.toString(), execution);
  }

  public abstract void perform(DelegateExecution delegateExecution);

  private void logDelegateActivity(String activityAction, DelegateExecution execution) {
    log.debug("delegate {} execute: businessKey={}, processInstanceId={}, activityId={}, activityInstanceId={}, processDefinitionId={}",
        activityAction,
        execution.getBusinessKey(),
        execution.getProcessInstanceId(),
        execution.getCurrentActivityId(),
        execution.getActivityInstanceId(),
        execution.getProcessDefinitionId());
  }

}
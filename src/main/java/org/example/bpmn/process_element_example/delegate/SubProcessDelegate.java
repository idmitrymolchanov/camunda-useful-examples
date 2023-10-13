package org.example.bpmn.process_element_example.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("SubProcessDelegate")
public class SubProcessDelegate implements JavaDelegate {

  @Override
  public void execute(DelegateExecution delegateExecution) {

  }

}
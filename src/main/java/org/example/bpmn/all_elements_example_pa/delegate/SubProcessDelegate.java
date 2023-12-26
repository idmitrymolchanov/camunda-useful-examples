package org.example.bpmn.all_elements_example_pa.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("SubProcessDelegate")
public class SubProcessDelegate implements JavaDelegate {

  @Override
  public void execute(DelegateExecution delegateExecution) {

  }

}
package org.example.bpmn.process_element_example;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("ExampleDelegate")
public class ExampleDelegate implements JavaDelegate {

  @Override
  public void execute(DelegateExecution delegateExecution) {

  }

}
package org.example.bpmn.all_elements_example_pa.delegate;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Slf4j
@Component("ProcessActionsWithCollectionDelegate")
public class ProcessActionsWithCollectionDelegate implements JavaDelegate {

  @Override
  public void execute(DelegateExecution execution) throws Exception {
    var collectionItem = execution.getVariable("item").toString();
    var businessKey = execution.getBusinessKey();

    log.info("got collection's intem: {}, businessKey: {}", collectionItem, businessKey);
  }

}
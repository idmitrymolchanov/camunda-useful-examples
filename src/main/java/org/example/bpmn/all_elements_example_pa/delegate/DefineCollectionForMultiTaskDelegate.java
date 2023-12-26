package org.example.bpmn.all_elements_example_pa.delegate;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.example.model.ProcessVariables;
import org.springframework.stereotype.Component;

@Slf4j
@Component("DefineCollectionForMultiTaskDelegate")
public class DefineCollectionForMultiTaskDelegate implements JavaDelegate {

  @Override
  public void execute(DelegateExecution execution) throws Exception {
    List<String> exampleCollection = List.of(
        "first-string-item",
        "second-string-item",
        "third-string-item"
    );

    execution.setVariable(ProcessVariables.EXAMPLE_COLLECTION, exampleCollection);
    log.info("set exampleCollection to process: {}", execution.getBusinessKey());
  }

}
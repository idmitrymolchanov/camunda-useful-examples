# camunda-useful-examples
get started + useful non-standard schemes

## 1 - Example of a process that performs an action without interrupting the parent process

![plot](./readme_images/event-sub-process-example.png)


## 2 - Example of a process that uses external tasks

![plot](./readme_images/external-task-example.png)


## 3 - Example of a base process with common bpm elements

- start curl example


      curl --location 'http://localhost:8081/start?processId=PROCESS_ELEMENT_EXAMPLE&businessKey=f9a63a48-9e65-11ee-83f7-3aa12c99ecd2' \
      --header 'Content-Type: application/json' \
      --data '{
      "var1" : "val1"
      }'

- correlate message curl example


      curl --location 'http://localhost:8081/correlate?businessKey=f9a63a48-9e65-11ee-83f7-3aa12c99ecd2&messageName=WaitExampleCorrelationMessageTask' \
      --header 'Content-Type: application/json' \
      --data '{
      "var1" : "val1"
      }'

- schema

![plot](./readme_images/process-element-example.png)

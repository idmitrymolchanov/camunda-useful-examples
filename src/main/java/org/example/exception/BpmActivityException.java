package org.example.exception;

public class BpmActivityException extends RuntimeException {

  public BpmActivityException(String exMessage, String businessKey, String activityId) {
    super(String
        .format("delegate/listener execute with exception %s, businessKey: %s, activityId: %s",
            exMessage, businessKey, activityId
        )
    );
  }

}
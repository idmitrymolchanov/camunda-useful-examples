package org.example.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KafkaExampleModel {

  private UUID id;
  private String messageName;
  private boolean createError;

}
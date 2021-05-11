package com.epam.asmt.ingest.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ServiceError {
  int errorCode;
  String errorMessage;
}

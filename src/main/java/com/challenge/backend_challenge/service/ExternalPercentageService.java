package com.challenge.backend_challenge.service;

import com.challenge.backend_challenge.enums.ErrorMessage;
import com.challenge.backend_challenge.exception.ExternalServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ExternalPercentageService {

  private static final Logger logger = LoggerFactory.getLogger(ExternalPercentageService.class);
  private static boolean shouldFail = false;

  public double getPercentage() {
    if (shouldFail) {
      logger.error("Forcing external service failure (controlled service)");
      throw new ExternalServiceException(ErrorMessage.EXTERNAL_SERVICE_SIMULATION);
    }
    return 20.0;
  }

  public void setShouldFail(boolean fail) {
    shouldFail = fail;
  }

}

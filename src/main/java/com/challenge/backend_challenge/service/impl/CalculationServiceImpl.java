package com.challenge.backend_challenge.service.impl;

import com.challenge.backend_challenge.enums.ErrorMessage;
import com.challenge.backend_challenge.exception.CacheException;
import com.challenge.backend_challenge.exception.CacheNotFoundException;
import com.challenge.backend_challenge.exception.ExternalServiceException;
import com.challenge.backend_challenge.model.dto.CalculationRequest;
import com.challenge.backend_challenge.model.dto.CalculationResponse;
import com.challenge.backend_challenge.service.CalculationService;
import com.challenge.backend_challenge.service.ExternalPercentageService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class CalculationServiceImpl implements CalculationService {

  private static final Logger logger = LoggerFactory.getLogger(CalculationServiceImpl.class);

  private final ExternalPercentageService externalPercentageService;
  private final CacheManager cacheManager;
  private static final String CACHE_NAME = "percentage";
  private static final String PERCENTAGE_KEY = "percentageValue";

  @Override
  public CalculationResponse calculate(CalculationRequest request) {
    double num1 = request.getNum1();
    double num2 = request.getNum2();
    double sum = num1 + num2;
    double percentage = getPercentageFromExternalService();
    double result = sum + (sum * (percentage / 100));

    CalculationResponse response = new CalculationResponse();
    response.setNum1(num1);
    response.setNum2(num2);
    response.setSum(sum);
    response.setPercentage(percentage);
    response.setResult(result);

    return response;
  }

  @Override
  public Double getCachedPercentage() {
    Cache cache = cacheManager.getCache(CACHE_NAME);
    if (cache != null) {
      return cache.get(PERCENTAGE_KEY, Double.class);
    }
    logger.warn("Cache is not available");
    throw new CacheException(ErrorMessage.CACHE_UNAVAILABLE);
  }

  public double getPercentageFromExternalService() {
    Cache cache = cacheManager.getCache("percentage");

    try {
      double percentage = externalPercentageService.getPercentage();
      logger.info("Percentage obtained from an external service: {}", percentage);

      if (cache != null) {
        cache.put("percentageValue", percentage);
        logger.info("Percentage stored in cache: {}", percentage);
      }

      return percentage;
    } catch (ExternalServiceException e) {
      if (cache != null) {
        Double cachedPercentage = cache.get(PERCENTAGE_KEY, Double.class);
        if (cachedPercentage != null) {
          logger.info("Using cache percentage after failure: {}", cachedPercentage);
          return cachedPercentage;
        }
      }

      throw new CacheNotFoundException(e);
    } catch (Exception e) {
      if (cache != null) {
        Double cachedPercentage = cache.get(PERCENTAGE_KEY, Double.class);
        if (cachedPercentage != null) {
          logger.info("Using cache percentage after failure: {}", cachedPercentage);
          return cachedPercentage;
        }
      }

      throw new CacheException(ErrorMessage.PERCENTAGE_NOT_FOUND, e);
    }
  }
}

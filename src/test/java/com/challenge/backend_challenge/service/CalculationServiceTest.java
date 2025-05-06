package com.challenge.backend_challenge.service;

import com.challenge.backend_challenge.model.dto.CalculationRequest;
import com.challenge.backend_challenge.model.dto.CalculationResponse;
import com.challenge.backend_challenge.service.impl.CalculationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class CalculationServiceTest {

  @Mock private ExternalPercentageService externalPercentageService;

  @Mock private CacheManager cacheManager;

  @Mock private Cache cache;

  @InjectMocks private CalculationServiceImpl calculationService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    when(cacheManager.getCache("percentage")).thenReturn(cache);
  }

  @Test
  void givenWorkingExternalService_whenCalculate_thenReturnCorrectResult() {
    CalculationRequest request = new CalculationRequest();
    request.setNum1(100.0);
    request.setNum2(50.0);

    when(externalPercentageService.getPercentage()).thenReturn(10.0);

    CalculationResponse response = calculationService.calculate(request);

    assertEquals(100.0, response.getNum1());
    assertEquals(50.0, response.getNum2());
    assertEquals(150.0, response.getSum());
    assertEquals(10.0, response.getPercentage());
    assertEquals(165.0, response.getResult());

    verify(externalPercentageService).getPercentage();

    verify(cache).put(eq("percentageValue"), eq(10.0));
  }

  @Test
  void givenFailingExternalServiceWithCachedValue_whenCalculate_thenUseCachedValue() {
    CalculationRequest request = new CalculationRequest();
    request.setNum1(100.0);
    request.setNum2(50.0);

    when(externalPercentageService.getPercentage())
        .thenThrow(new RuntimeException("External service failure"));

    when(cache.get(eq("percentageValue"), eq(Double.class))).thenReturn(20.0);

    CalculationResponse response = calculationService.calculate(request);

    assertEquals(100.0, response.getNum1());
    assertEquals(50.0, response.getNum2());
    assertEquals(150.0, response.getSum());
    assertEquals(20.0, response.getPercentage());
    assertEquals(180.0, response.getResult());

    verify(externalPercentageService).getPercentage();

    verify(cache).get(eq("percentageValue"), eq(Double.class));
  }

  @Test
  void givenFailingExternalServiceWithoutCachedValue_whenCalculate_thenThrowException() {
    CalculationRequest request = new CalculationRequest();
    request.setNum1(100.0);
    request.setNum2(50.0);

    when(externalPercentageService.getPercentage())
        .thenThrow(new RuntimeException("External service failure"));

    when(cache.get(eq("percentageValue"), eq(Double.class))).thenReturn(null);

    RuntimeException exception =
        assertThrows(
            RuntimeException.class,
            () -> {
              calculationService.calculate(request);
            });

    assertTrue(exception.getMessage().contains("Could not get the percentage"));

    verify(externalPercentageService).getPercentage();

    verify(cache).get(eq("percentageValue"), eq(Double.class));
  }

  @Test
  void givenCacheRequest_whenGetCachedPercentage_thenReturnCachedValue() {
    when(cache.get(eq("percentageValue"), eq(Double.class))).thenReturn(15.0);

    Double cachedValue = calculationService.getCachedPercentage();

    assertEquals(15.0, cachedValue);

    verify(cache).get(eq("percentageValue"), eq(Double.class));
  }
}

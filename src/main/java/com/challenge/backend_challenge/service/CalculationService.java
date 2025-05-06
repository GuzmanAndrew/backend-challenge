package com.challenge.backend_challenge.service;

import com.challenge.backend_challenge.model.dto.CalculationRequest;
import com.challenge.backend_challenge.model.dto.CalculationResponse;

public interface CalculationService {
    CalculationResponse calculate(CalculationRequest request);
    Double getCachedPercentage();
}

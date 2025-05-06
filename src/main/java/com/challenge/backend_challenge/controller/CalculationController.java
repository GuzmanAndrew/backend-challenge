package com.challenge.backend_challenge.controller;

import com.challenge.backend_challenge.model.dto.CalculationRequest;
import com.challenge.backend_challenge.model.dto.CalculationResponse;
import com.challenge.backend_challenge.service.CalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/calculate")
@RequiredArgsConstructor
public class CalculationController {

  private final CalculationService calculationService;

  @PostMapping
  public ResponseEntity<CalculationResponse> calculate(@RequestBody CalculationRequest request) {
    CalculationResponse response = calculationService.calculate(request);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/cache/percentage")
  public ResponseEntity<Map<String, Object>> getCachedPercentage() {
    Double percentage = calculationService.getCachedPercentage();

    Map<String, Object> response = new HashMap<>();
    response.put("cached", percentage != null);
    response.put("value", percentage);
    response.put("timestamp", LocalDateTime.now());

    return ResponseEntity.ok(response);
  }
}

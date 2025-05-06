package com.challenge.backend_challenge.controller;

import com.challenge.backend_challenge.service.ExternalPercentageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

  private final ExternalPercentageService controlledService;

  @PostMapping("/external-service/fail")
  public ResponseEntity<Map<String, Object>> setExternalServiceFailure(
      @RequestParam boolean shouldFail) {
    controlledService.setShouldFail(shouldFail);

    Map<String, Object> response = new HashMap<>();
    response.put(
        "message",
        "External service configured for " + (shouldFail ? "fail" : "work properly"));
    response.put("shouldFail", shouldFail);

    return ResponseEntity.ok(response);
  }
}

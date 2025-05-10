package com.challenge.backend_challenge.service.impl;

import com.challenge.backend_challenge.enums.ErrorMessage;
import com.challenge.backend_challenge.exception.RepositoryException;
import com.challenge.backend_challenge.exception.ValidationException;
import com.challenge.backend_challenge.model.entity.CallHistory;
import com.challenge.backend_challenge.repository.CallHistoryRepository;
import com.challenge.backend_challenge.service.CallHistoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CallHistoryServiceImpl implements CallHistoryService {

  private static final Logger logger = LoggerFactory.getLogger(CallHistoryServiceImpl.class);
  private final CallHistoryRepository callHistoryRepository;

  @Async("taskExecutor")
  @Override
  public void saveCallAsync(String endpoint, String parameters, String response, boolean isError) {
    try {
      CallHistory callHistory = new CallHistory();
      callHistory.setCallDate(LocalDateTime.now());
      callHistory.setEndpoint(endpoint);
      callHistory.setParameters(parameters);
      callHistory.setResponse(response);
      callHistory.setError(isError);

      callHistoryRepository.save(callHistory);
      logger.info("Call history saved successfully");
    } catch (Exception e) {
      logger.error("Error saving call history", e);
      throw new RepositoryException(ErrorMessage.SAVE_ENTITY_ERROR, e);
    }
  }

  @Override
  public Page<CallHistory> getCallHistory(int page, int size) {
    try {
      if (page < 0 || size <= 0 || size > 20) {
        throw new ValidationException(ErrorMessage.INVALID_PAGE_PARAMS);
      }

      Pageable pageable = PageRequest.of(page, size);
      return callHistoryRepository.findAllByOrderByCallDateDesc(pageable);
    } catch (ValidationException e) {
      throw e;
    } catch (Exception e) {
      logger.error("Error retrieving call history", e);
      throw new RepositoryException(ErrorMessage.ENTITY_NOT_FOUND, e);
    }
  }
}

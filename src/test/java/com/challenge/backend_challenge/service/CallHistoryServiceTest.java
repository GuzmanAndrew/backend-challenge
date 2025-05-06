package com.challenge.backend_challenge.service;

import com.challenge.backend_challenge.model.entity.CallHistory;
import com.challenge.backend_challenge.repository.CallHistoryRepository;
import com.challenge.backend_challenge.service.impl.CallHistoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CallHistoryServiceTest {

  @Mock private CallHistoryRepository callHistoryRepository;

  @InjectMocks private CallHistoryServiceImpl callHistoryService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void givenApiCall_whenSaveCallAsync_thenStoreInDatabase() {
    String endpoint = "/api/calculate";
    String parameters = "{\"num1\":100,\"num2\":50}";
    String response = "{\"result\":165}";
    boolean isError = false;

    callHistoryService.saveCallAsync(endpoint, parameters, response, isError);

    verify(callHistoryRepository, timeout(1000).times(1)).save(any(CallHistory.class));
  }

  @Test
  void givenHistoryData_whenGetCallHistory_thenReturnPaginatedResults() {
    int page = 0;
    int size = 10;

    List<CallHistory> historyList = new ArrayList<>();
    CallHistory history1 = new CallHistory();
    history1.setId(1L);
    history1.setEndpoint("/api/calculate");
    historyList.add(history1);

    Page<CallHistory> historyPage = new PageImpl<>(historyList);

    when(callHistoryRepository.findAllByOrderByCallDateDesc(any(Pageable.class)))
        .thenReturn(historyPage);

    Page<CallHistory> result = callHistoryService.getCallHistory(page, size);

    assertNotNull(result);
    assertEquals(1, result.getContent().size());
    assertEquals("/api/calculate", result.getContent().get(0).getEndpoint());

    verify(callHistoryRepository).findAllByOrderByCallDateDesc(PageRequest.of(page, size));
  }
}

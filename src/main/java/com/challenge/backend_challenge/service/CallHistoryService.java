package com.challenge.backend_challenge.service;

import com.challenge.backend_challenge.model.entity.CallHistory;
import org.springframework.data.domain.Page;

public interface CallHistoryService {

    void saveCallAsync(String endpoint, String parameters, String response, boolean isError);
    Page<CallHistory> getCallHistory(int page, int size);
}

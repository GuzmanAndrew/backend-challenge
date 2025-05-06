package com.challenge.backend_challenge.controller;

import com.challenge.backend_challenge.model.dto.PageResponse;
import com.challenge.backend_challenge.model.entity.CallHistory;
import com.challenge.backend_challenge.service.CallHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class CallHistoryController {

    private final CallHistoryService callHistoryService;

    @GetMapping
    public ResponseEntity<PageResponse<CallHistory>> getCallHistory(
            @RequestParam int page,
            @RequestParam int size) {

        Page<CallHistory> historyPage = callHistoryService.getCallHistory(page, size);
        return ResponseEntity.ok(new PageResponse<>(historyPage));
    }
}

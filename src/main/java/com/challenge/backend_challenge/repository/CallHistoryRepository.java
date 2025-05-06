package com.challenge.backend_challenge.repository;

import com.challenge.backend_challenge.model.entity.CallHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CallHistoryRepository extends JpaRepository<CallHistory, Long> {
    Page<CallHistory> findAllByOrderByCallDateDesc(Pageable pageable);
}

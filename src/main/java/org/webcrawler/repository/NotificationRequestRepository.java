package org.webcrawler.repository;

import org.springframework.data.repository.CrudRepository;
import org.webcrawler.model.NotificationRequest;

import java.util.List;

public interface NotificationRequestRepository extends CrudRepository<NotificationRequest, Long> {

    List<NotificationRequest> findByStatus(String status);
}
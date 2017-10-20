package org.webcrawler.repository;

import org.springframework.data.repository.CrudRepository;
import org.webcrawler.model.NotificationRequest;

public interface NotificationRequestRepository extends CrudRepository<NotificationRequest, Long> {

}
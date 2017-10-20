package org.webcrawler.repository;

import org.springframework.data.repository.CrudRepository;
import org.webcrawler.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

    User findFirstByEmail(String email);

}
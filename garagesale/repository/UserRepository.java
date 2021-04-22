package com.endava.garagesale.repository;

import com.endava.garagesale.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Operations on users in repository
 * This interface helps with already implemented methods from CrudRepository
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}

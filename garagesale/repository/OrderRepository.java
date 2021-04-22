package com.endava.garagesale.repository;

import com.endava.garagesale.entity.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Operations on orders in repository
 * This interface helps with already implemented methods from CrudRepository
 */
@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
}

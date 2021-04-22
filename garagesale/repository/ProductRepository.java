package com.endava.garagesale.repository;

import com.endava.garagesale.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Operations on assets in repository
 * This interface helps with already implemented methods from CrudRepository
 */
@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
}

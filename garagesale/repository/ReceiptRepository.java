package com.endava.garagesale.repository;

import com.endava.garagesale.entity.Receipt;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Operations on receipts in repository
 * This interface helps with already implemented methods from CrudRepository
 */
@Repository
public interface ReceiptRepository extends CrudRepository<Receipt, Long> {
}

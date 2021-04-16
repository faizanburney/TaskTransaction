package com.n26.service;

import com.n26.domainobject.Transaction;
import com.n26.exception.FutureTransactionException;
import com.n26.exception.OldTransactionException;
import com.n26.datatransferobject.TransactionDto;
import com.n26.repository.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class AddTransaction {

  private final TransactionRepository transactionRepository;

  public AddTransaction(TransactionRepository transactionRepository) {
    this.transactionRepository = transactionRepository;
  }

  public void execute(TransactionDto transactionDto) {
    final Transaction transaction = new Transaction(transactionDto);
    if(transaction.isOldTransaction())
      throw new OldTransactionException("Transaction time expired");
    validateTransactionDates(transaction);
    transactionRepository.add(transaction);
  }

  private void validateTransactionDates(Transaction transaction)
  {

    if (transaction.isFutureTransaction())
    {
      throw new FutureTransactionException("Transaction time is in future");
    }
  }
}

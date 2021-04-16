package com.n26.service;

import com.n26.datatransferobject.TransactionDto;
import com.n26.domainobject.Statistics;
import com.n26.domainobject.Transaction;
import com.n26.exception.FutureTransactionException;
import com.n26.exception.OldTransactionException;
import com.n26.repository.TransactionRepository;
import java.time.Instant;
import java.time.ZoneOffset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TransactionService
{

    private final TransactionRepository transactionRepository;

    @Autowired
    Statistics currentStatistics;

    public TransactionService(TransactionRepository transactionRepository)
    {
        this.transactionRepository = transactionRepository;
    }


    public void add(TransactionDto transactionDto)
    {
        final Transaction transaction = new Transaction(transactionDto);
        validateTransactionDates(transaction);
        transactionRepository.add(transaction);
        currentStatistics.updateStatistics(transactionRepository.getAll());
    }


    private void validateTransactionDates(Transaction transaction)
    {
        if (transaction.isOldTransaction())
        {
            throw new OldTransactionException("Transaction time expired");
        }
        if (transaction.isFutureTransaction())
        {
            throw new FutureTransactionException("Transaction time is in future");
        }
    }


    public void delete()
    {
        transactionRepository.deleteAll();
        currentStatistics.updateStatistics(transactionRepository.getAll());
    }

    /**
     * Removes older transactions for every seconds.
     */
    @Scheduled(fixedDelay = 1 * 1000)
    public void removeOlderTransaction()
    {
        transactionRepository.removeExpiredTransaction();
        currentStatistics.updateStatistics(transactionRepository.getAll());
    }
}

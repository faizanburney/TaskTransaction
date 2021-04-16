package com.n26.repository;

import com.n26.domainobject.Statistics;
import com.n26.domainobject.Transaction;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionRepository
{

    private AtomicInteger atomicInteger = new AtomicInteger(0);
    private ConcurrentHashMap<Integer, Transaction> transactions = new ConcurrentHashMap<>();



    public void add(final Transaction transaction)
    {
        transactions.put(atomicInteger.incrementAndGet(), transaction);
    }


    public Collection<Transaction> getAll()
    {
        return transactions.values();
    }


    public void deleteAll()
    {
        transactions.clear();

    }


    /**
     * Removes expired transactions
     */
    public void removeExpiredTransaction()
    {
        Long expDate = Instant.now().getEpochSecond() - 59;
        transactions.entrySet().removeIf(e -> e.getValue().getTime().toEpochSecond(ZoneOffset.UTC) <= expDate);
    }
}

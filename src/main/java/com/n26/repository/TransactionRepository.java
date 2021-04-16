package com.n26.repository;

import com.n26.domainobject.Statistics;
import com.n26.domainobject.Transaction;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionRepository {

  private AtomicInteger atomicInteger = new AtomicInteger(0);
  private ConcurrentHashMap<Integer, Transaction> transactions = new ConcurrentHashMap<>();
  private Statistics currentStatistics;

  public void add(final Transaction transaction) {
    transactions.put(atomicInteger.incrementAndGet(), transaction);
    currentStatistics = new Statistics(getAll());
  }


  public Statistics getCurrentStatistics()
  {
    return currentStatistics;
  }


  public Collection<Transaction> getAll() {
    return transactions.values();
  }

  public void deleteAll() {
    transactions.clear();
    currentStatistics = new Statistics(getAll());
  }

  /**
   * Removes older transactions for every seconds.
   */
  @Scheduled(fixedDelay = 1 * 1000)
  public void removeOlderTransaction() {
    Long expDate = Instant.now().getEpochSecond() - 59;
    transactions.entrySet().removeIf(e -> e.getValue().getTime().toEpochSecond(ZoneOffset.UTC) <= expDate);
    currentStatistics = new Statistics(getAll());
  }
}

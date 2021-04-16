package com.n26.domainobject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.n26.datatransferobject.SummaryStatisticsDto;
import com.n26.domainobject.Statistics;
import com.n26.domainobject.Transaction;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class StatisticsTest {

  @Test
  public void shouldReturnStatistics_whenAskedForResults() {
    Statistics statistics = new Statistics();
    final SummaryStatisticsDto summaryStatistics = statistics.getSummaryStatistics();
    assertNotNull(summaryStatistics);
    assertEquals(summaryStatistics.getCount(), 0L);
  }

  @Test
  public void shouldReturnStatisticsForLastSixtySeconds_whenAskedForResults() {
    final List<Transaction> transactionList = createTransactionList();
    transactionList.addAll(createTransactionListOlderThanNow());
    final Statistics statistics = new Statistics();
    statistics.updateStatistics(transactionList);
    final SummaryStatisticsDto summaryStatistics = statistics.getSummaryStatistics();
    assertNotNull(summaryStatistics);
    assertEquals(summaryStatistics.getCount(), 3L);
    assertEquals(summaryStatistics.getSum(), "20.00");
    assertEquals(summaryStatistics.getMax(), "10.00");
    assertEquals(summaryStatistics.getMin(), "5.00");
    assertEquals(summaryStatistics.getAvg(), "6.67");
  }

  private List<Transaction> createTransactionList() {
    List<Transaction> transactions = new LinkedList<>();
    transactions.add(new Transaction(new BigDecimal(10.00), LocalDateTime.now(ZoneOffset.UTC)));
    transactions.add(new Transaction(new BigDecimal(5), LocalDateTime.now(ZoneOffset.UTC)));
    transactions.add(new Transaction(new BigDecimal(5.0), LocalDateTime.now(ZoneOffset.UTC)));
    return transactions;
  }

  private List<Transaction> createTransactionListOlderThanNow() {
    List<Transaction> transactions = new LinkedList<>();
    transactions.add(new Transaction(new BigDecimal(10.00), LocalDateTime.now(ZoneOffset.UTC).minusMinutes(2)));
    transactions.add(new Transaction(new BigDecimal(5), LocalDateTime.now(ZoneOffset.UTC).minusMinutes(2)));
    transactions.add(new Transaction(new BigDecimal(5.0), LocalDateTime.now(ZoneOffset.UTC).minusMinutes(2)));
    return transactions;
  }

}

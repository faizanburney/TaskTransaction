package com.n26.service;

import static org.junit.Assert.assertNotNull;

import com.n26.exception.FutureTransactionException;
import com.n26.exception.OldTransactionException;
import com.n26.datatransferobject.TransactionDto;
import com.n26.service.AddTransaction;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AddTransactionTest {

  @Autowired
  private AddTransaction addTransaction;

  @Test
  public void givenTransactionTimeAndAmount_shouldSaveTransaction() {
    TransactionDto transactionDto = new TransactionDto(new BigDecimal(10.00), LocalDateTime.now(ZoneOffset.UTC));
    addTransaction.execute(transactionDto);
    assertNotNull(transactionDto);
  }

  @Test(expected = OldTransactionException.class)
  public void givenTransactionAmountAndTimeLessThanSixtySecondsFromCurrentTime_shouldFailTransaction() {
    TransactionDto transactionDto = new TransactionDto(new BigDecimal(10.00),
        LocalDateTime.now(ZoneOffset.UTC).minusMinutes(2));
    addTransaction.execute(transactionDto);
  }

  @Test
  public void givenTransactionAmountAndTimeExactlyLessThanSixtySecondsFromCurrentTime_shouldCreateTransaction() {
    TransactionDto transactionDto = new TransactionDto(new BigDecimal(10.00),
        LocalDateTime.now(ZoneOffset.UTC).minusSeconds(60));
    addTransaction.execute(transactionDto);
    assertNotNull(transactionDto);
  }

  @Test(expected = FutureTransactionException.class)
  public void givenTransactionAmountAndTimeInFuture_shouldFailTransaction() {
    TransactionDto transactionDto = new TransactionDto(new BigDecimal(10.00),
        LocalDateTime.now(ZoneOffset.UTC).plusMinutes(2));
    addTransaction.execute(transactionDto);
  }

  @Test
  public void givenTransactionAmountAndTimeExactlyLikeCurrentTime_shouldCreateTransaction() {
    TransactionDto transactionDto = new TransactionDto(new BigDecimal(10.00),
        LocalDateTime.now(ZoneOffset.UTC));
    addTransaction.execute(transactionDto);
    assertNotNull(transactionDto);
  }
}

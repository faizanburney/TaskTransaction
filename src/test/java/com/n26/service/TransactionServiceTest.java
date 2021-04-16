package com.n26.service;

import static org.junit.Assert.assertNotNull;

import com.n26.exception.FutureTransactionException;
import com.n26.exception.OldTransactionException;
import com.n26.datatransferobject.TransactionDto;
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
public class TransactionServiceTest
{

  @Autowired
  private TransactionService transactionService;

  @Test
  public void shouldSaveTransaction() {
    TransactionDto transactionDto = new TransactionDto(new BigDecimal(10.00), LocalDateTime.now(ZoneOffset.UTC));
    transactionService.add(transactionDto);
    assertNotNull(transactionDto);
  }

  @Test(expected = OldTransactionException.class)
  public void transactionAmountAndTimeLessThanSixtySeconds_shouldFailTransaction() {
    TransactionDto transactionDto = new TransactionDto(new BigDecimal(10.00),
        LocalDateTime.now(ZoneOffset.UTC).minusMinutes(2));
    transactionService.add(transactionDto);
  }

  @Test
  public void transactionAmountAndTimeExactlyLessThanSixtySeconds_shouldCreateTransaction() {
    TransactionDto transactionDto = new TransactionDto(new BigDecimal(10.00),
        LocalDateTime.now(ZoneOffset.UTC).minusSeconds(59));
    transactionService.add(transactionDto);
    assertNotNull(transactionDto);
  }

  @Test(expected = FutureTransactionException.class)
  public void transactionAmountAndTimeInFuture_shouldFailTransaction() {
    TransactionDto transactionDto = new TransactionDto(new BigDecimal(10.00),
        LocalDateTime.now(ZoneOffset.UTC).plusMinutes(2));
    transactionService.add(transactionDto);
  }

  @Test
  public void transactionAmountAndTimeExactlyLikeCurrentTime_shouldCreateTransaction() {
    TransactionDto transactionDto = new TransactionDto(new BigDecimal(10.00),
        LocalDateTime.now(ZoneOffset.UTC));
    transactionService.add(transactionDto);
    assertNotNull(transactionDto);
  }
  
}

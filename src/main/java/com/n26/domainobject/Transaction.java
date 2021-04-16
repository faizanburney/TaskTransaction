package com.n26.domainobject;

import static java.math.BigDecimal.ROUND_HALF_UP;
import static java.time.LocalDateTime.now;

import com.n26.datatransferobject.TransactionDto;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class Transaction {

  private BigDecimal amount;
  private LocalDateTime time;

  public Transaction(BigDecimal amount, LocalDateTime time) {
    this.amount = amount;
    this.time = time;
  }

  public Transaction(TransactionDto transactionDto) {
    this(transactionDto.getAmount(), transactionDto.getTimestamp());
  }

  public BigDecimal getAmount() {
    return amount;
  }


  public LocalDateTime getTime()
  {
    return time;
  }


  public boolean isOldTransaction() {
    return time.toInstant(ZoneOffset.UTC).isBefore(Instant.now().minusSeconds(60));
  }

  public BigDecimal retrieveRoundedAmount() {
    return amount.setScale(2, ROUND_HALF_UP);
  }

  public boolean isFutureTransaction() {
    return Instant.now().isBefore(time.toInstant(ZoneOffset.UTC));
  }
}

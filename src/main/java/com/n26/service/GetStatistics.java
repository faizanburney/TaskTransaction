package com.n26.service;

import com.n26.domainobject.Statistics;
import com.n26.domainobject.Transaction;
import com.n26.datatransferobject.SummaryStatisticsDto;
import com.n26.repository.TransactionRepository;
import java.util.Collection;
import org.springframework.stereotype.Service;

@Service
public class GetStatistics {

  private final TransactionRepository transactionRepository;

  public GetStatistics(TransactionRepository transactionRepository) {
    this.transactionRepository = transactionRepository;
  }

  public SummaryStatisticsDto execute() {
    return transactionRepository.getCurrentStatistics().getSummaryStatistics();
  }
}

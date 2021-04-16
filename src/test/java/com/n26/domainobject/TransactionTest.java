package com.n26.domainobject;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static java.math.BigDecimal.ROUND_HALF_UP;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

@RunWith(JUnit4.class)
public class TransactionTest
{

    @Test
    public void amountAndTransactionTime_shouldCreateTransaction()
    {
        Transaction transaction = new Transaction(new BigDecimal(10.00), LocalDateTime.now(ZoneOffset.UTC));
        assertNotNull(transaction);
        assertEquals(transaction.getAmount().floatValue(), 10.00f);
    }


    @Test
    public void amountAndTransactionTimeLessThanSixtyOneSeconds_shouldReturnOlderTransactionStatusTrue()
    {
        boolean isOlderTransaction = new Transaction(
            new BigDecimal(10.00),
            subtractSecondsFromCurrentLocalDateTime(61))
            .isOldTransaction();

        assertTrue(isOlderTransaction);
    }


    @Test
    public void amountAndTransactionTimeExactlyLessThanSixtySecondsFromCurrentTime_shouldReturnOlderTransactionStatusFalse()
    {
        boolean isOlderTransaction = new Transaction(
            new BigDecimal(10.00),
            subtractSecondsFromCurrentLocalDateTime(59))
            .isOldTransaction();

        assertFalse(isOlderTransaction);
    }


    @Test
    public void amountAndTransactionTimeLessThanTwentySecondsFromCurrentTime_shouldReturnOlderTransactionStatusFalse()
    {
        boolean isOlderTransaction = new Transaction(
            new BigDecimal(10.00),
            subtractSecondsFromCurrentLocalDateTime(20))
            .isOldTransaction();

        assertFalse(isOlderTransaction);
    }


    @Test
    public void amountAndTransactionTimeMoreThanTwentySecondsFromCurrentTime_shouldReturnFutureTransactionStatusTrue()
    {
        boolean isFutureTransaction = new Transaction(
            new BigDecimal(10.00),
            subtractSecondsFromCurrentLocalDateTime(-20))
            .isFutureTransaction();

        assertTrue(isFutureTransaction);
    }


    @Test
    public void amountAndTransactionTimeExactlyCurrentTime_shouldReturnFutureTransactionStatusFalse()
    {
        boolean isFutureTransaction = new Transaction(
            new BigDecimal(10.00),
            subtractSecondsFromCurrentLocalDateTime(0))
            .isFutureTransaction();

        assertFalse(isFutureTransaction);
    }


    @Test
    public void transactionAmountWithOneDecimal_shouldRoundUpHafAndReturnInDouble()
    {
        Transaction transaction = new Transaction(new BigDecimal(10.0), LocalDateTime.now(ZoneOffset.UTC));
        assertNotNull(transaction);
        assertEquals(
            transaction.retrieveRoundedAmount(),
            new BigDecimal(10.00).setScale(2, ROUND_HALF_UP));
    }


    @Test
    public void transactionAmountWithThreeDecimals_shouldRoundUpHafAndReturnInDouble()
    {
        Transaction transaction = new Transaction(new BigDecimal(10.010), LocalDateTime.now(ZoneOffset.UTC));
        assertNotNull(transaction);
        assertEquals(transaction.retrieveRoundedAmount(), new BigDecimal(10.01).setScale(2, ROUND_HALF_UP));
    }


    @Test
    public void transactionAmountWithTwoDecimals_shouldRoundUpHafAndReturnInDouble()
    {
        Transaction transaction = new Transaction(new BigDecimal(10.01), LocalDateTime.now(ZoneOffset.UTC));
        assertNotNull(transaction);
        assertEquals(transaction.retrieveRoundedAmount(), new BigDecimal(10.01).setScale(2, ROUND_HALF_UP));
    }


    private LocalDateTime subtractSecondsFromCurrentLocalDateTime(long seconds)
    {
        return LocalDateTime.now(ZoneOffset.UTC)
            .minusSeconds(seconds);
    }
}

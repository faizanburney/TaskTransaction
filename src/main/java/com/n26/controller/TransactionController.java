package com.n26.controller;

import com.n26.datatransferobject.TransactionDto;
import com.n26.service.TransactionService;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/transactions")
public class TransactionController
{

    private final TransactionService transactionService;


    public TransactionController(
        TransactionService transactionService)
    {
        this.transactionService = transactionService;
    }


    @PostMapping
    @ResponseStatus(CREATED)
    public void addTransaction(@Valid @RequestBody TransactionDto transactionDto)
    {
        transactionService.add(transactionDto);
    }


    @DeleteMapping
    @ResponseStatus(NO_CONTENT)
    public void deleteTransaction()
    {
        transactionService.delete();
    }
}

package com.n26.controller;

import com.n26.datatransferobject.SummaryStatisticsDto;
import com.n26.service.StatisticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/statistics")
public class StatisticsController
{

    private final StatisticsService getStatistics;


    public StatisticsController(StatisticsService getStatistics)
    {
        this.getStatistics = getStatistics;
    }


    @GetMapping
    @ResponseStatus(OK)
    public SummaryStatisticsDto getStatistics()
    {
        return getStatistics.get();
    }
}

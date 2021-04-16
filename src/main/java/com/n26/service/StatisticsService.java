package com.n26.service;

import com.n26.datatransferobject.SummaryStatisticsDto;
import com.n26.domainobject.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService
{
    @Autowired
    Statistics currentStatistics;


    public StatisticsService()
    {

    }


    public SummaryStatisticsDto get()
    {
        return currentStatistics.getSummaryStatistics();
    }


}

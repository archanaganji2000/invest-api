package com.ark.invest_api.controller;


import com.ark.invest_api.dto.FundSummary;
import com.ark.invest_api.dto.InvestorSummary;
import com.ark.invest_api.service.ReportingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportingService reporting;

    @GetMapping("/funds/{fundId}/summary")
    public FundSummary fundSummary(@PathVariable Long fundId) {
        return reporting.fundSummary(fundId);
    }

    @GetMapping("/investors/{investorId}/summary")
    public InvestorSummary investorSummary(@PathVariable Long investorId) {
        return reporting.investorSummary(investorId);
    }
}

package com.ark.invest_api.controller;


import com.ark.invest_api.dto.FundSummary;
import com.ark.invest_api.dto.InvestorSummary;
import com.ark.invest_api.service.ReportingService;

import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportingService reporting;

    public ReportController(ReportingService reporting) {
        this.reporting = reporting;
    }

    @GetMapping("/funds/{fundId}/summary")
    public FundSummary fundSummary(@PathVariable Long fundId) {
        return reporting.fundSummary(fundId);
    }

    @GetMapping("/investors/{investorId}/summary")
    public InvestorSummary investorSummary(@PathVariable Long investorId) {
        return reporting.investorSummary(investorId);
    }
}

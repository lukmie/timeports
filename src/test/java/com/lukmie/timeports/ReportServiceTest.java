package com.lukmie.timeports;

import com.lukmie.timeports.entity.FlightReport;
import com.lukmie.timeports.repository.ReportRepository;
import com.lukmie.timeports.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {

    @Mock
    private ReportRepository reportRepository;

    @InjectMocks
    private ReportService reportService;

    private List<Object[]> queryResults;

    @BeforeEach
    void setUp() {
        queryResults = new ArrayList<>();
        queryResults.add(new Object[]{"Adam", "Adamski", "JIT Team", "JIT_RESERVATION", buildCount("10")});
        queryResults.add(new Object[]{"Beata", "Kowalska", "JIT Team", "JIT_RESERVATION", buildCount("1")});
    }

    private BigInteger buildCount(String input) {
        return new BigInteger(input);
    }

    @Test
    void whenGetFlightsByClientThenReturn() {
        //given
        queryResults = new ArrayList<>();
        queryResults.add(new Object[]{"Adam", "Adamski", "JIT Team", "JIT_RESERVATION", buildCount("10")});
        queryResults.add(new Object[]{"Beata", "Kowalska", "JIT Team", "JIT_RESERVATION", buildCount("1")});

        when(reportRepository.fetchReportFlightsEntries()).thenReturn(queryResults);

        //when
        FlightReport flightReport = reportService.flightsByClient();

        //then
        assertThat(flightReport.getEntries()).hasSize(2);
    }


}

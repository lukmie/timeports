package com.lukmie.timeports.service;

import com.lukmie.timeports.entity.FlightReport;
import com.lukmie.timeports.entity.WorkReport;
import com.lukmie.timeports.repository.ReportRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {

    @Mock
    private ReportRepository reportRepository;

    @InjectMocks
    private ReportService reportService;

    @Test
    void whenGetFlightsByClientThenReturnReport() {
        //given
        List<Object[]> results = new ArrayList<>();
        results.add(new Object[]{"Adam", "Adamski", "JIT Team", "JIT_RESERVATION", new BigInteger("10")});
        results.add(new Object[]{"Beata", "Kowalska", "JIT Team", "JIT_RESERVATION", new BigInteger("1")});

        when(reportRepository.fetchReportFlightsEntries()).thenReturn(results);

        //when
        FlightReport flightReport = reportService.flightsByClient();

        //then
        assertThat(flightReport.getEntries()).hasSize(2);
        assertThat(flightReport.getEntries().get(0).getName()).isEqualTo("Adam");
        assertThat(flightReport.getEntries().get(0).getSum()).isEqualTo(10);
        assertThat(flightReport.getEntries().get(1).getName()).isEqualTo("Beata");
        assertThat(flightReport.getEntries().get(1).getSum()).isEqualTo(1);
    }

    @Test
    void whenGetHoursByClientWithParameterThenReturnReport() {
        //given
        List<Object[]> results = new ArrayList<>();
        results.add(new Object[]{"Beata", "Kowalska", "JIT Team", new BigDecimal(898), "weJit"});
        results.add(new Object[]{"Roman", "Romanowski", "JIT Team", new BigDecimal(1010), "weJit"});

        when(reportRepository.fetchReportWorkHoursEntriesPerClient("name")).thenReturn(results);

        //when
        WorkReport report = reportService.hoursByClient("name");

        //then
        assertThat(report.getEntries()).hasSize(2);
        assertThat(report.getEntries().get(0).getName()).isEqualTo("Beata");
        assertThat(report.getEntries().get(0).getSum()).isEqualTo(new BigDecimal(898));
        assertThat(report.getEntries().get(1).getName()).isEqualTo("Roman");
        assertThat(report.getEntries().get(1).getSum()).isEqualTo(new BigDecimal(1010));
    }

    @Test
    void whenGetHoursByProjectWithParameterThenReturnReport() {
        //given
        List<Object[]> results = new ArrayList<>();
        results.add(new Object[]{"Michal", "Michalski", "JIT Team", new BigDecimal(1898), "weJit"});
        results.add(new Object[]{"Aneta", "Nowak", "JIT Team", new BigDecimal(11010), "weJit"});

        when(reportRepository.fetchReportWorkHoursEntriesPerProject("surname")).thenReturn(results);

        //when
        WorkReport report = reportService.hoursByProject("surname");

        //then
        assertThat(report.getEntries()).hasSize(2);
        assertThat(report.getEntries().get(0).getName()).isEqualTo("Michal");
        assertThat(report.getEntries().get(0).getSum()).isEqualTo(new BigDecimal(1898));
        assertThat(report.getEntries().get(1).getName()).isEqualTo("Aneta");
        assertThat(report.getEntries().get(1).getSum()).isEqualTo(new BigDecimal(11010));
    }

}

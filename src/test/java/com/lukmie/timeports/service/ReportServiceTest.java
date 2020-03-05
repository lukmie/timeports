package com.lukmie.timeports.service;

import com.lukmie.timeports.entity.FlightReport;
import com.lukmie.timeports.repository.ReportRepository;
import com.lukmie.timeports.service.ReportService;
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

    @Test
    void whenGetFlightsByClientThenReturn() {
        //given
        List<Object[]> results = new ArrayList<>();
        results.add(new Object[]{"Adam", "Adamski", "JIT Team", "JIT_RESERVATION", count("10")});
        results.add(new Object[]{"Beata", "Kowalska", "JIT Team", "JIT_RESERVATION", count("1")});

        when(reportRepository.fetchReportFlightsEntries()).thenReturn(results);

        //when
        FlightReport flightReport = reportService.flightsByClient();

        //then
        assertThat(flightReport.getEntries()).hasSize(2);
        assertThat(flightReport.getEntries().get(0).getName()).isEqualTo("Adam");
        assertThat(flightReport.getEntries().get(0).getSurname()).isEqualTo("Adamski");
        assertThat(flightReport.getEntries().get(0).getClient()).isEqualTo("JIT Team");
        assertThat(flightReport.getEntries().get(0).getTravelReservation()).isEqualTo("JIT_RESERVATION");
        assertThat(flightReport.getEntries().get(0).getSum()).isEqualTo(10);
        assertThat(flightReport.getEntries().get(1).getName()).isEqualTo("Beata");
        assertThat(flightReport.getEntries().get(1).getSurname()).isEqualTo("Kowalska");
        assertThat(flightReport.getEntries().get(1).getClient()).isEqualTo("JIT Team");
        assertThat(flightReport.getEntries().get(1).getTravelReservation()).isEqualTo("JIT_RESERVATION");
        assertThat(flightReport.getEntries().get(1).getSum()).isEqualTo(1);
    }



    private BigInteger count(String input) {
        return new BigInteger(input);
    }
}

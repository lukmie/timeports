package com.lukmie.timeports.service;

import com.lukmie.timeports.component.ReportUtils;
import com.lukmie.timeports.entity.BusinessTripReportEntry;
import com.lukmie.timeports.repository.BusinessTripRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BusinessTripServiceReportTest {

    @Mock
    private BusinessTripRepository businessTripRepository;

    @Mock
    private ReportUtils reportUtils;

    @Mock
    private Pageable pageable;

    @InjectMocks
    private BusinessTripReportService businessTripReportService;

    private BusinessTripReportEntry businessTripReportEntry1, businessTripReportEntry2;

    @BeforeEach
    void setUp() {
        businessTripReportEntry1 = BusinessTripReportEntry.builder()
                .name("Adam Adamski")
                .projectName("WeJit")
                .clientName("JIT Team")
                .numberOfNights(new BigInteger("122"))
                .totalTrips(new BigInteger("33"))
                .insuranceBy("JIT_INSURANCE")
                .travelBy("JIT_INSURANCE")
                .accommodationBy("JIT_INSURANCE")
                .build();

        businessTripReportEntry2 = BusinessTripReportEntry.builder()
                .name("Piotr Piotrowski")
                .numberOfNights(new BigInteger("111"))
                .totalTrips(new BigInteger("22"))
                .build();
    }

    @Test
    void whenGetBusinessTripAccountsReportThenReturnReport() {
        //given
        List<BusinessTripReportEntry> collect = List.of(businessTripReportEntry2);

        Page<BusinessTripReportEntry> pagedResponse = new PageImpl<>(collect.subList((int)pageable.getOffset(), collect.size()), pageable, collect.size());

        when(businessTripRepository.getBusinessTripsAccounts(any(Pageable.class))).thenReturn(new ArrayList<>());
        doReturn(pagedResponse).when(reportUtils).createPageFromListOfBusinessTripReportEntry(anyList(), any(Pageable.class));

        //when
        Page<BusinessTripReportEntry> businessTripReport = businessTripReportService.getBusinessTripAccountsReport(pageable);

        //then
        assertThat(businessTripReport.getContent().size()).isEqualTo(1);
        verify(businessTripRepository, times(1)).getBusinessTripsAccounts(any(Pageable.class));
    }

    @Test
    void whenGetBusinessTripAccountsByNameThenReturnReport() {
        //given
        List<Object[]> list = new ArrayList<>();
        list.add(new Object[]{"Piotr Piotrowski", new BigInteger("111"), new BigInteger("22")});

        when(reportUtils.prepareInputData(anyString())).thenReturn("");
        when(businessTripRepository.getBusinessTripsAccountsByAccountName(anyString())).thenReturn(list);

        //when
        List<BusinessTripReportEntry> businessTripReport = businessTripReportService.getBusinessTripAccountsByName("Piotr Piotrowski");

        //then
        assertThat(businessTripReport.size()).isEqualTo(1);
        verify(businessTripRepository, times(1)).getBusinessTripsAccountsByAccountName(anyString());
    }

    @Test
    void whenGetBusinessTripProjectsReportThenReturnReport() {
        //given
        List<BusinessTripReportEntry> collect = List.of(businessTripReportEntry2);

        Page<BusinessTripReportEntry> pagedResponse = new PageImpl<>(collect.subList((int)pageable.getOffset(), collect.size()), pageable, collect.size());

        when(businessTripRepository.getBusinessTripsProjects(any(Pageable.class))).thenReturn(new ArrayList<>());
        doReturn(pagedResponse).when(reportUtils).createPageFromListOfBusinessTripReportEntry(anyList(), any(Pageable.class));

        //when
        Page<BusinessTripReportEntry> businessTripReport = businessTripReportService.getBusinessTripProjectsReport(pageable);

        //then
        assertThat(businessTripReport.getContent().size()).isEqualTo(1);
        verify(businessTripRepository, times(1)).getBusinessTripsProjects(any(Pageable.class));
    }

    @Test
    void whenGetBusinessTripProjectsReportByNameThenReturnReport() {
        //given
        List<Object[]> list = new ArrayList<>();
        list.add(new Object[]{"WeJit", new BigInteger("221"), new BigInteger("122")});

        when(reportUtils.prepareInputData(anyString())).thenReturn("");
        when(businessTripRepository.getBusinessTripsProjectsByProjectName(anyString())).thenReturn(list);

        //when
        List<BusinessTripReportEntry> businessTripReport = businessTripReportService.getBusinessTripProjectsReportByName("WeJit");

        //then
        assertThat(businessTripReport.size()).isEqualTo(1);
        verify(businessTripRepository, times(1)).getBusinessTripsProjectsByProjectName(anyString());
    }

    @Test
    void whenGetBusinessTripClientsReportThenReturnReport() {
        //given
        List<BusinessTripReportEntry> collect = List.of(businessTripReportEntry2);

        Page<BusinessTripReportEntry> pagedResponse = new PageImpl<>(collect.subList((int)pageable.getOffset(), collect.size()), pageable, collect.size());

        when(businessTripRepository.getBusinessTripsClients(any(Pageable.class))).thenReturn(new ArrayList<>());
        doReturn(pagedResponse).when(reportUtils).createPageFromListOfBusinessTripReportEntry(anyList(), any(Pageable.class));

        //when
        Page<BusinessTripReportEntry> businessTripReport = businessTripReportService.getBusinessTripClientsReport(pageable);

        //then
        assertThat(businessTripReport.getContent().size()).isEqualTo(1);
        verify(businessTripRepository, times(1)).getBusinessTripsClients(any(Pageable.class));
    }

    @Test
    void whenGetBusinessTripClientsReportByNameThenReturnReport() {
        //given
        List<Object[]> list = new ArrayList<>();
        list.add(new Object[]{"JIT Team", new BigInteger("221"), new BigInteger("122")});

        when(reportUtils.prepareInputData(anyString())).thenReturn("");
        when(businessTripRepository.getBusinessTripsClientsByClientName(anyString())).thenReturn(list);

        //when
        List<BusinessTripReportEntry> businessTripReport = businessTripReportService.getBusinessTripClientsReportByName("JIT Team");

        //then
        assertThat(businessTripReport.size()).isEqualTo(1);
        verify(businessTripRepository, times(1)).getBusinessTripsClientsByClientName(anyString());
    }

    @Test
    void whenGetAllWorkTimeReportThenReturnReport() {
        //given
        List<BusinessTripReportEntry> collect = List.of(businessTripReportEntry1);

        Page<BusinessTripReportEntry> pagedResponse = new PageImpl<>(collect.subList((int)pageable.getOffset(), collect.size()), pageable, collect.size());

        when(businessTripRepository.getAllBusinessTripFullData(any(Pageable.class))).thenReturn(new ArrayList<>());
        doReturn(pagedResponse).when(reportUtils).createPageFromListOfBusinessTripReportEntry(anyList(), any(Pageable.class));

        //when
        Page<BusinessTripReportEntry> businessTripReport = businessTripReportService.getAllWorkTimeReport(pageable);

        //then
        assertThat(businessTripReport.getContent().size()).isEqualTo(1);
        verify(businessTripRepository, times(1)).getAllBusinessTripFullData(any(Pageable.class));
    }

    @Test
    void whenGetBusinessTripPerAccountReportThenReturnReport() {
        //given
        List<BusinessTripReportEntry> collect = List.of(businessTripReportEntry1);

        Page<BusinessTripReportEntry> pagedResponse = new PageImpl<>(collect.subList((int)pageable.getOffset(), collect.size()), pageable, collect.size());

        when(reportUtils.prepareInputData(anyString())).thenReturn("");
        when(businessTripRepository.getBusinessTripPerAccountFullData(any(Pageable.class), anyString())).thenReturn(new ArrayList<>());
        doReturn(pagedResponse).when(reportUtils).createPageFromListOfBusinessTripReportEntry(anyList(), any(Pageable.class));

        //when
        Page<BusinessTripReportEntry> businessTripReport = businessTripReportService.getBusinessTripPerAccountReport(pageable, "Adam Adamski");

        //then
        assertThat(businessTripReport.getContent().size()).isEqualTo(1);
        assertThat(businessTripReport.getContent()).extracting(BusinessTripReportEntry::getName).allMatch("Adam Adamski"::equals);
        verify(businessTripRepository, times(1)).getBusinessTripPerAccountFullData(any(Pageable.class), anyString());
    }

    @Test
    void whenGetBusinessTripPerProjectReportThenReturnReport() {
        //given
        List<BusinessTripReportEntry> collect = List.of(businessTripReportEntry1);

        Page<BusinessTripReportEntry> pagedResponse = new PageImpl<>(collect.subList((int)pageable.getOffset(), collect.size()), pageable, collect.size());

        when(reportUtils.prepareInputData(anyString())).thenReturn("");
        when(businessTripRepository.getBusinessTripPerProjectFullData(any(Pageable.class), anyString())).thenReturn(new ArrayList<>());
        doReturn(pagedResponse).when(reportUtils).createPageFromListOfBusinessTripReportEntry(anyList(), any(Pageable.class));

        //when
        Page<BusinessTripReportEntry> businessTripReport = businessTripReportService.getBusinessTripPerProjectReport(pageable, "WeJit");

        //then
        assertThat(businessTripReport.getContent().size()).isEqualTo(1);
        assertThat(businessTripReport.getContent()).extracting(BusinessTripReportEntry::getProjectName).allMatch("WeJit"::equals);
        verify(businessTripRepository, times(1)).getBusinessTripPerProjectFullData(any(Pageable.class), anyString());
    }

    @Test
    void whenGetBusinessTripPerClientReportThenReturnReport() {
        //given
        List<BusinessTripReportEntry> collect = List.of(businessTripReportEntry1);

        Page<BusinessTripReportEntry> pagedResponse = new PageImpl<>(collect.subList((int)pageable.getOffset(), collect.size()), pageable, collect.size());

        when(reportUtils.prepareInputData(anyString())).thenReturn("");
        when(businessTripRepository.getBusinessTripPerClientFullData(any(Pageable.class), anyString())).thenReturn(new ArrayList<>());
        doReturn(pagedResponse).when(reportUtils).createPageFromListOfBusinessTripReportEntry(anyList(), any(Pageable.class));

        //when
        Page<BusinessTripReportEntry> businessTripReport = businessTripReportService.getBusinessTripPerClientReport(pageable, "JIT Team");

        //then
        assertThat(businessTripReport.getContent().size()).isEqualTo(1);
        assertThat(businessTripReport.getContent()).extracting(BusinessTripReportEntry::getClientName).allMatch("JIT Team"::equals);
        verify(businessTripRepository, times(1)).getBusinessTripPerClientFullData(any(Pageable.class), anyString());
    }
}


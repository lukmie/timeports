package com.lukmie.timeports.service;

import com.lukmie.timeports.component.ReportUtils;
import com.lukmie.timeports.entity.WorkTimeReportEntry;
import com.lukmie.timeports.repository.WorkTimeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkTimeReportServiceTest {

    @Mock
    private WorkTimeRepository workTimeRepository;

    @Mock
    private ReportUtils reportUtils;

    @Mock
    private Pageable pageable;

    @InjectMocks
    private WorkTimeReportService workTimeReportService;

    private WorkTimeReportEntry workTimeReportEntry1, workTimeReportEntry2;

    @BeforeEach
    void setUp() {
        workTimeReportEntry1 = WorkTimeReportEntry.builder()
                .name("Adam Adamski")
                .month(8)
                .year(2019)
                .workedHours(new BigDecimal("122"))
                .project("WeJit")
                .client("JIT Team")
                .build();

        workTimeReportEntry2 = WorkTimeReportEntry.builder()
                .name("Piotr Piotrowski")
                .month(8)
                .year(2019)
                .workedHours(new BigDecimal("168"))
                .project("LOCUS")
                .client("LPP S.A.")
                .build();
    }

    @Test
    void whenGetWorkTimeReportThenReturnReport() {
        //given
        List<WorkTimeReportEntry> collect = List.of(workTimeReportEntry1);

        Page<WorkTimeReportEntry> pagedResponse = new PageImpl<>(collect.subList((int)pageable.getOffset(), collect.size()), pageable, collect.size());

        when(workTimeRepository.getWorkTimeReport(any(Pageable.class), anyInt(), anyInt(), anyInt(), anyInt())).thenReturn(new ArrayList<>());
        doReturn(pagedResponse).when(reportUtils).createPageFromListOfBusinessTripReportEntry(anyList(), any(Pageable.class));

        //when
        Page<WorkTimeReportEntry> workTimeReport = workTimeReportService.getWorkTimeReport(pageable, 2019, 2019, 1, 12);

        //then
        assertThat(workTimeReport.getContent().size()).isEqualTo(1);
    }

    @Test
    void whenGetWorkTimeReportByAccountNameThenReturnReport() {
        //given
        List<WorkTimeReportEntry> collect = List.of(workTimeReportEntry2);

        Page<WorkTimeReportEntry> pagedResponse = new PageImpl<>(collect.subList((int)pageable.getOffset(), collect.size()), pageable, collect.size());

        when(reportUtils.prepareInputData(anyString())).thenReturn("");
        when(workTimeRepository.getWorkTimeReportByAccountName(any(Pageable.class), anyString(), anyInt(), anyInt(), anyInt(), anyInt())).thenReturn(new ArrayList<>());
        doReturn(pagedResponse).when(reportUtils).createPageFromListOfBusinessTripReportEntry(anyList(), any(Pageable.class));

        //when
        Page<WorkTimeReportEntry> workTimeReport = workTimeReportService.getWorkTimeReportByAccountName(pageable, "Piotr Piotrowski",2019, 2019, 1, 12);

        //then
        assertThat(workTimeReport.getContent().size()).isEqualTo(1);
        assertThat(workTimeReport.getContent()).extracting(WorkTimeReportEntry::getName).allMatch("Piotr Piotrowski"::equals);
        verify(workTimeRepository, times(1)).getWorkTimeReportByAccountName(any(Pageable.class), anyString(), anyInt(), anyInt(), anyInt(), anyInt());
    }

    @Test
    void whenGetWorkTimeReportByProjectNameThenReturnReport() {
        //given
        List<WorkTimeReportEntry> collect = List.of(workTimeReportEntry2);

        Page<WorkTimeReportEntry> pagedResponse = new PageImpl<>(collect.subList((int)pageable.getOffset(), collect.size()), pageable, collect.size());

        when(reportUtils.prepareInputData(anyString())).thenReturn("");
        when(workTimeRepository.getWorkTimeReportByProjectName(any(Pageable.class), anyString(), anyInt(), anyInt(), anyInt(), anyInt())).thenReturn(new ArrayList<>());
        doReturn(pagedResponse).when(reportUtils).createPageFromListOfBusinessTripReportEntry(anyList(), any(Pageable.class));

        //when
        Page<WorkTimeReportEntry> workTimeReport = workTimeReportService.getWorkTimeReportByProjectName(pageable, "LOCUS",2019, 2019, 1, 12);

        //then
        assertThat(workTimeReport.getContent().size()).isEqualTo(1);
        assertThat(workTimeReport.getContent()).extracting(WorkTimeReportEntry::getProject).allMatch("LOCUS"::equals);
        verify(workTimeRepository, times(1)).getWorkTimeReportByProjectName(any(Pageable.class), anyString(), anyInt(), anyInt(), anyInt(), anyInt());
    }

    @Test
    void whenGetWorkTimeReportByClientNameThenReturnReport() {
        //given
        List<WorkTimeReportEntry> collect = List.of(workTimeReportEntry1);

        Page<WorkTimeReportEntry> pagedResponse = new PageImpl<>(collect.subList((int)pageable.getOffset(), collect.size()), pageable, collect.size());

        when(reportUtils.prepareInputData(anyString())).thenReturn("");
        when(workTimeRepository.getWorkTimeReportByClientName(any(Pageable.class), anyString(), anyInt(), anyInt(), anyInt(), anyInt())).thenReturn(new ArrayList<>());
        doReturn(pagedResponse).when(reportUtils).createPageFromListOfBusinessTripReportEntry(anyList(), any(Pageable.class));

        //when
        Page<WorkTimeReportEntry> workTimeReport = workTimeReportService.getWorkTimeReportByClientName(pageable, "JIT Team",2019, 2019, 1, 12);

        //then
        assertThat(workTimeReport.getContent().size()).isEqualTo(1);
        assertThat(workTimeReport.getContent()).extracting(WorkTimeReportEntry::getClient).allMatch("JIT Team"::equals);
        verify(workTimeRepository, times(1)).getWorkTimeReportByClientName(any(Pageable.class), anyString(), anyInt(), anyInt(), anyInt(), anyInt());
    }
}

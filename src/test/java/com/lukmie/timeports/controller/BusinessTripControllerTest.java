package com.lukmie.timeports.controller;

import com.lukmie.timeports.TimeportsApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = TimeportsApplication.class)
class BusinessTripControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenGetBusinessTripAccountsReportThenReturnReport() throws Exception {
        mockMvc.perform(get("/api/business-trip/partial-report/accounts?page=0&size=20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.pageable.pageNumber", equalTo(0)))
                .andExpect(jsonPath("$.pageable.pageSize", equalTo(20)));
    }

    @Test
    void whenGetBusinessTripAccountsReportCsvThenReturnReportInCsv() throws Exception {
        mockMvc.perform(get("/api/business-trip/partial-report/accounts/csv"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/csv"));
    }

    @Test
    void whenGetBusinessTripAccountsReportByAccountNameThenReturnReport() throws Exception {
        String name = "Maciej Kankowski";
        mockMvc.perform(get("/api/business-trip/partial-report/accounts/{name}", name))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].name").value(everyItem(is(name))))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void whenGetBusinessTripProjectsReportThenReturnReport() throws Exception {
        mockMvc.perform(get("/api/business-trip/partial-report/projects?page=0&size=15"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.pageable.pageNumber", equalTo(0)))
                .andExpect(jsonPath("$.pageable.pageSize", equalTo(15)));
    }

    @Test
    void whenGetBusinessTripProjectsReportCsvThenReturnReportInCsv() throws Exception {
        mockMvc.perform(get("/api/business-trip/partial-report/projects/csv"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/csv"));
    }

    @Test
    void whenGetBusinessTripProjectsReportByProjectNameThenReturnReport() throws Exception {
        String name = "Investor team";
        mockMvc.perform(get("/api/business-trip/partial-report/projects/{name}", name))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].name").value(everyItem(is(name))))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void whenGetBusinessTripClientsReportThenReturnReport() throws Exception {
        mockMvc.perform(get("/api/business-trip/partial-report/clients?page=0&size=15"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.pageable.pageNumber", equalTo(0)))
                .andExpect(jsonPath("$.pageable.pageSize", equalTo(15)));
    }

    @Test
    void whenGetBusinessTripClientsReportCsvThenReturnReportInCsv() throws Exception {
        mockMvc.perform(get("/api/business-trip/partial-report/clients/csv"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/csv"));
    }

    @Test
    void whenGetBusinessTripClientsReportByClientNameThenReturnReport() throws Exception {
        String name = "JIT Team";
        mockMvc.perform(get("/api/business-trip/partial-report/clients/{name}", name))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].name").value(everyItem(is(name))))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void whenGetAllWorkTimeThenReturnReport() throws Exception {
        mockMvc.perform(get("/api/business-trip/overall-report?page=0&size=15"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.pageable.pageNumber", equalTo(0)))
                .andExpect(jsonPath("$.pageable.pageSize", equalTo(15)));
    }

    @Test
    void whenGetAllWorkTimeCsvThenReturnReportInCsv() throws Exception {
        mockMvc.perform(get("/api/business-trip/overall-report/csv?page=0&size=15"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/csv"));
    }

    @Test
    void whenGetBusinessTripByAccountNameThenReturnReport() throws Exception {
        String name = "Maciej Kankowski";
        mockMvc.perform(get("/api/business-trip/overall-report/accounts/{name}", name))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].name").value(everyItem(is(name))))
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    void whenGetBusinessTripByAccountNameCsvThenReturnReportInCsv() throws Exception {
        String name = "Maciej Kankowski";
        mockMvc.perform(get("/api/business-trip/overall-report/accounts/{name}/csv", name))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/csv"));
    }

    @Test
    void whenGetBusinessTripByProjectNameThenReturnReport() throws Exception {
        String name = "Investor team";
        mockMvc.perform(get("/api/business-trip/overall-report/projects/{name}", name))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].name").value(everyItem(is(name))))
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    void whenGetBusinessTripByProjectNameCsvThenReturnReportInCsv() throws Exception {
        String name = "Investor team";
        mockMvc.perform(get("/api/business-trip/overall-report/projects/{name}/csv", name))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/csv"));
    }

    @Test
    void whenGetBusinessTripByClientNameThenReturnReport() throws Exception {
        String name = "JIT Team";
        mockMvc.perform(get("/api/business-trip/overall-report/clients/{name}", name))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].name").value(everyItem(is(name))))
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    void whenGetBusinessTripByClientNameCsvThenReturnReportInCsv() throws Exception {
        String name = "JIT Team";
        mockMvc.perform(get("/api/business-trip/overall-report/clients/{name}/csv", name))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/csv"));
    }

}

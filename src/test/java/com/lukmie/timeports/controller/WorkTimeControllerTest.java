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

//@SpringBootTest
//@ExtendWith(SpringExtension.class)
//@AutoConfigureMockMvc
//@WithMockUser

//@ExtendWith(SpringExtension.class)
//@WebMvcTest(HomeController.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = TimeportsApplication.class)
class WorkTimeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenGetWorkTimeReportThenReturnReport() throws Exception {
        mockMvc.perform(get("/api/work-time/report?page=0&size=20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.pageable.pageNumber", equalTo(0)))
                .andExpect(jsonPath("$.pageable.pageSize", equalTo(20)));
    }

    @Test
    void whenGetWorkTimeReportCsvThenReturnReportInCsv() throws Exception {
        mockMvc.perform(get("/api/work-time/report/csv"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/csv"));
    }

    @Test
    void whenGetWorkTimeReportByAccountThenReturnReport() throws Exception {
        String name = "Łukasz Mienicki";
        mockMvc.perform(get("/api/work-time/report/accounts/{name}", name))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[*].name").value(everyItem(is(name))))
                .andExpect(jsonPath("$.pageable.pageNumber", equalTo(0)))
                .andExpect(jsonPath("$.pageable.pageSize", equalTo(15)));
    }

    @Test
    void whenGetWorkTimeReportCsvByAccountThenReturnReportInCsv() throws Exception {
        String name = "Łukasz Mienicki";
        mockMvc.perform(get("/api/work-time/report/accounts/{name}/csv", name))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/csv"));
    }

    @Test
    void whenGetWorkTimeReportByProjectThenReturnReport() throws Exception {
        String name = "WeJit";
        mockMvc.perform(get("/api/work-time/report/projects/{name}?page=0&size=5", name))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[*].project").value(everyItem(is(name))))
                .andExpect(jsonPath("$.pageable.pageNumber", equalTo(0)))
                .andExpect(jsonPath("$.pageable.pageSize", equalTo(5)));
    }

    @Test
    void whenGetWorkTimeReportCsvByProjectThenReturnReportInCsv() throws Exception {
        String name = "WeJit";
        mockMvc.perform(get("/api/work-time/report/projects/{name}/csv", name))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/csv"));
    }

    @Test
    void whenGetWorkTimeReportByClientThenReturnReport() throws Exception {
        String name = "Signal Maritime";
        mockMvc.perform(get("/api/work-time/report/clients/{name}?page=0&size=5", name))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[*].client").value(everyItem(is(name))))
                .andExpect(jsonPath("$.pageable.pageNumber", equalTo(0)))
                .andExpect(jsonPath("$.pageable.pageSize", equalTo(5)));
    }

    @Test
    void whenGetWorkTimeReportCsvByClientThenReturnReportInCsv() throws Exception {
        String name = "Signal Maritime";
        mockMvc.perform(get("/api/work-time/report/clients/{name}/csv", name))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/csv"));
    }
}

package com.lukmie.timeports.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Service
public class CsvWriterService {

    public <T> void configureCsvWriterAndPrint(HttpServletResponse resp, Page<T> page, String[] header, String fileName) throws IOException {
        log.info("report: {} generating started", fileName);
        csvSetupProperties(resp, fileName);
        try (ICsvBeanWriter csvWriter = new CsvBeanWriter(resp.getWriter(), new CsvPreference.Builder('|', ',', "\n").build())) {
            csvWriter.writeHeader(header);
            List<T> content = page.getContent();
            for (T businessTripReportEntry : content) {
                csvWriter.write(businessTripReportEntry, header);
            }
        } catch (IOException e) {
            log.warn("Error while creating CSV file", e);
        }
        log.info("report: {} generating finished, contains {} rows", fileName, page.getNumberOfElements());
    }

    private void csvSetupProperties(HttpServletResponse resp, String fileName) throws IOException {
        String headerValue = String.format("attachment; filename=\"%s\"", fileName);
        resp.setHeader(HttpHeaders.CONTENT_DISPOSITION, headerValue);
        resp.setContentType("text/csv");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.getWriter().write("\uFEFF");
    }
}

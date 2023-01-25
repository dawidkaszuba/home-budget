package pl.dawidkaszuba.homebudget.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.dawidkaszuba.homebudget.model.UserReport;
import pl.dawidkaszuba.homebudget.service.impl.RtfReportServiceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Principal;

@Slf4j
@RestController
public class ReportController {

    private final RtfReportServiceImpl rtfReportService;

    public ReportController(RtfReportServiceImpl rtfReportService) {
        this.rtfReportService = rtfReportService;
    }

    @PostMapping("/generate-report")
    public ResponseEntity getFile(@ModelAttribute("userReport") UserReport report, Principal principal) throws IOException {

        String fileName = rtfReportService.generateReport(report.getReportType(), principal.getName());

        File file = new File(fileName);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        log.info("Generowanie raportu " + report.getReportType() + " dla użytkownika " + principal.getName() + ".");
        return ResponseEntity.ok().headers(headers).contentLength(
            file.length()).contentType(MediaType.parseMediaType("application/txt")).body(resource);
    }
}

package org.example.controlers;

import org.example.entity.User;
import org.example.service.BillingFileWriterAndFolderCreator;
import org.example.service.BillingProcessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class BillingController {

    private final BillingProcessor billingProcessor;

    public BillingController(BillingProcessor billingProcessor) {
        this.billingProcessor = billingProcessor;
    }

    @PostMapping("/billing")
    public void processBillings(@RequestParam() String outputDir) {
        outputDir = "C:\\Users\\ivani\\Desktop\\outputdir";
        try {
            billingProcessor.processBillings(outputDir);
            // Можете да изпишете съобщение за успешно стартирана функция, ако желаете
            System.out.println("Billing process started");
        } catch (IOException e) {
            // Обработка на грешката
            e.printStackTrace();
        }
    }
}

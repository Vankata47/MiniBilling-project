package org.example.controlers;

import org.example.entity.User;
import org.example.repository.UserRepository;
import org.example.service.BillingFileWriterAndFolderCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;

@RestController
@RequestMapping("/api/users/{user_ref}/invoices")
public class InvoiceController {

    private final UserRepository userRepository;
    private final BillingFileWriterAndFolderCreator billingFileWriterAndFolderCreator;

    @Autowired
    public InvoiceController(UserRepository userRepository, BillingFileWriterAndFolderCreator billingFileWriterAndFolderCreator) {
        this.userRepository = userRepository;
        this.billingFileWriterAndFolderCreator = billingFileWriterAndFolderCreator;
    }

    @GetMapping
    public ResponseEntity<String> getInvoices(@PathVariable("user_ref") String userRef) {
        try {
            User user = userRepository.findByReferenceNumber(userRef);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            String outputDir = "C:\\Users\\ivani\\Desktop\\outputdir\\";
            String directoryPath = String.valueOf(billingFileWriterAndFolderCreator.findInvoiceFile(user, outputDir));
            if (directoryPath == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invoice not found for user " + userRef);
            }

            String invoiceContent = readInvoiceFile(directoryPath);
            return ResponseEntity.ok(invoiceContent);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving invoices: " + e.getMessage());
        }
    }

    private String readInvoiceFile(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException("Invoice file not found");
        }

        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line);
            }
        }

        return contentBuilder.toString();
    }
}

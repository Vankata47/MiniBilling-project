package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.example.entity.User;
import org.example.service.Billing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillingFileWriterAndFolderCreator {

    private final ObjectMapper objectMapper;

    @Autowired
    public BillingFileWriterAndFolderCreator() {
        this.objectMapper = new ObjectMapper();
    }

    public void writeBilling(Billing billing, int documentNumber, String outputDirectory) throws IOException {
        String userOutputDirectory = outputDirectory + "/" + billing.getConsumer() + "_" + billing.getReference();
        File userDirectory = new File(userOutputDirectory);
        if (!userDirectory.exists()) {
            userDirectory.mkdirs();
        }

        String fileName = documentNumber + "_" + DateUtility.getBulgarianMonth() + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy")) + ".json";
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        String json = objectMapper.writeValueAsString(billing);

        try (PrintWriter writer = new PrintWriter(new FileWriter(userOutputDirectory + "/" + fileName))) {
            writer.println(json);
        }
    }
    public File findInvoiceFile(User user, String outputDirectory) {
        String userDirectoryName = user.getName() + "_" + user.getReferenceNumber();
        String userDirectoryPath = outputDirectory + File.separator + userDirectoryName;
        File userDirectory = new File(userDirectoryPath);
        if (userDirectory.exists() && userDirectory.isDirectory()) {
            File[] files = userDirectory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".json")) {
                        return file;
                    }
                }
            }
        }

        return null;
    }
}


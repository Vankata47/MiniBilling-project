package org.example.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.example.entity.Price;
import org.example.entity.Reading;
import org.example.entity.User;
import org.example.repository.PriceRepository;
import org.example.repository.ReadingRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillingProcessor {
    private final BillingGenerator billingGenerator;
    private final ReadingRepository readingRepository;
    private final PriceRepository priceRepository;

    private final UserRepository userRepository;
    private final BillingFileWriterAndFolderCreator billingFileWriterAndFolderCreator;
    @Autowired
    public BillingProcessor(BillingGenerator billingGenerator, ReadingRepository readingRepository,UserRepository userRepository, PriceRepository priceRepository,
                            BillingFileWriterAndFolderCreator billingFileWriterAndFolderCreator) {
        this.billingGenerator = billingGenerator;
        this.readingRepository = readingRepository;
        this.priceRepository = priceRepository;
        this.userRepository = userRepository;
        this.billingFileWriterAndFolderCreator = billingFileWriterAndFolderCreator;
    }

    public void processBillings(String outputDirectory) throws IOException {
        int documentNumber = 10000;
        // Convert the list of users from the database to a map
        Map<String, User> users = userRepository.findAll().stream()
                .collect(Collectors.toMap(user -> String.valueOf(user.getId()), user -> user));
        for (User user : users.values()) {
            List<Reading> readings = readingRepository.findAllByReferenceNumber(user.getReferenceNumber());
            List<Price> prices = priceRepository.findByPriceListNumber(user.getPriceListNumber());
            Billing billing = billingGenerator.generateBilling(user, documentNumber, readings, prices);
            billingFileWriterAndFolderCreator.writeBilling(billing, documentNumber, outputDirectory);
            documentNumber++;
        }
    }
}
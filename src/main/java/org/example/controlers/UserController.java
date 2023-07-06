package org.example.controlers;

import org.example.entity.Price;
import org.example.entity.Reading;
import org.example.entity.User;
import org.example.repository.PriceRepository;
import org.example.repository.ReadingRepository;
import org.example.repository.UserRepository;
import org.example.service.Billing;
import org.example.service.BillingGenerator;
import org.example.service.LineItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final ReadingRepository readingRepository;
    private final PriceRepository priceRepository;

    @Autowired
    public UserController(UserRepository userRepository,
                          ReadingRepository readingRepository,
                          PriceRepository priceRepository) {
        this.userRepository = userRepository;
        this.readingRepository = readingRepository;
        this.priceRepository = priceRepository;
    }
    @Autowired
    private BillingGenerator billingGenerator;
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User newUser = userRepository.save(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PostMapping("{userRef}/reading")
    public ResponseEntity<String> addReadingAndGenerateBilling(@PathVariable String userRef, @RequestBody Reading newReading) {
        User user = userRepository.findByReferenceNumber(userRef);

        // Save the new reading.
        readingRepository.save(newReading);

        // Fetch the last two readings.
        List<Reading> allReadings = readingRepository.findAllByReferenceNumberOrderByDateTimeDesc(user.getReferenceNumber());
        List<Reading> lastTwoReadings = allReadings.stream().limit(2).collect(Collectors.toList());
        Collections.reverse(lastTwoReadings);
        List<Price> prices = priceRepository.findAll();

        int documentNumber = 10000;

        Billing billing = billingGenerator.generateBilling(user, documentNumber, lastTwoReadings, prices);

        // Get the last line item and its price.
        List<LineItem> lineItems = billing.getLines();
        String lastLinePrice = null;
        if (!lineItems.isEmpty()) {
            LineItem lastLineItem = lineItems.get(lineItems.size() - 1);
            lastLinePrice = lastLineItem.getAmount();
        }

        return ResponseEntity.ok(lastLinePrice);
    }
    @GetMapping("/{userRef}")
    public ResponseEntity<User> getUserByReferenceNumber(@PathVariable String userRef) {
        User user = userRepository.findByReferenceNumber(userRef);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

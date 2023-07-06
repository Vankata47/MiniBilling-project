package org.example.controlers;

import org.example.entity.Reading;
import org.example.entity.User;
import org.example.repository.ReadingRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userRef}/readings")
public class ReadingController {

    private final UserRepository userRepository;
    private final ReadingRepository readingRepository;

    @Autowired
    public ReadingController(UserRepository userRepository, ReadingRepository readingRepository) {
        this.userRepository = userRepository;
        this.readingRepository = readingRepository;
    }

    @PostMapping
    public ResponseEntity<Reading> createReadingForUser(@PathVariable String userRef, @RequestBody Reading reading) {
        User user = userRepository.findByReferenceNumber(userRef);
        if (user != null) {
            reading.setReferenceNumber(user.getReferenceNumber());
            Reading newReading = readingRepository.save(reading);
            return new ResponseEntity<>(newReading, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Reading>> getUserReadings(@PathVariable("userRef") String userRef) {
        User user = userRepository.findByReferenceNumber(userRef);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        List<Reading> readings = readingRepository.findAllByReferenceNumber(userRef);
        return ResponseEntity.ok(readings);
    }
}

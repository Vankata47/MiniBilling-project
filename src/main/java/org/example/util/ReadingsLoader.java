package org.example.util;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.example.entity.Reading;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReadingsLoader {
    public static List<Reading> loadReadings(File readingsFile) {
        List<Reading> readings = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(readingsFile))) {
            List<String[]> records = reader.readAll();
            int id = 1; // new line
            for (String[] record : records) {
                String referenceNumber = record[0];
                String product = record[1];

                String[] dateTimeParts = record[2].split("T");

                LocalDate date = LocalDate.parse(dateTimeParts[0], DateTimeFormatter.ISO_LOCAL_DATE);

                LocalDateTime dateTime = LocalDateTime.parse(record[2], DateTimeFormatter.ISO_DATE_TIME);

                String dateTimeAsString = DateTimeFormatter.ISO_DATE_TIME.format(dateTime);

                double reading = Double.parseDouble(record[3]);

                readings.add(new Reading(id++, referenceNumber, product, date, dateTimeAsString, reading)); // modified line
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
        return readings;
    }
}


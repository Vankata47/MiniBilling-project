package org.example.util;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.example.entity.Price;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PricesLoader {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static Map<String, List<Price>> loadPrices(String inputDir) {
        Map<String, List<Price>> prices = new HashMap<>();
        File dir = new File(inputDir);
        int id = 1;
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.isFile() && file.getName().matches("^prices-\\d+.csv$")) {
                String fileName = file.getName();
                String[] splitName = fileName.split("-");
                String numberString = splitName[1].substring(0, splitName[1].lastIndexOf('.'));
                int priceListNumber = Integer.parseInt(numberString);

                try (CSVReader reader = new CSVReader(new FileReader(file))) {
                    List<String[]> records = reader.readAll();
                    for (String[] record : records) {
                        String product = record[0];
                        LocalDate startDate = LocalDate.parse(record[1], DATE_FORMATTER);
                        LocalDate endDate = LocalDate.parse(record[2], DATE_FORMATTER);
                        double price = Double.parseDouble(record[3]);
                        prices.computeIfAbsent(product, k -> new ArrayList<>())
                                .add(new Price(id++, priceListNumber, product, startDate, endDate, price));
                    }
                } catch (IOException | CsvException e) {
                    e.printStackTrace();
                }
            }
        }
        return prices;
    }
}




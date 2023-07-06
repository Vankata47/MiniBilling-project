package org.example.service;

import org.example.entity.Price;
import org.example.entity.Reading;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class HelperMethodsForBillingGenerator {
    public static LineItem createNewLineItem(int index, ZonedDateTime lineStart, ZonedDateTime lineEnd, String product, int priceList, double quantity, double price, double amount) {
        LineItem lineItem = new LineItem();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssxxx");

        lineItem.setIndex(index);
        lineItem.setLineStart(lineStart.format(formatter));
        lineItem.setLineEnd(lineEnd.format(formatter));
        lineItem.setProduct(product);
        lineItem.setPriceList(priceList);
        lineItem.setUnit("KW/h");
        lineItem.setQuantity(new DecimalFormat("#0.00").format(quantity));
        lineItem.setPrice(new DecimalFormat("#0.00").format(price));
        lineItem.setAmount(new DecimalFormat("#0.00").format(amount));

        return lineItem;
    }
    public static Tax createNewTaxItem(int lineIndex, int index, ZonedDateTime start, ZonedDateTime end, String taxName, double quantity, double price, double amount) {
        Tax taxItem = new Tax();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssxxx");

        taxItem.setIndex(index);
        List<Number> lines = new ArrayList<>();
        lines.add(lineIndex);
        taxItem.setLines(lines);
        taxItem.setStart(start.format(formatter));
        taxItem.setEnd(end.format(formatter));
        taxItem.setUnit("days");
        taxItem.setName(taxName);
        taxItem.setQuantity(quantity);
        taxItem.setPrice(price);
        taxItem.setAmount(amount);

        return taxItem;
    }
    public static Vat createNewVatItem(int index, int lines, int taxes, double gas, double elect,double gasPrice, double electPrice, double amount, int priceListNum) {
        Vat vat = new Vat();
        double electPercentage = 0;
        double gasPercentage = 0;
        vat.setIndex(index);
        vat.setLines(generateNumberList(lines));
        vat.setTaxes(generateNumberList(taxes));
        if (priceListNum == 2){
            if (elect > 1000) {
                electPercentage = 20;
            } else {
                electPercentage = 5;
            }
        if (gas > 4397) {
            gasPercentage = 20;
        } else {
            gasPercentage = 5;
        }
        }
        else {electPercentage = 20; gasPercentage = 20;}
        List<Number> percentages = new ArrayList<>();
        percentages.add(electPercentage);
        percentages.add(gasPercentage);
        vat.setPercentage(percentages);
        vat.setAmount((gasPrice * gasPercentage/100)+(electPrice * electPercentage/100));

        return vat;
    }

    private static List<Number> generateNumberList(int count) {
        List<Number> numbers = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            numbers.add(i);
        }
        return numbers;
    }
    static long getDaysBetween(LocalDate start, LocalDate end) {
        return ChronoUnit.DAYS.between(start, end) + 1;
    }

    static double roundValue(double value) {
        return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    static boolean isPriceInPeriod(Price price, Reading initialReading, Reading finalReading) {
        return (initialReading.getDate().isAfter(price.getStartDate()) || initialReading.getDate().isEqual(price.getStartDate()))
                && (finalReading.getDate().isBefore(price.getEndDate()) || finalReading.getDate().isEqual(price.getEndDate()));
    }

    static boolean isPriceStartingInPeriod(Price price, Reading initialReading) {
        return initialReading.getDate().isAfter(price.getStartDate()) || initialReading.getDate().isEqual(price.getStartDate());
    }

    static boolean isPriceInsidePeriod(Price price, Reading initialReading, Reading finalReading) {
        return price.getStartDate().isAfter(initialReading.getDate()) && price.getEndDate().isBefore(finalReading.getDate());
    }

    static boolean isPriceEndingInPeriod(Price price, Reading finalReading) {
        return price.getStartDate().isBefore(finalReading.getDate());
    }
}

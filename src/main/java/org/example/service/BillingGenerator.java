package org.example.service;

import org.example.entity.Price;
import org.example.entity.Reading;
import org.example.entity.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BillingGenerator {

    public BillingGenerator() {
    }

    public Billing generateBilling(User user, int documentNumber, List<Reading> readings, List<Price> prices) {

        Map<String, List<Price>> priceMap = prices.stream()
                .collect(Collectors.groupingBy(Price::getProduct));
        LocalDateTime now = LocalDateTime.now();
        String DocumentDate = now.format(DateTimeFormatter.ISO_DATE_TIME);

        Reading initialReading = null;
        Reading finalReading = null;
        double totalCost = 0;
        int lineItemIndex = 1;
        int taxItemIndex = 1;

        Billing billing = new Billing();
        billing.setDocumentDate(DocumentDate);
        billing.setDocumentNumber(String.valueOf(documentNumber));
        billing.setConsumer(user.getName());
        billing.setReference(user.getReferenceNumber());

        List<LineItem> lineItems = new ArrayList<>();
        List<Tax> tax = new ArrayList<>();
        double gasQuantity = 0;
        double electQuantity = 0;
        double gasPrice = 0;
        double electPrice = 0;
        for (int y = 0; y < readings.size(); y++) {
            Reading reading = readings.get(y);

            // взимаме първия reading
            if(initialReading != null && finalReading!= null) {
                initialReading = null;
                finalReading = null;
                reading = readings.get(y);
            }
            if(initialReading != null && reading.getProduct().equals(initialReading.getProduct())) {
                // Запазваме последния reading
                finalReading = reading;

                List<Price> productPrices = priceMap.get(finalReading.getProduct());

                double totalConsumption = finalReading.getReading() - initialReading.getReading();
                long totalDaysBetween = ChronoUnit.DAYS.between(initialReading.getDateTime(), finalReading.getDateTime()) + 1;
                double consumptionPerDay = totalConsumption / totalDaysBetween;


                for (Price price : productPrices) {
                    double roundedQuantity, roundedPrice, costForThisLine;
                    long daysBetween = 0;
                    ZonedDateTime lineStart = initialReading.getDateTime();
                    ZonedDateTime lineEnd = finalReading.getDateTime();


                        if (HelperMethodsForBillingGenerator.isPriceInPeriod(price, initialReading, finalReading)) {
                            if(HelperMethodsForBillingGenerator.getDaysBetween(initialReading.getDate(), finalReading.getDate()) >= 1){
                            daysBetween = HelperMethodsForBillingGenerator.getDaysBetween(initialReading.getDate(), finalReading.getDate());}
                        } else if (HelperMethodsForBillingGenerator.isPriceStartingInPeriod(price, initialReading)) {
                            if(HelperMethodsForBillingGenerator.getDaysBetween(initialReading.getDate(), price.getEndDate()) >= 1){
                            daysBetween = HelperMethodsForBillingGenerator.getDaysBetween(initialReading.getDate(), price.getEndDate());
                            lineEnd = price.getEndDate().atStartOfDay(ZoneId.of("Europe/Athens")).with(LocalTime.MAX);}
                        } else if (HelperMethodsForBillingGenerator.isPriceInsidePeriod(price, initialReading, finalReading)) {
                            if(HelperMethodsForBillingGenerator.getDaysBetween(price.getStartDate(), price.getEndDate()) >= 1){
                            daysBetween = HelperMethodsForBillingGenerator.getDaysBetween(price.getStartDate(), price.getEndDate());
                            lineStart = price.getStartDate().atStartOfDay(ZoneId.of("Europe/Athens"));
                            lineEnd = price.getEndDate().atStartOfDay(ZoneId.of("Europe/Athens")).with(LocalTime.MAX);}
                        } else if (HelperMethodsForBillingGenerator.isPriceEndingInPeriod(price, finalReading)) {
                            if(HelperMethodsForBillingGenerator.getDaysBetween(price.getStartDate(), finalReading.getDate()) >= 1){
                            daysBetween = HelperMethodsForBillingGenerator.getDaysBetween(price.getStartDate(), finalReading.getDate());
                            lineStart = price.getStartDate().atStartOfDay(ZoneId.of("Europe/Athens"));}
                        } else {
                            continue;
                        }
                    if(daysBetween >= 1) {
                        roundedQuantity = HelperMethodsForBillingGenerator.roundValue(consumptionPerDay * daysBetween);
                        roundedPrice = HelperMethodsForBillingGenerator.roundValue(price.getPrice());
                        costForThisLine = HelperMethodsForBillingGenerator.roundValue(consumptionPerDay * daysBetween * price.getPrice());

                        LineItem lineItem = HelperMethodsForBillingGenerator.createNewLineItem(lineItemIndex, lineStart, lineEnd, initialReading.getProduct(), user.getPriceListNumber(), roundedQuantity, roundedPrice, costForThisLine);
                        Tax taxItem = HelperMethodsForBillingGenerator.createNewTaxItem(lineItemIndex, taxItemIndex, lineStart, lineEnd, "Standing Charge",daysBetween , 1.5, (daysBetween * 1.5));
                        taxItemIndex++;
                        Tax taxItemCCL = HelperMethodsForBillingGenerator.createNewTaxItem(lineItemIndex , taxItemIndex, lineStart, lineEnd, "CCL",roundedQuantity , 0.05, (roundedQuantity * 0.05));
                        taxItemIndex++;
                        lineItems.add(lineItem);
                        tax.add(taxItem);
                        tax.add(taxItemCCL);
                        if( initialReading.getProduct().equals("gas"))
                        {
                            gasQuantity+= roundedQuantity;
                            gasPrice += costForThisLine + taxItem.getAmount() + taxItemCCL.getAmount();
                        }
                        else {
                            electQuantity += roundedQuantity;
                            electPrice += consumptionPerDay + taxItem.getAmount() + taxItemCCL.getAmount();
                        }
                        lineItemIndex++;
                        totalCost += (costForThisLine + taxItem.getAmount() + taxItemCCL.getAmount());
                    }
                }
            }
            if (initialReading == null) {
                initialReading = reading;
            }
        }



        Vat vat = HelperMethodsForBillingGenerator.createNewVatItem(1, lineItemIndex - 1, taxItemIndex - 1, gasQuantity, electQuantity, gasPrice, electPrice, totalCost, user.getPriceListNumber());
        BigDecimal bd = new BigDecimal(Double.toString(totalCost));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        totalCost = bd.doubleValue();
        billing.setVat(vat);
        billing.setTotalAmount(totalCost);
        totalCost += vat.getAmount();
        billing.setTotalAmountDDS(totalCost);
        billing.setLines(lineItems);
        billing.setTaxes(tax);
        return billing;
    }
}


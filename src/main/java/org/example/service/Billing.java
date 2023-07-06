package org.example.service;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({"documentDate", "documentNumber", "consumer", "reference", "totalAmount","totalAmountDDS", "lines", "taxes", "vat"})
public class Billing {
    private String documentDate;
    private String documentNumber;
    private String consumer;
    private String reference;
    private Number totalAmount;
    private Number totalAmountDDS;
    private List<LineItem> lines;
    private List<Tax> taxes;
    private Vat vat;

    public Billing() {
        this.lines = new ArrayList<>();
        this.taxes = new ArrayList<>();
        this.vat = new Vat();
    }

    public String getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(String documentDate) {
        this.documentDate = documentDate;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getConsumer() {
        return consumer;
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Number getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Number totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Number getTotalAmountDDS() {
        return totalAmountDDS;
    }
    public void setTotalAmountDDS(Number totalAmountDDS) {
        this.totalAmountDDS = totalAmountDDS;
    }

    public List<LineItem> getLines() {
        return lines;
    }

    public void setLines(List<LineItem> lines) {
        this.lines = lines;
    }

    public List<Tax> getTaxes() {
        return taxes;
    }

    public void setTaxes(List<Tax> taxes) {
        this.taxes = taxes;
    }

    public Vat getVat() {
        return vat;
    }

    public void setVat(Vat vat) {
        this.vat = vat;
    }

    public String toFormattedString() {
        StringBuilder sb = new StringBuilder();
        for (LineItem line : this.getLines()) {
            sb.append(line.getLineStart()).append(",")
                    .append(line.getLineEnd()).append(",")
                    .append(line.getQuantity()).append(",")
                    .append(line.getPrice()).append("\n");
        }

        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }
}

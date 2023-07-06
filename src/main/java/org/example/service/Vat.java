package org.example.service;

import java.util.ArrayList;
import java.util.List;

public class Vat {
    private Number index;
    private List<Number> lines;
    private List<Number> taxes;

    private List<Number> percentage;
    private double amount;

    public Vat() {
        this.lines = new ArrayList<>();
        this.taxes = new ArrayList<>();
    }

    public Number getIndex() {
        return index;
    }

    public void setIndex(Number index) {
        this.index = index;
    }

    public List<Number> getLines() {
        return lines;
    }

    public void setLines(List<Number> lines) {
        this.lines = lines;
    }

    public List<Number> getTaxes() {
        return taxes;
    }

    public void setTaxes(List<Number> taxes) {
        this.taxes = taxes;
    }

    public List<Number> getPercentage() {
        return percentage;
    }

    public void setPercentage(List<Number> percentage) {
        this.percentage = percentage;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}

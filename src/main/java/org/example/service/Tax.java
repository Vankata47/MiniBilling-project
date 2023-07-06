package org.example.service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class Tax {
    private Number index;
    private List<Number> lines;
    private String name;
    private double quantity;
    private String start;
    private String end;
    private String unit;
    private Number price;
    private double amount;

    public Tax() {
        this.lines = new ArrayList<>();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Number getPrice() {
        return price;
    }

    public void setPrice(Number price) {
        this.price = price;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}

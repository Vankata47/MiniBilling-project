package org.example.entity;

import javax.persistence.*;
import java.time.*;

@Entity
@Table(name = "readings")
public class Reading {

    @Id
    private Long id;

    @Column(name = "reference_number")
    private String referenceNumber;

    @Column(name = "product")
    private String product;

    @Column(name = "date")
    private LocalDate date;

    private ZonedDateTime dateTime;

    @Column(name = "date_time_as_string")
    private String dateTimeAsString;

    @Column(name = "reading")
    private double reading;

    public Reading() {}

    public Reading(long ID, String referenceNumber, String product, LocalDate date, String dateTimeAsString, double reading) {
        this.id = ID;
        this.referenceNumber = referenceNumber;
        this.product = product;
        this.date = date;
        this.dateTime = date.atStartOfDay(ZoneId.of("Europe/Athens"));
        this.dateTimeAsString = dateTimeAsString;
        this.reading = reading;
    }

    public Reading(ZonedDateTime date, double reading, String referenceNumber, String product) {
        this.date = LocalDate.from(date);
        this.dateTime = date;
        this.reading = reading;
        this.referenceNumber = referenceNumber;
        this.product = product;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getReferenceNumber() { return referenceNumber; }
    public void setReferenceNumber(String referenceNumber) { this.referenceNumber = referenceNumber; }

    public String getProduct() { return product; }
    public void setProduct(String product) { this.product = product; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public ZonedDateTime getDateTime() { return dateTime; }
    public void setDateTime(ZonedDateTime dateTime) { this.dateTime = dateTime; }
    public String getDateTimeAsString() { return dateTimeAsString; }
    public void setDateTimeAsString(String dateTimeAsString) { this.dateTimeAsString = dateTimeAsString; }

    public double getReading() { return reading; }
    public void setReading(double reading) { this.reading = reading; }
}



package org.example.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "prices")
public class Price {

    @Id
    private Long id;

    @Column(name = "price_list_number")
    private  int priceListNumber;
    @Column(name = "product")
    private String product;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "price")
    private double price;

    public Price() {}

    public Price(long ID,int priceListNumber, String product, LocalDate startDate, LocalDate endDate, double price) {
        this.id = ID;
        this.priceListNumber = priceListNumber;
        this.product = product;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getPriceListNumber() {return  priceListNumber; }
    public void setPriceListNumber(int priceListNumber) {this.priceListNumber = priceListNumber; }

    public String getProduct() { return product; }
    public void setProduct(String product) { this.product = product; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }


    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}


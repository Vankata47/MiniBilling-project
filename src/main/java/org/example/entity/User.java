package org.example.entity;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "reference_number")
    private String referenceNumber;

    @Column(name = "price_list_number")
    private int priceListNumber;

    public User() {}

    public User(int Id, String name, String referenceNumber, int priceListNumber) {
        this.id = Id;
        this.name = name;
        this.referenceNumber = referenceNumber;
        this.priceListNumber = priceListNumber;
    }

    // getters and setters
    public Long getId() { return (long) id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getReferenceNumber() { return referenceNumber; }
    public void setReferenceNumber(String referenceNumber) { this.referenceNumber = referenceNumber; }

    public int getPriceListNumber() { return priceListNumber; }
    public void setPriceListNumber(int priceListNumber) { this.priceListNumber = priceListNumber; }
}



package org.example.service;


public class LineItem {
    private Number index;
    private String quantity;
    private String lineStart;
    private String lineEnd;
    private String product;

    private String unit;
    private String price;
    private Number priceList;
    private String amount;

    public LineItem() {
    }

    public Number getIndex() {
        return index;
    }

    public void setIndex(Number index) {
        this.index = index;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getLineStart() {
        return lineStart;
    }


    public void setLineStart(String lineStart) {
        this.lineStart = lineStart;
    }

    public String getLineEnd() {
        return lineEnd;
    }

    public void setLineEnd(String lineEnd) {
        this.lineEnd = lineEnd;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Number getPriceList() {
        return priceList;
    }

    public void setPriceList(Number priceList) {
        this.priceList = priceList;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}

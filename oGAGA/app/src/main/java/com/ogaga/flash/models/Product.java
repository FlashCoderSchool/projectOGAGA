package com.ogaga.flash.models;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by Kanet on 4/13/2016.
 */
@Parcel
public class Product {
    private long id;
    private String name;
    private String url;
    private long prices;
    private long id_productStatus;
    private long create_at;
    private long start_date;
    private long finished_date;
    private long id_shipping;
    private long id_unit;
    private long id_productType;
    private User user;
    private List<Order> orders;

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getPrices() {
        return prices;
    }

    public void setPrices(long prices) {
        this.prices = prices;
    }

    public long getId_productStatus() {
        return id_productStatus;
    }

    public void setId_productStatus(long id_productStatus) {
        this.id_productStatus = id_productStatus;
    }

    public long getCreate_at() {
        return create_at;
    }

    public void setCreate_at(long create_at) {
        this.create_at = create_at;
    }

    public long getStart_date() {
        return start_date;
    }

    public void setStart_date(long start_date) {
        this.start_date = start_date;
    }

    public long getFinished_date() {
        return finished_date;
    }

    public void setFinished_date(long finished_date) {
        this.finished_date = finished_date;
    }

    public long getId_shipping() {
        return id_shipping;
    }

    public void setId_shipping(long id_shipping) {
        this.id_shipping = id_shipping;
    }

    public long getId_unit() {
        return id_unit;
    }

    public void setId_unit(long id_unit) {
        this.id_unit = id_unit;
    }

    public long getId_productType() {
        return id_productType;
    }

    public void setId_productType(long id_productType) {
        this.id_productType = id_productType;
    }

    public Product() {
    }

    public Product(long id, String name, String url, long prices, long id_productStatus, long create_at, long start_date, long finished_date, long id_shipping, long id_unit, long id_productType, User user, List<Order> orders) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.prices = prices;
        this.id_productStatus = id_productStatus;
        this.create_at = create_at;
        this.start_date = start_date;
        this.finished_date = finished_date;
        this.id_shipping = id_shipping;
        this.id_unit = id_unit;
        this.id_productType = id_productType;
        this.user = user;
        this.orders = orders;
    }
}

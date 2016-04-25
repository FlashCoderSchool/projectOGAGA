package com.ogaga.flash.models;

import org.parceler.Parcel;

/**
 * Created by Kanet on 4/25/2016.
 */
@Parcel
public class Order {
    private User user;
    private Integer count;
    private long created_at;
    private long prices;
    private int id;
    private int id_shipping_status;
    private long id_product;

    public long getId_product() {
        return id_product;
    }

    public void setId_product(long id_product) {
        this.id_product = id_product;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_shipping_status() {
        return id_shipping_status;
    }

    public void setId_shipping_status(int id_shipping_status) {
        this.id_shipping_status = id_shipping_status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    public long getPrices() {
        return prices;
    }

    public void setPrices(long prices) {
        this.prices = prices;
    }

    public Order(User user, Integer count, long created_at, long prices, int id, int id_shipping_status, long id_product) {
        this.user = user;
        this.count = count;
        this.created_at = created_at;
        this.prices = prices;
        this.id = id;
        this.id_shipping_status = id_shipping_status;
        this.id_product = id_product;
    }

    public Order() {
    }
}

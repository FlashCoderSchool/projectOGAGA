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

    public Order(User user, Integer count, long created_at, long prices) {
        this.user = user;
        this.count = count;
        this.created_at = created_at;
        this.prices = prices;
    }

    public Order() {
    }
}

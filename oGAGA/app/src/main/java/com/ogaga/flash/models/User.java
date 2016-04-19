package com.ogaga.flash.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.SyncStateContract;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.MutableData;
import com.firebase.client.Query;
import com.firebase.client.Transaction;
import com.firebase.client.ValueEventListener;
import com.firebase.client.core.Constants;
import com.ogaga.flash.clients.FirebaseClient;
import com.ogaga.flash.imgurmodel.ImageResponse;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;


/**
 * Created by Kanet on 4/13/2016.
 */

public class User {
    public interface LoginUser{
        public void onLoginSuccess(User user);
    }
    public LoginUser mListener;
    private long id;
    private String fullname;
    private String profile_image;
    private String address_user;
    private String location;
    private long followed_count;
    private String phonenumber;
    private long success_transaction;
    private long created_at;
    private String email;
    public String getEmail() {
        return this.phonenumber+"@ogaga.com";
    }


    public long getId() {
        return id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public String getAddress_user() {
        return address_user;
    }

    public String getLocation() {
        return location;
    }

    public long getFollowed_count() {
        return followed_count;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public long getSuccess_transaction() {
        return success_transaction;
    }

    public long getCreated_at() {
        return created_at;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public void setAddress_user(String address_user) {
        this.address_user = address_user;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setFollowed_count(long followed_count) {
        this.followed_count = followed_count;
    }


    public void setSuccess_transaction(long success_transaction) {
        this.success_transaction = success_transaction;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    public User(long id, String fullname, String profile_image, String address_user, String location, long followed_count, String phonenumber, long success_transaction, long created_at) {
        this.id = id;
        this.fullname = fullname;
        this.profile_image = profile_image;
        this.address_user = address_user;
        this.location = location;
        this.followed_count = followed_count;
        this.phonenumber = phonenumber;
        this.success_transaction = success_transaction;
        this.created_at = created_at;
    }

    public User() {
        this.followed_count = 0;
        this.phonenumber = "";
        this.success_transaction = 0;
        this.created_at = System.currentTimeMillis();
    }

    public void getUserLogin(String uid,LoginUser listener) {
        this.mListener=listener;
        Firebase mFirebaseUser=FirebaseClient.getUsers();
        Query queryRef = mFirebaseUser.child(uid);
        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                mListener.onLoginSuccess(user);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }



}

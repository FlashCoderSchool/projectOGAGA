package com.ogaga.flash.acitivies;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.MutableData;
import com.firebase.client.Transaction;
import com.ogaga.flash.Fragments.OrderProductFragment;
import com.ogaga.flash.R;
import com.ogaga.flash.clients.FirebaseClient;
import com.ogaga.flash.models.Order;
import com.ogaga.flash.models.Product;
import com.ogaga.flash.models.User;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductDetailActivity extends AppCompatActivity {
    Product mProduct;
    User mUser;
    @Bind(R.id.fabSell)
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ButterKnife.bind(this);
        mProduct= Parcels.unwrap(getIntent().getParcelableExtra("product"));
        mUser= Parcels.unwrap(getIntent().getParcelableExtra("user"));
    }

    @OnClick(R.id.fabSell)
    public void onClickOrder(View view){
        final OrderProductFragment settingFragment=new OrderProductFragment();
        final Firebase firebaseProducts=FirebaseClient.getProduct();
        settingFragment.setOnOrderListener(new OrderProductFragment.OnOrderListener() {
            @Override
            public void onOrder(Integer count) {
                final Integer orderCount=count;
                firebaseProducts.runTransaction(new Transaction.Handler() {

                    @Override
                    public Transaction.Result doTransaction(MutableData mutableData) {
                        return Transaction.success(mutableData); //we can also abort by calling Transaction.abort()
                    }

                    @Override
                    public void onComplete(FirebaseError firebaseError, boolean b, DataSnapshot dataSnapshot) {
                        Order order = new Order();
                        order.setUser(mUser);
                        order.setCount(orderCount);
                        order.setCreated_at(System.currentTimeMillis());
                        order.setPrices(mProduct.getPrices());

                        Firebase firebaseOrders = FirebaseClient.getProduct().child(String.valueOf(mProduct.getId())).child("orders");
                        firebaseOrders.setValue(String.valueOf(mUser.getId()));
                        Firebase firebaseOrder = firebaseOrders.child(String.valueOf(mUser.getId()));
                        firebaseOrder.setValue(order);
                    }
                });

                }
            }

            );
            settingFragment.show(

                    getFragmentManager(),

                    "orderProduct");

        }
    }

package com.ogaga.flash.acitivies;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.MutableData;
import com.firebase.client.Transaction;
import com.ogaga.flash.R;
import com.ogaga.flash.clients.FirebaseClient;
import com.ogaga.flash.fragments.OrderProductFragment;
import com.ogaga.flash.fragments.OrderViewFragment;
import com.ogaga.flash.models.Order;
import com.ogaga.flash.models.Product;
import com.ogaga.flash.models.User;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductDetailActivity extends AppCompatActivity {
    Product mProduct;
    User mUser;
    //    @Bind(R.id.fabBuy)
//    FloatingActionButton fab;
    @Bind(R.id.ivProductDetailImage)
    ImageView ivProductDetailImage;
    @Bind(R.id.tvProductDetailName)
    TextView tvProductDetailName;
    @Bind(R.id.tvProductDetailStatus)
    TextView tvProductDetailStatus;
    @Bind(R.id.tvProductDetailDesciption)
    TextView tvProductDetailDescription;
    @Bind(R.id.tvProductDetailOrigin)
    TextView tvProductDetailOrigin;
    @Bind(R.id.tvProductDetailPrice)
    TextView tvProductDetailPrice;
    @Bind(R.id.iv_Producer_Avatar) ImageView ivProducerPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ButterKnife.bind(this);
        mProduct = Parcels.unwrap(getIntent().getParcelableExtra("product"));
        mUser = Parcels.unwrap(getIntent().getParcelableExtra("user"));

        setupContent();
    }

    private void setupContent() {
        Picasso.with(getApplicationContext()).load(mProduct.getUrl()).placeholder(R.drawable.im_placeholder).into(ivProductDetailImage);
        tvProductDetailName.setText(mProduct.getName());
        tvProductDetailPrice.setText(String.valueOf(mProduct.getPrices()));
        //Status
        String stsMsg = String.valueOf(mProduct.countDays()) + " day";
        if (mProduct.countDays() > 1) {
            stsMsg += "s";
            tvProductDetailStatus.setText(stsMsg);
            tvProductDetailStatus.setTextColor(Color.BLUE);
        } else if (mProduct.countDays() == 0) {
            tvProductDetailStatus.setText("FRESH");
            tvProductDetailStatus.setTextColor(Color.GREEN);
        } else {
            stsMsg += "s";
            tvProductDetailStatus.setText(stsMsg);
            tvProductDetailStatus.setTextColor(Color.RED);
        }
        //Description
        //
        // Producer Image
        Picasso.with(getApplicationContext()).load(mProduct.getUserSell().getProfile_image()).placeholder(R.drawable.im_placeholder).into(ivProducerPhoto);
        //
        tvProductDetailOrigin.setText(mProduct.getUserSell().getAddress_user());
//        tvProductDetailStatus.setText();
//        tvFullName.setText(mUser.getFullname());
        //        tvProductDetailOrigin.setText(mUser.getAddress_user());
    }

    @OnClick(R.id.fabBuy)
    public void onClickOrder(View view) {
        if (mProduct.getUserSell().getId_user() == mUser.getId_user()) {
            final OrderViewFragment settingFragment = new OrderViewFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("product", Parcels.wrap(mProduct));
            settingFragment.setArguments(bundle);
            settingFragment.show(getFragmentManager(), "orderView");
        } else {
            final OrderProductFragment settingFragment = new OrderProductFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("product", Parcels.wrap(mProduct));
            settingFragment.setArguments(bundle);
            final Firebase firebaseOrders = FirebaseClient.getProduct().child(String.valueOf(mProduct.getId())).child("orders");
            settingFragment.setOnOrderListener(new OrderProductFragment.OnOrderListener() {
                                                   @Override
                                                   public void onOrder(Integer count) {
                                                       final Integer orderCount = count;
                                                       firebaseOrders.runTransaction(new Transaction.Handler() {

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
                                                               order.setId_product(mProduct.getId());

                                                               //firebaseOrders.setValue(String.valueOf(dataSnapshot.getChildrenCount()+1));
                                                               Firebase firebaseOrder = firebaseOrders.child(String.valueOf(dataSnapshot.getChildrenCount() + 1));
                                                               firebaseOrder.setValue(order);
                                                           }
                                                       });

                                                   }
                                               }
            );
            settingFragment.show(getFragmentManager(), "orderProduct");
        }
    }
}

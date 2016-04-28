package com.ogaga.flash.adapters;

import android.content.Context;
import android.graphics.Color;

import com.firebase.client.Query;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.ogaga.flash.R;
import com.ogaga.flash.models.Product;
import com.ogaga.flash.models.User;
import com.squareup.picasso.Picasso;

/**
 * Created by IceStone on 4/24/2016.
 */
public class ProductRecyclerViewAdapter extends FirebaseRecyclerAdapter<Product, ProductItemViewHolder> {

    private Context context;
    private User mUser;

    public ProductRecyclerViewAdapter(Query ref, Context parentContext, User user) {
        super(Product.class, R.layout.product_item, ProductItemViewHolder.class, ref);
        context = parentContext;
        mUser = user;
    }

    @Override
    public void populateViewHolder(ProductItemViewHolder viewHolder, Product product, int i) {
        viewHolder.bindUser(mUser);
        viewHolder.bind(product);
        Picasso.with(context).load(product.getUrl()).into(viewHolder.productImage);
        viewHolder.productName.setText(product.getName());
        viewHolder.producer.setText(product.getUserSell().getFullname());
        viewHolder.productOrigin.setText(product.getUserSell().getAddress_user());
        viewHolder.productPrice.setText(String.valueOf(product.getPrices()) + " VND");
        viewHolder.productPrice.setTextColor(Color.RED);

        //Status calculate
        String statusMsg = String.valueOf(product.countDays()) + " day";
        if (product.countDays() > 1) {
            statusMsg += "s";
            viewHolder.productStatus.setText(statusMsg);
            viewHolder.productStatus.setTextColor(Color.BLUE);
        } else if (product.countDays() == 0){
            viewHolder.productStatus.setText("FRESH");
            viewHolder.productStatus.setTextColor(Color.GREEN);
        } else {
            statusMsg += "s";
            viewHolder.productStatus.setText(statusMsg);
            viewHolder.productStatus.setTextColor(Color.RED);
        }
    }
}

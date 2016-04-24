package com.ogaga.flash.adapters;

import android.content.Context;

import com.firebase.client.Firebase;
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
    public ProductRecyclerViewAdapter(Firebase ref, Context parentContext,User user) {
        super(Product.class, R.layout.product_item, ProductItemViewHolder.class, ref);
        context = parentContext;
        mUser=user;
    }

    @Override
    public void populateViewHolder(ProductItemViewHolder viewHolder, Product product, int i) {
        viewHolder.bindUser(mUser);
        viewHolder.bind(product);
        Picasso.with(context).load(product.getUrl()).into(viewHolder.productImage);
        viewHolder.productName.setText(product.getName());
       viewHolder.producer.setText(product.getUser().getFullname());
        viewHolder.productOrigin.setText(product.getUser().getAddress_user());
        viewHolder.productPrice.setText(String.valueOf(product.getPrices()));
//        viewHolder.productStatus.setText(product.getStatus());
    }
}

package com.ogaga.flash.adapters;

import android.content.Context;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.ogaga.flash.R;
import com.ogaga.flash.models.Product;
import com.squareup.picasso.Picasso;

/**
 * Created by IceStone on 4/24/2016.
 */
public class ProductRecyclerViewAdapter extends FirebaseRecyclerAdapter<Product, ProductItemViewHolder> {

    private Context context;

    public ProductRecyclerViewAdapter(Firebase ref, Context parentContext) {
        super(Product.class, R.layout.product_item, ProductItemViewHolder.class, ref);
        context = parentContext;
    }

    @Override
    public void populateViewHolder(ProductItemViewHolder viewHolder, Product product, int i) {
        Picasso.with(context).load(product.getUrl()).into(viewHolder.productImage);
        viewHolder.productName.setText(product.getName());
//        viewHolder.producer.setText(product.getProducer());
//        viewHolder.productOrigin.setText(product.getOrigin());
        viewHolder.productPrice.setText(String.valueOf(product.getPrices()));
//        viewHolder.productStatus.setText(product.getStatus());
    }
}

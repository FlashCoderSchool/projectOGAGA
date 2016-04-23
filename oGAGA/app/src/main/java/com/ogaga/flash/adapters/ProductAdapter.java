package com.ogaga.flash.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.ogaga.flash.R;
import com.ogaga.flash.models.Product;

import butterknife.ButterKnife;

/**
 * Created by carot on 4/23/2016.
 */
public class ProductAdapter extends FirebaseRecyclerAdapter<Product,CategoryAdapter.ViewHolder> {

    private Context context;
    public ProductAdapter(Firebase ref, Context parentContext ) {
        super(Product.class, R.layout.catalogies_item, CategoryAdapter.ViewHolder.class, ref);
        context = parentContext;
    }
    @Override
    protected void populateViewHolder(CategoryAdapter.ViewHolder viewHolder, Product product, int i) {

    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}

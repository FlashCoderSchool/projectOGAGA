package com.ogaga.flash.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ogaga.flash.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by IceStone on 4/24/2016.
 */
public class ProductItemViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.ivProductImage)
    ImageView productImage;
    @Bind(R.id.tvProductName) TextView productName;
    @Bind(R.id.tvProducer) TextView producer;
    @Bind(R.id.tvProductOrigin) TextView productOrigin;
    @Bind(R.id.tvProductPrice) TextView productPrice;
    @Bind(R.id.ivProductStatus) ImageView productStatusImage;
    @Bind(R.id.tvProductStatus) TextView productStatus;

    public ProductItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }



    //    /*-- Without Butterkife --*/
//    public ImageView productImage;
//    public TextView productName;
//    public TextView productProducer;
//    public TextView productOrigin;
//    public TextView productPrice;
//    public ImageView productStatusImage;
//    public TextView productStatus;
//
//    public ProductItemViewHolder(View itemView) {
//        super(itemView);
//        this.productImage = (ImageView) itemView.findViewById(R.id.ivProductImage);
//        this.productName = (TextView) itemView.findViewById(R.id.tvProductName);
//        this.productProducer = (TextView) itemView.findViewById(R.id.tvProducer);
//        this.productOrigin = (TextView) itemView.findViewById(R.id.tvProductOrigin);
//        this.productPrice = (TextView) itemView.findViewById(R.id.tvProductPrice);
//        this.productStatusImage = (ImageView) itemView.findViewById(R.id.ivProductStatus);
//        this.productStatus = (TextView) itemView.findViewById(R.id.tvProductStatus);
//    }
}

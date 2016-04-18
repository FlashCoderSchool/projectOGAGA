package com.ogaga.flash.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ogaga.flash.R;
import com.ogaga.flash.models.Catalogies;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by carot on 4/18/2016.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<Catalogies> cateList;
    private Context context;

    public CategoryAdapter(List<Catalogies> list) {
        cateList = list;
    }

    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View cateView = inflater.inflate(R.layout.catalogies_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(cateView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CategoryAdapter.ViewHolder holder, int position) {

        Catalogies cate = cateList.get(position);
        holder.tvName.setText(cate.getName());
        //holder.ivImage.setImageResource(cate.getLocalImage());
        Picasso.with(context).load(cate.getUrl()).into(holder.ivImage);
    }

    @Override
    public int getItemCount() {
        if (cateList == null) {
            return 0;
        }
        return cateList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ivPicture)
        ImageView ivImage;
        @Bind(R.id.tvName)
        TextView tvName;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
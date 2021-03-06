package com.anton.dietpro.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.anton.dietpro.R;
import com.anton.dietpro.models.CircleTransform;
import com.anton.dietpro.models.Diet;
import com.anton.dietpro.models.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by admin on 07.11.16.
 */

public class ProductAdapter extends BaseAdapter {
    private List<Product> list;
    private LayoutInflater layoutInflater;

    public ProductAdapter(Context contex, List<Product> list) {
        this.list = list;
        this.layoutInflater = (LayoutInflater) contex.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.list.get(position).getId();
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null){
            view = this.layoutInflater.inflate(R.layout.item_layout,parent,false);
        }
        Product product = getProduct(position);
        TextView textView = (TextView) view.findViewById(R.id.textViewItem);
        textView.setText(product.getName());
        TextView textViewSub = (TextView) view.findViewById(R.id.textViewItemSub);
        textViewSub.setText(String.format(view.getResources().getString(R.string.productCalories),(int)product.getCalories(Double.valueOf(1))));
        ImageView productImg = (ImageView) view.findViewById(R.id.productImg);
        if (product.getUrl() != null) {
            if (!product.getUrl().equals("") && product.getUrl() != null) {
                if (product.getUrl().trim().length() > 0) {
                    Picasso.with(parent.getContext())
                            .load(product.getUrl())
                            .transform(new CircleTransform())
                            .placeholder(R.drawable.progress_animation)
                            .error(R.drawable.image_not_load)
                            .into(productImg);

                }
            }
        }
        return view;
    }

    private Product getProduct(int position){
        return (Product)getItem(position);
    }

}

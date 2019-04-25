package com.sample.pixel;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ApiImageAdapterAuc extends BaseAdapter {


    private Context mContext;
    ArrayList<String> eatFoodyImages = new ArrayList<String>();
    ArrayList<String> authNames = new ArrayList<String>();

    public ApiImageAdapterAuc(Context c, ArrayList<String> eatFoodyImages, ArrayList<String> authNames){
        mContext = c;
        this.eatFoodyImages=eatFoodyImages;
        this.authNames=authNames;
    }

    @Override
    public Object getItem(int i) {
        return eatFoodyImages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getCount() {
        return eatFoodyImages.size();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View gridViewAndroid;

        ImageView imageView;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        if(view == null){
//            imageView = new ImageView(mContext);
//
//        }
//        else{
//            imageView = (ImageView) view;
//        }


        if(view==null)
        {
        gridViewAndroid = new View(mContext);
        gridViewAndroid = inflater.inflate(R.layout.grid_view_auc, null);
        ImageView imageViewAndroid = (ImageView) gridViewAndroid.findViewById(R.id.android_gridview_image_auc);
        TextView textViewAndroid = (TextView) gridViewAndroid.findViewById(R.id.android_gridview_text_auc);
        textViewAndroid.setText(authNames.get(i));
        Picasso.with(mContext).setLoggingEnabled(true);
        Picasso.with(mContext)
                .load(eatFoodyImages.get(i))
                .resize(170,200)
                .centerCrop()
                .into(imageViewAndroid);
        }
        else
        {
            gridViewAndroid = (View) view;
        }

        return gridViewAndroid;

    }



}



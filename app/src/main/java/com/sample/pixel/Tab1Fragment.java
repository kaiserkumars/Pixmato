package com.sample.pixel;


import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView;


import com.sample.pixel.R;


public class Tab1Fragment extends Fragment {
    private static final String TAG = "Tab1Fragment";

    private Button btnTEST;
    GridView androidGridView;

    String[] gridSampleStr = {"Alpha", "Beta"," Gamma", "Delta","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday","Monday"};
    int[] gridSampleImg={
            R.drawable.ic_assignment,R.drawable.ic_attach_file,R.drawable.ic_autorenew,R.drawable.ic_autorenew,R.drawable.ic_home_black_24dp,
            R.drawable.ic_map_black_24dp, R.drawable.ic_assignment, R.drawable.ic_mode_comment_black_24dp, R.drawable.ic_autorenew,R.drawable.ic_search_black_24dp,
    R.drawable.ic_favorite_border_black_24dp, R.drawable.ic_assignment};


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1_layout,container,false);
        btnTEST = (Button) view.findViewById(R.id.btnTEST);

        //PROFILE IMAGE

        ImageView  imageView = (ImageView)view.findViewById(R.id.profile_img);
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.propic));

        //GRID IMAGE


        GridView gridview  = (GridView) getActivity().findViewById(R.id.grid_view_image_text);

        CustomGridView adapterViewAndroid = new CustomGridView(getContext(), gridSampleStr, gridSampleImg);
        androidGridView=(GridView)view.findViewById(R.id.grid_view_image_text);
        androidGridView.setAdapter(adapterViewAndroid);
////        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int i, long id) {
//                Toast.makeText(Tab1Fragment.this, "GridView Item: " + gridSampleStr[+i], Toast.LENGTH_LONG).show();
//            }
//        });


        //      Footer Text Updater
//        TextView FooterText = (TextView)view.findViewById(R.id.footer_text);
//        FooterText.setText("Sell works from your collection through Pixmato");

//        btnTEST.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getActivity(), "TESTING BUTTON CLICK 1",Toast.LENGTH_SHORT).show();
//            }
//        });

        return view;
    }
}
package com.sample.pixel;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ChildFragmentMapFairs extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<FruitModel> imageModelArrayList;
    private FruitAdapter adapter;
    private int[] imgId;

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initData();
        view =  inflater.inflate(R.layout.fragment_child_map_fairs, container, false);
        // Recycler
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_map_fairs);
        imageModelArrayList = eatFruits();
        adapter = new FruitAdapter(getContext(), imageModelArrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        return  view;
    }


    private void initData()
    {
        imgId = new int[] {
                R.drawable.orangesq,R.drawable.orangesq,R.drawable.orangesq,R.drawable.orangesq,
                R.drawable.orangesq,R.drawable.orangesq,R.drawable.orangesq,R.drawable.orangesq,
                R.drawable.orangesq,R.drawable.orangesq,R.drawable.orangesq,R.drawable.orangesq
        };


    }

    private ArrayList<FruitModel> eatFruits(){

        ArrayList<FruitModel> list = new ArrayList<>();

        for(int i = 0; i < imgId.length; i++){
            FruitModel fruitModel = new FruitModel();
//            fruitModel.setName(myImageNameList[i]);
            fruitModel.setImage_drawable(imgId[i]);
            list.add(fruitModel);
        }

        return list;
    }

}

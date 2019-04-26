package com.sample.pixel;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Layout;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.support.v7.widget.RecyclerView;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sample.pixel.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Tab2Fragment extends Fragment {
    private static final String TAG = "Tab2Fragment";
    private RecyclerView recyclerView;
    private ArrayList<FruitModel> imageModelArrayList;
    private FruitAdapter adapter;
    GridView androidGridView;
    ExpandableHeightGridView mAppsGrid;

    private final static String urlString = "https://pixmato.herokuapp.com/multiImage";

    ArrayList<String> imageUrls = new ArrayList<String>();
    ArrayList<String> authorUrls = new ArrayList<String>();
    ApiImageAdapter adapterViewAndroid;


    private int[] imgId;
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment2_layout,container,false);
        initData();
        new Tab2Fragment.FetchAPIf2().execute(urlString);

        //ExpandableHeightGridView
        mAppsGrid = (ExpandableHeightGridView)view.findViewById(R.id.f2grid_view_image_text);
        mAppsGrid.setExpanded(true);

        // Recycler
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        imageModelArrayList = eatFruits();
        adapter = new FruitAdapter(getContext(), imageModelArrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));



        GridView gridview  = (GridView) getActivity().findViewById(R.id.f2grid_view_image_text);
        adapterViewAndroid = new ApiImageAdapter(getContext(), imageUrls,authorUrls);
        androidGridView=(GridView)view.findViewById(R.id.f2grid_view_image_text);
        androidGridView.setAdapter(adapterViewAndroid);
//        btnTEST = (Button) view.findViewById(R.id.btnTEST);
//
//        btnTEST.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getActivity(), "TESTING BUTTON CLICK 2",Toast.LENGTH_SHORT).show();
//            }
//        });

        return view;
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



    //Async Fetch
    private class FetchAPIf2 extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {

            InputStream inputStream = null;
            String result= null;
            HttpClient client = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(params[0]);

            try {

                HttpResponse response = client.execute(httpGet);
                inputStream = response.getEntity().getContent();

                // convert inputstream to string
                if(inputStream != null){
                    result = convertInputStreamToString(inputStream);
                    Log.i("App", "Data received:" +result);

                }
                else
                    result = "Failed to fetch data";

                return result;

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String dataFetched) {
            //parsing the JSON data and then display
            parseJSON(dataFetched);
            androidGridView.setAdapter(adapterViewAndroid);

        }}

    private String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    private void parseJSON(String data){

        try{
            JSONArray jsonMainNode = new JSONArray(data);

            int jsonArrLength = jsonMainNode.length();

            for(int i=0; i < jsonArrLength; i++) {
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                String imgUrl = jsonChildNode.getString("imageUrl");
                String authUrl = jsonChildNode.getString("author");
                imageUrls.add(imgUrl);
                authorUrls.add(authUrl);
            }

//            System.out.println("Arraylist: ");
            System.out.println(imageUrls);
//            // Get ListView object from xml
//            listView = (ListView) findViewById(R.id.list);
//
//            // Define a new Adapter
//            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, tutorialList);
//
//            // Assign adapter to ListView
//            listView.setAdapter(adapter);

        }catch(Exception e){
            Log.i("App", "Error parsing data" +e.getMessage());

        }
    }




}
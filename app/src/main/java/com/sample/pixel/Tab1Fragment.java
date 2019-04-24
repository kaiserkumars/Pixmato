package com.sample.pixel;



import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;


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


public class Tab1Fragment extends Fragment {
    private static final String TAG = "Tab1Fragment";

    private Button btnTEST;
    GridView androidGridView;

//    String[] gridSampleStr = {"Alpha", "Beta"," Gamma", "Delta","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday","Monday"};
//    int[] gridSampleImg={
//            R.drawable.ic_assignment,R.drawable.ic_attach_file,R.drawable.ic_autorenew,R.drawable.ic_autorenew,R.drawable.ic_home_black_24dp,
//            R.drawable.ic_map_black_24dp, R.drawable.ic_assignment, R.drawable.ic_mode_comment_black_24dp, R.drawable.ic_autorenew,R.drawable.ic_search_black_24dp,
//    R.drawable.ic_favorite_border_black_24dp, R.drawable.ic_assignment};

    private final static String urlString = "http://10.0.2.2:5000/multiImage";

    ArrayList<String> imageUrls = new ArrayList<String>();
    ArrayList<String> authorUrls = new ArrayList<String>();

    ApiImageAdapter adapterViewAndroid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1_layout,container,false);
//        btnTEST = (Button) view.findViewById(R.id.btnTEST);

        //POPULATE USING API
        new FetchAPI().execute(urlString);

        //PROFILE IMAGE

        ImageView  imageView = (ImageView)view.findViewById(R.id.profile_img);
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.propic));

        //GRID IMAGE

        GridView gridview  = (GridView) getActivity().findViewById(R.id.grid_view_image_text);
//        System.out.print(imageUrls);
//      CustomGridView adapterViewAndroid = new CustomGridView(getContext(), gridSampleStr, imageUrls);
        adapterViewAndroid = new ApiImageAdapter(getContext(), imageUrls,authorUrls);
        androidGridView=(GridView)view.findViewById(R.id.grid_view_image_text);
        androidGridView.setAdapter(adapterViewAndroid);

//        //ONclick image listner
//        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                // set an Intent to Another Activity
//                Intent intent = new Intent(Tab1Fragment.this, SecondActivity.class);
//                intent.putExtra("image", imageUrls.get(position)); // put image data in Intent
//                startActivity(intent); // start Intent
//            }
//        });


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





    //Async Fetch
    private class FetchAPI extends AsyncTask<String,Void,String> {

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
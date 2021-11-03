package com.example.smartfarm;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StateActivity extends AppCompatActivity {
    private Context mContext;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private com.example.smartfarm.VPAdapter vpAdapter;
    private TextView nowtemp,nowmoist,nowlux,settemp,setmoist,setlux;
    private String mJsonString;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.state_main);

        nowtemp = (TextView)findViewById(R.id.nowTemp_value);
        nowmoist = (TextView)findViewById(R.id.nowMoisture_value);
        nowlux = (TextView)findViewById(R.id.nowLux_value);
        settemp = (TextView)findViewById(R.id.setTemp_value);
        setmoist = (TextView)findViewById(R.id.setMoisture_value);
        setlux = (TextView)findViewById(R.id.setLux_value);

        getData task = new getData();
        task.execute(singleton.getInstance().getURL()+"/gethousedata.php","houseID=1");

        getData task2 = new getData();
        task2.execute(singleton.getInstance().getURL()+"/getsettingvalue.php","houseID=1");

        mContext = getApplicationContext();
        mTabLayout = (TabLayout) findViewById(R.id.tab);

        mTabLayout.addTab(mTabLayout.newTab().setText("온도 설정"));
        mTabLayout.addTab(mTabLayout.newTab().setText("습도 설정"));
        mTabLayout.addTab(mTabLayout.newTab().setText("조도 설정"));

        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        vpAdapter = new VPAdapter(
                getSupportFragmentManager(), mTabLayout.getTabCount());

        mViewPager.setAdapter(vpAdapter);

        mViewPager.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }
    /*private View createTabView(String tabName) {
        View tabView = LayoutInflater.from(mContext).inflate(R.layout.tab, null);
        TextView txt_name = (TextView) tabView.findViewById(R.id.txt_name);
        txt_name.setText(tabName);
        return tabView;
    }*/

    private class getData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(StateActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Log.d("tt","response - " + result);

            mJsonString = result;
            showResult();
        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String postParameters = params[1];


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d("TAG", "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();


            } catch (Exception e) {

                Log.d("tt", "GetData : Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }


    private void showResult(){

        String TAG_JSON="ajm";
        String TAG_TEMP = "temp";
        String TAG_MOISTURE ="moisture";
        String TAG_LUX ="lux";
        String TAG_SOILMOIST = "soil_moist";

        String TAG_JSON2="ajmset";
        String TAG_setTEMP = "setTemp";
        String TAG_setMOISTURE ="setMoisture";
        String TAG_setLUX ="setLux";

        int m=0;

        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray;
            try {
                jsonArray = jsonObject.getJSONArray(TAG_JSON);
                m=1;
            }catch (Exception e){
                jsonArray = jsonObject.getJSONArray(TAG_JSON2);
                m=2;
            }

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                if(m==1){
                    String temp = item.getString(TAG_TEMP);
                    String moisture = item.getString(TAG_MOISTURE);
                    String lux = item.getString(TAG_LUX);
                    String soil_moist = item.getString(TAG_SOILMOIST);
                    nowtemp.setText(temp);
                    nowmoist.setText(moisture);
                    nowlux.setText(lux);
                }
                else if(m==2){
                    String setTemp = item.getString(TAG_setTEMP);
                    String setMoisture = item.getString(TAG_setMOISTURE);
                    String setLux = item.getString(TAG_setLUX);
                    settemp.setText(setTemp);
                    setmoist.setText(setMoisture);
                    setlux.setText(setLux);
                }
            }
        } catch (JSONException e) {
            Log.d("TAG", "showResult : ", e);
        }
    }
}
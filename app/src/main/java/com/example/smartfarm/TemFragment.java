package com.example.smartfarm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class TemFragment extends Fragment {

    private static String IP_ADDRESS = "210.182.153.118";
    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://" + IP_ADDRESS + "/tempstat.php";
    private static String TAG = "temperature";
    static RequestQueue requestQueue;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context context;

    public TemFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static TemFragment newInstance(String param1, String param2) {
        TemFragment fragment = new TemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inf = inflater.inflate(R.layout.fragment_tem, container, false);

        TextView tv = (TextView)inf.findViewById(R.id.inputTemp);
        TextView s_tv = (TextView)getActivity().findViewById(R.id.setTemp_value);
        Button btn = (Button)inf.findViewById(R.id.tembutton);
        context = container.getContext();

        btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tValue = tv.getText().toString();
                int value = Integer.parseInt(tValue);

                if(0 <= value && value <= 40) {
                    s_tv.setText(tValue + "˚C");  //설정값 입력
                    Toast.makeText(context, "설정이 저장되었습니다.", Toast.LENGTH_SHORT).show();
                    if(requestQueue == null){
                        requestQueue = Volley.newRequestQueue(context);
                    }
                    postTemp(tValue);
                }
                else {
                    Toast.makeText(context, "입력된 온도 수치를 확인해주세요. (0~40˚C까지 설정가능)", Toast.LENGTH_SHORT).show();
                }

            }
        });
        // Inflate the layout for this fragment
        return inf;

    }
    public void postTemp(String tValue) {
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                Toast.makeText(getActivity().getApplicationContext(), "응답:" + response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //에러나면 error로 나옴
                Toast.makeText(getActivity().getApplicationContext(), "에러:" + error.getMessage(), Toast.LENGTH_SHORT).show();

            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("userID",((AppTest)getActivity().getApplication()).getId());
                //param.put("userID",singleton.getInstance().getId());
                param.put("setTemp",tValue);
                //php로 설정값을 보낼 수 있음
                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);
    }
}


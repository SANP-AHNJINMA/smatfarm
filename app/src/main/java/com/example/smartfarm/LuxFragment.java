package com.example.smartfarm;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import android.app.Application;

public class LuxFragment extends Fragment {

    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = singleton.getInstance().getURL() + "/luxstat.php";
    private static String TAG = "LUX";
    static RequestQueue requestQueue;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context context;

    public LuxFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static LuxFragment newInstance(String param1, String param2) {
        LuxFragment fragment = new LuxFragment();
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

        View inf = inflater.inflate(R.layout.fragment_lux, container, false);

        TextView tv = (TextView)inf.findViewById(R.id.inputLux);
        TextView s_tv = (TextView)getActivity().findViewById(R.id.setLux_value);
        Button btn = (Button)inf.findViewById(R.id.luxbutton);
        context = container.getContext();

        btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lValue = tv.getText().toString();
                int value = Integer.parseInt(lValue);
                lValue = (value*255/100)+"";
                Toast.makeText(context,lValue,Toast.LENGTH_SHORT).show();

                if(0 <= value && value <= 100) {
                    s_tv.setText(value+"%");  //설정값 입력
                    Toast.makeText(context, "설정이 저장되었습니다.", Toast.LENGTH_SHORT).show();
                    Log.d("application id",((AppTest)getActivity().getApplication()).getId());
                    Log.d("singleton id",singleton.getInstance().getId());
                    //Toast.makeText(context, ((AppTest)this.getApplication()).getId(), Toast.LENGTH_SHORT).show();
                    if(requestQueue == null){
                        requestQueue = Volley.newRequestQueue(context);
                    }
                    postLux(lValue);
                }
                else {
                    Toast.makeText(context, "입력된 밝기 수치를 확인해주세요. (0~100까지 설정가능)", Toast.LENGTH_SHORT).show();
                }

            }
        });
        // Inflate the layout for this fragment
        return inf;
    }

    public void postLux(String lValue) {
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
                param.put("setLux",lValue);
                //php로 설정값을 보낼 수 있음
                return param;
            }
        };

        request.setShouldCache(false);
        requestQueue.add(request);
    }
}
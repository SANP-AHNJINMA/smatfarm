package com.example.smartfarm;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link com.example.smartfarm.MoisFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoisFragment extends Fragment {

    private static String IP_ADDRESS = "210.182.153.118";
    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://" + IP_ADDRESS + "/moisturestat.php";
    private static String TAG = "MOISTURE";
    static RequestQueue requestQueue;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context context;


    public MoisFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MoisFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MoisFragment newInstance(String param1, String param2) {
        MoisFragment fragment = new MoisFragment();
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

        View inf = inflater.inflate(R.layout.fragment_mois, container, false);

        TextView tv = (TextView)inf.findViewById(R.id.inputMoisture);
        TextView s_tv = (TextView)getActivity().findViewById(R.id.setMoisture_value);
        Button btn = (Button)inf.findViewById(R.id.moisbutton);
        context = container.getContext();

        btn.setOnClickListener(new Button.OnClickListener() {  //버튼 클릭시 디비에 데이터 전송, 설정상태 텍스트값 변경
            @Override
            public void onClick(View v) {
                String mValue = tv.getText().toString();
                int value = Integer.parseInt(mValue);

                if (0 <= value && value <= 255) {
                    s_tv.setText(mValue + "%");  //설정값 입력
                    Toast.makeText(context, "설정이 저장되었습니다.", Toast.LENGTH_SHORT).show();
                    Log.d("application id", ((AppTest) getActivity().getApplication()).getId());
                    Log.d("singleton id", singleton.getInstance().getId());
                    //Toast.makeText(context, ((AppTest)this.getApplication()).getId(), Toast.LENGTH_SHORT).show();
                    if (requestQueue == null) {
                        requestQueue = Volley.newRequestQueue(context);
                    }
                    postLux(mValue);
                } else {
                    Toast.makeText(context, "입력된 온도 수치를 확인해주세요. (0~255까지 설정가능)", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // Inflate the layout for this fragment
        return inf;
    }

        public void postLux(String mValue) {
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
                    param.put("setMoisture",mValue);
                    //php로 설정값을 보낼 수 있음
                    return param;
                }
            };

            request.setShouldCache(false);
            requestQueue.add(request);
        }
}
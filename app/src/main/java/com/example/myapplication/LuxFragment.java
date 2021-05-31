package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LuxFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LuxFragment extends Fragment {

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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LuxFragment.
     */
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
                s_tv.setText(tv.getText());
                Toast.makeText(context, "설정이 저장되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        // Inflate the layout for this fragment
        return inf;
    }

}
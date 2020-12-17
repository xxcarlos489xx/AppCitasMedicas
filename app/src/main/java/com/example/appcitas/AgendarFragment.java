package com.example.appcitas;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;


public class AgendarFragment extends Fragment {
DatePicker pocker;
TextView nameHeader;
String apellidos,nombres;
private SharedPreferences user;
private SharedPreferences.Editor editor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = this.getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor = user.edit();
        nombres = user.getString("nombres","null");
        apellidos = user.getString("apellidos","null");
       // View header = this.getActivity().getHeaderView(0);
        //TextView text = (TextView) header.findViewById(R.id.textView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_agendar, container, false);
        //nameHeader = v.findViewById(R.id.nameHeader);
        //nameHeader.setText(nombres+" "+apellidos);
        //nameHeader = this.getActivity().findViewById(R.id.nameHeader);
        return v;
    }

}
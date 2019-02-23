package com.example.stanislavlukanov.td.Navigation_Menu_Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.stanislavlukanov.td.R;

public class ArhieveFragment extends Fragment{
    public static ArhieveFragment newInstance(){
        Bundle args = new Bundle();
        ArhieveFragment fragment = new ArhieveFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fr_arhieve,container, false);
        return v;
    }
}

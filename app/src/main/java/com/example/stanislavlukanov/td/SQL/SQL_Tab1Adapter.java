package com.example.stanislavlukanov.td.SQL;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.stanislavlukanov.td.R;

import java.util.ArrayList;
import java.util.List;

public class SQL_Tab1Adapter extends RecyclerView.Adapter <SQL_Tab1Adapter.MyViewHolder>{

    private Context context;
    private List<SQL_Tab1> devslist;


    private String[] Months = {" Января", " Февраля", " Марта", " Апреля", " Мая", " Июня", " Июля", " Августа", " Сентября",
            " Октября", " Ноября", " Декабря"};
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView date;
        public TextView time;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            date = view.findViewById(R.id.date);
            time = view.findViewById(R.id.time);
        }
    }

    public SQL_Tab1Adapter(Context context, List<SQL_Tab1> devslist) {
        this.context = context;
        this.devslist= devslist;
    }

    @Override
    public SQL_Tab1Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sql_tab1_list_row, parent, false);

        return new SQL_Tab1Adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SQL_Tab1Adapter.MyViewHolder holder, int position) {
        SQL_Tab1 dev = devslist.get(position);

        holder.name.setText(dev.getName());

        holder.time.setText(dev.getTime());

        String str = dev.getDay() + Months[dev.getMonth()];
        holder.date.setText(str);
    }

    @Override
    public int getItemCount() {
        return devslist.size();
    }




}

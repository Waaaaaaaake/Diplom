package com.example.stanislavlukanov.td.SQL;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.stanislavlukanov.td.R;

import java.util.List;

public class SQL_CatalogAdapter extends RecyclerView.Adapter <SQL_CatalogAdapter.MyViewHolder>{

    private Context context;
    private List<SQL_Catalog> contactsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView contact;
        public TextView description;

        public MyViewHolder(View view) {
            super(view);
            contact = view.findViewById(R.id.contact);
            description = view.findViewById(R.id.description);
        }
    }

    public SQL_CatalogAdapter(Context context, List<SQL_Catalog> contactsList) {
        this.context = context;
        this.contactsList= contactsList;
    }

    @Override
    public SQL_CatalogAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sql_catalog_list_row, parent, false);

        return new SQL_CatalogAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SQL_CatalogAdapter.MyViewHolder holder, int position) {
        SQL_Catalog contact = contactsList.get(position);

        holder.contact.setText(contact.getContact());

        holder.description.setText(contact.getDescription());
    }
    @Override
    public int getItemCount() {
        return contactsList.size();
    }

}

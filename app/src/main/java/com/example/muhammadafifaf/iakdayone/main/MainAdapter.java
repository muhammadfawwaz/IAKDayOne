package com.example.muhammadafifaf.iakdayone.main;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.muhammadafifaf.iakdayone.R;
import com.example.muhammadafifaf.iakdayone.detail.DetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Muhammad Afif AF on 01/12/2017.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainHolder> {
    private List<MainDao> mData;

    public MainAdapter(List<MainDao> mData) {
        this.mData = mData;
    }

    @Override
    public MainHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_main,parent,false); //mirip ama setContent di MainActivity
        MainHolder mainHolder = new MainHolder(view);
        return mainHolder;
    }

    @Override
    public void onBindViewHolder(MainHolder holder, final int position) { //tpt utk ngeset nilai array
        holder.titleRow.setText(mData.get(position).getTitle());
        Picasso.with(holder.imageRow.getContext()).load(mData.get(position).getImageUrl()).into(holder.imageRow);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                intent.putExtra("dataMovie", mData.get(position));
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() { //jml datanya
        return mData.size();
    }

    public class MainHolder extends RecyclerView.ViewHolder {
        private ImageView imageRow;
        private TextView titleRow;

        public MainHolder(View itemView) {
            super(itemView);
            imageRow = (ImageView) itemView.findViewById(R.id.image_row);
            titleRow = (TextView) itemView.findViewById(R.id.title_row);
        }
    }
}
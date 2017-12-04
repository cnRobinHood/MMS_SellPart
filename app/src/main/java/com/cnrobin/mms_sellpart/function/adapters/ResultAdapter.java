package com.cnrobin.mms_sellpart.function.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cnrobin.mms_sellpart.R;
import com.cnrobin.mms_sellpart.function.entity.ClothInfo;
import com.cnrobin.mms_sellpart.function.view.ResultActivity;

import java.util.List;

/**
 * Created by cnrobin on 17-11-23.
 * Just Enjoy It!!!
 */

public class ResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<ClothInfo> clothInfos;

    public ResultAdapter(Context context, List<ClothInfo> clothInfos) {
        mContext = context;
        this.clothInfos = clothInfos;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recy_item, parent, false);
        final MyViewHolder holder = new MyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ResultActivity.class);
                Bundle imageBundle = new Bundle();
                int position = holder.getAdapterPosition();
                imageBundle.putString("image", clothInfos.get(position).getImages());
                imageBundle.putString("id", clothInfos.get(position).getID());
                imageBundle.putString("stars", clothInfos.get(position).getStars());
                intent.putExtra("image", imageBundle);
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ClothInfo clothInfo = clothInfos.get(position);
        Glide.with(mContext).load(clothInfo.getImages()).into(((MyViewHolder) viewHolder).itemImg);
        ((MyViewHolder) viewHolder).itemText.setText(clothInfo.getID());
    }

    @Override
    public int getItemCount() {
        return clothInfos.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView itemText;
        ImageView itemImg;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemImg = itemView.findViewById(R.id.item_img);
            itemText = itemView.findViewById(R.id.tv_goodname);
        }
    }
}

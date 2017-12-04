package com.cnrobin.mms_sellpart.function.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cnrobin.mms_sellpart.R;
import com.cnrobin.mms_sellpart.function.entity.ClothInfo;
import com.cnrobin.mms_sellpart.function.view.ResultActivity;

import java.util.List;

/**
 * Created by cnrobin on 17-10-18.
 * Just Enjoy It!!!
 */

public class GoodsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "GoodsAdapter";
    private static final int HEADER = 1;
    private static final int ITEM = 0;
    private Bundle bundle;
    private Context mContext;
    private int viewType;
    private List<ClothInfo> list;

    public GoodsAdapter(Context context, List<ClothInfo> list, Bundle bundle) {
        this.mContext = context;
        this.list = list;
        this.bundle = bundle;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.viewType = viewType;
        if (viewType == HEADER) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.recy_header, parent, false);
            return new HeaderHolder(view);
        } else if (viewType == ITEM) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.recy_item, parent, false);
            final ItemHolder holder = new ItemHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ResultActivity.class);
                    Bundle imageBundle = new Bundle();
                    int position = holder.getAdapterPosition();
                    imageBundle.putString("image", list.get(position - 1).getImages());
                    imageBundle.putString("id", list.get(position - 1).getID());
                    imageBundle.putString("stars", list.get(position - 1).getStars());
                    intent.putExtra("image", imageBundle);
                    mContext.startActivity(intent);
                }
            });
            return holder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (viewType == HEADER) {
            ((HeaderHolder) holder).titleText.setText(bundle.getString("kind"));
            switch (bundle.getString("kind")) {
                case "风衣":
                    ((HeaderHolder) holder).titleImage.setImageResource(R.drawable.fx);
                    break;
                case "半身裙":
                    ((HeaderHolder) holder).titleImage.setImageResource(R.drawable.bsq);
                    break;
                case "长裤":
                    ((HeaderHolder) holder).titleImage.setImageResource(R.drawable.ck);
                    break;
                case "衬衣":
                    ((HeaderHolder) holder).titleImage.setImageResource(R.drawable.cy);
                    break;
                case "连衣裙":
                    ((HeaderHolder) holder).titleImage.setImageResource(R.drawable.lyq);
                    break;
                case "西装":
                    ((HeaderHolder) holder).titleImage.setImageResource(R.drawable.xz);
                    break;
                case "针织衫":
                    ((HeaderHolder) holder).titleImage.setImageResource(R.drawable.zzs);
                    break;
            }

        } else if (viewType == ITEM)
            if (position != 0) {
                ClothInfo clothInfo = list.get(position - 1);
                Glide.with(mContext).load(clothInfo.getImages()).into(((ItemHolder) holder).itemImg);
                ((ItemHolder) holder).itemText.setText(clothInfo.getID());
            }
    }

    @Override
    public int getItemCount() {
        return 1 + list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? HEADER : ITEM;
    }

    static class HeaderHolder extends RecyclerView.ViewHolder {
        ImageView titleImage;
        FrameLayout headerFrame;
        TextView titleText;

        public HeaderHolder(View itemView) {
            super(itemView);
            titleImage = itemView.findViewById(R.id.title_img);
            headerFrame = itemView.findViewById(R.id.header_frame);
            titleText = itemView.findViewById(R.id.tv_title);
        }
    }

    static class ItemHolder extends RecyclerView.ViewHolder {
        TextView itemText;
        ImageView itemImg;

        public ItemHolder(View itemView) {
            super(itemView);
            itemImg = itemView.findViewById(R.id.item_img);
            itemText = itemView.findViewById(R.id.tv_goodname);

        }
    }
}

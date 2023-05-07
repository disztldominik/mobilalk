package com.example.hikeplanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class HikeItemAdapter extends RecyclerView.Adapter<HikeItemAdapter.ViewHolder> implements Filterable {

    private ArrayList<HikeItem> mHikeItemData = new ArrayList<>();
    private ArrayList<HikeItem> mHikeItemDataAll = new ArrayList<>();
    private Context mContext;
    private int lastPosition = -1;

    HikeItemAdapter(Context context, ArrayList<HikeItem> itemsData) {
        this.mHikeItemData = itemsData;
        this.mHikeItemDataAll = itemsData;
        this.mContext = context;
    }

    @Override
    public HikeItemAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(HikeItemAdapter.ViewHolder holder, int position) {

        HikeItem currentItem = mHikeItemData.get(position);

        Animation slide_out_animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_row);
        holder.itemView.startAnimation(slide_out_animation);
        holder.bindTo(currentItem);


        if(holder.getAdapterPosition() > lastPosition) {
            Animation slide_in_animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_row);
            holder.itemView.startAnimation(slide_in_animation);
            lastPosition = holder.getAdapterPosition();
        }
    }

    @Override
    public int getItemCount() {
        return mHikeItemData.size();
    }


    @Override
    public Filter getFilter() {
        return hikeFilter;
    }

    private Filter hikeFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<HikeItem> filteredList = new ArrayList<>();
            FilterResults results = new FilterResults();

            if(charSequence == null || charSequence.length() == 0) {
                results.count = mHikeItemDataAll.size();
                results.values = mHikeItemDataAll;
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for(HikeItem item : mHikeItemDataAll) {
                    if(item.getName().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }

                results.count = filteredList.size();
                results.values = filteredList;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mHikeItemData = (ArrayList)filterResults.values;
            notifyDataSetChanged();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitleText;
        private TextView mCreatorText;
        private TextView mInfoText;
        private TextView mPriceText;
        private ImageView mItemImage;
        private RatingBar mRatingBar;

        ViewHolder(View itemView) {
            super(itemView);

            mTitleText = itemView.findViewById(R.id.itemTitle);
            mCreatorText = itemView.findViewById(R.id.personName);
            mInfoText = itemView.findViewById(R.id.subTitle);
            mItemImage = itemView.findViewById(R.id.itemImage);
            mRatingBar = itemView.findViewById(R.id.ratingBar);
            mPriceText = itemView.findViewById(R.id.price);
        }

        void bindTo(HikeItem currentItem){
            mTitleText.setText(currentItem.getName());
            String current_creator_name = currentItem.getCreatorName() + " által publikálva.";
            mCreatorText.setText(current_creator_name);
            mInfoText.setText(currentItem.getInfo());
            mPriceText.setText(currentItem.getPrice());
            mRatingBar.setRating(currentItem.getRatedInfo());

            itemView.findViewById(R.id.add_to_cart).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((HikeListActivity)mContext).updateAlertIcon(currentItem);
                }
            });

            itemView.findViewById(R.id.delete).setOnClickListener(view -> ((HikeListActivity)mContext).deleteItem(currentItem));
        }
    }
}
package net.levinh.ytmusic.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.levinh.ytmusic.R;
import net.levinh.ytmusic.model.VideoItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by virus on 12/10/2016.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<VideoItem> mListVideo;
    private int mLayoutResID;
    Context mContext;
    OnItemClickListener mCallback;

    public RecyclerViewAdapter(Context Context, int LayoutResID, List<VideoItem> ListVideo, OnItemClickListener callback) {
        mContext = Context;
        mLayoutResID = LayoutResID;
        mCallback = callback;
        if (mListVideo != null) {
            this.mListVideo = ListVideo;
        } else
            this.mListVideo = new ArrayList<>();
    }

    public RecyclerViewAdapter(Context context, int layoutResID, OnItemClickListener callback) {
        this(context, layoutResID, null, callback);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(mLayoutResID, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int positon = (int) v.getTag();
                mCallback.onItemClick(positon);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        VideoItem data = getItem(position);
        if (data != null) {
            holder.bindData(data);
            holder.itemView.setTag(position);
        }
    }

    public VideoItem getItem(int position) {
        return (getItemCount() != 0)
                ? mListVideo.get(position) : null;
    }

    @Override
    public int getItemCount() {
        return mListVideo.size();
    }

    public void setListVideo(List<VideoItem> list) {
        if (list != null) {
            this.mListVideo = list;
            notifyDataSetChanged();
        }
    }

    /*View Holder for RecyclerView*/
    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvDescription)
        TextView tvDescription;
        @BindView(R.id.imageViewThumbnail)
        ImageView imgThumbnail;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(VideoItem data) {
            tvTitle.setText(data.getTitle().length()>50? TextUtils.substring(data.getTitle(),0,50):data.getTitle());
            tvDescription.setText(data.getDescription());
            Picasso.with(itemView.getContext())
                    .load(data.getThumbnailURL())
                    .resize(56, 56)
                    .centerCrop()
                    .into(imgThumbnail);

        }
    }

    /*OnItemClickListener for RecyclerView*/
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}

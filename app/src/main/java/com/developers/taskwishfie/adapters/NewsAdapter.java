package com.developers.taskwishfie.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.developers.taskwishfie.R;
import com.developers.taskwishfie.model.Post;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Amanjeet Singh on 3/9/17.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    private Context context;
    private List<Post> postList;

    public NewsAdapter(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        if (postList.get(position).getEventDate() != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(postList.get(position).getEventDate());
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            holder.newsTitle.setText(postList.get(position).getEventName() + " on " +
                    formatter.format(calendar.getTime()));
        } else {
            holder.newsTitle.setText(postList.get(position).getEventName());
        }
        holder.likeNumber.setText(String.valueOf(postList.get(position).getLikes()) + " Likes");
        holder.shareNumber.setText(String.valueOf(postList.get(position).getShares()) + " Shares");
        holder.viewNumber.setText(String.valueOf(postList.get(position).getViews()) + " Views");
        Picasso.with(context).load(postList.get(position).getThumbnailImage())
                .into(holder.newsImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {

                    }
                });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public void swap(List<Post> posts) {
        this.postList.addAll(posts);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.news_image_view)
        ImageView newsImage;
        @BindView(R.id.news_title_textview)
        TextView newsTitle;
        @BindView(R.id.progress_bar)
        ProgressBar progressBar;
        @BindView(R.id.like_text)
        TextView likeNumber;
        @BindView(R.id.share_text)
        TextView shareNumber;
        @BindView(R.id.view_text)
        TextView viewNumber;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

package com.example.sqliteandroidstudiojava;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sqliteandroidstudiojava.databse.SqliteDatabase;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AnimeAdapter extends RecyclerView.Adapter<ViewHolder>{


    private final List<Anime> mAnimeList;

    public AnimeAdapter(List<Anime> animeList) {
        mAnimeList = animeList;
    }

    @Override
    public void onBindViewHolder(com.example.sqliteandroidstudiojava.ViewHolder holder, int position) {
        holder.onBind(position);
    }

    @NonNull
    @Override
    public com.example.sqliteandroidstudiojava.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.anime_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if (mAnimeList != null & mAnimeList.size() > 0) {
            return mAnimeList.size();
        } else {
            return 0;
        }
    }

    public void addItems(List<Anime> animeList) {
        mAnimeList.addAll(animeList);
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        if (mAnimeList != null & mAnimeList.size() > 0) {
            mAnimeList.remove(position);
        }
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public class ViewHolder extends com.example.sqliteandroidstudiojava.ViewHolder {

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.animeImageView)
        ImageView mAnimeImageView;

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.animeCardView)
        CardView mAnimeCardView;

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.nameTextView)
        TextView mNameTextView;

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.scoreTextView)
        TextView mScoreEditText;

        SqliteDatabase dataBase;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear() {
            mAnimeImageView.setImageDrawable(null);
            mNameTextView.setText("");
            mScoreEditText.setText("");
        }

        public void onBind(int position) {
            super.onBind(position);

            Anime mAnime = mAnimeList.get(position);
            dataBase = new SqliteDatabase(itemView.getContext());

            if (mAnime.getUrl() != null) {
                Glide.with(itemView.getContext())
                        .load(mAnime.getUrl())
                        .into(mAnimeImageView);
            }

            if (mAnime.getName() != null) {
                mNameTextView.setText(mAnime.getName());
            }

            mScoreEditText.setText(String.valueOf(mAnime.getScore()));

            mAnimeCardView.setOnClickListener(v -> {
                Intent intent=new Intent(itemView.getContext(), DetailActivity.class);
                intent.putExtra("id",  mAnime.getId());
                itemView.getContext().startActivity(intent);
            });
        }
    }

}
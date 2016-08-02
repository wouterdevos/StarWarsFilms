package com.wouterdevos.starwarsfilms.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wouterdevos.starwarsfilms.R;
import com.wouterdevos.starwarsfilms.databinding.ListRowFilmBinding;
import com.wouterdevos.starwarsfilms.valueobject.Film;

import java.util.List;

public class FilmMasterAdapter extends RecyclerView.Adapter<FilmMasterAdapter.ViewHolder> {

    private List<Film> mFilms;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public FilmMasterAdapter(List<Film> mFilms) {
        this.mFilms = mFilms;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void addAll(List<Film> films) {
        mFilms.clear();
        mFilms.addAll(films);
    }

    public Film getFilm(int position) {
        return position < 0 || position > mFilms.size() ? null : mFilms.get(position);
    }

    public boolean hasFilms() {
        return mFilms != null && mFilms.size() > 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListRowFilmBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.list_row_film, parent, false);
        return new ViewHolder(binding, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setFilm(mFilms.get(position));
    }

    @Override
    public int getItemCount() {
        return mFilms.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ListRowFilmBinding mBinding;
        private OnItemClickListener mOnItemClickListener;

        public ViewHolder(@NonNull ListRowFilmBinding binding, OnItemClickListener onItemClickListener) {
            super(binding.getRoot());
            mBinding = binding;
            mBinding.executePendingBindings();
            mBinding.getRoot().setOnClickListener(this);
            mOnItemClickListener = onItemClickListener;
        }

        public void setFilm(Film film) {
            mBinding.setFilm(film);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }
}

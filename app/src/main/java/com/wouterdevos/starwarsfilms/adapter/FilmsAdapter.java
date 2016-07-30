package com.wouterdevos.starwarsfilms.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.wouterdevos.starwarsfilms.R;
import com.wouterdevos.starwarsfilms.databinding.ListRowFilmBinding;
import com.wouterdevos.starwarsfilms.valueobject.Film;

import java.util.List;

public class FilmsAdapter extends RecyclerView.Adapter<FilmsAdapter.ViewHolder> {

    private List<Film> mFilms;

    public FilmsAdapter(List<Film> mFilms) {
        this.mFilms = mFilms;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListRowFilmBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.list_row_film, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setFilm(mFilms.get(position));
    }

    @Override
    public int getItemCount() {
        return mFilms.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ListRowFilmBinding mBinding;

        public ViewHolder(ListRowFilmBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mBinding.executePendingBindings();
        }

        public void setFilm(Film film) {
            mBinding.setFilm(film);
        }
    }
}

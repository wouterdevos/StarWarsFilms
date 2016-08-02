package com.wouterdevos.starwarsfilms.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.wouterdevos.starwarsfilms.R;
import com.wouterdevos.starwarsfilms.databinding.ListRowFilmDetailBinding;
import com.wouterdevos.starwarsfilms.databinding.ListRowPeopleBinding;
import com.wouterdevos.starwarsfilms.valueobject.Film;
import com.wouterdevos.starwarsfilms.valueobject.People;

import java.util.List;

public class FilmDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Object> mFilmDetails;
    private boolean mLoading = false;
    private boolean mError = false;

    public FilmDetailAdapter(List<Object> filmDetails) {
        mFilmDetails = filmDetails;
    }

    public void addAll(List<Object> filmDetails) {
        mFilmDetails.addAll(filmDetails);
    }

    public boolean hasPeople() {
        return mFilmDetails.size() > 1;
    }

    public void setLoading(boolean loading) {
        mLoading = loading;
    }

    public void setError(boolean error) {
        mError = error;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = null;
        RecyclerView.ViewHolder viewHolder = null;
        switch(viewType) {
            case R.layout.list_row_film_detail:
                binding = DataBindingUtil.inflate(inflater, viewType, parent, false);
                viewHolder = new FilmDetailViewHolder((ListRowFilmDetailBinding) binding);
                break;
            default:
                binding = DataBindingUtil.inflate(inflater, viewType, parent, false);
                viewHolder = new PeopleViewHolder((ListRowPeopleBinding) binding);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = holder.getItemViewType();
        switch(viewType) {
            case R.layout.list_row_film_detail:
                FilmDetailViewHolder filmDetailViewHolder = (FilmDetailViewHolder) holder;
                filmDetailViewHolder.bind((Film) mFilmDetails.get(position), mLoading, mError);
                break;
            case R.layout.list_row_people:
                PeopleViewHolder peopleViewHolder = (PeopleViewHolder) holder;
                peopleViewHolder.bind((People) mFilmDetails.get(position));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mFilmDetails.size();
    }

    @Override
    public int getItemViewType(int position) {
        Object object = mFilmDetails.get(position);
        if (object instanceof Film) {
            return R.layout.list_row_film_detail;
        } else if (object instanceof People) {
            return R.layout.list_row_people;
        }

        return -1;
    }

    public static class FilmDetailViewHolder extends RecyclerView.ViewHolder {

        private ListRowFilmDetailBinding mBinding;

        public FilmDetailViewHolder(@NonNull ListRowFilmDetailBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mBinding.executePendingBindings();
        }

        public void bind(Film film, boolean loading, boolean error) {
            mBinding.setFilm(film);
            mBinding.setLoading(loading);
            mBinding.setError(error);
        }
    }

    public static class PeopleViewHolder extends RecyclerView.ViewHolder {

        private ListRowPeopleBinding mBinding;

        public PeopleViewHolder(@NonNull ListRowPeopleBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mBinding.executePendingBindings();
        }

        public void bind(People people) {
            mBinding.setPeople(people);
        }
    }
}

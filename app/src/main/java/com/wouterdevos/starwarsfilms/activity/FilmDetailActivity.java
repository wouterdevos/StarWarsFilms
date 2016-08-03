package com.wouterdevos.starwarsfilms.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.wouterdevos.starwarsfilms.R;
import com.wouterdevos.starwarsfilms.adapter.FilmDetailAdapter;
import com.wouterdevos.starwarsfilms.databinding.ActivityFilmDetailBinding;
import com.wouterdevos.starwarsfilms.presenter.FilmDetailContract;
import com.wouterdevos.starwarsfilms.presenter.FilmDetailPresenter;
import com.wouterdevos.starwarsfilms.valueobject.Film;

import java.util.ArrayList;
import java.util.List;

public class FilmDetailActivity extends AppCompatActivity implements FilmDetailContract.View {

    public static final String EXTRA_FILM = "film";

    private ActivityFilmDetailBinding mBinding;
    private FilmDetailPresenter mFilmDetailPresenter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FilmDetailAdapter mFilmDetailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_film_detail);

        setSupportActionBar(mBinding.toolbar.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("");

        Film film = getIntent().getParcelableExtra(EXTRA_FILM);
        mFilmDetailPresenter = new FilmDetailPresenter(this, film);

        initRecyclerView(film);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFilmDetailPresenter.registerEventBus();
        if (!mFilmDetailAdapter.hasPeople()) {
            mFilmDetailPresenter.getPeople();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mFilmDetailPresenter.unregisterEventBus();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setProgressIndicator(boolean loading) {
        mFilmDetailAdapter.setLoading(loading);
        mFilmDetailAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {
        mFilmDetailAdapter.setError(true);
        mFilmDetailAdapter.notifyDataSetChanged();
    }

    @Override
    public void showPeople(List<Object> peopleObjects) {
        mFilmDetailAdapter.addAll(peopleObjects);
        mFilmDetailAdapter.notifyDataSetChanged();
    }

    public void initRecyclerView(Film film) {
        List<Object> filmDetails = new ArrayList<>();
        filmDetails.add(film);

        mLayoutManager = new LinearLayoutManager(this);
        mFilmDetailAdapter = new FilmDetailAdapter(filmDetails);

        mBinding.recyclerView.setLayoutManager(mLayoutManager);
        mBinding.recyclerView.setAdapter(mFilmDetailAdapter);
    }

    public static void start(Activity activity, Film film) {
        Intent intent = new Intent(activity, FilmDetailActivity.class);
        intent.putExtra(EXTRA_FILM, film);
        activity.startActivity(intent);
    }
}

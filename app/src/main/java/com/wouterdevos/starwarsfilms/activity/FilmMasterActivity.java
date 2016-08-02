package com.wouterdevos.starwarsfilms.activity;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.wouterdevos.starwarsfilms.R;
import com.wouterdevos.starwarsfilms.adapter.DividerItemDecoration;
import com.wouterdevos.starwarsfilms.adapter.FilmMasterAdapter;
import com.wouterdevos.starwarsfilms.databinding.ActivityFilmMasterBinding;
import com.wouterdevos.starwarsfilms.presenter.FilmMasterContract;
import com.wouterdevos.starwarsfilms.presenter.FilmMasterPresenter;
import com.wouterdevos.starwarsfilms.valueobject.Film;

import java.util.ArrayList;
import java.util.List;

public class FilmMasterActivity extends AppCompatActivity implements FilmMasterContract.View {

    private static final String TAG = FilmMasterActivity.class.getSimpleName();

    private ActivityFilmMasterBinding mBinding;
    private FilmMasterContract.Presenter mFilmMasterPresenter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FilmMasterAdapter mFilmMasterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_film_master);
        mFilmMasterPresenter = new FilmMasterPresenter(this);

        setSupportActionBar(mBinding.toolbar.toolbar);
        getSupportActionBar().setTitle(R.string.app_name);

        initRecyclerView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFilmMasterPresenter.registerEventBus();
        if (!mFilmMasterAdapter.hasFilms()) {
            mFilmMasterPresenter.getFilms();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mFilmMasterPresenter.unregisterEventBus();
    }

    @Override
    public void setProgressIndicator(boolean loading) {
        mBinding.progressIndicator.setVisibility(loading ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showFilms(List<Film> films) {
        mFilmMasterAdapter.addAll(films);
        mFilmMasterAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {
        Toast.makeText(this, R.string.toast_error_connecting_to_internet, Toast.LENGTH_SHORT).show();
    }

    private void initRecyclerView() {
        Drawable divider = ContextCompat.getDrawable(this, R.drawable.divider_list_row_film);
        mLayoutManager = new LinearLayoutManager(this);
        mFilmMasterAdapter = new FilmMasterAdapter(new ArrayList<Film>());
        mFilmMasterAdapter.setOnItemClickListener(new FilmMasterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Film film = mFilmMasterAdapter.getFilm(position);
                FilmDetailActivity.start(FilmMasterActivity.this, film);
            }
        });

        mBinding.recyclerView.addItemDecoration(new DividerItemDecoration(divider));
        mBinding.recyclerView.setLayoutManager(mLayoutManager);
        mBinding.recyclerView.setAdapter(mFilmMasterAdapter);
    }
}

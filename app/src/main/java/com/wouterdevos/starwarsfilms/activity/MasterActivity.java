package com.wouterdevos.starwarsfilms.activity;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.wouterdevos.starwarsfilms.R;
import com.wouterdevos.starwarsfilms.adapter.DividerItemDecoration;
import com.wouterdevos.starwarsfilms.adapter.FilmsAdapter;
import com.wouterdevos.starwarsfilms.databinding.ActivityMasterBinding;
import com.wouterdevos.starwarsfilms.rest.StarWarsApiController;
import com.wouterdevos.starwarsfilms.valueobject.ErrorResponse;
import com.wouterdevos.starwarsfilms.valueobject.Film;
import com.wouterdevos.starwarsfilms.valueobject.FilmsResponse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MasterActivity extends AppCompatActivity {

    private static final String TAG = MasterActivity.class.getSimpleName();

    private ActivityMasterBinding mBinding;
    private RecyclerView.LayoutManager mLayoutManager;
    private FilmsAdapter mFilmsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_master);

        initRecyclerView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        StarWarsApiController.getFilms();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void initRecyclerView() {
        Drawable divider = ContextCompat.getDrawable(this, R.drawable.divider_list_row_film);
        mLayoutManager = new LinearLayoutManager(this);
        mFilmsAdapter = new FilmsAdapter(new ArrayList<Film>());

        mBinding.recyclerView.addItemDecoration(new DividerItemDecoration(divider));
        mBinding.recyclerView.setLayoutManager(mLayoutManager);
        mBinding.recyclerView.setAdapter(mFilmsAdapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFilmsResponse(FilmsResponse filmsResponse) {
        List<Film> films = filmsResponse.getFilms();
        for (Film film : films) {
            Log.i(TAG, "onFilmsResponse: title " + film.getTitle());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorResponse(ErrorResponse errorResponse) {

    }
}

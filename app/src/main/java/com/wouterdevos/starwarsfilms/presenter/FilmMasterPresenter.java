package com.wouterdevos.starwarsfilms.presenter;

import com.wouterdevos.starwarsfilms.rest.StarWarsApiController;
import com.wouterdevos.starwarsfilms.valueobject.ErrorResponse;
import com.wouterdevos.starwarsfilms.valueobject.Film;
import com.wouterdevos.starwarsfilms.valueobject.FilmsResponse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FilmMasterPresenter implements FilmMasterContract.Presenter {

    private FilmMasterContract.View mView;

    public FilmMasterPresenter(FilmMasterContract.View view) {
        mView = view;
    }

    @Override
    public void registerEventBus() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void unregisterEventBus() {
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void getFilms() {
        mView.setProgressIndicator(true);
        mView.setRetryButton(false);
        StarWarsApiController.getFilms();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRequestSuccess(FilmsResponse filmsResponse) {
        List<Film> films = filmsResponse.getFilms();
        Collections.sort(films, new Comparator<Film>() {
            @Override
            public int compare(Film lhs, Film rhs) {
                return lhs.getReleaseDate().compareTo(rhs.getReleaseDate());
            }
        });
        mView.setProgressIndicator(false);
        mView.showFilms(films);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRequestError(ErrorResponse errorResponse) {
        mView.setProgressIndicator(false);
        mView.setRetryButton(true);
        mView.showError();
    }
}

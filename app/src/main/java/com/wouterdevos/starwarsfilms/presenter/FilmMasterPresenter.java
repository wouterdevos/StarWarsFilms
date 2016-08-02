package com.wouterdevos.starwarsfilms.presenter;

import com.wouterdevos.starwarsfilms.rest.StarWarsApiController;
import com.wouterdevos.starwarsfilms.valueobject.ErrorResponse;
import com.wouterdevos.starwarsfilms.valueobject.FilmsResponse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
        StarWarsApiController.getFilms();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRequestSuccess(FilmsResponse filmsResponse) {
        mView.setProgressIndicator(false);
        mView.showFilms(filmsResponse.getFilms());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRequestError(ErrorResponse errorResponse) {
        mView.setProgressIndicator(false);
        mView.showError();
    }
}

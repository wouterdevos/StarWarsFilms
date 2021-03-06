package com.wouterdevos.starwarsfilms.presenter;

import com.wouterdevos.starwarsfilms.rest.StarWarsApiController;
import com.wouterdevos.starwarsfilms.valueobject.ErrorResponse;
import com.wouterdevos.starwarsfilms.valueobject.Film;
import com.wouterdevos.starwarsfilms.valueobject.People;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class FilmDetailPresenter implements FilmDetailContract.Presenter {

    private FilmDetailContract.View mView;
    private Film mFilm;
    private List<Object> mPeopleObjects;

    public FilmDetailPresenter(FilmDetailContract.View view, Film film) {
        mView = view;
        mFilm = film;
        mPeopleObjects = new ArrayList<>();
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
    public void getPeople() {
        mView.setProgressIndicator(true);
        StarWarsApiController.getPeople(mFilm);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRequestSuccess(People people) {
        mPeopleObjects.add(people);

        if (people.isFinal()) {
            mView.setProgressIndicator(false);
            mView.showPeople(mPeopleObjects);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRequestError(ErrorResponse errorResponse) {
        mView.setProgressIndicator(false);
        mView.showError();
    }
}

package com.wouterdevos.starwarsfilms.presenter;

import com.wouterdevos.starwarsfilms.valueobject.Film;

import java.util.List;

public class FilmMasterContract {

    public interface View extends BaseContract.View {
        void showFilms(List<Film> films);
    }

    public interface Presenter extends BaseContract.Presenter {
        void getFilms();
    }
}

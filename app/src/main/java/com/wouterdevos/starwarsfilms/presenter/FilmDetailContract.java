package com.wouterdevos.starwarsfilms.presenter;

import java.util.List;

public class FilmDetailContract {

    public interface View extends BaseContract.View {
        void showPeople(List<Object> peopleObjects);
    }

    public interface Presenter extends BaseContract.Presenter {
        void getPeople();
    }
}

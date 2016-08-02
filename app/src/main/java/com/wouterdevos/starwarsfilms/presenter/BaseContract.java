package com.wouterdevos.starwarsfilms.presenter;

public class BaseContract {

    public interface View {
        void setProgressIndicator(boolean loading);
        void showError();
    }

    public interface Presenter {
        void registerEventBus();
        void unregisterEventBus();
    }
}

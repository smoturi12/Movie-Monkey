package com.practice.sowmya.movie_monkey.framework;

public interface BaseContract {

    interface Presenter {
        /**
         *  Clear any data that is held by the presenter.
         */
        void dispose();
    }

    interface View {
        /**
         * @param error The error string that wil be showm
         */
        void showError(String error);

        /**
         * To show/hide basic loading overlay.
         */
        void showLoading(boolean show);
    }
}

package com.stateLayout.widget;

import com.stateLayout.widget.listeners.OnTryAgainListener;

import rx.Observable;

public interface StateLayoutContract {
    interface View {

        void toggleIndented(boolean show);

        void toggleProgressBar(boolean show);

        void toggleTryAgainButton(boolean show);

        void setIndentedMessage(String text);

        void setIndentedImage(int image);

        void setProgressBarColor(int color);

        void setTryButtonColor(int color);

        Observable<Void> setButtonClickListener();

        Presenter getPresenter();

        void setPresenter(Presenter presenter);
    }

    interface Presenter {

        void onLoadingToggled(boolean show);

        void onErrorToggled(boolean show);

        void onSetErrorText(String text);

        void onSetLoadingText(String text);

        void onSetLoadingImage(int image);

        void onSetErrorImage(int image);

        void onSetProgressBarColor(int color);

        void onSetTryButtonColor(int color);

        void onSetTryAgainListener(OnTryAgainListener onTryAgainListener);

        void onDestroy();
    }
}

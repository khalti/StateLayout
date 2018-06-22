package com.stateLayout.widget;

import com.stateLayout.widget.listeners.OnTryAgainListener;

import io.reactivex.Observable;

public interface StateLayoutContract {
    interface View {

        void toggleIndented(boolean show);

        void toggleProgressBar(boolean show);

        void toggleTryAgainButton(boolean show);

        void toggleIndentedView(boolean isCustom);

        void setIndentedMessage(String text);

        void setIndentedImage(int image);

        void setCustomIndentedView(String viewType);

        void setProgressBarColor(int color);

        void setTryButtonColor(int color);

        Observable<Object> setButtonClickListener();

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

        void onSetCustomLoadingView(boolean hasView);

        void onSetCustomErrorView(boolean hasView);

        void onSetProgressBarColor(int color);

        void onSetTryButtonColor(int color);

        void onSetTryAgainListener(OnTryAgainListener onTryAgainListener);

        Observable<Object> onSetTryAgainListener();

        void onDestroy();
    }
}

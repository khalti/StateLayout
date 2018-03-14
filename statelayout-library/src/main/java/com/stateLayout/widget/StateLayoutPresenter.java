package com.stateLayout.widget;

import android.support.annotation.NonNull;

import com.stateLayout.utils.EmptyUtil;
import com.stateLayout.utils.GuavaUtil;
import com.stateLayout.widget.listeners.OnTryAgainListener;

import rx.subscriptions.CompositeSubscription;

public class StateLayoutPresenter implements StateLayoutContract.Presenter {

    @NonNull
    private final StateLayoutContract.View view;
    private CompositeSubscription compositeSubscription;

    private String loadingText = "Loading... Please wait", errorText = "Something went wrong";
    private int loadingImage, errorImage;

    StateLayoutPresenter(@NonNull StateLayoutContract.View view) {
        this.view = GuavaUtil.checkNotNull(view);
        view.setPresenter(this);
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void onLoadingToggled(boolean show) {
        view.toggleIndented(show);
        view.toggleProgressBar(show);
        view.setIndentedMessage(show ? loadingText : errorText);
        view.setIndentedImage(show ? loadingImage : errorImage);
        view.toggleTryAgainButton(false);
    }

    @Override
    public void onErrorToggled(boolean show) {
        view.toggleIndented(show);
        view.setIndentedMessage(show ? errorText : loadingText);
        view.setIndentedImage(show ? errorImage : loadingImage);
        view.toggleProgressBar(!show);
        view.toggleTryAgainButton(true);
    }

    @Override
    public void onSetErrorText(String text) {
        if (EmptyUtil.isNotNull(text) && EmptyUtil.isNotEmpty(text)) {
            errorText = text;
        }
    }

    @Override
    public void onSetLoadingText(String text) {
        if (EmptyUtil.isNotNull(text) && EmptyUtil.isNotEmpty(text)) {
            loadingText = text;
        }
    }

    @Override
    public void onSetLoadingImage(int image) {
        loadingImage = image;
    }

    @Override
    public void onSetErrorImage(int image) {
        errorImage = image;
    }

    @Override
    public void onSetProgressBarColor(int color) {
        view.setProgressBarColor(color);
    }

    @Override
    public void onSetTryButtonColor(int color) {
        view.setTryButtonColor(color);
    }

    @Override
    public void onSetTryAgainListener(OnTryAgainListener onTryAgainListener) {
        compositeSubscription.add(view.setButtonClickListener().subscribe(aVoid -> onTryAgainListener.onTryAgain()));
    }

    @Override
    public void onDestroy() {
        if (EmptyUtil.isNotNull(compositeSubscription) && compositeSubscription.hasSubscriptions() && !compositeSubscription.isUnsubscribed()) {
            compositeSubscription.unsubscribe();
        }
    }
}

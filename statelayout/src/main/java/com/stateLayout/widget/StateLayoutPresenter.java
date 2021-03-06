package com.stateLayout.widget;

import android.support.annotation.NonNull;

import com.stateLayout.utils.SLEmptyUtil;
import com.stateLayout.utils.SLGuavaUtil;
import com.stateLayout.widget.listeners.OnTryAgainListener;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

public class StateLayoutPresenter implements StateLayoutContract.Presenter {

    @NonNull
    private final StateLayoutContract.View view;
    private CompositeDisposable compositeDisposable;

    private String loadingText = "", errorText = "";
    private int loadingImage = -998, errorImage = -999;
    private boolean hasCustomLoadView = false, hasCustomErrorView = false, shouldShowTryAgain = true;

    StateLayoutPresenter(@NonNull StateLayoutContract.View view) {
        this.view = SLGuavaUtil.checkNotNull(view);
        view.setPresenter(this);
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onLoadingToggled(boolean show) {
        view.toggleIndented(show);
        view.toggleProgressBar(show);
        view.setIndentedMessage(show ? loadingText : errorText);
        view.toggleIndentedView(hasCustomLoadView);
        if (hasCustomLoadView) {
            view.setCustomIndentedView("load");
        } else {
            view.setIndentedImage(show ? loadingImage : errorImage);
        }
        view.toggleTryAgainButton(false);
    }

    @Override
    public void onErrorToggled(boolean show) {
        view.toggleIndented(show);
        view.setIndentedMessage(show ? errorText : loadingText);
        view.toggleIndentedView(hasCustomErrorView);
        if (hasCustomErrorView) {
            view.setCustomIndentedView("error");
        } else {
            view.setIndentedImage(show ? errorImage : loadingImage);
        }
        view.toggleProgressBar(!show);
        view.toggleTryAgainButton(shouldShowTryAgain);
    }

    @Override
    public void onTryAgainToggled(boolean show) {
        shouldShowTryAgain = show;
    }

    @Override
    public void onSetErrorText(String text) {
        if (SLEmptyUtil.isNotNull(text) && SLEmptyUtil.isNotEmpty(text)) {
            errorText = text;
        }
    }

    @Override
    public void onSetLoadingText(String text) {
        if (SLEmptyUtil.isNotNull(text) && SLEmptyUtil.isNotEmpty(text)) {
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
    public void onSetCustomLoadingView(boolean hasView) {
        this.hasCustomLoadView = hasView;
    }

    @Override
    public void onSetCustomErrorView(boolean hasView) {
        this.hasCustomErrorView = hasView;
    }

    @Override
    public void onSetCustomTryAgainView(boolean hasView) {
        if (hasView) {
            view.setCustomTryAgainView();
        }
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
    public void onSetTryButtonText(String text) {
        view.setTryButtonText(text);
    }

    @Override
    public void onSetTryAgainListener(OnTryAgainListener onTryAgainListener) {
        compositeDisposable.add(view.setButtonClickListener().subscribe(aVoid -> onTryAgainListener.onTryAgain()));
    }

    @Override
    public Observable<Object> onSetTryAgainListener() {
        return view.setButtonClickListener();
    }

    @Override
    public void onDestroy() {
        if (SLEmptyUtil.isNotNull(compositeDisposable) && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }
}

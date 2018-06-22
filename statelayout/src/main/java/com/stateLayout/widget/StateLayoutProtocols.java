package com.stateLayout.widget;

import android.view.View;

import com.stateLayout.widget.listeners.OnTryAgainListener;

import io.reactivex.Observable;

public interface StateLayoutProtocols {

    void toggleLoading(boolean show);

    void toggleError(boolean show);

    void setLoadingText(String text);

    void setErrorText(String text);

    void setLoadingImage(int image);

    void setErrorImage(int image);

    void setCustomLoadView(View view);

    void setCustomErrorView(View view);

    void setProgressBarColor(int color);

    void setTryButtonColor(int color);

    void setOnTryAgainListener(OnTryAgainListener onTryAgainListener);

    Observable<Object> setOnTryAgainListener();

    void onDestroy();
}

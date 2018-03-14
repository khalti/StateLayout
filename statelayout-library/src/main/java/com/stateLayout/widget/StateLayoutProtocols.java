package com.stateLayout.widget;

import com.stateLayout.widget.listeners.OnTryAgainListener;

public interface StateLayoutProtocols {

    void toggleLoading(boolean show);

    void toggleError(boolean show);

    void setLoadingText(String text);

    void setErrorText(String text);

    void setLoadingImage(int image);

    void setErrorImage(int image);

    void setProgressBarColor(int color);

    void setTryButtonColor(int color);

    void setOnTryAgainListener(OnTryAgainListener onTryAgainListener);

    void onDestroy();
}

package com.stateLayout.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.jakewharton.rxbinding2.view.RxView;
import com.stateLayout.R;
import com.stateLayout.carbonX.widget.Button;
import com.stateLayout.carbonX.widget.ProgressBar;
import com.stateLayout.utils.EmptyUtil;
import com.stateLayout.widget.listeners.OnTryAgainListener;

import io.reactivex.Observable;


public class StateLayout extends FrameLayout implements StateLayoutProtocols {

    private Context context;
    private AttributeSet attributeSet;
    private StateLayoutContract.Presenter presenter;

    /*Views*/
    private NestedScrollView nsvIndented;
    private ProgressBar pdLoad;
    private AppCompatTextView tvMessage;
    private Button btnTryAgain;
    private ImageView ivIndented;
    private FrameLayout flContainer, flCustomView, flLoad;

    private View loadingView, errorView;

    public StateLayout(@NonNull Context context) {
        super(context);
        this.context = context;
        init();
    }

    public StateLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.attributeSet = attrs;
        init();
    }

    @Override
    public void toggleLoading(boolean show) {
        if (EmptyUtil.isNotNull(presenter)) {
            presenter.onLoadingToggled(show);
        }
    }

    @Override
    public void toggleError(boolean show) {
        if (EmptyUtil.isNotNull(presenter)) {
            presenter.onErrorToggled(show);
        }
    }

    @Override
    public void setLoadingText(String text) {
        if (EmptyUtil.isNotNull(presenter)) {
            presenter.onSetLoadingText(text);
        }
    }

    @Override
    public void setErrorText(String text) {
        if (EmptyUtil.isNotNull(presenter)) {
            presenter.onSetErrorText(text);
        }
    }

    @Override
    public void setLoadingImage(int image) {
        if (EmptyUtil.isNotNull(presenter)) {
            presenter.onSetLoadingImage(image);
        }
    }

    @Override
    public void setErrorImage(int image) {
        if (EmptyUtil.isNotNull(presenter)) {
            presenter.onSetErrorImage(image);
        }
    }

    @Override
    public void setCustomLoadView(View view) {
        this.loadingView = view;
        if (EmptyUtil.isNotNull(presenter)) {
            presenter.onSetCustomLoadingView(EmptyUtil.isNotNull(loadingView));
        }
    }

    @Override
    public void setCustomErrorView(View view) {
        this.errorView = view;
        if (EmptyUtil.isNotNull(presenter)) {
            presenter.onSetCustomErrorView(EmptyUtil.isNotNull(errorView));
        }
    }

    @Override
    public void setProgressBarColor(int color) {
        if (EmptyUtil.isNotNull(presenter)) {
            presenter.onSetProgressBarColor(color);
        }
    }

    @Override
    public void setTryButtonColor(int color) {
        if (EmptyUtil.isNotNull(presenter)) {
            presenter.onSetTryButtonColor(color);
        }
    }

    @Override
    public void setOnTryAgainListener(OnTryAgainListener onTryAgainListener) {
        if (EmptyUtil.isNotNull(presenter)) {
            presenter.onSetTryAgainListener(onTryAgainListener);
        }
    }

    @Override
    @Nullable
    public Observable<Object> setOnTryAgainListener() {
        if (EmptyUtil.isNotNull(presenter)) {
            return presenter.onSetTryAgainListener();
        }
        return null;
    }

    @Override
    public void onDestroy() {
        if (EmptyUtil.isNotNull(presenter)) {
            presenter.onDestroy();
        }
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (EmptyUtil.isNotNull(flContainer)) {
            flContainer.addView(child, index, params);
        } else {
            super.addView(child, index, params);
        }
    }

    private void init() {
        @SuppressLint("CustomViewStyleable")
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.app, 0, 0);
        String errorText = typedArray.getString(R.styleable.app_errorText);
        String loadingText = typedArray.getString(R.styleable.app_loadingText);
        int progressBarColor = typedArray.getInt(R.styleable.app_progressBarColor, R.color.sl_progress);
        int tryButtonColor = typedArray.getInt(R.styleable.app_tryButtonColor, R.color.sl_try_again);
        int loadingImage = typedArray.getResourceId(R.styleable.app_loadingImage, -998);
        int errorImage = typedArray.getResourceId(R.styleable.app_errorImage, -999);

        typedArray.recycle();

        View mainView;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (EmptyUtil.isNotNull(inflater)) {
            mainView = inflater.inflate(R.layout.state_layout, this, true);

            nsvIndented = mainView.findViewById(R.id.nsvIndented);
            pdLoad = mainView.findViewById(R.id.pdLoad);
            tvMessage = mainView.findViewById(R.id.tvMessage);
            ivIndented = mainView.findViewById(R.id.ivIndented);
            btnTryAgain = mainView.findViewById(R.id.btnTryAgain);
            flContainer = mainView.findViewById(R.id.flContainer);
            flCustomView = mainView.findViewById(R.id.flCustomView);
            flLoad = mainView.findViewById(R.id.flLoad);

            presenter = new State().getPresenter();
            presenter.onSetErrorText(errorText);
            presenter.onSetLoadingText(loadingText);
            presenter.onSetProgressBarColor(progressBarColor);
            presenter.onSetTryButtonColor(tryButtonColor);
            presenter.onSetErrorImage(errorImage);
            presenter.onSetLoadingImage(loadingImage);
        }
    }

    private class State implements StateLayoutContract.View {

        private StateLayoutContract.Presenter presenter;

        State() {
            presenter = new StateLayoutPresenter(this);
        }

        @Override
        public void toggleIndented(boolean show) {
            nsvIndented.setVisibility(show ? VISIBLE : GONE);
            flContainer.setVisibility(show ? GONE : VISIBLE);
        }

        @Override
        public void toggleProgressBar(boolean show) {
            flLoad.setVisibility(show ? VISIBLE : GONE);
        }

        @Override
        public void toggleTryAgainButton(boolean show) {
            btnTryAgain.setVisibility(show ? VISIBLE : INVISIBLE);
        }

        @Override
        public void toggleIndentedView(boolean isCustom) {
            flCustomView.setVisibility(isCustom ? VISIBLE : GONE);
            ivIndented.setVisibility(!isCustom ? VISIBLE : GONE);
        }

        @Override
        public void setIndentedMessage(String text) {
            if (EmptyUtil.isNotEmpty(text)) {
                tvMessage.setVisibility(VISIBLE);
                tvMessage.setText(text);
            } else {
                tvMessage.setVisibility(GONE);
            }
        }

        @Override
        public void setIndentedImage(int image) {
            if (image > 0) {
                ivIndented.setVisibility(VISIBLE);
                ivIndented.setImageResource(image);
            } else {
                ivIndented.setVisibility(GONE);
            }
        }

        @Override
        public void setCustomIndentedView(String viewType) {
            flCustomView.removeAllViews();
            flCustomView.addView(viewType.equals("load") ? loadingView : errorView);
        }

        @Override
        public void setProgressBarColor(int color) {
            pdLoad.setTint(getResources().getColorStateList(color));
        }

        @Override
        public void setTryButtonColor(int color) {
            btnTryAgain.setBackgroundTint(getResources().getColorStateList(color));
        }

        @Override
        public Observable<Object> setButtonClickListener() {
            return RxView.clicks(btnTryAgain);
        }

        @Override
        public StateLayoutContract.Presenter getPresenter() {
            return presenter;
        }

        @Override
        public void setPresenter(StateLayoutContract.Presenter presenter) {
            this.presenter = presenter;
        }
    }
}

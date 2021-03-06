package com.stateLayout.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
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
import com.stateLayout.utils.SLEmptyUtil;
import com.stateLayout.widget.listeners.OnTryAgainListener;

import io.reactivex.Observable;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;


public class StateLayout extends FrameLayout implements StateLayoutProtocols {

    private Context context;
    private AttributeSet attributeSet;
    private StateLayoutContract.Presenter presenter;

    /*Views*/
    private NestedScrollView nsvIndented;
    private MaterialProgressBar pdLoad;
    private AppCompatTextView tvMessage;
    private MaterialButton btnTryAgain;
    private ImageView ivIndented;
    private FrameLayout flContainer, flCustomView, flLoad, flCustomTryAgain;

    private View loadingView, errorView, tryAgainView;

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
        if (SLEmptyUtil.isNotNull(presenter)) {
            presenter.onLoadingToggled(show);
        }
    }

    @Override
    public void toggleError(boolean show) {
        if (SLEmptyUtil.isNotNull(presenter)) {
            presenter.onErrorToggled(show);
        }
    }

    @Override
    public void toggleTryAgain(boolean show) {
        if (SLEmptyUtil.isNotNull(presenter)) {
            presenter.onTryAgainToggled(show);
        }
    }

    @Override
    public void setLoadingText(String text) {
        if (SLEmptyUtil.isNotNull(presenter)) {
            presenter.onSetLoadingText(text);
        }
    }

    @Override
    public void setErrorText(String text) {
        if (SLEmptyUtil.isNotNull(presenter)) {
            presenter.onSetErrorText(text);
        }
    }

    @Override
    public void setLoadingImage(int image) {
        if (SLEmptyUtil.isNotNull(presenter)) {
            presenter.onSetLoadingImage(image);
        }
    }

    @Override
    public void setErrorImage(int image) {
        if (SLEmptyUtil.isNotNull(presenter)) {
            presenter.onSetErrorImage(image);
        }
    }

    @Override
    public void setCustomLoadView(View view) {
        this.loadingView = view;
        if (SLEmptyUtil.isNotNull(presenter)) {
            presenter.onSetCustomLoadingView(SLEmptyUtil.isNotNull(loadingView));
        }
    }

    @Override
    public void setCustomErrorView(View view) {
        this.errorView = view;
        if (SLEmptyUtil.isNotNull(presenter)) {
            presenter.onSetCustomErrorView(SLEmptyUtil.isNotNull(errorView));
        }
    }

    @Override
    public void setCustomTryAgainButton(View view) {
        this.tryAgainView = view;
        if (SLEmptyUtil.isNotNull(presenter)) {
            presenter.onSetCustomTryAgainView(SLEmptyUtil.isNotNull(tryAgainView));
        }
    }

    @Override
    public void setButtonText(String text) {
        if (SLEmptyUtil.isNotNull(presenter)) {
            presenter.onSetTryButtonText(text);
        }
    }

    @Override
    public void setProgressBarColor(int color) {
        if (SLEmptyUtil.isNotNull(presenter)) {
            presenter.onSetProgressBarColor(color);
        }
    }

    @Override
    public void setTryButtonColor(int color) {
        if (SLEmptyUtil.isNotNull(presenter)) {
            presenter.onSetTryButtonColor(color);
        }
    }

    @Override
    public void setOnTryAgainListener(OnTryAgainListener onTryAgainListener) {
        if (SLEmptyUtil.isNotNull(presenter)) {
            presenter.onSetTryAgainListener(onTryAgainListener);
        }
    }

    @Override
    @Nullable
    public Observable<Object> setOnTryAgainListener() {
        if (SLEmptyUtil.isNotNull(presenter)) {
            return presenter.onSetTryAgainListener();
        }
        return null;
    }

    @Override
    public ImageView getIndentedImageView() {
        return ivIndented;
    }

    @Override
    public void onDestroy() {
        if (SLEmptyUtil.isNotNull(presenter)) {
            presenter.onDestroy();
        }
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (SLEmptyUtil.isNotNull(flContainer)) {
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
        boolean hasTryAgain = typedArray.getBoolean(R.styleable.app_has_try_again, true);
        int tId = typedArray.getResourceId(R.styleable.app_custom_try_again, -999);
        int lId = typedArray.getResourceId(R.styleable.app_custom_load, -999);
        int eId = typedArray.getResourceId(R.styleable.app_custom_error, -999);

        typedArray.recycle();

        View mainView;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (SLEmptyUtil.isNotNull(inflater)) {
            mainView = inflater.inflate(R.layout.state_layout, this, true);

            nsvIndented = mainView.findViewById(R.id.state_layout_nsvIndented);
            pdLoad = mainView.findViewById(R.id.state_layout_pdLoad);
            tvMessage = mainView.findViewById(R.id.state_layout_tvMessage);
            ivIndented = mainView.findViewById(R.id.state_layout_ivIndented);
            btnTryAgain = mainView.findViewById(R.id.state_layout_btnTryAgain);
            flContainer = mainView.findViewById(R.id.state_layout_flContainer);
            flCustomView = mainView.findViewById(R.id.state_layout_flCustomView);
            flLoad = mainView.findViewById(R.id.state_layout_flLoad);
            flCustomTryAgain = mainView.findViewById(R.id.state_layout_flCustomTryAgain);

            presenter = new State().getPresenter();
            presenter.onSetErrorText(errorText);
            presenter.onSetLoadingText(loadingText);
            presenter.onSetProgressBarColor(progressBarColor);
            presenter.onSetTryButtonColor(tryButtonColor);
            presenter.onSetErrorImage(errorImage);
            presenter.onSetLoadingImage(loadingImage);
            presenter.onTryAgainToggled(hasTryAgain);

            if (tId > 0) {
                tryAgainView = LayoutInflater.from(context).inflate(tId, flCustomTryAgain, false);
                presenter.onSetCustomTryAgainView(SLEmptyUtil.isNotNull(tryAgainView));
            }

            if (lId > 0) {
                loadingView = LayoutInflater.from(context).inflate(lId, flCustomView, false);
                presenter.onSetCustomLoadingView(SLEmptyUtil.isNotNull(loadingView));
            }

            if (eId > 0) {
                errorView = LayoutInflater.from(context).inflate(eId, flCustomView, false);
                presenter.onSetCustomErrorView(SLEmptyUtil.isNotNull(errorView));
            }
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
            if (SLEmptyUtil.isNotNull(tryAgainView)) {
                flCustomTryAgain.setVisibility(show ? VISIBLE : INVISIBLE);
            } else {
                btnTryAgain.setVisibility(show ? VISIBLE : INVISIBLE);
            }
        }

        @Override
        public void toggleIndentedView(boolean isCustom) {
            flCustomView.setVisibility(isCustom ? VISIBLE : GONE);
            ivIndented.setVisibility(!isCustom ? VISIBLE : GONE);
        }

        @Override
        public void setIndentedMessage(String text) {
            if (SLEmptyUtil.isNotEmpty(text)) {
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
        public void setCustomTryAgainView() {
            flCustomTryAgain.removeAllViews();
            btnTryAgain.setVisibility(INVISIBLE);
            flCustomTryAgain.setVisibility(VISIBLE);
            flCustomTryAgain.addView(tryAgainView);
        }

        @Override
        public void setProgressBarColor(int color) {
            pdLoad.setIndeterminateTintList(getResources().getColorStateList(color));
        }

        @Override
        public void setTryButtonColor(int color) {
            btnTryAgain.setBackgroundTintList(getResources().getColorStateList(color));
        }

        @Override
        public void setTryButtonText(String text) {
            if (SLEmptyUtil.isNotNull(btnTryAgain)) {
                btnTryAgain.setText(text);
            }
        }

        @Override
        public Observable<Object> setButtonClickListener() {
            return RxView.clicks(SLEmptyUtil.isNotNull(tryAgainView) ? tryAgainView : btnTryAgain);
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

package com.stateLayoutImpl;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import com.stateLayout.utils.EmptyUtil;
import com.stateLayout.widget.StateLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.slSample)
    StateLayout slSample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        slSample.setErrorImage(R.drawable.ic_launcher_background);
//        slSample.setLoadingImage(R.mipmap.cat);

//        View loadView = getLayoutInflater().inflate(R.layout.custom_view, null);
//        ((ImageView) loadView.findViewById(R.id.iv)).setImageResource(R.mipmap.cat);
//        slSample.setCustomLoadView(loadView);

        /*View errorView = getLayoutInflater().inflate(R.layout.custom_view, null);
        ((ImageView) errorView.findViewById(R.id.iv)).setImageResource(R.drawable.ic_launcher_background);
        slSample.setCustomErrorView(errorView);*/

//        slSample.setOnTryAgainListener(() -> load(true));
        slSample.setCustomTryAgainButton(LayoutInflater.from(this).inflate(R.layout.custom_try_again_view, slSample, false));
        Observable<Object> tryAgain = slSample.setOnTryAgainListener();
        if (EmptyUtil.isNotNull(tryAgain)) {
            new CompositeDisposable().add(tryAgain.subscribe(o -> load(true)));
        }
        load(false);
    }

    public void load(boolean success) {
        slSample.toggleLoading(true);
        new Handler().postDelayed(() -> {
            if (success) {
                slSample.toggleLoading(false);
            } else {
                slSample.toggleLoading(false);
                slSample.toggleError(true);
            }
        }, 3000);
    }
}
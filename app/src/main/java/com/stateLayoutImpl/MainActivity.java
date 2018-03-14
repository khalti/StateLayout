package com.stateLayoutImpl;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.stateLayout.widget.StateLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.slSample)
    StateLayout slSample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        slSample.setErrorImage(R.drawable.ic_launcher_background);
        slSample.setLoadingImage(R.mipmap.cat);
        slSample.toggleLoading(true);
        new Handler().postDelayed(() -> slSample.toggleLoading(false), 3000);
    }
}

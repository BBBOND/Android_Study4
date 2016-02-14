package com.kim.floatmenu;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean isOpen = false;
    private int[] ids = {R.id.iv_a, R.id.iv_b, R.id.iv_c, R.id.iv_d, R.id.iv_e, R.id.iv_f, R.id.iv_g, R.id.iv_h};
    private List<ImageView> imageViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        for (int i = 0; i < ids.length; i++) {
            ImageView imageView = (ImageView) findViewById(ids[i]);
            imageView.setOnClickListener(this);
            imageViews.add(imageView);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_a:
                startAnim();
                break;
            case R.id.iv_b:
            case R.id.iv_c:
            case R.id.iv_d:
            case R.id.iv_e:
            case R.id.iv_f:
            case R.id.iv_g:
            case R.id.iv_h:
                Toast.makeText(MainActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void startAnim() {
        for (int i = 1; i < ids.length; i++) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(imageViews.get(i),
                    "translationY", isOpen ? 180f * i : 0f, isOpen ? 0f : 180f * i);
            animator.setDuration(500);
            //差值器，设置运动的速度
            //Bounce、Accelerate、Decelerate、Accelerate/Decelerate、Anticipate、OverShoot、Anticipate/OverShoot
            animator.setInterpolator(new BounceInterpolator());
            animator.start();
        }
        isOpen = !isOpen;
    }
}

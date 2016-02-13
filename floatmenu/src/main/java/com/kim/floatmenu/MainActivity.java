package com.kim.floatmenu;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;
    private ImageView imageView5;
    private ImageView imageView6;
    private ImageView button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        imageView1 = (ImageView) findViewById(R.id.iv_camera);
        imageView2 = (ImageView) findViewById(R.id.iv_music);
        imageView3 = (ImageView) findViewById(R.id.iv_place);
        imageView4 = (ImageView) findViewById(R.id.iv_sleep);
        imageView5 = (ImageView) findViewById(R.id.iv_thought);
        imageView6 = (ImageView) findViewById(R.id.iv_with);
        button = (ImageView) findViewById(R.id.iv_normal);
    }

    public void click(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", 0f, 45f);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(imageView1, "translationX", 0f, 53f * 1);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(imageView2, "translationX", 0f, 53f * 2);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(imageView3, "translationX", 0f, 53f * 3);
        ObjectAnimator animator4 = ObjectAnimator.ofFloat(imageView4, "translationX", 0f, 53f * 4);
        ObjectAnimator animator5 = ObjectAnimator.ofFloat(imageView5, "translationX", 0f, 53f * 5);
        ObjectAnimator animator6 = ObjectAnimator.ofFloat(imageView6, "translationX", 0f, 53f * 6);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(animator, animator1, animator2, animator3, animator4, animator5, animator6);
        set.setDuration(1000);
        set.start();

    }
}

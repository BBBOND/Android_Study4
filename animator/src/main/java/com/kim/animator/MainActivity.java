package com.kim.animator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageView);
    }

    public void click(View view) {
        Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
    }

    /**
     * 旧的Animation方法
     * 缺点:
     * 1. view移动动作响应范围未移动
     *
     * @param view
     */
    public void move(View view) {
        Toast.makeText(this, "moving...", Toast.LENGTH_SHORT).show();
        TranslateAnimation animation = new TranslateAnimation(0, 200, 0, 0);
        animation.setDuration(1000);
        animation.setFillAfter(true);
        imageView.startAnimation(animation);
    }

    /**
     * 新的ObjectAnimator方法
     * 特点:
     * 1. view移动动作响应范围同时跟着移动
     *
     * @param view
     */
    public void objectAnimatorMove(View view) {
        Toast.makeText(this, "moving...", Toast.LENGTH_SHORT).show();
        //translationX、translationY 偏移量X、Y
        //X、Y  最终所到达的绝对值
        //rotation 旋转角度
        //alpha 透明度
        ObjectAnimator.ofFloat(imageView, "rotation", 0f, 360f).setDuration(2000).start();
        ObjectAnimator.ofFloat(imageView, "translationX", 0f, 200f).setDuration(2000).start();
        ObjectAnimator.ofFloat(imageView, "translationY", 0f, 200f).setDuration(2000).start();
    }

    /**
     * 新的PropertyValuesHolder设置变换方法及参数
     * 特点:
     * 1. 相对于直接使用ObjectAnimator方法资源更优化
     *
     * @param view
     */
    public void propertyValuesHolderMove(View view) {
        Toast.makeText(this, "moving...", Toast.LENGTH_SHORT).show();
        PropertyValuesHolder p1 = PropertyValuesHolder.ofFloat("rotation", 0f, 360f);
        PropertyValuesHolder p2 = PropertyValuesHolder.ofFloat("translationX", 0f, 200f);
        PropertyValuesHolder p3 = PropertyValuesHolder.ofFloat("translationY", 0f, 200f);
        ObjectAnimator.ofPropertyValuesHolder(imageView, p1, p2, p3).setDuration(2000).start();
    }

    /**
     * 新的AnimatorSet方法，可以顺序执行Animator动画
     * 特点:
     * 1. 可以按指定的顺序执行动画，而不是同时执行
     * <p/>
     * 注意:
     * 1. Animator不可重用
     *
     * @param view
     */
    public void animatorSetMove(View view) {
        Toast.makeText(this, "moving...", Toast.LENGTH_SHORT).show();
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(imageView, "rotation", 0f, 360f);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(imageView, "translationX", 0f, 200f);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(imageView, "translationY", 0f, 200f);
        AnimatorSet set = new AnimatorSet();
        set.play(animator2).with(animator3).after(animator1);
//        set.playTogether(animator1, animator2, animator3);
//        set.playSequentially(animator1, animator2, animator3);
        set.setDuration(2000).start();
    }

    public void moveListener(View view) {
        Toast.makeText(this, "Starting...", Toast.LENGTH_SHORT).show();
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "alpha", 0f, 1f);
        animator.setDuration(10000);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Toast.makeText(MainActivity.this, "End...", Toast.LENGTH_SHORT).show();
            }
        });
//        animator.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//                Toast.makeText(MainActivity.this, "Start...", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                Toast.makeText(MainActivity.this, "End...", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//                Toast.makeText(MainActivity.this, "Cancel...", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//                Toast.makeText(MainActivity.this, "Repeat...", Toast.LENGTH_SHORT).show();
//            }
//        });
        animator.start();
    }

    public void valueAnimator(View view) {
        final Button button = (Button) view;
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 100f);
        valueAnimator.setDuration(10000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value = (Float) animation.getAnimatedValue();
                button.setText(String.format("%.2f", value));
            }
        });
        valueAnimator.start();

        PointF startPoint = new PointF(0, 0);
        PointF endPoint = new PointF(200f, 200f);
        ValueAnimator animator = ValueAnimator.ofObject(new TypeEvaluator<PointF>() {
            @Override
            public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
                PointF point = new PointF();
                point.set((endValue.x - startValue.x) * fraction, (endValue.y - startValue.y) * fraction);
                return point;
            }
        }, startPoint, endPoint);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF point = (PointF) animation.getAnimatedValue();
                imageView.setX(point.x);
                imageView.setY(point.y);
            }
        });
        animator.setDuration(2000);
        animator.start();
    }
}
package com.aaa.mvpapp;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

public class MyProgressView extends View implements ValueAnimator.AnimatorUpdateListener {
    private Bitmap bitmap;
    private int height;//进度条高度
    private int borderHeight = 12;//边框宽

    private int loadingWidth;//加载进度宽度
    private static final int secMill = 2 * 1000;
    private ValueAnimator animator;

    public MyProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        animator = ValueAnimator.ofInt(0, getMeasuredWidth());
        animator.setDuration(secMill);
        animator.addUpdateListener(this);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getWidth() <= 0) return;

        //设置画笔基本属性
        Paint paint = new Paint();
        paint.setColor(Color.RED);  //设置画笔颜色
        paint.setStyle(Paint.Style.STROKE);//设置填充样式
        paint.setStrokeWidth(borderHeight);//设置画笔宽度

        RectF rect = new RectF(0, 0, getWidth(), getHeight());
        canvas.drawRect(rect, paint);
//
//        //加载进度
        paint.setStyle(Paint.Style.FILL);//填充
        paint.setColor(Color.parseColor("#ff0000"));
        RectF rectLoading = new RectF(0, 0, loadingWidth, getHeight());
        canvas.drawRect(rectLoading, paint);
        //字体
        int progress = (int) ((loadingWidth / (float) getMeasuredWidth()) * 100);
        paint.setTextSize(30);
        paint.setColor(Color.parseColor("#ffffff"));
        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(progress + "%", (loadingWidth - 20) > 0 ? (loadingWidth - 20) : 0, getMeasuredHeight() / 2 + 15, paint);
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        int currentWidth = (int) animation.getAnimatedValue();
        loadingWidth = currentWidth;
        postInvalidate();
    }

    public void startAnim() {
        animator.start();
    }

    public void reset() {
        animator.cancel();
        loadingWidth = 0;
        postInvalidate();
    }
}

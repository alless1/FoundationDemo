package com.alless.commonlib.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by chengjie on 2018/12/4
 * Description: 发散圆
 */
public class CircleSpeedUpView extends View {
    private static final String TAG = "CircleProgressView";
    private Paint mGrayPaint;
    private int mWidth;
    private int mHeight;
    private final int BASE_CIRCLE_SIDE = 180;//手动配置，控件高度dp-80，（200 以内部圆为基准220dp, 外圆计算是276dp）
    private final int BIG_STROKE_WIDTH = 13;//粗圆边
    private final int MIDDLE_STROKE_WIDTH = 7;//粗圆边
    private final int SMALL_STROKE_WIDTH = 4;//细圆边
    private int startAngle = -90;//起始角度
    private int sweepAngle = 0;//扫描角度
    private int firstARectSide;
    private int secondARectSide;
    private int thirdRectSide;
    private RectF firstARect;
    private RectF secondARect;
    private RectF thirdARect;
    private Paint firstPaint;
    private Paint secondPaint;
    private Paint thirdPaint;
    private Paint wavePaint;

    private Paint whitePaint;


    private ValueAnimator mAnimator;
    private OnProgressMaxListener mListener;

    // 透明度集合
    private List<Integer> mAlphas = new ArrayList<>();
    // 扩散圆增长直径集合
    private List<Double> mWidths = new ArrayList<>();
    // 扩散圆的最大个数
    private int mDiffuseNum = 3;
    //步进每次减少1透明度
    private double stepRadius = 0;
    //间距半径
    private int spaceWidth = 0;
    // 中心圆半径
    private int mCenterRadius = 0;

    private boolean isDrawWave = false;

    private boolean isInterrupt = false;

    private boolean isSpeedTest = false;

    private int delayMilliseconds = 5 * 2;
    private int mTotalSpaceWidth;


    public CircleSpeedUpView(Context context) {
        this(context, null);
    }

    public CircleSpeedUpView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleSpeedUpView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //属性动画差值器
        mAnimator = ValueAnimator.ofInt(0, 0);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        initSize();
        initData();
    }

    private void initSize() {
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        firstARectSide = dp2px(BASE_CIRCLE_SIDE);
        //Log.e(TAG, "initSize: mWidth ="+mWidth +"mHeight="+mHeight +"firstARectSide="+firstARectSide );
        int secondSide = BASE_CIRCLE_SIDE + BIG_STROKE_WIDTH * 3 + SMALL_STROKE_WIDTH;
        secondARectSide = dp2px(secondSide);
        int thirdSide = secondSide + SMALL_STROKE_WIDTH * 2 + BIG_STROKE_WIDTH * 2;
        thirdRectSide = dp2px(thirdSide);
        //mCenterRadius = (firstARectSide+BIG_STROKE_WIDTH) / 2;
        mCenterRadius = (firstARectSide) / 2;
    }


    private void initData() {
        mAlphas.add(255);
        mWidths.add(0.0);
        mTotalSpaceWidth = thirdRectSide - firstARectSide;
        spaceWidth = mTotalSpaceWidth / mDiffuseNum;
        stepRadius = mTotalSpaceWidth / 2f / 255f;

        mGrayPaint = new Paint();
        mGrayPaint.setStyle(Paint.Style.STROKE);

        int yellow = 0xFFD6EC23;
        int green = 0xFF51B198;
        int cyan = 0xFF05E1EC;//青色
        int blue = 0xFF4591F4;//蓝色
        //int[] colors1 = {yellow, green, green, yellow};
        int[] colors1 = {cyan,blue, blue, cyan};//cyan,blue, blue, cyan
        float[] positions1 = {0.125f,0.375f, 0.625f, 0.875f};//0.125f,0.375f, 0.875f, 1.0f
        Shader shader1 = new SweepGradient(mWidth / 2, mHeight / 2, colors1, positions1);
        firstPaint = new Paint();
        firstPaint.setShader(shader1);
        firstPaint.setStrokeWidth(dp2px(BIG_STROKE_WIDTH));
        firstPaint.setStyle(Paint.Style.STROKE);
        firstPaint.setAntiAlias(true);
        firstPaint.setStrokeCap(Paint.Cap.ROUND);

        whitePaint = new Paint();
        whitePaint.setStrokeWidth(dp2px(BIG_STROKE_WIDTH));
        whitePaint.setStyle(Paint.Style.FILL);
        whitePaint.setAntiAlias(true);
        whitePaint.setColor(0xFFFFFFFF);

        int yellow2 = 0x66D6EC23;
        int green2 = 0x6651B198;
        int[] colors2 = {green2, yellow2, green2};
        float[] positions2 = {0.625f, 0.875f, 1.0f};
        Shader shader2 = new SweepGradient(mWidth / 2, mHeight / 2, colors2, positions2);
        secondPaint = new Paint();
        secondPaint.setShader(shader2);
        secondPaint.setStrokeWidth(dp2px(SMALL_STROKE_WIDTH));
        secondPaint.setStyle(Paint.Style.STROKE);
        secondPaint.setAntiAlias(true);

        int yellow3 = 0x33D6EC23;
        int green3 = 0x3351B198;
        int[] colors3 = {green3, yellow3, green3};
        float[] positions3 = {0.25f, 0.5f, 0.75f};
        Shader shader3 = new SweepGradient(mWidth / 2, mHeight / 2, colors3, positions3);
        thirdPaint = new Paint();
        thirdPaint.setShader(shader3);
        thirdPaint.setStrokeWidth(dp2px(SMALL_STROKE_WIDTH));
        thirdPaint.setStyle(Paint.Style.STROKE);
        thirdPaint.setAntiAlias(true);

        firstARect = getRect(firstARectSide);
        secondARect = getRect(secondARectSide);
        thirdARect = getRect(thirdRectSide);

      /*  int yellow4 = 0xFFD6EC23;
        int green4 = 0xFF51B198;
        int[] colors4 = {green4, yellow4, green4};
        float[] positions4 = {0.625f, 0.875f, 1.0f};
        Shader shader4 = new SweepGradient(mWidth / 2, mHeight / 2, colors4, positions4);*/
        wavePaint = new Paint();
        wavePaint.setShader(shader1);
        wavePaint.setStrokeWidth(dp2px(SMALL_STROKE_WIDTH));
        wavePaint.setStyle(Paint.Style.STROKE);
        //wavePaint.setAntiAlias(true);

    }

    private RectF getRect(int side) {
        int left = (mWidth - side) / 2;
        int top = (mHeight - side) / 2;
        int right = (mWidth + side) / 2;
        int bottom = (mHeight + side) / 2;
        return new RectF(left, top, right, bottom);
    }


    @Override
    protected void onDraw(Canvas canvas) {
/*        if(isSpeedTest){
            drawColorCircle(canvas);
            drawBigGrayCircle(canvas);
            return;
        }*/
        drawWhiteCircle(canvas);
        if (isDrawWave) {
            drawWaveCircle(canvas);
        } else {
            //drawBaseGrayCircle(canvas);
            drawColorCircle(canvas);
            drawBigGrayCircle(canvas);
        }

    }

    private void drawWhiteCircle(Canvas canvas) {
        canvas.drawCircle(mWidth / 2, mHeight / 2, mCenterRadius + dp2px(BIG_STROKE_WIDTH) / 2, whitePaint);
    }

    private void drawBigGrayCircle(Canvas canvas) {
        int gray1 = 0x0F4B89F6;
        mGrayPaint.setColor(gray1);
        mGrayPaint.setStrokeWidth(dp2px(BIG_STROKE_WIDTH));
        canvas.drawCircle(mWidth / 2, mHeight / 2, mCenterRadius + dp2px(BIG_STROKE_WIDTH), mGrayPaint);
    }


    private void drawBaseGrayCircle(Canvas canvas) {
        int gray1 = 0xFF3B414F;
        mGrayPaint.setColor(gray1);
        mGrayPaint.setStrokeWidth(dp2px(BIG_STROKE_WIDTH));
        canvas.drawArc(firstARect, 0, 360, false, mGrayPaint);
/*        int gray2 = 0x663B414F;
        mGrayPaint.setColor(gray2);
        mGrayPaint.setStrokeWidth(dp2px(MIDDLE_STROKE_WIDTH));
        canvas.drawArc(secondARect, 0, 360, false, mGrayPaint);
        int gray3 = 0x333B414F;
        mGrayPaint.setColor(gray3);
        mGrayPaint.setStrokeWidth(dp2px(SMALL_STROKE_WIDTH));
        canvas.drawArc(thirdARect, 0, 360, false, mGrayPaint);*/

    }

    private void drawColorCircle(Canvas canvas) {
        canvas.drawArc(firstARect, startAngle, sweepAngle, false, firstPaint);
        // canvas.drawArc(secondARect, startAngle, sweepAngle, false, secondPaint);
        // canvas.drawArc(thirdARect, startAngle, sweepAngle, false, thirdPaint);
    }

    private void drawWaveCircle(Canvas canvas) {

        // Log.e(TAG, "drawWaveCircle: mAlphas.size() ="+mAlphas.size() );
        for (int i = 0; i < mAlphas.size(); i++) {
            // 设置透明度
            int alpha = mAlphas.get(i);
            wavePaint.setAlpha(alpha);

            //设置宽度
            int paintWidth = alpha * dp2px(SMALL_STROKE_WIDTH) / 255;
            wavePaint.setStrokeWidth(paintWidth);

            // 绘制扩散圆
            double width = mWidths.get(i);

            canvas.drawCircle(mWidth / 2, mHeight / 2, (float) (mCenterRadius + width / 2), wavePaint);


            if (alpha > 0 && width < mTotalSpaceWidth) {
                mAlphas.set(i, alpha - 1);
                mWidths.set(i, width + stepRadius * 2);
            }
            //Log.e(TAG, "drawWaveCircle: "+mAlphas.get(i)+"---"+mWidths.get(i) );
        }

        // 判断当扩散圆扩散到指定宽度时添加新扩散圆

        if (mWidths.get(mWidths.size() - 1) > spaceWidth) {
            mAlphas.add(255);
            mWidths.add(0.0);
        }

        // 超过5个扩散圆，删除最外层
        if (mWidths.size() > 5) {
            mWidths.remove(0);
            mAlphas.remove(0);
        }

        // 绘制中心圆
        canvas.drawArc(firstARect, startAngle, sweepAngle, false, firstPaint);
        if (!isInterrupt) {
            postInvalidateDelayed(delayMilliseconds);
        }
    }


    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    public void reset() {
        if (mAnimator.isRunning()) {
            mAnimator.cancel();
            clearAnimation();
        }
        startAngle = -90;
        sweepAngle = 0;
        isDrawWave = false;
        isInterrupt = true;
        mAlphas.clear();
        mAlphas.add(255);
        mWidths.clear();
        mWidths.add(0.0);
        postInvalidate();
    }


    private int lastInterValue = -1;

    public void setValue(int end) {
        if (mAnimator.isRunning()) {
            mAnimator.cancel();
            clearAnimation();
        }
        isSpeedTest = false;
        sweepAngle = end;
        postInvalidate();
    }

    public void setSpeedTestUi() {
        if (mAnimator.isRunning()) {
            mAnimator.cancel();
            clearAnimation();
        }
        isSpeedTest = true;
        sweepAngle = 360;
        postInvalidate();
    }

    /**
     * @param end      结束角度 360
     * @param duration 持续时间 ms
     */
    public void setValue(int end, int duration) {


        if (end < 0 || end > 360 || end < sweepAngle)
            return;

        if (mAnimator.isRunning()) {
            mAnimator.cancel();
            clearAnimation();
        }
        mAnimator = ValueAnimator.ofInt(sweepAngle, end);
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());//先加速 后减速差值
        mAnimator.setDuration(duration);
        mAnimator.start();
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                if (lastInterValue != value) {
                    lastInterValue = value;
                    //Log.d(TAG, "onAnimationUpdate: value ="+value);
                    sweepAngle = value;
                    if (sweepAngle == 360) {
                        if (mListener != null)
                            mListener.onFinish();
                        isDrawWave = true;
                        isInterrupt = false;
                    } else {
                        if (mListener != null)
                            mListener.onProgress(sweepAngle);
                    }
                    postInvalidate();
                }
            }
        });
    }

    public void setOnProgressMaxListener(OnProgressMaxListener listener) {
        mListener = listener;
    }

    public interface OnProgressMaxListener {
        void onProgress(int sweepAngle);

        void onFinish();
    }
}

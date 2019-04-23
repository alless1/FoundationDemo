package com.alless.commonlib.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

/**
 * Created by alless on 2019/3/31.
 * Description: 火箭发射云雾效果
 * y = (float) (A * Math.sin(ω * x + φ + Math.PI * startPeriod) + K);
 */

public class WaveCloudView extends View {
    private static final String TAG = "WaveCloudView";
    private Paint mBgPaint;
    private Paint mSinFrontPaint;
    private int depth;//定位深度 0-getHeight
    private Point centerPoint;//中心位置
    private int radius;//半径
    private Path mSinFrontPath;
    private final int PATH_STEP = 20;//px
    private final int CIRCLE_STROKE_WIDTH = dp2px(14);
    private final int transparent1 = 0x66ffffff;
    private final int transparent2 = 0x33ffffff;
    private int A;//振幅
    private double W;//角速度 周长
    private double φ;//初相
    private double offsetX;//偏移周期
    private float waveSpeed;//移动速度

    private boolean isRunning;
    private Paint mSinBehindPaint;
    private Path mSinBehindPath;

    private ValueAnimator mAnimator;

    private int currentPercent;
    private Paint mBorderPaint;

    public WaveCloudView(Context context) {
        this(context, null);
    }

    public WaveCloudView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mAnimator = ValueAnimator.ofInt(0, 0);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);//关闭硬件加速

        mBorderPaint = new Paint();
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(CIRCLE_STROKE_WIDTH);

        mBgPaint = new Paint();
        mBgPaint.setColor(0xFF3CC43C);
        mBgPaint.setAntiAlias(true);
        mBgPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mSinBehindPaint = new Paint();
        mSinBehindPaint.setAntiAlias(true);
        mSinBehindPaint.setColor(0xFF98DF98);
        mSinBehindPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mSinBehindPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));//混合模式
        mSinBehindPath = new Path();

        mSinFrontPaint = new Paint();
        mSinFrontPaint.setAntiAlias(true);
        mSinFrontPaint.setColor(0xFFFFFFFF);
        mSinFrontPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mSinFrontPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));//混合模式
        mSinFrontPath = new Path();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initSize();
    }

    private void initSize() {
        radius = Math.min(getWidth() / 2, getHeight() / 2) - 2 * CIRCLE_STROKE_WIDTH;
        centerPoint = new Point(getWidth() / 2, getHeight() / 2);
        currentPercent = 10;
        depth = radius * 2 - radius * 2 * currentPercent / 100;
        waveSpeed = 40f;
        A = dp2px(10);
        W = 5 * Math.PI / getWidth();//周长
        φ = 0;
        offsetX = 0;//Math.PI*1
        isRunning = false;
    }

    private int count;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int layer = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        canvas.drawCircle(centerPoint.x, centerPoint.y, radius, mBgPaint);

        drawWave(true, canvas);
        drawWave(false, canvas);

        drawWave(canvas, mSinFrontPaint, mSinFrontPath, depth);

        canvas.restoreToCount(layer);

        drawCircle(canvas);

        if (isRunning && depth > 0) {
            φ -= waveSpeed / 100;
            postInvalidateDelayed(40);
        }

    }

    private void drawCircle(Canvas canvas) {
        mBorderPaint.setColor(transparent1);
        canvas.drawCircle(centerPoint.x, centerPoint.y, radius + CIRCLE_STROKE_WIDTH / 2, mBorderPaint);
        mBorderPaint.setColor(transparent2);
        canvas.drawCircle(centerPoint.x, centerPoint.y, radius + CIRCLE_STROKE_WIDTH * 3 / 2, mBorderPaint);
    }

    private void drawWave(boolean isBehind, Canvas canvas) {
        Paint paint;
        Path path;
        int dep;
        if (isBehind) {
            paint = mSinBehindPaint;
            path = mSinBehindPath;
            dep = depth - dp2px(20);
        } else {
            paint = mSinFrontPaint;
            path = mSinFrontPath;
            dep = depth;
        }

        canvas.save();
        canvas.translate(getWidth() / 2, dep);

        path.reset();
        path.moveTo(0, 0);

        for (float x = 0; x <= radius + PATH_STEP; x += PATH_STEP) {

            if (isBehind) {
                path.lineTo(x, getBehindY(x));
            } else {
                path.lineTo(x, getFrontX(x));
            }

        }

        path.lineTo(radius, getHeight() - dep);
        path.lineTo(0, getHeight() - dep);

        path.close();

        canvas.drawPath(path, paint);

        canvas.scale(-1, 1);
        canvas.drawPath(path, paint);
        canvas.restore();
    }

    private float getBehindY(float x) {
        return (float) (A * Math.sin(W * 1.2 * x + φ + offsetX));
    }

    private float getFrontX(float x) {
        return (float) (A * Math.sin(W * x + φ + offsetX));
    }

    private void drawWave(Canvas canvas, Paint paint, Path path, int depth) {

        canvas.save();
        canvas.translate(getWidth() / 2, depth);

        float y;
        path.reset();
        path.moveTo(0, 0);

        for (float x = 0; x <= radius + PATH_STEP; x += PATH_STEP) {

            y = (float) (A * Math.sin(W * x + φ + offsetX));

            path.lineTo(x, y);
        }

        path.lineTo(radius, radius);
        path.lineTo(0, radius);

        path.close();

        canvas.drawPath(path, paint);

        canvas.scale(-1, 1);
        canvas.drawPath(path, paint);
        canvas.restore();

    }

    protected int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }

    public void start() {
        if (!isRunning) {
            isRunning = true;
            postInvalidate();
        }
    }

    public void reset() {
        setPercent(10);
        φ = 0;
        isRunning = false;
        postInvalidate();
    }

    public void stop() {
        isRunning = false;
        φ = 0;
    }

    /**
     * @param percent 0-100
     */
    public void setPercent(int percent) {
        if (percent < 0 || percent > 100)
            return;
        if (radius == 0)
            return;
        depth = radius * 2 - radius * 2 * percent / 100;
        waveSpeed = percent/2 + 40;

    }

    float lastInterValue;

    public void setValue(int percent, int duration) {

        if (percent < 0 || percent > 100)
            return;
        if (percent < currentPercent)
            return;

        if (mAnimator.isRunning()) {
            mAnimator.cancel();
            clearAnimation();
        }
        mAnimator = ValueAnimator.ofInt(currentPercent, percent);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.setDuration(duration);
        mAnimator.start();
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                if (lastInterValue != value) {
                    lastInterValue = value;
                    setPercent(value);
                    postInvalidate();
                }
            }
        });
    }
}

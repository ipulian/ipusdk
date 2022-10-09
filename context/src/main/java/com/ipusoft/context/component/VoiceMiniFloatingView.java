package com.ipusoft.context.component;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.ipusoft.context.constant.Direction;
import com.ipusoft.context.manager.IWindowManager;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author : Ziwen Lan
 * Date : 2020/5/7
 * Time : 17:07
 * Introduction : 仿微信语音通话悬浮弹窗
 * 内部管理实现悬浮窗功能
 * 实现拖动时判断左右方向自动粘边效果
 * 实现粘边处圆角转直角、非粘边时直角转圆角效果
 */
public class VoiceMiniFloatingView extends View {

    private static final String TAG = "VoiceFloatingView";
    /**
     * 默认宽高与当前View实际宽高
     */
    private int mDefaultWidth, mDefaultHeight;
    private int mWidth, mHeight;
    /**
     * 当前View绘制相关
     */
    private Paint mPaint;
    private Bitmap mBitmap;
    private PorterDuffXfermode mPorterDuffXfermode;
    private Direction mDirection = Direction.right;
    private int mOrientation;
    private int mWidthPixels, mHeightPixels;
    /**
     * 悬浮窗管理相关
     */
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayoutParams;
    private boolean mIsShow;
    ///////////////////////////////////////////////////

    private static final String DEFAULT_TEXT = " 请录音 ";
    private static final int LINE_WIDTH = 9;//默认矩形波纹的宽度，9像素, 原则上从layout的attr获得
    private Paint paint = new Paint();
    private Runnable task;
    private ExecutorService executorService;
    private RectF rectRight = new RectF();//右边波纹矩形的数据，10个矩形复用一个rectF
    private RectF rectLeft = new RectF();//左边波纹矩形的数据
    private String text = DEFAULT_TEXT;
    private int updateSpeed;
    private int lineColor;
    private int textColor;
    private float lineWidth;
    private float textSize;
    //////////////////////////////////////////////////


    public VoiceMiniFloatingView(Context context) {
        super(context);


        initView(context);
        resetView(mWaveList, DEFAULT_WAVE_HEIGHT);

        init();
    }

    private void initView(Context context) {
        //获取布局属性里的值
        //TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.LineWaveVoiceView);
        lineColor = Color.parseColor("#0000FF");
        lineWidth = 8;
        textSize = 42;
        textColor = Color.parseColor("#000000");
        updateSpeed = 500;
    }


    private static final int MIN_WAVE_HEIGHT = 1;//矩形线最小高
    private static final int MAX_WAVE_HEIGHT = 3;//矩形线最大高
    private static final int[] DEFAULT_WAVE_HEIGHT = {2, 3, 1, 2, 1, 2};
    private static final int UPDATE_INTERVAL_TIME = 100;//100ms更新一次
    private LinkedList<Integer> mWaveList = new LinkedList<>();
    private float maxDb;

    private void resetView(List<Integer> list, int[] array) {
        list.clear();
        for (int anArray : array) {
            list.add(anArray);
        }
    }

    private synchronized void refreshElement() {
        Random random = new Random();
        maxDb = random.nextInt(3) + 1;
        int waveH = MIN_WAVE_HEIGHT + Math.round(maxDb * (MAX_WAVE_HEIGHT - MIN_WAVE_HEIGHT));
        mWaveList.add(0, waveH);
        mWaveList.removeLast();
    }

    public boolean isStart = false;

    private class LineJitterTask implements Runnable {
        @Override
        public void run() {
            while (isStart) {
                refreshElement();
                try {
                    Thread.sleep(updateSpeed);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                postInvalidate();
            }
        }
    }

    public synchronized void startRecord() {
        isStart = true;
        executorService.execute(task);
    }

    public synchronized void stopRecord() {
        isStart = false;
        mWaveList.clear();
        resetView(mWaveList, DEFAULT_WAVE_HEIGHT);
        postInvalidate();
        if (executorService != null) {
            executorService.shutdownNow();
            executorService = null;
        }

    }

    private void init() {

        mWindowManager = IWindowManager.getWindowManager();
        mLayoutParams = getWrapLayoutParams();

        //当前View绘制相关
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP);
        //mBitmap = ((BitmapDrawable) getResources().getDrawable(R.mipmap.b8e)).getBitmap();
        mDefaultHeight = 180;
        mDefaultWidth = 215;

        //记录当前屏幕方向和屏幕宽度
        recordScreenWidth();
    }


    public static WindowManager.LayoutParams getWrapLayoutParams() {
        WindowManager.LayoutParams mLayoutParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
                    | WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
        }
        mLayoutParams.format = PixelFormat.RGBA_8888;
        mLayoutParams.gravity = Gravity.START | Gravity.TOP;
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        mLayoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        mLayoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        return mLayoutParams;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidth = measureSize(mDefaultWidth, heightMeasureSpec);
        mHeight = measureSize(mDefaultHeight, widthMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        //间隔和圆角
        int d = 20;
        int r = 180;

        // border
//        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setColor(Color.parseColor("#FF0000"));
//        mPaint.setStrokeWidth(2);
        //canvas.drawCircle(100f, 100f, r, mPaint);


        //获取实际宽高的一半
        int widthCentre = 3 * getWidth() / 4;

        //根据最后停留方向（left or right）绘制多一层直角矩形，覆盖圆角
        switch (mDirection) {
            default:
            case right:
                //画透明色圆角背景
                mPaint.setColor(Color.parseColor("#DDECECEC"));
                canvas.drawRoundRect(0, 0, mWidth, mHeight, r, r, mPaint);

                mPaint.setXfermode(mPorterDuffXfermode);
                //mPaint.setColor(Color.parseColor("#FF0000"));
                // mPaint.setShadowLayer(5, 10, 10, Color.parseColor("#00FF00"));
                //mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                canvas.drawRoundRect(2 * mWidth / 3 - 20, 0, mWidth, mHeight, 0, 0, mPaint);

                // canvas.drawLine(mWidthPixels - (mWidth / 3 + 20), 0, mWidthPixels, 0, mPaint);
                // mPaint.set
                break;
            case left:
                //画透明色圆角背景
                mPaint.setColor(Color.parseColor("#EEECECEC"));
                canvas.drawRoundRect(0, 0, mWidth, mHeight, r, r, mPaint);

                mPaint.setXfermode(mPorterDuffXfermode);
                canvas.drawRoundRect(0, 0, 2 * mWidth / 3 - 20, mHeight, 0, 0, mPaint);
                break;
            case move:
                widthCentre = 5 * getWidth() / 8;
                //画透明色圆角背景
                mPaint.setColor(Color.parseColor("#88999999"));
                canvas.drawRoundRect(0, 0, mHeight, mHeight, r, r, mPaint);
                break;
        }
        mPaint.setXfermode(null);
        //画实色圆角矩形
        mPaint.setColor(Color.WHITE);
        //canvas.drawRoundRect(d, d, mWidth - d, mHeight - d, r, r, mPaint);
        //居中填充icon
        // canvas.drawBitmap(mBitmap, (mWidth - mBitmap.getWidth()) / 2, (mHeight - mBitmap.getHeight()) / 2, mPaint);

        int heightCentre = getHeight() / 2;
        paint.setStrokeWidth(2);
//        paint.setColor(textColor);
//        paint.setTextSize(textSize);
        //float textWidth = paint.measureText(text);
        //canvas.drawText(text, widthCentre - textWidth / 2, heightCentre - (paint.ascent() + paint.descent()) / 2, paint);

        //设置颜色
        paint.setColor(lineColor);
        //填充内部
        paint.setStyle(Paint.Style.FILL);
        //设置抗锯齿
        paint.setAntiAlias(true);
        for (int i = 0; i < 5; i++) {
//            rectRight.left = widthCentre + textWidth / 2 + (1 + 2 * i) * lineWidth;
//            rectRight.top = heightCentre - lineWidth * mWaveList.get(i) / 2;
//            rectRight.right = widthCentre + textWidth / 2 + (2 + 2 * i) * lineWidth;
//            rectRight.bottom = heightCentre + lineWidth * mWaveList.get(i) / 2;

            //左边矩形
            rectLeft.left = widthCentre - (2 + 2 * i) * lineWidth;
            rectLeft.top = heightCentre - mWaveList.get(i) * lineWidth / 2;
            rectLeft.right = widthCentre - (1 + 2 * i) * lineWidth;
            rectLeft.bottom = heightCentre + mWaveList.get(i) * lineWidth / 2;

            // canvas.drawRoundRect(rectRight, 6, 6, paint);
            canvas.drawRoundRect(rectLeft, 6, 6, paint);
        }


    }

    private int x;
    private int y;

    /**
     * 处理触摸事件，实现拖动、形状变更和粘边效果
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mWindowManager != null) {
            if (getResources().getConfiguration().orientation != mOrientation) {
                //屏幕方向翻转了，重新获取并记录屏幕宽度
                recordScreenWidth();
            }
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x = (int) event.getRawX();
                    y = (int) event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    int nowX = (int) event.getRawX();
                    int nowY = (int) event.getRawY();
                    int movedX = nowX - x;
                    int movedY = nowY - y;
                    x = nowX;
                    y = nowY;
                    mLayoutParams.x = mLayoutParams.x + movedX;
                    mLayoutParams.y = mLayoutParams.y + movedY;
                    if (mLayoutParams.x < 0) {
                        mLayoutParams.x = 0;
                    }
                    if (mLayoutParams.y < 0) {
                        mLayoutParams.y = 0;
                    }
                    if (mDirection != Direction.move) {
                        mDirection = Direction.move;
                        invalidate();
                    }
                    mWindowManager.updateViewLayout(this, mLayoutParams);
                    break;
                case MotionEvent.ACTION_UP:
                    handleDirection((int) event.getRawX(), (int) event.getRawY());
                    invalidate();
                    mWindowManager.updateViewLayout(this, mLayoutParams);
                    break;
                default:
                    break;
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 计算宽高
     */
    private int measureSize(int defaultSize, int measureSpec) {
        int result = defaultSize;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        //UNSPECIFIED	父容器没有对当前View有任何限制，当前View可以任意取尺寸
        //EXACTLY	当前的尺寸就是当前View应该取的尺寸
        //AT_MOST	当前尺寸是当前View能取的最大尺寸
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            result = Math.min(result, specSize);
        }
        return result;
    }

    /**
     * 记录当前屏幕方向和屏幕宽度
     */
    private void recordScreenWidth() {
        mOrientation = getResources().getConfiguration().orientation;
        Log.d(TAG, "recordScreenWidth: --------<" + mOrientation);
        DisplayMetrics outMetrics = new DisplayMetrics();
        mWindowManager.getDefaultDisplay().getMetrics(outMetrics);
        mWidthPixels = outMetrics.widthPixels;
        mHeightPixels = outMetrics.heightPixels;
        Log.d(TAG, "recordScreenWidth: ---------->" + mWidthPixels);
    }

    /**
     * 判定所处方向
     */
    private void handleDirection(int x, int y) {
        if (x > (mWidthPixels / 2)) {
            mDirection = Direction.right;
            mLayoutParams.x = mWidthPixels - getMeasuredWidth();
            Log.d(TAG, "handleDirection: ---------->" + mLayoutParams.x);
        } else {
            mDirection = Direction.left;
            mLayoutParams.x = 0;
        }
    }

    /**
     * show
     */
    public void show() {
        if (!mIsShow) {
            if (mLayoutParams.x == 0 && mLayoutParams.y == 0 && mDirection == Direction.right) {
                mLayoutParams.x = mWidthPixels - mDefaultWidth;
                mLayoutParams.y = mHeightPixels / 2;
            }
            if (mDirection == Direction.move) {
                handleDirection(mLayoutParams.x, mLayoutParams.y);
            }
            mWindowManager.addView(this, mLayoutParams);
            mIsShow = true;
        }

        executorService = Executors.newCachedThreadPool();
        task = new LineJitterTask();
        startRecord();
    }

    /**
     * 调整悬浮窗位置
     * 根据提供坐标自动判断粘边
     */
    public void updateViewLayout(int x, int y) {
        if (mIsShow) {
            handleDirection(x, y);
            invalidate();
            mLayoutParams.y = y;
            mWindowManager.updateViewLayout(this, mLayoutParams);
        }
    }

    /**
     * dismiss
     */
    public void dismiss() {
        if (mIsShow) {
            mWindowManager.removeView(this);
            mIsShow = false;
        }
        stopRecord();
    }
}

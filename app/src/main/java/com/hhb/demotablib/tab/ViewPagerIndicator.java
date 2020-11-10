package com.hhb.demotablib.tab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.viewpager.widget.ViewPager;

import com.hhb.demotablib.MainActivity;
import com.hhb.demotablib.R;

import java.util.List;

public class ViewPagerIndicator extends HorizontalScrollView {
    public static final int DEFAULT_OFFSET = 200;
    public static final int DEFAULT_TAB_COUNT = 4;
    public int mTabWidth;
    private LinearLayout mTabRoot;
    private ViewPager mViewPager;
    private int mDefaultIndex;
    private int mStartIndex;
    private int mOldX;
    private int mTrangleInitX;
    private int mTrangleWidth;
    private float mTrangleRatial = 1F / 6;
    private int mTrangleHeight = 0;
    private Paint mLinePaint;
    private Paint mTrangglePaint;
    private Path mTrangglePath;
    private int mTranslationX;
    private TYPE mShap = TYPE.TRANGGLE;//默认形状
    private int mOffset = DEFAULT_OFFSET;//滚动偏移量
    private int mTabCount = DEFAULT_TAB_COUNT;//tab数量默认值


    private int mPaintColor = Color.BLUE;//默认颜色


    public void setmShap(TYPE mShap) {
        this.mShap = mShap;
    }


    public void setmOffset(int mOffset) {
        this.mOffset = mOffset;
    }


    public void setmTabWidth(int mTabWidth) {
        this.mTabWidth = mTabWidth;
    }

    public void setmTabCount(int mTabCount) {
        this.mTabCount = mTabCount;
    }

    public void setmPaintColor(int mPaintColor) {
        this.mPaintColor = mPaintColor;
    }

    public void setmStartIndex(int mStartIndex) {
        this.mStartIndex = mStartIndex;
    }


    public enum TYPE {
        TRANGGLE, LINE
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicator_StyleConfig);
        mPaintColor = typedArray.getInt(R.styleable.ViewPagerIndicator_StyleConfig_indicator_color, Color.BLUE);
        mTabCount = typedArray.getInt(R.styleable.ViewPagerIndicator_StyleConfig_tab_count, 4);
        String shap = typedArray.getString(R.styleable.ViewPagerIndicator_StyleConfig_indicator_shap);
        mShap = TextUtils.equals(shap, "1") ? TYPE.TRANGGLE : TYPE.LINE;
        typedArray.recycle();
        initLinePaint();
        initTrangglePaint();
        initTabRoot();


    }


    private void initTragglePath() {
        mTrangglePath = new Path();
        mTrangleHeight = (int) (mTrangleWidth / 2 / Math.sqrt(2));
        mTrangglePath.moveTo(0, 0);
        mTrangglePath.lineTo(mTrangleWidth, 0);
        mTrangglePath.lineTo(mTrangleWidth / 2, -mTrangleHeight);
        mTrangglePath.close();

    }


    private void initLinePaint() {
        mLinePaint = new Paint();
        mLinePaint.setStrokeWidth(10);
        mLinePaint.setColor(mPaintColor);

    }

    private void initTrangglePaint() {
        mTrangglePaint = new Paint();
        mTrangglePaint.setStrokeWidth(10);
        mTrangglePaint.setStyle(Paint.Style.FILL);
        mTrangglePaint.setColor(mPaintColor);
    }

    private void initTabRoot() {
        mTabRoot = new LinearLayout(getContext());
        mTabRoot.setOrientation(LinearLayout.HORIZONTAL);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.addView(mTabRoot, lp);
    }

    private void initTranggleConfig() {
        mTabWidth = getContext().getResources().getDisplayMetrics().widthPixels / mTabCount;
        mTrangleWidth = (int) (mTabWidth * mTrangleRatial);
        mTrangleHeight = mTrangleWidth / 2;
        mTrangleInitX = mTabWidth / 2 - mTrangleWidth / 2;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initTranggleConfig();
        initTragglePath();

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = mTabRoot.getChildCount();
        for (int index = 0; index < childCount; index++) {
            View childAt = getChildAt(index);
            childAt.getLayoutParams().width = mTabWidth;

        }
    }


    public void addTabs(List<String> mTables) {
        for (int index = 0; index < mTables.size(); index++) {
            TextView mTab = new TextView(getContext());
            mTab.setText(mTables.get(index));
            mTab.setGravity(Gravity.CENTER);
            mTabWidth = getContext().getResources().getDisplayMetrics().widthPixels / 4;
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(mTabWidth, ViewGroup.LayoutParams.MATCH_PARENT);
            mTabRoot.addView(mTab, lp);
            final int finalIndex = index;
            if (mDefaultIndex == index) {
                mTab.setTextColor(Color.RED);
            } else {
                mTab.setTextColor(Color.BLACK);
            }
            mTab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View mView = mTabRoot.getChildAt(finalIndex);
                    scrollPosition(mView, finalIndex, 0);
                    mViewPager.setCurrentItem(finalIndex);
                    Log.e(MainActivity.class.getSimpleName(), "run: " + mView.getLeft() + "");

                }
            });
        }


    }


    public void setViewPager(ViewPager viewPager) {
        this.mViewPager = viewPager;
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                scrollPosition(mTabRoot.getChildAt(position), position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                highText(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        //随便绘制一下
        canvas.save();
        if (mShap == TYPE.LINE) {
            canvas.translate(0, getHeight());
            canvas.drawLine(mStartIndex, 0, mStartIndex + mTabWidth, 0, mLinePaint);
        } else if (mShap == TYPE.TRANGGLE) {
            canvas.translate(mTrangleInitX + mTranslationX, getHeight() + 1);
            canvas.drawPath(mTrangglePath, mTrangglePaint);
        }

        canvas.restore();
        super.dispatchDraw(canvas);


    }

    private void highText(int position) {
        for (int index = 0; index < mTabRoot.getChildCount(); index++) {
            if (index == position) {
                ((TextView) mTabRoot.getChildAt(index)).setTextColor(Color.RED);
            } else {
                ((TextView) mTabRoot.getChildAt(index)).setTextColor(Color.BLACK);
            }
        }
    }

    private void scrollPosition(View mView, int position, float offset) {
        int left = mView.getLeft();
        int mStartX = (int) (left + offset * mTabWidth);
        mTranslationX = mStartX;
        setmStartIndex(mStartX);
        invalidate();
        if (position > 0 || offset > 0) {
            mStartX = mStartX - 150;
        }
        if (mStartX != mOldX) {
            mOldX = mStartX;
            smoothScrollTo(mStartX, 0);
        }


    }


}

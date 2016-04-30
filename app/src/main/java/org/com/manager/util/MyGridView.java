package org.com.manager.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by jie.hua on 2016/3/27.
 * 自定义
 */
public class MyGridView extends GridView {
    public MyGridView(Context context) {
        super(context);
    }

    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    //设置子控件需要多大的地方
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        /*MeasureSpec.EXACTLY是精确尺寸，
        MeasureSpec.AT_MOST是最大尺寸，
        MeasureSpec.UNSPECIFIED是未指定尺寸*/
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 5,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }


}

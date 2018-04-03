package com.example.myphotoviewer;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.tmall.ultraviewpager.UltraViewPager;
import com.tmall.ultraviewpager.transformer.UltraDepthScaleTransformer;
import com.tmall.ultraviewpager.transformer.UltraScaleTransformer;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/17/017.
 */

public class ImageBrowserDialog extends Dialog {

    private Context mContext;
    private List<String> mImgUrls;
    private View mView;
    private UltraViewPager mUltraViewPager;

    public ImageBrowserDialog(@NonNull Context context, List<String> imgUrls) {
        //设置Context和Style
        super(context, R.style.transparentBgDialog);

        mContext = context;
        mImgUrls = imgUrls;

        initView();
    }

    private void initView() {
        mView = View.inflate(mContext, R.layout.dialog_images_brower, null);
        mUltraViewPager = mView.findViewById(R.id.ultra_viewpager);
        //设置滑动方向
        mUltraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);

        //UltraPagerAdapter 绑定子view到UltraViewPager
        UltraPagerAdapter adapter = new UltraPagerAdapter(mContext, mImgUrls);
        mUltraViewPager.setAdapter(adapter);
        //设置一开始为第012，第2个页面
        mUltraViewPager.setCurrentItem(2);
        //内置indicator初始化
        mUltraViewPager.initIndicator();
        //设置indicator样式
        mUltraViewPager.getIndicator()
                .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
                .setFocusColor(Color.BLACK)
                .setNormalColor(Color.GRAY)
                .setRadius((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, mContext.getResources().getDisplayMetrics()));
        //设置indicator对齐方式
        mUltraViewPager.getIndicator().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        mUltraViewPager.getIndicator().setMargin(0, 0, 0, DensityUtil.dip2px(mContext, 50));
        //构造indicator,绑定到UltraViewPager
        mUltraViewPager.getIndicator().build();

        mUltraViewPager.setInfiniteLoop(false);

        //使用回调控制点击photoview后退出对话框，因为对话框是全屏的，没有外部，所以只能在内部使用了
        adapter.setOnPhotoViewImageClickListener(new UltraPagerAdapter.OnPhotoViewImageClickListener() {
            @Override
            public void onPhotoViewImageClick(View view, int position) {
                cancel();
                ToastUtil.showShort(mContext, "position == " + position);
            }
        });

//        setMultiScreen
//        开启一屏多显模式,child view的宽度不占满全屏。
//        mUltraViewPager.setMultiScreen(0.6f);
//        mUltraViewPager.setItemRatio(1.0f);
//        mUltraViewPager.setAutoMeasureHeight(true);

        //设置切换模式，两个pager之间有一点间隔
//        mUltraViewPager.setPageTransformer(false, new UltraScaleTransformer());
        //景深模式的翻页
        mUltraViewPager.setPageTransformer(false, new UltraDepthScaleTransformer());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mView);//设置窗口的view才能显示，但不能全屏显示，需要配置其他信息，如下：
        setCancelable(true);//设置该对话框是否可以取消
        setCanceledOnTouchOutside(true);//设置点击外部取消，由于是全屏的对话框所以无效，必须监听点击图片回退

        //设置全屏，结合style使用
        Window window = getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = 0;
        wl.height = Config.EXACT_SCREEN_HEIGHT;
        wl.width = Config.EXACT_SCREEN_WIDTH;
        wl.gravity = Gravity.CENTER;
        window.setAttributes(wl);
    }
}

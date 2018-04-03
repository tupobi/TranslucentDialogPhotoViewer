package com.example.myphotoviewer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button mBtnOpenPhotoViewer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnOpenPhotoViewer = findViewById(R.id.btn_open_photoviewer);
        mBtnOpenPhotoViewer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> imgUrls = new ArrayList<>();
                imgUrls.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=862591842,2864954084&fm=27&gp=0.jpg");
                imgUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1521269841870&di=fa819053ac7c099a6e42201a6002b9f2&imgtype=0&src=http%3A%2F%2Fimgk.zol.com.cn%2Fdcbbs%2F10430%2Fa10429839.jpg");
                imgUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1521269841869&di=2ccc25117e517083e8d1d203cb8f7fd7&imgtype=0&src=http%3A%2F%2Fwww.zhlzw.com%2FUploadFiles%2FArticle_UploadFiles%2F201303%2F2013030914472860.jpg");
                imgUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1521269841869&di=1f4666c2ab76d0ad6d758f4eb0abb337&imgtype=0&src=http%3A%2F%2Fwww.zhlzw.com%2FUploadFiles%2FArticle_UploadFiles%2F201204%2F20120422013508986.JPG");
                imgUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1521269841868&di=723687d1d4a22e3407b8a0ec2049386a&imgtype=0&src=http%3A%2F%2Fscimg.jb51.net%2Fallimg%2F150630%2F14-150630144U2M0.jpg");
                new ImageBrowserDialog(MainActivity.this, imgUrls).show();
            }
        });

        getDeviceDensity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImageLoader.getInstance().clearMemoryCache();
        ImageLoader.getInstance().clearDiskCache();
    }

    /**
     * 获取当前设备的屏幕密度等基本参数
     */
    protected void getDeviceDensity() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Config.EXACT_SCREEN_HEIGHT = metrics.heightPixels;
        Config.EXACT_SCREEN_WIDTH = metrics.widthPixels;
    }
}

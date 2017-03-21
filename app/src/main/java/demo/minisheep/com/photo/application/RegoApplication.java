package demo.minisheep.com.photo.application;

import android.app.Application;
import android.graphics.Bitmap;

import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by minisheep on 17/3/13.
 */

public class RegoApplication extends Application {
    private Bitmap bitmaps;
    private int language_code;

    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);  //正式版的时候设置false,关闭调试
        JPushInterface.init(this);
        Set<String> set = new HashSet<>();
        set.add("minisheep");
        JPushInterface.setTags(this,set,null);
    }

    public void setBitmap(Bitmap bitmaps){
        this.bitmaps = bitmaps;
    }

    public Bitmap getBitmaps(){
        return bitmaps;
    }

    public int getLanguage_code() {
        return language_code;
    }

    public void setLanguage_code(int language_code) {
        this.language_code = language_code;
    }
}

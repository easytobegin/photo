package demo.minisheep.com.photo.factory;

import android.support.v4.app.Fragment;

import demo.minisheep.com.photo.R;
import demo.minisheep.com.photo.fragment.AboutFragment;
import demo.minisheep.com.photo.fragment.FromGalleryFragment;
import demo.minisheep.com.photo.fragment.SettingFragment;
import demo.minisheep.com.photo.fragment.TakePhotoFragment;

/**
 * Created by minisheep on 17/3/8.
 */

public class FragmentFactory {
    public static Fragment getInstanceByIndex(int index){
        Fragment fragment = null;
        switch (index){
            case R.id.take_photo_selection:
                fragment = new TakePhotoFragment();  //拍照识别
                break;
            case R.id.take_from_photo:
                fragment = new FromGalleryFragment(); //图库导入
                break;
            case R.id.take_photo_setting:
                fragment = new SettingFragment();  //设置
                break;
            case R.id.about:
                fragment = new AboutFragment(); //关于
                break;
        }
        return fragment;
    }
}

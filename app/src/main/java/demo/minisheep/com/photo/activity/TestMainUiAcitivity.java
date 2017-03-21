package demo.minisheep.com.photo.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.PopupMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import demo.minisheep.com.photo.R;
import demo.minisheep.com.photo.adapter.MyFragmentPagerAdapter;
import demo.minisheep.com.photo.fragment.AboutFragment;
import demo.minisheep.com.photo.fragment.FromGalleryFragment;
import demo.minisheep.com.photo.fragment.SettingFragment;
import demo.minisheep.com.photo.fragment.TakePhotoFragment;

import static demo.minisheep.com.photo.R.layout.popup;

/**
 * Created by minisheep on 17/3/8.
 */

public class TestMainUiAcitivity extends FragmentActivity {
    private FragmentManager mFragmentManager;

    private RadioGroup radioGroup;
    private ViewPager mPager;
    private RadioButton rbTakePhoto;
    private RadioButton rbGrallery;
    private RadioButton rbSetting;
    private RadioButton rbAbout;
    private ImageView iv_addImage;
    private TextView tv_titleText;

    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainui);
        Toast.makeText(this,"欢迎",Toast.LENGTH_LONG).show();
        initView();

        initViewPager();
    }

    private void initViewPager() {
        TakePhotoFragment takePhotoFragment = new TakePhotoFragment();
        FromGalleryFragment fromGalleryFragment = new FromGalleryFragment();
        SettingFragment settingFragment = new SettingFragment();
        AboutFragment aboutFragment = new AboutFragment();

        fragments = new ArrayList<Fragment>();
        fragments.add(takePhotoFragment);
        fragments.add(fromGalleryFragment);
        fragments.add(settingFragment);
        fragments.add(aboutFragment);

        //ViewPager设置适配器
        mPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), (ArrayList<Fragment>) fragments));

        //ViewPager显示第一个Fragment
        radioGroup.check(R.id.take_photo_selection);
        bitmapFactory(R.drawable.add2);
        mPager.setCurrentItem(0,false);

        iv_addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"第一个",Toast.LENGTH_SHORT).show();
                popupMenu(iv_addImage);
            }
        });

        //ViewPager页面切换监听
        mPager.setOnPageChangeListener(new myOnPageChangeListener());

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {   //页面选择
                if(position == 0){
                    bitmapFactory(R.drawable.add2);  //fragment1和2不同风格的按钮
                    showAddImage();

                    iv_addImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getApplicationContext(),"第一个",Toast.LENGTH_SHORT).show();
                            popupMenu(iv_addImage);
                        }
                    });
                }else if(position == 1){
                    bitmapFactory(R.drawable.add);
                    showAddImage();
                    iv_addImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getApplicationContext(),"第二个",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    hideAddImage();
                }
            }

            private void hideAddImage() {
                iv_addImage.setVisibility(View.GONE);
            }

            private void showAddImage() {
                iv_addImage.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void popupMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(this,v);
        MenuInflater inflater = popupMenu.getMenuInflater();

        //添加单击事件
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.take:
                        Toast.makeText(getApplicationContext(),"拍照识别",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.rego:
                        Toast.makeText(getApplicationContext(), "识别", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return false;
            }
        });
        inflater.inflate(R.menu.popupmenu,popupMenu.getMenu());
        popupMenu.show();
    }

    private void bitmapFactory(int drawable) {
        Bitmap bitmap= BitmapFactory.decodeResource(TestMainUiAcitivity.this.getResources(), drawable);
        iv_addImage.setImageBitmap(bitmap);
    }

    private void setTitleText(int text){
        tv_titleText.setText(text);
    }

    private void initView() {
        //获取FragmentManager
        radioGroup = (RadioGroup) findViewById(R.id.take_photo_radiogroup);
        mPager = (ViewPager) findViewById(R.id.packpage_vPager);
        rbTakePhoto = (RadioButton) findViewById(R.id.take_photo_selection);
        rbGrallery = (RadioButton) findViewById(R.id.take_from_photo);
        rbSetting = (RadioButton) findViewById(R.id.take_photo_setting);
        rbAbout = (RadioButton) findViewById(R.id.about);
        iv_addImage = (ImageView) findViewById(R.id.iv_addImg);
        tv_titleText = (TextView) findViewById(R.id.tv_titleBar);
        radioGroup.setOnCheckedChangeListener(new MyCheckChangeListener());
    }


    private class MyCheckChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            switch (i){
                case R.id.take_photo_selection:
                    mPager.setCurrentItem(0,false);  //拍照识别
                    setTitleText(R.string.take_photo);
                    //Toast.makeText(getApplicationContext(),"拍照识别",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.take_from_photo:
                    mPager.setCurrentItem(1,false); //图库导入
                    setTitleText(R.string.from_album);
                    //Toast.makeText(getApplicationContext(),"图库导入",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.take_photo_setting:
                    mPager.setCurrentItem(2,false);  //设置
                    setTitleText(R.string.setting);
                    //Toast.makeText(getApplicationContext(),"设置",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.about:
                    mPager.setCurrentItem(3,false);  //关于
                    setTitleText(R.string.about_software);
                    //Toast.makeText(getApplicationContext(),"关于",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    private class myOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            switch (position){
                case 0:
                    radioGroup.check(R.id.take_photo_selection);
                    break;
                case 1:
                    radioGroup.check(R.id.take_from_photo);
                    break;
                case 2:
                    radioGroup.check(R.id.take_photo_setting);
                    break;
                case 3:
                    radioGroup.check(R.id.about);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

    }
}

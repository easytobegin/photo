package demo.minisheep.com.photo.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;

import demo.minisheep.com.photo.R;
import demo.minisheep.com.photo.application.RegoApplication;
import demo.minisheep.com.photo.view.CircleProgressView;

import static android.R.attr.data;
import static android.R.attr.visible;

/**
 * Created by minisheep on 17/3/13.
 */

public class ShowViewActivity extends Activity {
    private Bitmap bitmap;
    private ImageView iv_show;
    private ImageView iv_back;
    private ImageView iv_text;
    private TextView tv_hint;
    private String data;
    private CircleProgressView mCircleBar;
    //sd卡路径
    private static final String SD_PATH= Environment.getExternalStorageDirectory().getAbsolutePath();
    //private final String SD_PATH = getActivity().getFilesDir().getPath();
    private static final String CHINESE_LANGUAGE = "chi_sim";
    //字典名
    private static final String DICTIONARY="eng";

    private String Language = "chi_sim";  //初始中文简体

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_image);
        initView();
        initLanguage();
        setEvent();
    }

    private void initLanguage() {
        int code = ((RegoApplication)getApplication()).getLanguage_code();
        if(code == 0){  //中文
            Language = "chi_sim";
        }else if(code == 1){  //英文
            Language = "eng";
        }
    }

    private void setEvent() {
        if(bitmap != null)
            iv_show.setImageBitmap(bitmap);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        iv_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_hint.setVisibility(View.VISIBLE);
                iv_text.setClickable(false);
                mCircleBar.setVisibility(View.VISIBLE);
                mCircleBar.setProgress(0);
                //子线程
                new Thread(new Runnable() {
                    int progress = 0;

                    @Override
                    public void run() {
                        //int i = 0;
                        String outputText = "";
                        TessBaseAPI baseAPI = new TessBaseAPI();
                        baseAPI.init(SD_PATH + "/test",Language);
                        mCircleBar.setProgressNotInUiThread(20);
                        baseAPI.setPageSegMode(TessBaseAPI.PSM_AUTO);

                        mCircleBar.setProgressNotInUiThread(40);
                        Message msg = new Message();

                        if(bitmap != null){
                            baseAPI.setImage(bitmap);
                            mCircleBar.setProgressNotInUiThread(60);
                            outputText = baseAPI.getUTF8Text();
                            mCircleBar.setProgressNotInUiThread(80);

                            baseAPI.end();
                            iv_text.setClickable(true);
                            msg.obj = outputText;
                            mHandler.sendMessage(msg);
                        }else{
                            baseAPI.end();
                            iv_text.setClickable(true);
                            msg.obj = outputText;
                            mHandler.sendMessage(msg);
                        }
                    }
                }).start();

            }
            //UI线程接收
            Handler mHandler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    switch (msg.what){
                        case 0:
                            data = (String) msg.obj;
                            Intent intent = new Intent(ShowViewActivity.this,ShowResultTextActivity.class);
                            intent.putExtra("data",data);
                            mCircleBar.setProgress(100);
                            mCircleBar.setVisibility(View.GONE);
                            tv_hint.setVisibility(View.GONE);
                            startActivity(intent);
                            break;
                        default:
                            break;
                    }
                }
            };
        });
    }

    private Bitmap getDiskBitmap(String pathString) {
        Bitmap bitmap = null;
        try {
            File file = new File(pathString);
            if(file.exists()) {
                bitmap = BitmapFactory.decodeFile(pathString);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    private void initView() {
        data = "";
        bitmap = ((RegoApplication)getApplication()).getBitmaps();
        iv_show = (ImageView) findViewById(R.id.iv_showImage);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_text = (ImageView) findViewById(R.id.iv_text);
        mCircleBar = (CircleProgressView) findViewById(R.id.circleprogressview);
        mCircleBar.setVisibility(View.GONE);
        tv_hint = (TextView) findViewById(R.id.tv_hint);
    }


}

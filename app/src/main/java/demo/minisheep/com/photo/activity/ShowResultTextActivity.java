package demo.minisheep.com.photo.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import demo.minisheep.com.photo.R;

import static demo.minisheep.com.photo.R.id.iv_back_to_show;

/**
 * Created by minisheep on 17/3/15.
 */

public class ShowResultTextActivity extends Activity {
    private TextView tv_result;
    private String text;
    private ImageView iv_back_to_show;
    private ImageView iv_finish_bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bitmaptotext);
        initView();

        setEvent();
    }

    private void setEvent() {
        Intent intent = getIntent();
        String text = intent.getStringExtra("data");
        tv_result.setText(text);
        iv_back_to_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        iv_finish_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView() {
        text = "";
        tv_result = (TextView) findViewById(R.id.tv_result);
        iv_back_to_show = (ImageView) findViewById(R.id.iv_back_to_show);
        iv_finish_bottom = (ImageView) findViewById(R.id.iv_finish);
    }
}

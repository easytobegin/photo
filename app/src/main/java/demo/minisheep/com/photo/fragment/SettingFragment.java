package demo.minisheep.com.photo.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import demo.minisheep.com.photo.R;
import demo.minisheep.com.photo.application.RegoApplication;

/**
 * Created by minisheep on 17/3/8.
 */
public class SettingFragment extends BaseFragment {
    private View view;
    private Spinner sp_language;
    @Override
    public View initView() {
        TextView tv = new TextView(mContext);
        tv.setText("设置");
        tv.setTextColor(Color.BLACK);
        tv.setTextSize(40);
        return tv;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null){
            view = inflater.inflate(R.layout.setting,container,false);  //防止重新加载布局,数据丢失
        }

        ViewGroup parent = (ViewGroup) view.getParent();
        if(parent != null){
            parent.removeView(view);
        }

        sp_language = (Spinner) view.findViewById(R.id.sp_change);
        sp_language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String[] languages = getResources().getStringArray(R.array.languages);
                Toast.makeText(getActivity(),"已切换到" + languages[i] + "模式",Toast.LENGTH_SHORT).show();
                if(languages[i].equals("英文识别")){
                    ((RegoApplication)getActivity().getApplication()).setLanguage_code(1);  //英文识别为1
                }else{
                    ((RegoApplication)getActivity().getApplication()).setLanguage_code(0);  //中文识别为2
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        return view;
    }
}

package demo.minisheep.com.photo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import demo.minisheep.com.photo.activity.TestMainUiAcitivity;

/**
 * Created by minisheep on 17/3/8.
 */

public abstract class BaseFragment extends Fragment {
    //主页面的上下文
    public TestMainUiAcitivity mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = (TestMainUiAcitivity) getActivity();
        super.onCreate(savedInstanceState);
    }

    /*
        一定要实现的方法
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView();
    }

    /*
        抽象方法,子类必须实现
     */
    public abstract View initView();

    /*
        初始化数据
     */
    public void initEvent(){

    }

    //此方法只执行一次
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        //只要activity不销毁,此方法就只调用一次
        initData();
        initEvent();

        super.onActivityCreated(savedInstanceState);
    }

    private void initData() {

    }

    @Override
    public void onStart() {
        super.onStart();
    }
}

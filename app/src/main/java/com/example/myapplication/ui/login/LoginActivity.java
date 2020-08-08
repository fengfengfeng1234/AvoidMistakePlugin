package com.example.myapplication.ui.login;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

/**
 * url 值的判断
 * 优化速度
 * 理解编译的速度
 */
public class LoginActivity extends AppCompatActivity {
    /**
     *  这个字节码无法识别
     *  属于复杂问题 需要排查
     */
    String url = ConfigDemo.SERVER_URL;


    public static final String HTTP_URL = "https://api.baidu.cn";

    public String HTTP_MO_URL = "https://api.baidu.cn";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        test();
    }


    private void test() {
        Log.d("LoginActivity", " LoginActivity Test ");
    }

    private void test111() {
        Log.d("LoginActivity", " LoginActivity Test url =" + url);
    }


}

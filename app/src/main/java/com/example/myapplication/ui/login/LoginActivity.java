package com.example.myapplication.ui.login;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * url 值的判断
 * 优化速度
 * 理解编译的速度
 */
public class LoginActivity extends AppCompatActivity {
    /**
     * 这个字节码无法识别
     * 属于复杂问题 需要排查
     */
    String url = ConfigDemo.SERVER_URL;


    public static final String HTTP_URL = "https://api.baidu.cn";

    public String HTTP_MO_URL = "https://api.baidu.cn";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String str = readAssetFileByStr(view.getContext(), "release .txt");
                    EditText text = findViewById(R.id.username);
                    text.setText(str);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        test();
    }


    private void test() {
        Log.d("LoginActivity", " LoginActivity Test ");
    }

    private void test111() {
        Log.d("LoginActivity", " LoginActivity Test url =" + url);
    }

    public static String readAssetFileByStr(Context context, String fileName) throws IOException {
        InputStreamReader inputReader = null;
        BufferedReader bufReader = null;
        try {
            inputReader = new InputStreamReader(context.getAssets().open(fileName));
            bufReader = new BufferedReader(inputReader);
            String line = "";
            String result = "";
            while ((line = bufReader.readLine()) != null) {
                result += line;
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufReader != null) {
                bufReader.close();
            }
            if (inputReader != null) {
                inputReader.close();
            }
        }
        return "";
    }

}

package com.example.cardmanagementx;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    private Button btn_register,btn_cancel;//注册按钮
    //用户名，密码，再次输入的密码的控件
    private EditText et_user_name,et_psw,et_psw_again;
    //用户名，密码，再次输入的密码的值
    private String userName,psw,pswAgain;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
    }

    private void init() {

        btn_cancel = findViewById(R.id.btn_cancel);
        btn_register = findViewById(R.id.btn_register);

        et_user_name = findViewById(R.id.et_user_name);
        et_psw = findViewById(R.id.et_psw);
        et_psw_again = findViewById(R.id.et_psw_again);


        //取消按钮
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //注册按钮
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入的字符串
                getEditString();
                //判断输入框内容  <Android开发中，我们经常使用TextUtils.isEmpty()来判断字符串是否为null或者空字符串>
                if(TextUtils.isEmpty(userName)){
                    Toast.makeText(RegisterActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                    return ;
                }else if (TextUtils.isEmpty(psw)){
                    Toast.makeText(RegisterActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
                    return ;
                }else if(TextUtils.isEmpty(pswAgain)){
                    Toast.makeText(RegisterActivity.this,"请再次输入密码",Toast.LENGTH_SHORT).show();
                    return ;
                    /**
                     * 从sharedpreferences中读取输入的用户名，判断sharepreferences中是否又次用户名
                     */
                }else if(isExistUserName(userName)){
                    Toast.makeText(RegisterActivity.this,"此用户名已存在",Toast.LENGTH_SHORT).show();
                    return ;
                }else{
                    Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();

                    saveRegisterInfo(userName,psw);

                    /*//注册成功后把账号传递到LoginActivity.java中
                    // 返回值到loginActivity显示
                    Intent data = new Intent();
                    data.putExtra("userName", userName);
                    setResult(RESULT_OK, data);
                    //RESULT_OK为Activity系统常量，状态码为-1，
                    // 表示此页面下的内容操作成功将data返回到上一页面，如果是用back返回过去的则不存在用setResult传递data值*/
                    startActivity(new Intent(RegisterActivity.this,LoginActivity.class));

                }
            }



        });
    }
    /**
     * 获取控件中的字符串
     * ToString()是转化为字符串的方法 Trim()是去两边空格的方法
     */
    private void getEditString(){
        userName = et_user_name.getText().toString().trim();
        psw = et_psw.getText().toString().trim();
        pswAgain = et_psw_again.getText().toString().trim();
    }
    /**
     *从sharedpreferences中读取输入的用户名，判断sharepreferences中是否又次用户名
     * Android 中的 SharedPreference 是轻量级的数据存储方式，能够保存简单的数据类型，比如 String、int、boolean 值等
     * loginInfo是文件名称 存储用户的账号和密码
     * Context.MODE_PRIVATE：为默认操作模式,代表该文件是私有数据,只能被应用本身访问,在该模式下,写入的内容会覆盖原文件的内容
     */
    private  boolean isExistUserName(String userName){
        boolean has_userName=false;

        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        String spPsw = sp.getString(userName,"");//传入用户名获取密码
        if(!TextUtils.isEmpty(spPsw)){
            has_userName=true;
        }
        return has_userName;
    }
    /**
     * 保存账号和密码到SharedPreferences中SharedPreferences
     * getSharedPreferences(name,mode)方法的第一个参数用于指定该文件的名称，名称不用带后缀，后缀会由Android自动加上。
     * 方法的第二个参数指定文件的操作模式，共有四种操作模式 后三个被弃用
     * Context.MODE_PRIVATE = 0  为默认操作模式，代表该文件是私有数据，只能被应用本身访问，在该模式下，写入的内容会覆盖原文件的内容，如果想把新写入的内容追加到原文件中。可以使用Context.MODE_APPEND
     * Context.MODE_APPEND = 32768
     * Context.MODE_WORLD_READABLE = 1
     * Context.MODE_WORLD_WRITEABLE = 2
     */
    private  void saveRegisterInfo(String userName,String psw){
        String md5Psw = MD5Utils.md5(psw);//MD5加密

        SharedPreferences.Editor editor = getSharedPreferences("loginInfo",MODE_PRIVATE).edit();

        editor.putString(userName,md5Psw);
        editor.apply();
    }
}

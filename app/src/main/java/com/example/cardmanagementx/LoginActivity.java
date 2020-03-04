package com.example.cardmanagementx;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private Button btn_login,btn_cancel;//登录按钮,取消按钮
    private String userName, psw, spPsw;//获取的用户名，密码，加密密码
    private EditText et_user_name, et_psw;//编辑框
    private CheckBox rememberPass;//是否记住密码框

    private SharedPreferences pref;//记住密码的文件
    private SharedPreferences.Editor editor;//记住密码的编辑器

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    private void init() {
        et_user_name = findViewById(R.id.et_user_name);
        et_psw = findViewById(R.id.et_psw);
        rememberPass = findViewById(R.id.remember_pass);
        btn_login = findViewById(R.id.btn_login);
        btn_cancel = findViewById(R.id.btn_cancel);

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRemember = pref.getBoolean("remember_password",false);
        if(isRemember){
            String account = pref.getString("account","");//account 存放用户名信息
            String password = pref.getString("password","");//password 存放密码信息

            //将取出的用户名和密码放到edittext中
            et_user_name.setText(account);
            et_psw.setText(password);
            rememberPass.setChecked(true);
        }
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = et_user_name.getText().toString().trim();
                psw = et_psw.getText().toString().trim();

                String md5Psw = MD5Utils.md5(psw);
                spPsw = readPsw(userName);

                if(TextUtils.isEmpty(userName)){
                    Toast.makeText(LoginActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                    return ;
                }else if (TextUtils.isEmpty(psw)){
                    Toast.makeText(LoginActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
                    return ;
                }else if(md5Psw.equals(spPsw)){
                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();

                    //判断是否勾选了 rememberPass
                    editor = pref.edit();
                    if(rememberPass.isChecked()){
                        editor.putBoolean("remember_password",true); //标记已记住密码
                        editor.putString("account",userName);//存放userName
                        editor.putString("password",psw);//存放password
                    }else{
                        editor.clear();
                    }
                    editor.apply();
                    //保存登录状态，在界面保存登录的用户名
                    saveLoginStatus(true,userName);
                    //登录成功都关闭页面进入主页
                    Intent data = new Intent();
                    data.putExtra("isLogin",true);
                    setResult(RESULT_OK,data);
                    LoginActivity.this.finish();
                    startActivity(new Intent(LoginActivity.this,BoardView.class));
                    return ;
                }else if(spPsw != null&&!TextUtils.isEmpty(spPsw)&&!md5Psw.equals(spPsw)){
                    Toast.makeText(LoginActivity.this,"输入的用户名和密码不一致",Toast.LENGTH_SHORT).show();
                    return ;
                }else{
                    Toast.makeText(LoginActivity.this,"此用户名不存在",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    /**
     * 从sharedpreferences中根据用户名读取密码
     */

    private String readPsw(String userName){
        SharedPreferences sp = getSharedPreferences("loginInfo",MODE_PRIVATE);
        String s = sp.getString(userName,"");
        return s;
    }

    /**
     * 保存登录状态和登录用户名到SharedPreferences中
     */

    private void saveLoginStatus(boolean status ,String userName){
        SharedPreferences.Editor editor = getSharedPreferences("loginInfo",MODE_PRIVATE).edit();

        editor.putBoolean("isLogin",status);
        editor.putString("loginUserName",userName);
        editor.apply();
    }
}

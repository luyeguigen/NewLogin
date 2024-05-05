package luo.NewLogin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class Login_Activity extends AppCompatActivity {

    private static final String TAG = "tag";
    public static final int REQUEST_CODE_REGISTER = 1;
    private Button login;
    private EditText etAccount, etPasswd;
    private String userName = null;
    private String pass =null;
    private CheckBox cbRemember,cbAutoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        getSupportActionBar().setTitle("登录");

        //把findViewById移到initView里面，方便看代码
        initView();
        initDate();
        //点击登录按钮时的方法
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String account = etAccount.getText().toString();
                String password = etPasswd.getText().toString();
                Log.d(TAG, "onClick:------------------" + account);
                Log.d(TAG, "onClick:------------------" + password);


//                if (TextUtils.equals(account, userName)) {
//                    if (TextUtils.equals(password, pass)) {
//                        Toast.makeText(Login_Activity.this, "登录成功！", Toast.LENGTH_LONG).show();
//                    } else {
//                        Toast.makeText(Login_Activity.this, "密码错误！", Toast.LENGTH_LONG).show();
//                    }
//                } else {
//                    Toast.makeText(Login_Activity.this, "用户名错误！", Toast.LENGTH_LONG).show();
//                }
                if (TextUtils.isEmpty(userName)) {
                    Toast.makeText(Login_Activity.this, "还未注册账号！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.equals(account, userName)) {
                    if (TextUtils.equals(password, pass)) {
                        Toast.makeText(Login_Activity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                        if (cbRemember.isChecked()) {
                            SharedPreferences spf = getSharedPreferences("spf", MODE_PRIVATE);
                                //存东西
                                SharedPreferences.Editor edit = spf.edit();
                                //账号密码输入的字符串放入
                                edit.putString("account", account);
                                edit.putString("password", password);
                                //CheckBox是否勾选
                                edit.putBoolean("isRemember", true);
                            if (cbAutoLogin.isChecked()) {
                                edit.putBoolean("isLogin", true);
                            }else {
                                edit.putBoolean("isLogin", false);
                            }
                                //提交
                                edit.apply();


                        } else {
                            SharedPreferences spf = getSharedPreferences("spf", MODE_PRIVATE);
                            //存东西
                            SharedPreferences.Editor edit = spf.edit();
                            edit.putBoolean("isRemember", false);
                            edit.apply();

                        }
                        Intent intent = new Intent(Login_Activity.this, User_Activity.class);
                        intent.putExtra("account", account);
                        startActivity(intent);
                    Login_Activity.this.finish();
                    } else {
                        Toast.makeText(Login_Activity.this, "密码错误！", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(Login_Activity.this, "用户名错误！", Toast.LENGTH_SHORT).show();

                }


            }
        });

        cbAutoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbRemember.setChecked(true);

                }
            }
        });
        cbRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    cbAutoLogin.setChecked(false);
                }
            }
        });

    }


    private void initView() {
        login = findViewById(R.id.login);
        etPasswd = findViewById(R.id.etPasswd);
        etAccount = findViewById(R.id.etAccount);
        cbRemember = findViewById(R.id.cb_remember);
        cbAutoLogin = findViewById(R.id.cb_auto_login);




    }

    private void initDate() {
        SharedPreferences spf = getSharedPreferences("spf", MODE_PRIVATE);
        //取东西
        boolean isRemember = spf.getBoolean("isRemember", false);
        boolean isLogin= spf.getBoolean("isLogin", false);
        String account = spf.getString("account","");
        String password = spf.getString("password","");

        if (isLogin) {  Intent intent = new Intent(Login_Activity.this, User_Activity.class);
            intent.putExtra("account", account);
            startActivity(intent);
            Login_Activity.this.finish();

        }
        //放注册账号和密码
        userName = account;
        pass = password;
        //
        if (isRemember) {
            etAccount.setText(account);
            etAccount.setSelection(account.length());
            etPasswd.setText(password);
            cbRemember.setChecked(true);

        }

    }

    public void zhu_ce(View view) {
        Intent intent = new Intent(this, ZC_MainActivity.class);
//        startActivity(intent);
        startActivityForResult(intent, REQUEST_CODE_REGISTER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //引用注册页面的变量
        if (requestCode == REQUEST_CODE_REGISTER && resultCode== ZC_MainActivity.RESULT_CODEREGISTER && data!=null) {
            Bundle extras = data.getExtras();
            String account = extras.getString("account", "");
            String password = extras.getString("password", "");
            etAccount.setText(account);
            etPasswd.setText(password);

            userName = account;
            pass = password;
        }
    }
}
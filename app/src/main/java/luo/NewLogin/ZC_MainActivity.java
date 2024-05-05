package luo.NewLogin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ZC_MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int RESULT_CODEREGISTER = 0;
    private EditText etAccount, etPasswd, etPasswdSure;
    private Button register;
    private CheckBox cbAgree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zc_main);
        getSupportActionBar().setTitle("注册");
        initView();
        register.setOnClickListener(this);


    }

    private void initView() {
        etAccount = findViewById(R.id.etAccount);
        etPasswd = findViewById(R.id.etPasswd);
        etPasswdSure = findViewById(R.id.etPasswd_sure);
        register = findViewById(R.id.register);
        cbAgree = findViewById(R.id.cb_agree);

    }


    @Override
    public void onClick(View v) {
        String name = etAccount.getText().toString();
        String pass = etPasswd.getText().toString();
        String PasswdSure = etPasswdSure.getText().toString();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(ZC_MainActivity.this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(ZC_MainActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!TextUtils.equals(pass, PasswdSure)) {
            Toast.makeText(ZC_MainActivity.this, "密码不一致！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!cbAgree.isChecked()) {
            Toast.makeText(ZC_MainActivity.this, "请同意用户协议！", Toast.LENGTH_SHORT).show();
            return;
        }
//存储注册用户名，密码
        SharedPreferences spf = getSharedPreferences("spf", MODE_PRIVATE);
        SharedPreferences.Editor edit = spf.edit();
        edit.putString("account", name);
        edit.putString("password", pass);
        //回传数据，所以需要打包
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("account",name);
        bundle.putString("password",pass);
        intent.putExtras(bundle);
                setResult(RESULT_CODEREGISTER,intent);
        Toast.makeText(ZC_MainActivity.this, "注册成功！", Toast.LENGTH_LONG).show();
        this.finish();
    }
}
package luo.NewLogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class User_Activity extends AppCompatActivity {
private TextView tvContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        getSupportActionBar().setTitle("User");
        tvContent = findViewById(R.id.tv_content);
        Intent intent = getIntent();
        String account = intent.getStringExtra("account");
        tvContent.setText("欢迎:"+account);

    }

    public void logout(View view) {
      //  取值
        SharedPreferences spf = getSharedPreferences("spf", MODE_PRIVATE);
        SharedPreferences.Editor edit = spf.edit();
        //保存退出状态
        edit.putBoolean("isLogin", false);
        edit.apply();
        Intent intent = new Intent(this, Login_Activity.class);
        this.finish();
        startActivity(intent);

    }
}
package cn.leo.magicthread;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import cn.leo.magic.annotation.RunOnBackGround;
import cn.leo.magic.annotation.RunOnIOThread;
import cn.leo.magic.annotation.RunOnUIThread;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test();
    }

    @RunOnBackGround
    private void test() {
        Toast.makeText(this, "测试", Toast.LENGTH_SHORT).show();
    }
}

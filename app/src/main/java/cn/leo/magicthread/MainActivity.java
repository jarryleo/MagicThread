package cn.leo.magicthread;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import cn.leo.magic.annotation.RunOnIOThread;
import cn.leo.permission.PermissionRequest;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tvTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test();
            }
        });
    }


    @RunOnIOThread
    private void test() {
        String name = Thread.currentThread().getName();
        Log.e("当前线程:", name);
    }
}

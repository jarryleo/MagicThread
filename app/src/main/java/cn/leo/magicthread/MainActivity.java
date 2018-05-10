package cn.leo.magicthread;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import cn.leo.magic.annotation.RunOnIOThread;
import cn.leo.magic.annotation.RunOnUIThread;

public class MainActivity extends AppCompatActivity {

    private TextView mTvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTvTest = findViewById(R.id.tvTest);
        mTvTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress();
            }
        });
    }

    @RunOnIOThread
    public void progress() {
        for (int i = 0; i <= 100; i++) {
            //处理内存泄漏
            if (Thread.currentThread().isInterrupted()) return;
            showProgress(i);
            SystemClock.sleep(1000);
        }
    }

    @RunOnUIThread
    private void showProgress(int progress) {
        mTvTest.setText(progress + "%");
    }
}

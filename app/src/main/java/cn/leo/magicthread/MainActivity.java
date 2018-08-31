package cn.leo.magicthread;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import cn.leo.magic.annotation.RunOnCalcThread;
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
                Log.e("-----", "跑完了");
                testDelay();
            }
        });
    }

    @RunOnCalcThread
    public void progress() {
        for (int i = 0; i <= 10; i++) {
            //处理内存泄漏
            if (Thread.currentThread().isInterrupted()) {
                break;
            }
            Log.e("-----" + Thread.currentThread().getName(), "progress: " + i);
            showProgress(i);
            SystemClock.sleep(1000);
        }
        Log.e("-----", "跳出循环");
    }

    @RunOnUIThread
    private void showProgress(int progress) {
        mTvTest.setText(progress + "%");
    }

    @RunOnUIThread(5000)
    public void testDelay() {
        Toast.makeText(this, "延迟成功", Toast.LENGTH_SHORT).show();
    }
}

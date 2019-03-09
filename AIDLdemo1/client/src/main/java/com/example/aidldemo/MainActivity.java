package com.example.aidldemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;

import com.example.marko.aidldemo.IMyAidlInterface;


/**
 * @author Marko
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private AppCompatEditText edit_num1,edit_num2,edit_sum;
    private int num1,num2,sum;
    private AppCompatButton cal;
    private IMyAidlInterface iMyAidlInterface;

    //绑定服务回调
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            //接触绑定时调用，清空接口，防止内存溢出
            iMyAidlInterface = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edit_num1 = findViewById(R.id.edit_num1);
        edit_num2 = findViewById(R.id.edit_num2);
        edit_sum = findViewById(R.id.edit_sum);
        cal = findViewById(R.id.btn_cal);
        cal.setOnClickListener(this);

        bindService();
    }

    private void bindService(){
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.example.marko.aidldemo",
                "com.example.marko.aidldemo.IRemoteService"));
        bindService(intent,serviceConnection,BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }

    @Override
    public void onClick(View view) {
        num1 = Integer.parseInt(edit_num1.getText().toString());
        num2 = Integer.parseInt(edit_num2.getText().toString());
        try {
            sum = iMyAidlInterface.add(num1,num2);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        edit_sum.setText(String.valueOf(sum));
    }
}

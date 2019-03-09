package com.example.marko.aidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;

import com.example.marko.aidlserver.Book;
import com.example.marko.aidlserver.BookController;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatButton btn_getBookList, btn_addBook_inOut,btn_addBook_in,btn_addBook_out;
    private BookController controller;
    private boolean connect;
    private final String TAG = "Client";
    private List<Book> bookList;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            controller = BookController.Stub.asInterface(iBinder);
            connect = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            connect = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_getBookList = findViewById(R.id.btn_getBookList);
        btn_addBook_inOut = findViewById(R.id.btn_addBook_inOut);
        btn_addBook_in = findViewById(R.id.btn_addBook_in);
        btn_addBook_out = findViewById(R.id.btn_addBook_out);

        btn_getBookList.setOnClickListener(this);
        btn_addBook_inOut.setOnClickListener(this);
        btn_addBook_in.setOnClickListener(this);
        btn_addBook_out.setOnClickListener(this);
        bindService();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_getBookList:
                if (connect) {
                    try {
                        bookList = controller.getBookList();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    log();
                }
                break;
            case R.id.btn_addBook_inOut:
                if (connect) {
                    Book book = new Book("这是一本新书 InOut");
                    try {
                        controller.addBookInout(book);
                        Log.e(TAG, "向服务器以InOut方式添加了一本新书");
                        Log.e(TAG, "新书名：" + book.getName());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.btn_addBook_in:
                if (connect) {
                    Book book = new Book("这是一本新书 In");
                    try {
                        controller.addBookIn(book);
                        Log.e(TAG, "向服务器以In方式添加了一本新书");
                        Log.e(TAG, "新书名：" + book.getName());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.btn_addBook_out:
                if (connect) {
                    Book book = new Book("这是一本新书 Out");
                    try {
                        controller.addBookOut(book);
                        Log.e(TAG, "向服务器以Out方式添加了一本新书");
                        Log.e(TAG, "新书名：" + book.getName());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }

    }

    private void bindService() {
        Intent intent = new Intent();
        intent.setPackage("com.example.marko.aidlserver");
        intent.setAction("com.example.marko.aidlserver.action");
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (connect) {
            unbindService(serviceConnection);
        }
    }
    private void log() {
        for (Book book : bookList) {
            Log.e(TAG, book.toString());
        }
    }

}

package com.example.marko.aidldemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

/**
 * @author Marko
 */
public class IRemoteService extends Service {
    public IRemoteService() {
    }

    @Override
    public IBinder onBind(Intent intent) {

        return iBinder;
    }

    private IBinder iBinder = new IMyAidlInterface.Stub() {
        @Override
        public int add(int num1, int num2) throws RemoteException {
            return num1 + num2;
        }
    };
}

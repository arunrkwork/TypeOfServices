package apps.arunrk.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyBoundService extends Service {

    private final IBinder mBinder  = new LocalService();
    private static final String TAG = MyBoundService.class.getSimpleName();

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        return mBinder;
    }

    public class LocalService extends Binder {

        MyBoundService getService(){
            Log.d(TAG, "getService: ");
            return MyBoundService.this;
        }

    }

    public String getFirstMessage(){
        return "First Message";
    }

    public String getSecondMessage(){
        return "Second Message";
    }

}

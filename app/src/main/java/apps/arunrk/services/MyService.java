package apps.arunrk.services;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.io.FileDescriptor;
import java.io.PrintWriter;

public class MyService extends Service {

    private static final String TAG = MyService.class.getSimpleName();

    final class MyThread implements Runnable {

        int serviceID;

        public MyThread(int serviceID) {
            this.serviceID = serviceID;
        }

        @Override
        public void run() {
            int i = 0;

            synchronized (this) {
                while (i < 10) {
                    try {
                        wait(3000);
                        Log.d(TAG, "run: " + i);
                        i++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                stopSelf(serviceID);
            }
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service Started...", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onStartCommand: flag : " + flags + " start id : " + startId);

        Thread thread = new Thread(new MyThread(startId));
        thread.start();

      /*  int i = 0;

        synchronized (this) {
            while (i < 10) {
                try {
                    wait(1500);
                    i++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
                stopSelf();
        }*/

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
        Toast.makeText(this, "Service Stopped...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        return null;
    }







    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG, "onConfigurationChanged: ");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.d(TAG, "onLowMemory: ");
    }

    public MyService() {
        super();
        Log.d(TAG, "MyService: ");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Log.d(TAG, "onTrimMemory: ");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: ");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.d(TAG, "onRebind: ");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Log.d(TAG, "onTaskRemoved: ");
    }

    @Override
    protected void dump(FileDescriptor fd, PrintWriter writer, String[] args) {
        super.dump(fd, writer, args);
        Log.d(TAG, "dump: ");
    }
}

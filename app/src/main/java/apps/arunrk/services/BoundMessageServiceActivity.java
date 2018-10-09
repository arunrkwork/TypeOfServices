package apps.arunrk.services;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class BoundMessageServiceActivity extends AppCompatActivity {

    private static final String TAG = BoundMessageServiceActivity.class.getSimpleName();
    Messenger mMessenger = null;
    boolean isBind = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bound_message_service);
    }

    public void bindService(View view) {

        Intent intent = new Intent(this, MyBoundMessageService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

    }

    public void sayHello(View view) {

        if (isBind) {
            if (view.getId() == R.id.btnSayHello) {
                Message message = Message.obtain(null, MyBoundMessageService.JOB_1, 0, 0, 0);
                try {
                    Log.d(TAG, "sayHello: message sent to job 1...");
                    mMessenger.send(message);
                } catch (RemoteException e) {
                    Log.e(TAG, "sayHello: job 1", e);
                }
            } else if (view.getId() == R.id.btnSayAgainHello) {
                Message message = Message.obtain(null, MyBoundMessageService.JOB_2, 0, 0, 0);
                try {
                    Log.d(TAG, "sayHello: message sent to job 2...");
                    mMessenger.send(message);
                } catch (RemoteException e) {
                    Log.e(TAG, "sayHello: job 2", e);
                }
            }
        } else {
            Log.d(TAG, "sayHello: service not bind. please bind service first...");
            Toast.makeText(this, "service not bind. please bind service first...", Toast.LENGTH_SHORT).show();
        }

    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

            mMessenger = new Messenger(iBinder);
            isBind = true;

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mMessenger = null;
            isBind = false;
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        if (isBind) {
            unbindService(mConnection);
            isBind = false;
            mMessenger = null;
        }
    }
}

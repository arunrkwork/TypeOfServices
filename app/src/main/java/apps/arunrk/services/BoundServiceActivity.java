package apps.arunrk.services;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class BoundServiceActivity extends AppCompatActivity {

    private static final String TAG = BoundServiceActivity.class.getSimpleName();
    TextView textView;
    MyBoundService myBoundService;
    boolean isBind  = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bound_service);

        textView = findViewById(R.id.textView);
        Intent intent = new Intent(this, MyBoundService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

    }

    public void getSecondMessage(View view) {
        String message = myBoundService.getSecondMessage();
        textView.setText(message);
    }

    public void getFirstMessage(View view) {
        String message = myBoundService.getFirstMessage();
        textView.setText(message);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG, "onServiceConnected: ");
            MyBoundService.LocalService localService = (MyBoundService.LocalService) iBinder;
            myBoundService = localService.getService();
            isBind = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG, "onServiceDisconnected: ");
            isBind = false;
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
        if (isBind) {
            unbindService(mConnection);
            Log.d(TAG, "onStop: connection");
            isBind = false;
        }

    }
}

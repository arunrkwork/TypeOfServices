package apps.arunrk.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.widget.Toast;

public class MyBoundMessageService extends Service {

    Messenger messenger = new Messenger(new IncommingHandler());
    private static final String TAG = MyBoundMessageService.class.getSimpleName();
    static final int JOB_1 = 1;
    static final int JOB_2 = 2;

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        Toast.makeText(this, "onBind called", Toast.LENGTH_SHORT).show();
        return messenger.getBinder();
    }

    class IncommingHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, "handleMessage: ");

            switch (msg.what) {
                case JOB_1:
                    Log.d(TAG, "handleMessage: job 1 called");
                    Toast.makeText(MyBoundMessageService.this, "Job1 called", Toast.LENGTH_SHORT).show();
                    break;
                case JOB_2:
                    Log.d(TAG, "handleMessage: job 2 called");
                    Toast.makeText(MyBoundMessageService.this, "Job2 called", Toast.LENGTH_SHORT).show();
                    break;
                    default:
                        super.handleMessage(msg);
            }


        }
    }

}

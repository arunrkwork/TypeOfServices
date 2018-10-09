package apps.arunrk.services;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class InterProcessCommunicationActivity extends AppCompatActivity {

    TextView txtMessage;
    private final int JOB_1 = 1;
    private final int JOB_2 = 2;
    private final int JOB_1_RESPONSE = 3;
    private final int JOB_2_RESPONSE = 4;
    Messenger messenger = null;
    boolean isBind = false;
    private static final String TAG = InterProcessCommunicationActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inter_process_communication);
        txtMessage = findViewById(R.id.txtMessage);

        Intent intent = new Intent(this, MyBoundMessageInterCommunicationService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

    }


    public void getMessage(View view) {

        if (view.getId() == R.id.btnFirstMessage) {
            Log.d(TAG, "getMessage: first message");
            Message message = Message.obtain(null, JOB_1);
            message.replyTo =   new Messenger(new ResponseHandler());
            try {
                messenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        } else if (view.getId() == R.id.btnSecondMessage) {
            Log.d(TAG, "getMessage: second message");
            Message message = Message.obtain(null, JOB_2);
            message.replyTo =   new Messenger(new ResponseHandler());
            try {
                messenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

    }

    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            messenger = new Messenger(iBinder);
            isBind = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBind = false;
        }
    };

    class ResponseHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {

            String message = "";

            switch (msg.what) {

                case JOB_1_RESPONSE:

                    message = msg.getData().getString("response_message");
                    txtMessage.setText(message);
                    Log.d(TAG, "handleMessage: first response " + message);

                    break;
                case JOB_2_RESPONSE:
                    message = msg.getData().getString("response_message");
                    txtMessage.setText(message);
                    Log.d(TAG, "handleMessage: second response " + message);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isBind) {
            unbindService(mConnection);
            isBind = false;
        }

    }
}

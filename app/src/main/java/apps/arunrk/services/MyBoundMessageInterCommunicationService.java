package apps.arunrk.services;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

public class MyBoundMessageInterCommunicationService extends Service {

    private final int JOB_1 = 1;
    private final int JOB_2 = 2;
    private final int JOB_1_RESPONSE = 3;
    private final int JOB_2_RESPONSE = 4;
    Messenger messenger = new Messenger(new IncommingHandler());

    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }

    class IncommingHandler extends Handler {

        Message message;
        String string;
        Bundle bundle = new Bundle();

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {

                case JOB_1:
                    string = "This is the message from service job 1";
                    message =  Message.obtain(null, JOB_1_RESPONSE);
                    bundle.putString("response_message", string);
                    message.setData(bundle);
                    try {
                        msg.replyTo.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                case JOB_2:
                    string = "This is the message from service job 2";
                    message =  Message.obtain(null, JOB_2_RESPONSE);
                    bundle.putString("response_message", string);
                    message.setData(bundle);
                    try {
                        msg.replyTo.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                    default:
                        super.handleMessage(msg);
            }

        }
    }

}

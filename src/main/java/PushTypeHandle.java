import java.io.IOException;
import java.net.Socket;

/** 时间管理类 */
public class PushTypeHandle extends Thread {
    PushTypeClass _pushTypeClass;

    public PushTypeHandle set_pushTypeClass(PushTypeClass _pushTypeClass) {
        this._pushTypeClass = _pushTypeClass;
        return this;
    }

    @Override
    public void run() {
        super.run();
        long t1 = System.currentTimeMillis();
        while (true) {
            if (System.currentTimeMillis() - t1 >= 60000) {
                Socket socket = null;
                try {
                    socket = new Socket("127.0.0.1", 1993);
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                t1 = System.currentTimeMillis();
            }
        }

    }
}

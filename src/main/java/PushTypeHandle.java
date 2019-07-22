
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Socket socket = null;
                try {
                    socket = new Socket("127.0.0.1", 1993);
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 60*1000, 60*1000, TimeUnit.MILLISECONDS);

    }
}

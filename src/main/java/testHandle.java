import java.io.IOException;
import java.net.Socket;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class testHandle extends Thread {

    PushTypeClass _pushTypeClass;

    public testHandle set_pushTypeClass(PushTypeClass _pushTypeClass) {
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
               for (int i=0;i<20;i++)
               {
                   String _key=UUID.randomUUID().toString();
                   _pushTypeClass.set(_key, "TestData",System.currentTimeMillis(),Long.valueOf("1000"),"none");
               }
            }
        }, 50, 50, TimeUnit.MILLISECONDS); /** 一秒钟检查十次 */


    }
}

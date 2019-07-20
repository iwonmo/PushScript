import java.io.*;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SaveTypeClass extends Thread {
    PushTypeClass _pushTypeClass;

    public SaveTypeClass set_pushTypeClass(PushTypeClass _pushTypeClass) {
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
                if (_pushTypeClass.getCount() == 0) return; /** 数据为空 则不做保存 */
                FileOutputStream outSTr = null;
                try {
                    outSTr = new FileOutputStream(new File(RunVariable.filePath+ "dateDir"+File.separator + System.currentTimeMillis() + "_" + UUID.randomUUID().toString() + ".ps"));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                BufferedOutputStream Buff = new BufferedOutputStream(outSTr);
                Long num = _pushTypeClass.getCount();
                /** 写入头 */
                try {
                    Buff.write((String.valueOf(num) + "\r\n").getBytes());
                    Buff.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                /** 每次都讲缓存置入到磁盘内 */
                _pushTypeClass.forEach(new ValueFuction() {
                    @Override
                    public void value(String _key, String _data, Long _time,Long _utime,String _type) {
                        try {
                            String _tmp = _key + "   " + _data + "   " + _time +  "   " + _utime  + "   " + _type + "\r\n";
                            Buff.write(_tmp.getBytes());
                            /** 每次都讲缓存置入到磁盘内 */
                            Buff.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                try {
                    Buff.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 60000, TimeUnit.MILLISECONDS); //3600000 毫秒执行

    }
}

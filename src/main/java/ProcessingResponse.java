
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * 过期检测类
 */
public class ProcessingResponse extends Thread {
    PushTypeClass _pushTypeClass;

    public ProcessingResponse set_pushTypeClass(PushTypeClass _pushTypeClass) {
        this._pushTypeClass = _pushTypeClass;
        return this;
    }

    @Override
    public void run() {
        super.run();
        try {
            ServerSocket serverSocket = new ServerSocket(1993);
            while (true) {
                Socket socket = serverSocket.accept();
                PushType[] pushTypes_tmp;
                System.out.print("---- 查询事件 ----\r\n");
                _pushTypeClass.forEach(new ValueFuctionRetList() {
                    @Override
                    public void value(String _key, String _data, Long _time, Long _utime, String _type, List<PushType> _list) {
                        Long time_ = System.currentTimeMillis();
                        if (time_ >= _time) {
                            System.out.print("      " + ToolClass.timeFormat() + "  过期：" + _key + "   " + String.valueOf(_time) + "   " + _type + "\n");
                            _list.add(new PushType(_key, _data, _time, _utime, _type));
                            ExecutorServiceClass.exe(new Runnable() {
                                @Override
                                public void run() {
                                    /** 回调请求接口 */
                                    CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
                                    try {
                                        httpclient.start();
                                        String url = ToolClass.buildGet(RunVariable.getHttp, "pskey=" + _key + "&psdata=" + _data + "&pstype=" + _type);
                                        final Future future = httpclient.execute(new HttpGet(url), null);
                                        HttpResponse response = (HttpResponse) future.get();
                                        String result = EntityUtils.toString(response.getEntity(), "UTF-8");
                                    } catch (Exception e) {
                                    } finally {
                                        try {
                                            httpclient.close();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            });
                        }
                    }
                });
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

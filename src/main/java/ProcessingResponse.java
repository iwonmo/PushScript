import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.concurrent.Future;

/** 过期检测类 */
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
                for (int i = 0; i < 5; i++) {
                    _pushTypeClass.forEach(new ValueFuction() {
                        @Override
                        public void value(String _key, String _data, Long _time) {
                            Long time_ = System.currentTimeMillis();
                            if (time_ - _time >= 0) {
                                /** 先清除 */
                                _pushTypeClass.removeKey(_key);
                                ExecutorServiceClass.exe(new Runnable() {
                                    @Override
                                    public void run() {
                                        /** 回调请求接口 */
                                        CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
                                        try {
                                            httpclient.start();
                                            URIBuilder uriBuilder = new URIBuilder(RunVariable.getHttp);
                                            uriBuilder.addParameter("PushScript", _data);
                                            httpclient.execute(new HttpGet(uriBuilder.build()), null);
                                        } catch (URISyntaxException e) {
                                            e.printStackTrace();
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
                }
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

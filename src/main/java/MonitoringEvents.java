import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MonitoringEvents extends Thread {
    PushTypeClass _pushTypeClass;

    public MonitoringEvents set_PushType(PushTypeClass PushTypeClass_) {
        this._pushTypeClass = PushTypeClass_;
        return this;
    }

    @Override
    public void run() {
        super.run();
        try {
            ServerSocket serverSocket = new ServerSocket(1992);
            while (true) {
                    Socket socket = serverSocket.accept();
                    InputStream inputStream = socket.getInputStream();
                    byte buffer[] = new byte[1024 * 4];
                    StringBuilder sb = new StringBuilder();
                    int len;
                    while ((len = inputStream.read(buffer)) != -1) {
                        sb.append(new String(buffer, 0, len, "UTF-8"));
                    }
                    inputStream.close();
                    try {
                        JsonObject jsonObject = (JsonObject) new JsonParser().parse(sb.toString());
                        //System.out.print("收到："+jsonObject.get("key").getAsString()+"\n");
                        /** 如果是删除事件 则删除对应的key */
                        if( jsonObject.get("type").getAsString().equals("del")){
                            _pushTypeClass.removeKey(jsonObject.get("key").getAsString());
                        }else{
                            _pushTypeClass.set(jsonObject.get("key").getAsString(), jsonObject.get("data").getAsString(), jsonObject.get("time").getAsLong(), jsonObject.get("utime").getAsLong(), jsonObject.get("type").getAsString());
                        }
                    }catch (Exception e){}
                    socket.close();
            }
        } catch (IOException e) { }

    }
}

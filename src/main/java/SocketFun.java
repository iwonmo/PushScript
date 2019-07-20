import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class SocketFun  {

    /** 写入到Socket */
    public static void push(String string) throws IOException {
        Socket socket=new Socket("127.0.0.1",1993);
        OutputStream ops = socket.getOutputStream();
        OutputStreamWriter opsw = new OutputStreamWriter(ops);
        BufferedWriter bw = new BufferedWriter(opsw);
        bw.write(string);
        bw.flush();
        bw.close();
        opsw.close();
        ops.close();
        socket.close();
    }
}


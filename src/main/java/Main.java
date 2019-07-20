import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.management.ManagementFactory;

public class Main {
    public static void main(String[] args) {
        /** 先把PID存储 */
        ToolClass.savePid(Main.class.getResource("").getPath() + "pid.txt");
        /** 初始化数据库存储目录 */
        String dateDir = Main.class.getResource("").getPath() + "dateDir";
        ToolClass.createDir(dateDir);
        /** 实例化存储结构 */
        PushTypeClass pushTypeClass = new PushTypeClass();
        /** 读取本地数据库 */
        String sqlName = ToolClass.getSqlFile(dateDir);
        if (!"".equals(sqlName)) {
            sqlName = dateDir + File.separator + sqlName;
            File file = new File(sqlName);
            try {
                BufferedReader bf = new BufferedReader(new FileReader(file));
                String readCon = "";
                long num = 0, num_state = 1;
                String[] conList;
                while (readCon != null) {
                    readCon = bf.readLine();
                    if (readCon == null) {
                        break;
                    }
                    if (num_state == 1) {
                        num = Long.valueOf(readCon);
                        num_state++;
                    } else {
                        conList = readCon.split("   ");
                        if (conList.length != 5) {
                            throw new Exception("强制判定格式错误");
                        } else {
                            pushTypeClass.set(conList[0], conList[1], Long.valueOf(conList[2]), Long.valueOf(conList[3]), conList[4]);
                        }
                    }
                }
                bf.close();
                if(num != pushTypeClass.getCount()){
                    throw new Exception("文件存储的数据与真实的数据不相符");
                }
            } catch (Exception e) {
                /** 错误状态下清空结构体已经存储的文本 */
                pushTypeClass.clear();
            }
        }
        /** 获取回调接口 */
        String http = Main.class.getResource("").getPath() + "http";
        /** 读取回调地址 */
        RunVariable.getHttp = ToolClass.readFileAll(http);
        RunVariable.filePath = Main.class.getResource("").getPath();
        System.out.print("PushScript 正在运行：\n");
        /** 接收线程 */
        new MonitoringEvents().set_PushType(pushTypeClass).start();
        /** 每秒执行线程 */
        new PushTypeHandle().set_pushTypeClass(pushTypeClass).start();
        /** 过期检测线程 */
        new ProcessingResponse().set_pushTypeClass(pushTypeClass).start();
        /** 定期保存线程 */
        new SaveTypeClass().set_pushTypeClass(pushTypeClass).start();
    }
}

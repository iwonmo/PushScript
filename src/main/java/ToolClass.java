import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

public class ToolClass {

    /**
     * 读取文本
     */
    public static String readFileAll(String fileName) {
        File file = new File(fileName);
        Long fileLength = file.length();
        byte[] fileContent = new byte[fileLength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(fileContent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(fileContent, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取年月日时分秒
     */
    public static String timeFormat() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()).toString();
    }

    /** 创建文件夹 */
    public static void createDir(String dir) {
        File file = new File(dir);
        if (!file.exists() && !file.isDirectory())
            file.mkdir();
    }
    /** 获取数据库名称 */
    public static String getSqlFile(String filePath) {
        File file = new File(filePath);
        if (file.isFile() || (0 == file.list().length)) {
            return "";
        }
        File[] files = file.listFiles();
        List fileList = Arrays.asList(files);
        Collections.sort(fileList, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                if (o1.isDirectory() && o2.isFile())
                    return -1;
                if (o1.isFile() && o2.isDirectory())
                    return 1;
                return o1.getName().compareTo(o2.getName());
            }
        });
        return files[files.length - 1].getName();
    }

}

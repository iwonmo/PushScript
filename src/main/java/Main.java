public class Main {
    public static void main(String[] args) {
        System.out.print("PushScript 正在运行：\n");
        /** 实例化存储结构 */
        PushTypeClass pushTypeClass = new PushTypeClass();
        /** 接收线程 */
        new MonitoringEvents().set_PushType(pushTypeClass).start();
        /** 每秒执行线程 */
        new PushTypeHandle().set_pushTypeClass(pushTypeClass).start();
        /** 过期检测线程 */
        new ProcessingResponse().set_pushTypeClass(pushTypeClass).start();
    }
}

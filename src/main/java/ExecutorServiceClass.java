import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 请求线程池
 */
public class ExecutorServiceClass {
    static ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void exe(Runnable runnable) {
        executorService.execute(runnable);
    }
}

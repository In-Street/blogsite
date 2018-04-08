package cyf.blog.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Cheng Yufei
 * @create 2018-04-04 16:58
 **/
@Configuration
public class ThreadPoolConfig {

    @Bean
    public AsyncTaskExecutor init() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setKeepAliveSeconds(60);
        executor.setMaxPoolSize(10);
        executor.setThreadNamePrefix("My-Async：");
        //线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean，这样这些异步任务的销毁就会先于 使用@asyn中对其他线程池的引用的销毁（eg: @Async 方法中有对redis的使用，但异步程序没执行完，redis线程池已经关闭的情况）
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //线程池中任务的等待时间，如果超过这个时候还没有销毁就强制销毁，以确保应用最后能够被关闭，而不是阻塞住
        executor.setAwaitTerminationSeconds(60);
        return executor;
    }

}

package ml.raketeufo.barliquer.threadpool;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@ApplicationScoped
public class BarKeeperThreadPoolManager {
    ExecutorService service;

    @PostConstruct
    void setup() {
        service = Executors.newFixedThreadPool(2);
    }

    @PreDestroy
    void close() {
        service.shutdown();
    }

    public ThreadPoolExecutor getService() {
        return (ThreadPoolExecutor) service;
    }

    public void enqueue(Runnable runnable){
        service.submit(runnable);
    }
}

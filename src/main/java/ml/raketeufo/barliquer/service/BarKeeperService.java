package ml.raketeufo.barliquer.service;

import io.quarkus.vertx.ConsumeEvent;
import lombok.SneakyThrows;
import ml.raketeufo.barliquer.dto.OrderRecord;
import ml.raketeufo.barliquer.threadpool.BarKeeperThreadPoolManager;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class BarKeeperService {
    public static final String EVENT_TOPIC = "orderPlaced";

    @Inject
    Logger logger;

    @Inject
    BarKeeperThreadPoolManager barKeeperThreadPoolManager;

    @SneakyThrows
    @ConsumeEvent(value = EVENT_TOPIC)
    public void onOrderPlaced(OrderRecord orderRecord) {
        barKeeperThreadPoolManager.enqueue(createRunnable(orderRecord));
        logger.infof("Current BackPreassure: %d", barKeeperThreadPoolManager.getService().getQueue().size());
    }

    private Runnable createRunnable(OrderRecord orderRecord) {
        return () -> {
            logger.infof("[%08x] Some order is Heard:  (%s) %s",
                    System.identityHashCode(this),
                    orderRecord.customer(), orderRecord.order());
            try {
                Thread.sleep(30_000);
            } catch (InterruptedException e) {
            }
            logger.infof("[%08x] Some order is Placed: (%s) %s",
                    System.identityHashCode(this),
                    orderRecord.customer(), orderRecord.order());
        };
    }
}

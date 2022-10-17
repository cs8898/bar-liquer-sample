package ml.raketeufo.barliquer.service;

import io.vertx.core.eventbus.EventBus;
import ml.raketeufo.barliquer.dto.OrderRecord;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class CustomerService {
    @Inject
    EventBus eventBus;

    public OrderRecord placeOrder(String orderString) {
        String customerId = "%08x".formatted(System.identityHashCode(this));
        OrderRecord orderRecord = new OrderRecord(customerId, orderString);
        eventBus.publish(BarKeeperService.EVENT_TOPIC, orderRecord);
        return orderRecord;
    }
}

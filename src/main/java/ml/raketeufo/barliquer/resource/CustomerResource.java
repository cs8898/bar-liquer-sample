package ml.raketeufo.barliquer.resource;

import ml.raketeufo.barliquer.dto.NameRecord;
import ml.raketeufo.barliquer.dto.OrderRecord;
import ml.raketeufo.barliquer.service.CustomerService;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/customer")
public class CustomerResource {

    @Inject
    CustomerService customerService;

    @Inject
    Logger logger;

    @POST
    @Path("/placeOrder")
    public OrderRecord placeOrder(NameRecord orderName) {
        logger.infof("Received Order via HTTP");
        OrderRecord record = customerService.placeOrder(orderName.name());
        logger.infof("Order Submitted To Bus");
        return record;
    }
}

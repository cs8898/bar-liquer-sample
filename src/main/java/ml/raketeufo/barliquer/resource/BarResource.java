package ml.raketeufo.barliquer.resource;

import ml.raketeufo.barliquer.dto.NameRecord;
import ml.raketeufo.barliquer.enity.*;
import ml.raketeufo.barliquer.service.*;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/bars")
public class BarResource {
    @Inject
    BarService barService;

    @GET
    public List<Bar> get() {
        return barService.getAll();
    }

    @POST
    public Bar create(NameRecord bar) {
        return barService.create(bar.name());
    }

    @GET
    @Path("/{id}")
    public Bar getSingle(@PathParam("id") UUID id) {
        return barService.findById(id);
    }

    @GET
    @Path("/{id}/liquers")
    public Set<Liquer> getLiquers(@PathParam("id") UUID id) {
        return barService.findById(id).getLiquers();
    }

    @POST
    @Path("/{id}/liquers")
    public Liquer postLiquer(@PathParam("id") UUID id, NameRecord liquerIn) {
        return barService.addLiquerByName(id, liquerIn.name());
    }
}

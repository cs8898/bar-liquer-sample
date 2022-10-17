package ml.raketeufo.barliquer.resource;

import ml.raketeufo.barliquer.dto.NameRecord;
import ml.raketeufo.barliquer.enity.Liquer;
import ml.raketeufo.barliquer.service.LiquerService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/liquers")
public class LiquerResource {

    @Inject
    LiquerService liquerService;

    @GET
    public List<Liquer> getAll() {
        return liquerService.getAll();
    }

    @POST
    public Liquer create(NameRecord liquer) {
        return liquerService.create(liquer.name());
    }

    @GET
    @Path("/{id}")
    public Liquer getSingle(@PathParam("id") Long id) {
        return liquerService.findById(id);
    }
}

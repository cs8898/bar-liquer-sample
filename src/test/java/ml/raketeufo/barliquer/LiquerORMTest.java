package ml.raketeufo.barliquer;

import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import ml.raketeufo.barliquer.enity.Bar;
import ml.raketeufo.barliquer.enity.Liquer;
import ml.raketeufo.barliquer.service.BarService;
import ml.raketeufo.barliquer.service.LiquerService;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.*;

import io.quarkus.test.junit.QuarkusTest;


@QuarkusTest
public class LiquerORMTest {

    @Inject
    Logger logger;
    @Inject
    LiquerService liquerService;

    @Inject
    BarService barService;

    @Inject
    EntityManager em;

    private static final List<String> liquers = List
        .of("Baileys", "Liquer 43", "Kahlúa", "Malibu");
    
    private static final Map<String, List<String>> bars = Map.of(
        "LAGO Bar", List.of("Baileys","Liquer 43"),
        "Amadeus", List.of("Liquer 43","Kahlúa", "Malibu")
    );

    @BeforeEach
    public void init() {
        liquers
            .forEach(l -> liquerService.create(l));

        Map<Bar, List<Liquer>> barLiquerMap = bars.entrySet().stream()
            .map(e ->
                new SimpleEntry<>(barService.create(e.getKey()), e.getValue())
            )
            .collect(Collectors.toMap(
                    SimpleEntry::getKey,
                    e -> e.getValue().stream()
                        .map(l->liquerService.findByName(l))
                        .collect(Collectors.toList())));

        mapBarsAndLiquers(barLiquerMap);
    }

    @Transactional
    public void mapBarsAndLiquers(Map<Bar, List<Liquer>> map) {
        map.forEach( (b, ls) -> {
            b.setLiquers(Set.copyOf(ls));
            //b.setLiquers(ls);
            em.merge(b);
        });
    }

    @Test
    @Transactional
    public void test() {

        logger.info("Searching Amadeus");
        Bar amadeus = barService.findByName("Amadeus");
        Assertions.assertEquals(
                bars.get("Amadeus"),
                amadeus.getLiquers().stream()
                        .map(Liquer::getName)
                        .collect(Collectors.toList()),
                "Liquers of Amadeus");

        logger.info("Searching LAGO Bar");
        Bar lago = barService.findByName("LAGO Bar");
        Assertions.assertEquals(
                bars.get("LAGO Bar"),
                lago.getLiquers().stream()
                        .map(Liquer::getName)
                        .collect(Collectors.toList()),
                "Liquers of LAGO Bar");

        logger.info("Adding Malibu to Lago");
        lago.getLiquers().add(liquerService.findByName("Malibu"));
        em.merge(lago);
    }
}

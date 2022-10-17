package ml.raketeufo.barliquer.service;

import ml.raketeufo.barliquer.enity.Bar;
import ml.raketeufo.barliquer.enity.Liquer;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class BarService {

    @Inject
    EntityManager em;

    @Inject
    LiquerService liquerService;

    public List<Bar> getAll() {
        return em
                .createQuery("SELECT b FROM Bar b", Bar.class)
                .getResultList();
    }

    public Bar findById(UUID uuid) {
        return em
                .createQuery("SELECT b FROM Bar b WHERE b.id = :id", Bar.class)
                .setParameter("id", uuid)
                .getSingleResult();
    }

    public Bar findByName(String name) {
        return em
                .createQuery("SELECT b FROM Bar b WHERE b.name = :name", Bar.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    @Transactional
    public Bar create(String name) {
        Bar l = Bar.builder()
            .name(name)
            .build();
        em.persist(l);
        return l;
    }

    @Transactional
    public Liquer addLiquerByName(UUID id, String name) {
        Bar bar = findById(id);
        Liquer liquer = liquerService.findByName(name);
        bar.getLiquers().add(liquer);
        em.merge(bar);
        return liquer;
    }
}

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
public class LiquerService {

    @Inject
    EntityManager em;

    public List<Liquer> getAll() {
        return em
                .createQuery("SELECT l FROM Liquer l", Liquer.class)
                .getResultList();
    }

    public Liquer findById(Long id) {
        return em
                .createQuery("SELECT l FROM Liquer l WHERE l.id = :id", Liquer.class)
                .setParameter("id", id)
                .getSingleResult();
    }


    public Liquer findByName(String name) {
        return em
                .createQuery("SELECT l FROM Liquer l WHERE l.name = :name", Liquer.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    @Transactional
    public Liquer create(String name) {
        Liquer l = Liquer.builder()
            .name(name)
            .build();
        em.persist(l);
        return l;
    }
}

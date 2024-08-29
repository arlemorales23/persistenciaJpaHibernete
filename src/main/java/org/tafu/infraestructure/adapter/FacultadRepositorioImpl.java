package org.tafu.infraestructure.adapter;

import org.tafu.domain.model.Facultad;
import org.tafu.domain.port.FacultadRepositorio;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

public class FacultadRepositorioImpl implements FacultadRepositorio {
    private final EntityManagerFactory emf;

    public FacultadRepositorioImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Facultad guardar(Facultad facultad) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            if (facultad.getId() == null) {
                em.persist(facultad);
            } else {
                facultad = em.merge(facultad);
            }
            em.getTransaction().commit();
            return facultad;
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<Facultad> buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return Optional.ofNullable(em.find(Facultad.class, id));
        } finally {
            em.close();
        }
    }

    @Override
    public List<Facultad> buscarTodas() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT f FROM Facultad f", Facultad.class).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public void eliminar(Facultad facultad) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            if (!em.contains(facultad)) {
                facultad = em.merge(facultad);
            }
            em.remove(facultad);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}


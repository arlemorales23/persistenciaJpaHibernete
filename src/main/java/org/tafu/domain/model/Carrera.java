package org.tafu.domain.model;

import javax.persistence.*;

@Entity
public class Carrera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facultad_id")
    private Facultad facultad;

    // Constructores, getters y setters
    public Carrera() {}

    public Carrera(String nombre) {
        this.nombre = nombre;
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Facultad getFacultad() {
        return facultad;
    }

    public void setFacultad(Facultad facultad) {
        this.facultad = facultad;
    }
}

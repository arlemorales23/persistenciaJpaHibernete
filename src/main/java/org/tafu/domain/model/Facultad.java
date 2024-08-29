package org.tafu.domain.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Facultad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;

    @OneToMany(mappedBy = "facultad", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Carrera> carreras = new ArrayList<>();

    public Facultad(Long id, String nombre, List<Carrera> carreras) {
        this.id = id;
        this.nombre = nombre;
        this.carreras = carreras;
    }

    public Facultad() {
    }

    public Facultad(String nombre) {
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

    public List<Carrera> getCarreras() {
        return carreras;
    }

    public void setCarreras(List<Carrera> carreras) {
        this.carreras = carreras;
    }

    public void agregarCarrera(Carrera carrera) {
        carreras.add(carrera);
        carrera.setFacultad(this);
    }

    public void removerCarrera(Carrera carrera) {
        carreras.remove(carrera);
        carrera.setFacultad(null);
    }
}
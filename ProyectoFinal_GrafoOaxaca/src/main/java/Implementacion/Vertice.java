package Implementacion;

import java.util.Objects;

public class Vertice {

    private String nombre;
    private ColorVertice color;
    private Vertice anterior;
    private double distancia;
    private double llave;

    public Vertice(String nombre) {
        this.nombre = nombre;
        this.color = ColorVertice.BLANCO;
        this.anterior = null;
        this.distancia = Double.MAX_VALUE;
        this.llave = Double.MAX_VALUE;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ColorVertice getColor() {
        return color;
    }

    public void setColor(ColorVertice color) {
        this.color = color;
    }

    public Vertice getAnterior() {
        return anterior;
    }

    public void setAnterior(Vertice anterior) {
        this.anterior = anterior;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public double getLlave() {
        return llave;
    }

    public void setLlave(double llave) {
        this.llave = llave;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vertice)) {
            return false;
        }
        Vertice v = (Vertice) o;
        return nombre.equals(v.nombre);
    }

    @Override
    public int hashCode() {
        return nombre.hashCode();
    }

}

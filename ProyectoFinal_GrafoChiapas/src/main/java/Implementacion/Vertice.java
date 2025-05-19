package Implementacion;

public class Vertice {                                      //  T(N) 34   O(1)

    private String nombre;
    private ColorVertice color;
    private Vertice anterior;
    private double distancia;
    private double llave;

    public Vertice(String nombre) {
        this.nombre = nombre;               //1
        this.color = ColorVertice.BLANCO;   //2
        this.anterior = null;       //1
        this.distancia = Double.MAX_VALUE;  //2
        this.llave = Double.MAX_VALUE;  //2
    }

    public String getNombre() {
        return nombre;       //1
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;       //2
    }

    public ColorVertice getColor() {
        return color;           //1
    }

    public void setColor(ColorVertice color) {
        this.color = color;     //2
    }

    public Vertice getAnterior() {
        return anterior;        //1
    }

    public void setAnterior(Vertice anterior) {
        this.anterior = anterior;       //2
    }

    public double getDistancia() {
        return distancia;       //1 
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;     //2
    }

    public double getLlave() {
        return llave;       //1
    }

    public void setLlave(double llave) {
        this.llave = llave;     //2
    }



    @Override
    public boolean equals(Object o) {       // 9
        if (this == o) {    //1
            return true;    //1
        }
        if (!(o instanceof Vertice)) {  //1
            return false;       //1
        }
        
        Vertice v = (Vertice) o; //2
        return nombre.equals(v.nombre); //3
    }

    @Override
    public int hashCode() {
        return nombre.hashCode();       //2
    }

}

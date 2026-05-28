package es.ugr.isiproject.findhome.model;

public class PaisPuntuado implements Comparable<PaisPuntuado> {
    private String iso3;
    private double puntuacion;

    public PaisPuntuado(String iso3, double puntuacion) {
        this.iso3 = iso3;
        this.puntuacion = puntuacion;
    }
    // getters
    public String getIso3() { return iso3; }
    public double puntuacion() { return puntuacion; }


    @Override
    public int compareTo(PaisPuntuado other) {
        // Orden descendente (mayor puntuación primero)
        return Double.compare(other.puntuacion, this.puntuacion);
    }
}
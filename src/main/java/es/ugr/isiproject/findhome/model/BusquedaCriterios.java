package es.ugr.isiproject.findhome.model;

public class BusquedaCriterios {

  private String habitantes;      // 'cualquier', 'grande', etc.
  private String costeVida;       // 'cualquier', 'bajo', etc.
  private String calidadVida;     // 'no importa', 'alta', etc.
  private String tasaIngresos;
  private String tasaEmpleo;

  // getters y setters...

  public String getHabitantes() { return habitantes; }
  public void setHabitantes(String habitantes) { this.habitantes = habitantes; }
  public String getCosteVida() { return costeVida; }
  public void setCosteVida(String costeVida) { this.costeVida = costeVida; }
  public String getCalidadVida() { return calidadVida; }
  public void setCalidadVida(String calidadVida) { this.calidadVida = calidadVida; }
  public String getTasaIngresos() { return tasaIngresos; }
  public void setTasaIngresos(String tasaIngresos) { this.tasaIngresos = tasaIngresos; }
  public String getTasaEmpleo() { return tasaEmpleo; }
  public void setTasaEmpleo(String tasaEmpleo) { this.tasaEmpleo = tasaEmpleo; }
}
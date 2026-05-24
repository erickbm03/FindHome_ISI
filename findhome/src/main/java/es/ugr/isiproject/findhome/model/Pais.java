package es.ugr.isiproject.findhome.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "paises")
public class Pais {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String nombre;

  @Column(name= "iso3",nullable=false, unique=true, length=3)
  private String iso3;

  private String bandera;
  private String habitantes;

  @Column(name = "coste_vida")
  private String costeVida;

  @Column(name = "transporte_estrellas")
  private Integer transporteEstrellas;

  @Column(name = "calidad_vida_estrellas")
  private Integer calidadVidaEstrellas;

  @Column(name = "ingresos_medios")
  private String ingresosMedios;

  @Column(name = "tasa_empleo")
  private BigDecimal tasaEmpleo;

  // getters:
  public Integer getId() { return id; }
  public String getNombre() { return nombre; }
  public String getIso3() { return iso3; }
  public String getBandera() { return bandera; }
  public String getHabitantes() { return habitantes; }
  public String getCosteVida() { return costeVida; }
  public Integer getTransporteEstrellas() { return transporteEstrellas; }
  public Integer getCalidadVidaEstrellas() { return calidadVidaEstrellas; }
  public String getIngresosMedios() { return ingresosMedios; }
  public BigDecimal getTasaEmpleo() { return tasaEmpleo; }

  //setters:
  public void setNombre(String nombre){ this.nombre= nombre; }
  public void setIso3(String iso3){ this.iso3= iso3; }
  public void setBandera(String bandera){ this.bandera= bandera; }
  public void setHabitantes(String habitantes){ this.habitantes= habitantes; }
  public void setCosteVida(String costeVida){ this.costeVida= costeVida; }
  public void setTransporteEstrellas(Integer transporteEstrellas){ this.transporteEstrellas= transporteEstrellas; }
  public void setCalidadVidaEstrellas(Integer calidadVidaEstrellas){ this.calidadVidaEstrellas= calidadVidaEstrellas; }
  public void setIngresosMedios(String ingresosMedios){ this.ingresosMedios= ingresosMedios; }
  public void setTasaEmpleo(BigDecimal tasaEmpleo){ this.tasaEmpleo= tasaEmpleo; }


}
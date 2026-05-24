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
  public String getBandera() { return bandera; }
  public String getHabitantes() { return habitantes; }
  public String getCosteVida() { return costeVida; }
  public Integer getTransporteEstrellas() { return transporteEstrellas; }
  public Integer getCalidadVidaEstrellas() { return calidadVidaEstrellas; }
  public String getIngresosMedios() { return ingresosMedios; }
  public BigDecimal getTasaEmpleo() { return tasaEmpleo; }


}
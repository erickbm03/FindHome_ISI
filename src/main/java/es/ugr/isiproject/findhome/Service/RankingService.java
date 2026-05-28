package es.ugr.isiproject.findhome.service;

import org.springframework.stereotype.Service;
import java.util.*;

import es.ugr.isiproject.findhome.model.Pais;
import es.ugr.isiproject.findhome.model.PaisPuntuado;
import es.ugr.isiproject.findhome.model.BusquedaCriterios;
import es.ugr.isiproject.findhome.repository.PaisRepository;

@Service
public class RankingService {

    private final PaisRepository paisRepository;

    public RankingService(PaisRepository paisRepository) {
        this.paisRepository = paisRepository;
    }

    public List<PaisPuntuado> calcularRanking(BusquedaCriterios criterios) {
        List<Pais> paises = paisRepository.findAll();
        TreeSet<PaisPuntuado> ranking = new TreeSet<>();

        for (Pais pais : paises) {
            double puntuacion = 0.0;
            double valorpuntuacion = 50.0;

            if (!criterios.getHabitantes().equalsIgnoreCase("Empty")) {
                int M40 = 40000000;
                int M5 = 5000000;
                int habitantes = 0;

                if(pais.getHabitantes() != null){
                    try {
                        habitantes = Integer.parseInt(pais.getHabitantes());
                    } catch (NumberFormatException e) {
                        System.out.println("No se pudo parsear habitantes a int");
                    }
                }


                if (criterios.getHabitantes().equals("poca")) {
                  if(habitantes > M40) { puntuacion -= valorpuntuacion; }
                  else if(habitantes > M5)  { puntuacion -= valorpuntuacion/5; }
                  else { puntuacion += valorpuntuacion/10; }
                }
                if (criterios.getHabitantes().equals("mucha")) {
                  if(habitantes < M5) { puntuacion -= valorpuntuacion; }
                  else if(habitantes < M40)  { puntuacion -= valorpuntuacion/5; }
                  else { puntuacion += valorpuntuacion/10; }
                }
            }

            if (!criterios.getCosteVida().equalsIgnoreCase("Empty")) {
                if (criterios.getCosteVida().equals("bajo") && pais.getCosteVida() != null) {
                    puntuacion -= pais.getCosteVida();
                }
                if (criterios.getCosteVida().equals("alto") && pais.getCosteVida() != null) {
                    puntuacion += pais.getCosteVida();
                }
            }

            if (!criterios.getCalidadVida().equalsIgnoreCase("Empty") && pais.getCalidadVidaEstrellas() != null) {
                int estrellas = pais.getCalidadVidaEstrellas();
                puntuacion += estrellas * 10;
            }


            if (!criterios.getTasaIngresos().equalsIgnoreCase("Empty")) {
                if (criterios.getTasaIngresos().equals("bajo") && pais.getIngresosMedios() != null) {
                    puntuacion -= pais.getIngresosMedios()/500;
                }
                if (criterios.getTasaIngresos().equals("alto") && pais.getIngresosMedios() != null) {
                    puntuacion += pais.getIngresosMedios()/500;
                }
            }

            if (!criterios.getTasaEmpleo().equalsIgnoreCase("Empty")) {
                if (criterios.getTasaEmpleo().equals("bajo") && pais.getTasaEmpleo() != null) {
                    puntuacion -= pais.getTasaEmpleo();
                }
                if (criterios.getTasaEmpleo().equals("alto") && pais.getTasaEmpleo() != null) {
                    puntuacion += pais.getTasaEmpleo();
                }
            }

            ranking.add(new PaisPuntuado(pais.getIso3(), puntuacion));
        }
        // Lo conviertes a lista si lo prefieres
        return new ArrayList<>(ranking);
    }
}
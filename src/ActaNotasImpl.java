import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Function;

import aed.actanotas.ActaNotas;
import aed.actanotas.Calificacion;
import java.util.function.Function;
import java.util.Comparator;
import es.upm.aedlib.Pair;
import es.upm.aedlib.indexedlist.IndexedList;


public class ActaNotasImpl implements ActaNotas{

    private IndexedList<Calificacion> calificaciones;
    private String asignatura;
    private double notaMinimaAprobado;
    private int anyo;
    private boolean esConvocatoriaExtraordinaria;

    public ActaNotasImpl(String asignatura, double notaMinimaAprobado,
                        int anyo, boolean esConvocatoriaExtraordinaria){
        this.calificaciones = new IndexedList<Calificacion>();
        this.asignatura = asignatura;
        this.notaMinimaAprobado = notaMinimaAprobado;
        this.anyo = anyo;
        this.esConvocatoriaExtraordinaria = esConvocatoriaExtraordinaria;
    }

    public ActaNotas addCalificacion(String nombre, String matricula, String grupo, double nota){
        calificaciones.add(calificaciones.size(), new Calificacion(nombre, matricula, grupo, nota));
    }

    public Calificacion getCalificacion(String matricula){
        return calificaciones.get(calificacion.indexOf(matricula)).getCalificacion();
    }

    public ActaNotas updateCalificacion(Calificacion calificacion){
        //hacer
    }

    public ActaNotas deleteCalificacion(String matricula){
        //hacer
    }

    public double notaMedia(){
        int nota = 0;
        int i;
        for (i = 0; i < calificaciones.size(); i++){
            nota += calificaciones.get(i).nota(); 
        }
        return nota/i;
    }

    public boolean equals(Object obj){
        for (int i = 0; i < calificaciones.size(); i++){
            
        }
    }

    public String toString(){
        //hacer
    }

    public IndexedList<Pair<String, Integer>> alumnosPorGrupo(){
        //hacer
    }

    public IndexedList<Calificacion>
    getCalificaciones(Function<Calificacion,Boolean> filter,
                      Comparator<Calificacion> cmp){
        //hacer    
    }
}

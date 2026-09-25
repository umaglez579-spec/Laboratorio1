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

    public ActaNotasImpl(String asignatura, double notaMinimaAprobado,
                        int anyo, boolean esConvocatoriaExtraordinaria){
        calificaciones = new IndexedList<Calificacion>();
        asignatura = calificaciones.asignatura();
        notaMinimaAprobado = calificaciones.minNotaAprobado();
        anyo = calificaciones.anyo();
        esConvocatoriaExtraordinaria = calificaciones.esConvocatoriaExtraordinaria();
    }

    public ActaNotas addCalificacion(String nombre, String matricula, String grupo, double nota){
        calificaciones.add(calificaciones.size(), new Calificacion(nombre, matricula, grupo, nota));
    }

    public Calificacion getCalificacion(String matricula){
        return calificaciones.get(calificacion.indexOf(matricula)).getCalificacion();
    }

    public ActaNotas updateCalificacion(Calificacion calificacion){
        
    }

    public ActaNotas deleteCalificacion(String matricula){
        //hacer
    }

    public double notaMedia(){
        //hacer
    }

    public boolean equals(Object obj){
        //hacer
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
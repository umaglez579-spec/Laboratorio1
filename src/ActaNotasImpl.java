package aed.actanotas;

import java.util.Comparator;
import java.util.function.Function;
import es.upm.aedlib.Pair;
import es.upm.aedlib.indexedlist.*;


public class ActaNotasImpl implements ActaNotas{

	//atributos
    private IndexedList<Calificacion> calificaciones;
    private String asignatura;
    private double notaMinimaAprobado;
    private int año;
    private boolean julio; //esConvocatoriaExtraordinaria

	//constructor
    public ActaNotasImpl(String asignatura, double notaMinimaAprobado,
                        int anyo, boolean esConvocatoriaExtraordinaria){
        this.calificaciones = new ArrayIndexedList<Calificacion>();
        this.asignatura = asignatura;
        this.notaMinimaAprobado = notaMinimaAprobado;
        this.año = anyo;
        this.julio = esConvocatoriaExtraordinaria;
    }

	//funciones que devuelven los atributos
	@Override
	public String asignatura() {
		return this.asignatura;
	}

	@Override
	public int anyo() {
		return this.año ;
	}

	@Override
	public boolean esConvocatoriaExtraordinaria() {
		return this.julio;
	}

	@Override
	public double minNotaAprobado() {
		return this.notaMinimaAprobado;
	}

	//si el objeto es null lanza la excepción
    private void comprobarNull(Object objeto) {
	    if (objeto == null) {
	        throw new IllegalArgumentException();
	    }
	}
	
	@Override
	public ActaNotas addCalificacion(String nombre, String matricula, String grupo, double nota) {	
		if(getCalificacion(matricula) != null) { 
			throw new IllegalStateException();
		}
		
		calificaciones.add(calificaciones.size(),new Calificacion(nombre, matricula, grupo, nota));
		return this;
	}

	@Override
	public Calificacion getCalificacion(String matricula) {
		comprobarNull(matricula); 
		int a;
		//bucle que recorre la lista entera o para cuando encuentre la matrícula que busco
		for(a = 0; a < calificaciones.size() && !(calificaciones.get(a).matricula().equals(matricula)); a++);
		if(a < calificaciones.size()) { //si sale antes del bucle será porque la hemos encontrado
			return calificaciones.get(a); 
		}
		return null;
	}

	@Override
	public ActaNotas updateCalificacion(Calificacion calificacion) {
		comprobarNull(calificacion); 
		int a;
		//bucle que recorre la lista entera o sale cuando hay una matrícula en calificación que no existe en .this
		for (a = 0; a < calificaciones.size() && !calificaciones.get(a).matricula().equals(calificacion.matricula()); a++);
		    if (a == calificaciones.size()) { //salta excepción si no se encuentra la matrícula que necesito
		        throw new IllegalStateException();
		    }
		    calificaciones.set(a, calificacion); //añadir la nueva calificación en el índice a de .this
		    return this;
	}

	@Override
	public ActaNotas deleteCalificacion(String matricula) {
		Calificacion aBorrar = getCalificacion(matricula); 
		if(aBorrar == null) { //si no existe getCalificacion(matricula), aBorrar será null
			throw new IllegalStateException() ;
		}
		calificaciones.remove(aBorrar);
		return this;
	}

	@Override
	public double notaMedia() {
	    if (calificaciones.size() == 0) {
	        throw new IllegalStateException();
	    }
	    double suma = 0;
	    for (int i=0; i < calificaciones.size(); i++) {
	        suma += calificaciones.get(i).nota(); //la suma de todas las notas se almacena en suma
	    }

	    return suma/calificaciones.size();
	}

    @Override
	public boolean equals(Object obj) {
	    if (!(obj.getClass() == this.getClass())) { //false si los objetos no son de la misma clase
	        return false;
	    }
	    ActaNotasImpl otra = (ActaNotasImpl) obj; //conversión explícita de Object a ActaNotasImpl y almacenado en  otra
	    return (this.asignatura.equals(otra.asignatura) && this.año == otra.año && this.julio == otra.julio); //comparar todos sus atributos
	}
	
	@Override
	public String toString() {
		String devolver = "ActaNotas(" + "asignatura=" + this.asignatura + ", año=" + this.año + ", convocatoria=";
		if(julio) { 
			devolver += "extraordinaria)";
		} else {
			devolver += "ordinaria)";
		}
	    return devolver;
	}

    public IndexedList<Pair<String, Integer>> alumnosPorGrupo() {
		IndexedList<Pair<String, Integer>> pares = new ArrayIndexedList<Pair<String, Integer>>();
		Pair<String,Integer> par;
		String grupo;
		int j;
		for(int i = 0; i < calificaciones.size(); i++) {
			grupo = calificaciones.get(i).grupo; //extraigo el dato grupo de calificaciones.get(i) y lo guardo en una variable
			for(j = 0; j < pares.size() && (!(pares.get(j).left().equals(grupo))); j++); //j++ hasta que el valor izquierdo sea igual a grupo
			if (j < pares.size()){ //si un .left() es igual a grupo
				par = pares.get(j); //guardar el par en el índice j en una variable
				pares.set(j, new Pair<String,Integer>(par.left(), par.right() + 1)); //guardo en la posición j el grupo y número de alumnos+1 a la derecha
			} else {
				pares.add(pares.size(), new Pair<String,Integer>(grupo,1)); //añadir el nuevo valor al final
			}
		}
		return pares; //devolver pares
	}

	private Comparator<Calificacion> compararMatricula(){ //compara las matrículas de c1 y c2
		return (c1,c2) -> c1.matricula().compareTo(c2.matricula()); 
	}

    @Override
	public IndexedList<Calificacion> getCalificaciones(Function<Calificacion, Boolean> filter,
			Comparator<Calificacion> cmp) {
		IndexedList<Calificacion> ordenado = new ArrayIndexedList<Calificacion>();
		Calificacion compruebo;
		Calificacion aux;
		for (int i = 0; i < calificaciones.size(); i++) {
			compruebo = calificaciones.get(i); //variable que será comparada por cada iteración de i
			if (filter == null || filter.apply(compruebo)) { //si no hay filtro o la variable lo cumple
				ordenado.add(ordenado.size(), compruebo); //añadir la variable al final de ordenado
			}
		}
		if(cmp == null) { //si no hay comparador
			cmp = compararMatricula(); //el comparador será compararMatricula
		}
		//comparar ordenado.get(i) con los demás elementos de la lista
		for (int i = 0; i < ordenado.size() - 1; i++) {
			for (int j = i + 1; j < ordenado.size(); j++) {
				//si el anterior es mayor que el siguiente, intercambiar su orden
				if (cmp.compare(ordenado.get(i), ordenado.get(j)) > 0) { 
					aux = ordenado.get(i);
					ordenado.set(i, ordenado.get(j));
					ordenado.set(j, aux);
				}
			}
		}
		return ordenado; //devolver la lista filtrada y ordenada
	}
}

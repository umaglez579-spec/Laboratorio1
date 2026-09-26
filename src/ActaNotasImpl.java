package aed.actanotas;

import java.util.Comparator;
import java.util.function.Function;
import es.upm.aedlib.Pair;
import es.upm.aedlib.indexedlist.*;
import aed.actanotas.*;

public class ActaNotasImpl implements ActaNotas{
	private String asignatura;
	private double notaMin;
	private int año;
	private boolean julio;
	private IndexedList<Calificacion> calificaciones;

	public ActaNotasImpl(String asignatura, double notaMin, int año, boolean julio) {
		this.asignatura=asignatura;
		this.notaMin=notaMin;
		this.año=año;
		this.julio=julio;
		this.calificaciones= new ArrayIndexedList<Calificacion>();
	}

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
		return this.notaMin;
	}

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
		for(a=0;a<calificaciones.size()&&!(calificaciones.get(a).matricula().equals(matricula));a++);
		if(a<calificaciones.size()) {
			return calificaciones.get(a);
		}
		return null;
	}

	@Override
	public ActaNotas updateCalificacion(Calificacion calificacion) {
		comprobarNull(calificacion);
		int a;
		for (a = 0;a < calificaciones.size() && !calificaciones.get(a).matricula().equals(calificacion.matricula());a++);
		if (a == calificaciones.size()) {
			throw new IllegalStateException();
		}
		calificaciones.set(a, calificacion);
		return this;
	}

	@Override
	public ActaNotas deleteCalificacion(String matricula) {
		Calificacion aBorrar=getCalificacion(matricula);
		if(aBorrar==null) {
			throw new IllegalStateException() ;
		}
		calificaciones.remove(aBorrar);
		return this;
	}

	@Override
	public double notaMedia() {
		if (calificaciones.size()==0) {
			throw new IllegalStateException();
		}
		double suma=0;
		for (int i=0;i<calificaciones.size();i++) {
			suma+=calificaciones.get(i).nota();
		}

		return suma/calificaciones.size();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj.getClass()==this.getClass())) {
			return false;
		}
		ActaNotasImpl otra=(ActaNotasImpl) obj;
		return (this.asignatura.equals(otra.asignatura) &&this.año==otra.año && this.julio==otra.julio);
	}

	@Override
	public String toString() {
		String devolver="ActaNotas(" + "asignatura=" + this.asignatura + ", año=" + this.año + ", convocatoria=";
		if(julio) {
			devolver+= "estraordinaria)";
		}else {
			devolver+= "ordinaria)";
		}
		return devolver;
	}

	@Override
	public IndexedList<Pair<String, Integer>> alumnosPorGrupo() {
		IndexedList<Pair<String, Integer>> pares=new ArrayIndexedList<Pair<String,Integer>>();
		Pair<String,Integer> par;
		String grupo;
		int j;
		for(int i=0;i<calificaciones.size();i++) {
			grupo=calificaciones.get(i).grupo;
			for(j=0;j<pares.size()&&(!(pares.get(j).left().equals(grupo)));j++);
			if (j<pares.size()){
				par=pares.get(j);
				pares.set(j, new Pair<String,Integer>(par.left(),par.right()+1));		
			}else {
				pares.add(pares.size(), new Pair<String,Integer>(grupo,1));
			}
		}
		return pares;
	}

	private Comparator<Calificacion> compararMatricula(){
		return (c1,c2)->c1.matricula().compareTo(c2.matricula());
	}


	@Override
	public IndexedList<Calificacion> getCalificaciones(Function<Calificacion, Boolean> filter,
			Comparator<Calificacion> cmp) {
		IndexedList<Calificacion> ordenado=new ArrayIndexedList<Calificacion>();
		Calificacion compruebo;
		Calificacion aux;
		for (int i=0;i<calificaciones.size();i++) {

			compruebo=calificaciones.get(i);

			if (filter==null||filter.apply(compruebo)) {
				ordenado.add(ordenado.size(),compruebo);
			}
		}
		if(cmp==null) {
			cmp=compararMatricula();
		}
		for (int i=0;i< ordenado.size()-1;i++) {
			for (int j=i+1; j<ordenado.size(); j++) {
				if (cmp.compare(ordenado.get(i), ordenado.get(j)) > 0) {
					aux = ordenado.get(i);
					ordenado.set(i, ordenado.get(j));
					ordenado.set(j, aux);
				}
			}
		}
		return ordenado;
	}

}

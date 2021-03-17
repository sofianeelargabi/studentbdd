package fr.formation.afpa.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicLong;

public class Etudiant implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idProperty;
	private String nomProperty;
	private String prenomProperty;
	private String dateProperty;
	private String file;
    public static AtomicLong NEXT_ID = new AtomicLong(1);
	private HashMap<Integer, LinkedList<Double>> math;
	private HashMap<Integer, LinkedList<Double>> francais;
	private HashMap<Integer, LinkedList<Double>> anglais;
	private HashMap<Integer, LinkedList<Double>> histoire;
	public static boolean  incrementId = false;
	public Etudiant() {

	}

	public Etudiant(String nomProperty, String prenomProperty, String dateProperty, String file) {
		this.idProperty = NEXT_ID.getAndIncrement();
		this.nomProperty = nomProperty;
		this.prenomProperty = prenomProperty;
		this.dateProperty = dateProperty;
		this.file = file;
		
	}
	public Etudiant(String nomProperty, String prenomProperty, String dateProperty, String file,HashMap<Integer, LinkedList<Double>> math,HashMap<Integer, LinkedList<Double>> francais,HashMap<Integer, LinkedList<Double>> anglais,HashMap<Integer, LinkedList<Double>> histoire) {
		this.idProperty = NEXT_ID.getAndIncrement();
		this.nomProperty = nomProperty;
		this.prenomProperty = prenomProperty;
		this.dateProperty = dateProperty;
		this.file = file;
		this.math=math;
		this.francais=francais;
		this.anglais=anglais;
		this.histoire=histoire;
	}

	@Override
	public String toString() {
		return "Etudiant [idProperty=" + idProperty + ", nomProperty=" + nomProperty + ", prenomProperty="
				+ prenomProperty + ", dateProperty=" + dateProperty + ", file=" + file + ", math=" + math
				+ ", francais=" + francais + ", anglais=" + anglais + ", histoire=" + histoire + "]";
	}

	public Long getIdProperty() {
		return idProperty;
	}

	public void setIdProperty(Long idProperty) {
		this.idProperty = idProperty;
	}

	public String getNomProperty() {
		return nomProperty;
	}

	public void setNomProperty(String nomProperty) {
		this.nomProperty = nomProperty;
	}

	public String getPrenomProperty() {
		return prenomProperty;
	}

	public void setPrenomProperty(String prenomProperty) {
		this.prenomProperty = prenomProperty;
	}

	public String getDateProperty() {
		return dateProperty;
	}

	public void setDateProperty(String dateProperty) {
		this.dateProperty = dateProperty;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public static AtomicLong getNEXT_ID() {
		return NEXT_ID;
	}

	public static void setNEXT_ID(AtomicLong nEXT_ID) {
		NEXT_ID = nEXT_ID;
	}

	public HashMap<Integer, LinkedList<Double>> getMath() {
		return math;
	}

	public void setMath(HashMap<Integer, LinkedList<Double>> math) {
		this.math = math;
	}

	public HashMap<Integer, LinkedList<Double>> getFrancais() {
		return francais;
	}

	public void setFrancais(HashMap<Integer, LinkedList<Double>> francais) {
		this.francais = francais;
	}

	public HashMap<Integer, LinkedList<Double>> getAnglais() {
		return anglais;
	}

	public void setAnglais(HashMap<Integer, LinkedList<Double>> anglais) {
		this.anglais = anglais;
	}

	public HashMap<Integer, LinkedList<Double>> getHistoire() {
		return histoire;
	}

	public void setHistoire(HashMap<Integer, LinkedList<Double>> histoire) {
		this.histoire = histoire;
	}

	



}

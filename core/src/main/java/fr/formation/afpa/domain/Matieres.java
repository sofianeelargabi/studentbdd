package fr.formation.afpa.domain;

import java.io.Serializable;

public class Matieres implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String matiere;

	
	
	public Matieres(String matiere) {
	  this.matiere=matiere;

	}



	public String getMatiere() {
		return matiere;
	}



	public void setMatiere(String matiere) {
		this.matiere = matiere;
	}


	
	
}

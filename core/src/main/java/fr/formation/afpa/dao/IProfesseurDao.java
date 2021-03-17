package fr.formation.afpa.dao;

import fr.formation.afpa.domain.Professeur;

public interface IProfesseurDao {

	boolean comparePass(String pass1, String pass2);

	String Sha256(String s) throws Exception;

	void inscription(Professeur prof);

	boolean validation(Professeur p);

}

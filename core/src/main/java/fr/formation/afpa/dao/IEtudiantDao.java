package fr.formation.afpa.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import fr.formation.afpa.domain.Etudiant;



public interface IEtudiantDao extends IGenericDao<Etudiant, Integer>{
	
	
	public void add(Etudiant e) throws SQLException;
	
	public Etudiant update(Etudiant e) throws SQLException;
	
	public Etudiant delete(Etudiant e, long index) throws SQLException ;
	
	public void add();
	
	public static List<Etudiant> getListeEtudiant() throws SQLException {
		return null;
	}
	
	public void addNote(Etudiant e, double d, int matiere, Date date) throws SQLException;
	
	public static List<Double> getNote(Etudiant e,int matiere) throws SQLException {
		return null;
	}

	
}

package fr.formation.afpa.service;

import java.sql.SQLException;
import java.util.List;

import fr.formation.afpa.domain.Etudiant;



public interface IEtudiantService {
	
	public List<Etudiant> listEtudiant();
	
	public void ajouterEtudiant(Etudiant e) throws SQLException;
	
	public Etudiant modifierEtudiant(Etudiant e) throws SQLException;
	
	public Etudiant supprimerEtudiant(Etudiant e, long index) throws SQLException ;

}

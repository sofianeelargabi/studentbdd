package fr.formation.afpa.service;

import java.sql.SQLException;
import java.util.List;

import fr.formation.afpa.dao.EtudiantDaoJDBC;
import fr.formation.afpa.dao.IEtudiantDao;
import fr.formation.afpa.domain.Etudiant;

public class EtudiantService implements IEtudiantService {
	
	private IEtudiantDao dao = new EtudiantDaoJDBC();

	public List<Etudiant> listEtudiant() {
		return dao.getAll();
	}

	public void ajouterEtudiant(Etudiant e) throws SQLException {
		dao.add(e);
	}

	public Etudiant modifierEtudiant(Etudiant e) throws SQLException {
		return dao.update(e);
	}
	public Etudiant supprimerEtudiant(Etudiant e, long index) throws SQLException {
		return dao.delete(e, index);
	}
	
	
	
}

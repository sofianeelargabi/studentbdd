package fr.formation.afpa.dao;

import java.sql.SQLException;
import java.util.List;

import fr.formation.afpa.domain.Etudiant;
import junit.framework.TestCase;

public class EtudiantDaoJDBCTest extends TestCase {

	
	
	public void testGetAll() throws SQLException {
		EtudiantDaoJDBC dao  = new EtudiantDaoJDBC();
		List<Etudiant> list = EtudiantDaoJDBC.getListeEtudiant();
		assertNotNull(list);
		assertEquals(list.size(), 4);
	}

	public void testAddEtudiant() {
		
	}

	public void testUpdate() throws SQLException {
		EtudiantDaoJDBC dao  = new EtudiantDaoJDBC();
		Etudiant etu = new Etudiant ("","","","");
		dao.update(etu);
	}

	public void testDelete() {
	}

	public void testGetListeEtudiant() {
	}

}

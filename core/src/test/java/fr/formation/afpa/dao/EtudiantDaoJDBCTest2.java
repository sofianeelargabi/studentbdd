package fr.formation.afpa.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import fr.formation.afpa.domain.Etudiant;

public class EtudiantDaoJDBCTest2 {

	@Test
	public void testAddEtudiant() {
	}

	@Test
	public void testUpdate() {
	}

	@Test
	public void testDeleteEtudiantLong() {
	}

	@Test
	public void testGetListeEtudiant() throws SQLException {
		EtudiantDaoJDBC dao = new EtudiantDaoJDBC();
		List<Etudiant> list = dao.getListeEtudiant();
		assertNotNull(list);
		assertEquals(list.size(), 3);
		
	}

}

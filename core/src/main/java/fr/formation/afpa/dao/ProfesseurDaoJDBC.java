package fr.formation.afpa.dao;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.formation.afpa.domain.Professeur;
import fr.formation.afpa.utils.MysqlConnUtils;

public class ProfesseurDaoJDBC implements IProfesseurDao{
	
	private static final Log log = LogFactory.getLog(ProfesseurDaoJDBC.class);
	
	@Override
	public boolean validation(Professeur p) {
		boolean verif = false;

		Connection connection = null;
		try {
			connection = MysqlConnUtils.getMysqlConnection();
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
		PreparedStatement ps;
		try {
			ps = connection.prepareStatement("select * from professeur where login=? AND mdp= ?");
			ps.setString(1, p.getLogin());
			ps.setString(2, p.getMdp());

			ResultSet rs = ps.executeQuery();
			verif = rs.next();
			{

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return verif;
	}
	@Override
	public void inscription(Professeur prof) {

		Connection connection = null;
		try {
			connection = MysqlConnUtils.getMysqlConnection();
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
		PreparedStatement ps;
		try {
			ps = connection
					.prepareStatement("insert into professeur (nom,prenom,dateNaissance,login,mdp) values (?,?,?,?,?)");
			if (!prof.getNom().equals("") && !prof.getPrenom().equals("") && !prof.getDateNaiss().equals("")
					&& !prof.getLogin().equals("") && !prof.getMdp().equals("")) {
			
			//	ps.setLong(1, prof.getId());
				ps.setString(1, prof.getNom());
				ps.setString(2, prof.getPrenom());
				ps.setString(3, prof.getDateNaiss());
				ps.setString(4, prof.getLogin());
				ps.setString(5, prof.getMdp());

				ps.executeUpdate();

				ps.close();
			} else {
				log.error("pas de chance");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public boolean comparePass(String pass1, String pass2) {
		if (pass1.equals(pass2)) {
			return true;
		} else {
			return false;
		}		
	}
	@Override
	public String Sha256(String s) throws Exception {

		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(s.getBytes());
		byte byteData[] = md.digest();

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}
}

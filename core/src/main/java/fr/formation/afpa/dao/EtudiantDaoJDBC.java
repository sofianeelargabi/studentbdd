package fr.formation.afpa.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.formation.afpa.domain.Etudiant;
import fr.formation.afpa.utils.MysqlConnUtils;



public class EtudiantDaoJDBC implements IEtudiantDao {

	public static List<Etudiant> listeEtudiant = new ArrayList<Etudiant>();
	// static HashMap <Matieres,LinkedList<Notes>> list = new HashMap
	private static final Log log = LogFactory.getLog(EtudiantDaoJDBC.class);

	public List<Etudiant> getAll() {

		return listeEtudiant;
	}

	public void add(Etudiant e) throws SQLException {

		try(Connection con = MysqlConnUtils.getMysqlConnection()) {
			

			String req = MysqlConnUtils.INSERT_INTO_ETUDIANT;

			PreparedStatement pstm = con.prepareStatement(req);
			pstm.setLong(1, e.getIdProperty());
			pstm.setString(2, e.getNomProperty());
			pstm.setString(3, e.getPrenomProperty());
			pstm.setString(4, e.getDateProperty());
			pstm.setString(5, e.getFile());

			pstm.executeUpdate();
			log.info(("successfully inserted"));
		} catch (ClassNotFoundException | SQLException ex) {
			log.error((ex.getMessage()));
		} 

	}

	public Etudiant update(Etudiant e) throws SQLException {


		Connection con = null;
		try {
			con = MysqlConnUtils.getMysqlConnection();

			String req = "update etudiant set nom = ?, prenom = ?, dateNaissance = ?, imageLink = ? where id = ?";

			PreparedStatement pstm = con.prepareStatement(req);
			pstm.setString(1, e.getNomProperty());
			pstm.setString(2, e.getPrenomProperty());
			pstm.setString(3, e.getDateProperty());
			pstm.setString(4, e.getFile());
			pstm.setLong(5, e.getIdProperty());

			pstm.executeUpdate();
			log.info(("successfully updated"));
		} catch (ClassNotFoundException | SQLException ex) {
			log.error((ex.getMessage()));
		} finally {
			if (con != null)
				con.close();
		}
		return e;

	}

	public Etudiant delete(Etudiant e, long index) throws SQLException {

		Connection con = null;
		try {
			con = MysqlConnUtils.getMysqlConnection();

			String req = "delete from etudiant where id = ?";

			PreparedStatement pstm = con.prepareStatement(req);
			pstm.setLong(1, index);

			pstm.executeUpdate();
			log.info(("successfully deleted"));
		} catch (ClassNotFoundException | SQLException ex) {
			log.error((ex.getMessage()));
		} finally {
			if (con != null)
				con.close();
		}
		return e;

	}

	public static List<Etudiant> getListeEtudiant() throws SQLException {
		Connection connection = null;
		PreparedStatement ps;
		Etudiant etudiant = null;
		ArrayList<Etudiant> etudiants = new ArrayList<Etudiant>();

		try {
			connection = MysqlConnUtils.getMysqlConnection();
			ps = connection.prepareStatement("select * from etudiant ");
			
			ResultSet resultat = ps.executeQuery();
		
			
			while (resultat.next()) {
				etudiant = new Etudiant();
				etudiant.setIdProperty((resultat.getLong("id")));
				etudiant.setNomProperty((resultat.getString("nom")));
				etudiant.setPrenomProperty(resultat.getString("prenom"));
				etudiant.setDateProperty((resultat.getString("dateNaissance")));
				etudiant.setFile((resultat.getString("imageLink")));
			
				etudiants.add(etudiant);
			}
			
			
		} catch (ClassNotFoundException | SQLException e) {
			log.error(e.getMessage());
			e.getStackTrace();
		}
		return etudiants;

	}

	public void addNote(Etudiant e, double d, int matiere,Date date) throws SQLException {

		Connection con = null;
		try {
			con = MysqlConnUtils.getMysqlConnection();

			String req = "insert into notes (ID_Etudiant,ID_Matiere,note,date_note) values (?,?,?,?)";

			PreparedStatement pstm = con.prepareStatement(req);
			
			pstm.setLong(1, e.getIdProperty());
			pstm.setInt(2, matiere);
			pstm.setDouble(3, d);
			pstm.setDate(4, date);
			pstm.executeUpdate();
			log.info(("note successfully inserted"));
		} catch (ClassNotFoundException | SQLException ex) {
			log.error((ex.getMessage()));
		} finally {
			if (con != null)
				con.close();
		}

	}
	public Date convertToDateViaSqlDate(LocalDate dateToConvert) {
	    return java.sql.Date.valueOf(dateToConvert);
	}
	public static List<Double> getNote(Etudiant e,int matiere) throws SQLException {

		Connection con = null;
		List<Double> list = null;
		try {
			con = MysqlConnUtils.getMysqlConnection();

			String req = "select id_note, note from notes where id_etudiant = ? and id_matiere = ?";
			list = new LinkedList<Double>();
			PreparedStatement pstm = con.prepareStatement(req);
			pstm.setLong(1,e.getIdProperty());
			pstm.setLong(2, matiere);

			ResultSet resultat = pstm.executeQuery();
			while (resultat.next()) {
				
				list.add(resultat.getDouble("note"));
				
			}
			log.info(("note successfully selected"));
		} catch (ClassNotFoundException | SQLException ex) {
			log.error((ex.getMessage()));
		} finally {
			if (con != null)
				con.close();
		}
		return list;

	}
	public static HashMap<Integer,Double> getNoteAndId(Etudiant e,int matiere) throws SQLException {

		Connection con = null;

		HashMap<Integer,Double> hash = new HashMap <Integer,Double>();
		try {
			con = MysqlConnUtils.getMysqlConnection();

			String req = "select id_note, note from notes where id_etudiant = ? and id_matiere = ?";
			PreparedStatement pstm = con.prepareStatement(req);
			pstm.setLong(1,e.getIdProperty());
			pstm.setLong(2, matiere);

			ResultSet resultat = pstm.executeQuery();
			while (resultat.next()) {
				hash.put(resultat.getInt("id_note"), resultat.getDouble("note"));		
			}
			System.out.println(hash);
			log.info(("note successfully selected"));
		} catch (ClassNotFoundException | SQLException ex) {
			log.error((ex.getMessage()));
		} finally {
			if (con != null)
				con.close();
		}
		return hash;

	}
	public static HashMap<Integer,Date> getNoteAndDate(Etudiant e,int matiere) throws SQLException {

		Connection con = null;

		HashMap<Integer,Date> hash = new HashMap <Integer,Date>();
		try {
			con = MysqlConnUtils.getMysqlConnection();

			String req = "select id_note, date_note from notes where id_etudiant = ? and id_matiere = ?";
			PreparedStatement pstm = con.prepareStatement(req);
			pstm.setLong(1,e.getIdProperty());
			pstm.setLong(2, matiere);

			ResultSet resultat = pstm.executeQuery();
			while (resultat.next()) {
				hash.put(resultat.getInt("id_note"), resultat.getDate("date_note"));		
			}
			System.out.println(hash);
			log.info(("note successfully selected"));
		
		} catch (ClassNotFoundException | SQLException ex) {
			log.error((ex.getMessage()));
		} finally {
			if (con != null)
				con.close();
		}
		return hash;

	}
	public void updateNote(Etudiant e,double note,int id_note) throws SQLException {

		Connection con = null;
		try {
			con = MysqlConnUtils.getMysqlConnection();

			String req = "update notes set note = ? where id_note = ? ";

			PreparedStatement pstm = con.prepareStatement(req);
			pstm.setDouble(1, note);
			pstm.setInt(2, id_note);
			
			pstm.executeUpdate();
			log.info(("successfully updated"));
		} catch (ClassNotFoundException | SQLException ex) {
			log.error((ex.getMessage()));
		} finally {
			if (con != null)
				con.close();
		}
	

	}
	public void deleteNote(Etudiant e,int id_note) throws SQLException {

		List<Etudiant> l = getAll();

		for (Etudiant et : l) {
			if (e.getIdProperty() == et.getIdProperty()) {
				et.getNomProperty().equals(e.getNomProperty());
				et.getPrenomProperty().equals(e.getPrenomProperty());
				et.getDateProperty().equals(e.getDateProperty());
				et.getFile().equals(e.getFile());
			}
		}
		Connection con = null;
		try {
			con = MysqlConnUtils.getMysqlConnection();

			String req = "delete from notes where id_note = ? ";

			PreparedStatement pstm = con.prepareStatement(req);
			pstm.setInt(1, id_note);
			
			pstm.executeUpdate();
			log.info(("successfully deleted"));
		} catch (ClassNotFoundException | SQLException ex) {
			log.error((ex.getMessage()));
		} finally {
			if (con != null)
				con.close();
		}
	

	}

	@Override
	public void add() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Etudiant delete(Etudiant e, Integer index) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	

	
	
}

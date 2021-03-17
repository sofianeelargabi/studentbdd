package fr.formation.afpa.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import fr.formation.afpa.domain.Etudiant;



public interface IGenericDao<T, PK extends Serializable> {
	
	public List<T> getAll();
	
	public void add(T e) throws SQLException;
	
	public T update(T e) throws SQLException;
	
	public T delete(Etudiant e, PK index) throws SQLException ;
	
	public void add();
	
	
	
	
}

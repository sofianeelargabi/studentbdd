package fr.formation.afpa.domain;


import java.util.concurrent.atomic.AtomicLong;

public class Professeur {

	String nom;
	String prenom;
	String DateNaiss;
	String login;
	String mdp;
	Long id;
	public static AtomicLong NEXT_ID = new AtomicLong(1);

	public Professeur(String nom, String prenom, String DateNaiss, String login, String mdp) {
		this.id=NEXT_ID.getAndIncrement();
		this.nom = nom;
		this.prenom = prenom;
		this.DateNaiss = DateNaiss;
		this.login = login;
		this.mdp = mdp;
	}
	
	public Professeur (String login, String mdp) {
		
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getDateNaiss() {
		return DateNaiss;
	}

	public void setDateNaiss(String DateNaiss) {
		this.DateNaiss = DateNaiss;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getMdp() {
		return mdp;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public static AtomicLong getNEXT_ID() {
		return NEXT_ID;
	}

	public static void setNEXT_ID(AtomicLong nEXT_ID) {
		NEXT_ID = nEXT_ID;
	}
	
}

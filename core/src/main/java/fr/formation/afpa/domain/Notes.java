package fr.formation.afpa.domain;

public class Notes {
	int id_Note;
	double note;
	String date_Note;

	public Notes(int id_Note,double note,String date_Note) {
		
		this.id_Note=id_Note;
		this.note=note;
		this.date_Note=date_Note;
	}
}

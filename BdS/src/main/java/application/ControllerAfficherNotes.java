package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import fr.formation.afpa.dao.EtudiantDaoJDBC;
import fr.formation.afpa.domain.Etudiant;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class ControllerAfficherNotes implements Initializable {
	int testIndex;
	Etudiant etu;
	@FXML
	private ImageView img;
	@FXML
	private TextField nom,prenom,dateNaiss;
	@FXML
	AnchorPane centerPane;
	@FXML
	BorderPane mainPane;
	@FXML
	MenuBar barMenu;
	@FXML
	Label label, labelMoy;
	@FXML
	TextArea area;
	@FXML
	ListView<Double> listView;
	@FXML
	ListView<Date> listDate;
	
	@FXML
	private Button add;
	@FXML
	private Button cancel, display,modify;
	@FXML
	private ChoiceBox<String> choiceBox;
	@FXML
	private Label label1;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		choiceBox.getItems().addAll("1 : Mathematiques", "2 : Français", "3 : Anglais", "4 : Histoire");
		choiceBox.setValue("1 : Mathematiques");
		testIndex = ControllerList.selectIndex;
		try {
			etu = EtudiantDaoJDBC.getListeEtudiant().get(testIndex);
			
			ObservableList<Double> data = FXCollections.<Double>observableArrayList();

			data.addAll(EtudiantDaoJDBC.getNote(etu, Integer.parseInt(choiceBox.getValue().substring(0, 1))));

			listView.setItems(data);
			
			ObservableList<Date> data1 = FXCollections.<Date>observableArrayList();
			Collection<Date> dates = EtudiantDaoJDBC.getNoteAndDate(etu, Integer.parseInt(choiceBox.getValue().substring(0, 1)))
					.values();

			data1.addAll(dates);

			listDate.setItems(data1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		

		AfficherNotes();
		afficheMoyenneClasseMath();
		
		Image image = new Image(etu.getFile());
		img.setImage(image);
		nom.setText(etu.getNomProperty());
		prenom.setText(etu.getPrenomProperty());
		dateNaiss.setText(etu.getDateProperty());
		prenom.setDisable(true);
		nom.setDisable(true);
		dateNaiss.setDisable(true);
		dateNaiss.setEditable(false);
	
		
//		if (Integer.parseInt(choiceBox.getValue()) == 1) {
//			label1.setText("Mathematiques");
//		}
//		choiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
//
//			if (newVal == "1") {
//				label1.setText("Mathematiques");
//			} else if (newVal == "2") {
//				label1.setText("Français");
//			} else if (newVal == "3") {
//				label1.setText("Anglais");
//			} else if (newVal == "4") {
//				label1.setText("Histoire");
//			}
//		});
	}

		public double afficheMoyenneClasseMath() {
			List<Double> list = null;
			try {
				list = EtudiantDaoJDBC.getNote(etu, Integer.parseInt(choiceBox.getValue().substring(0, 1)));
			} catch (NumberFormatException | SQLException e) {
				e.printStackTrace();
			}

			Double moyenneNoteEleve = 0.;
			if (list.isEmpty()) {
				labelMoy.setText("L'élève " + etu.getNomProperty() + " " + etu.getPrenomProperty()
						+ " n'a pas de notes en Mathématiques ");

			} else {
				try {
					list = EtudiantDaoJDBC.getNote(etu, Integer.parseInt(choiceBox.getValue().substring(0, 1)));
				} catch (NumberFormatException | SQLException e) {
					e.printStackTrace();
				}
				Double noteEleve = 0.;

				for (Double n : list) {
					noteEleve += n;
				}
				moyenneNoteEleve = noteEleve / list.size();
				DecimalFormat df = new DecimalFormat("####0.00");
				String MoyenneArrondie = df.format(moyenneNoteEleve);
				labelMoy.setText("Moyenne Mathématiques : " + MoyenneArrondie);
			}

			return moyenneNoteEleve;
		}

		public double afficheMoyenneClasseAnglais() {
			List<Double> list = null;
			try {
				list = EtudiantDaoJDBC.getNote(etu, Integer.parseInt(choiceBox.getValue().substring(0, 1)));
			} catch (NumberFormatException | SQLException e) {
				e.printStackTrace();
			}

			Double moyenneNoteEleve = 0.;
			if (list.isEmpty()) {
				labelMoy.setText(
						"L'élève " + etu.getNomProperty() + " " + etu.getPrenomProperty() + " n'a pas de notes en Anglais");

			} else {
				try {
					list = EtudiantDaoJDBC.getNote(etu, Integer.parseInt(choiceBox.getValue().substring(0, 1)));
				} catch (NumberFormatException | SQLException e) {
					e.printStackTrace();
				}
				Double noteEleve = 0.;

				for (Double n : list) {
					noteEleve += n;
				}
				moyenneNoteEleve = noteEleve / list.size();
				DecimalFormat df = new DecimalFormat("####0.00");
				String MoyenneArrondie = df.format(moyenneNoteEleve);
				labelMoy.setText("Moyenne Mathématiques : " + MoyenneArrondie);
			}

			return moyenneNoteEleve;
		}

		public double afficheMoyenneClasseFrancais() {
			List<Double> list = null;
			try {
				list = EtudiantDaoJDBC.getNote(etu, Integer.parseInt(choiceBox.getValue().substring(0, 1)));
			} catch (NumberFormatException | SQLException e) {
				e.printStackTrace();
			}

			Double moyenneNoteEleve = 0.;
			if (list.isEmpty()) {
				labelMoy.setText("L'élève " + etu.getNomProperty() + " " + etu.getPrenomProperty()
						+ " n'a pas de notes en Français");

			} else {
				try {
					list = EtudiantDaoJDBC.getNote(etu, Integer.parseInt(choiceBox.getValue().substring(0, 1)));
				} catch (NumberFormatException | SQLException e) {
					e.printStackTrace();
				}
				Double noteEleve = 0.;

				for (Double n : list) {
					noteEleve += n;
				}
				moyenneNoteEleve = noteEleve / list.size();
				DecimalFormat df = new DecimalFormat("####0.00");
				String MoyenneArrondie = df.format(moyenneNoteEleve);
				labelMoy.setText("Moyenne Mathématiques : " + MoyenneArrondie);
			}

			return moyenneNoteEleve;
		}

		public double afficheMoyenneClasseHistoire() {
			List<Double> list = null;
			try {
				list = EtudiantDaoJDBC.getNote(etu, Integer.parseInt(choiceBox.getValue().substring(0, 1)));
			} catch (NumberFormatException | SQLException e) {
				e.printStackTrace();
			}

			Double moyenneNoteEleve = 0.;
			if (list.isEmpty()) {
				labelMoy.setText("L'élève " + etu.getNomProperty() + " " + etu.getPrenomProperty()
						+ " n'a pas de notes en Histoire");

			} else {
				try {
					list = EtudiantDaoJDBC.getNote(etu, Integer.parseInt(choiceBox.getValue().substring(0, 1)));
				} catch (NumberFormatException | SQLException e) {
					e.printStackTrace();
				}
				Double noteEleve = 0.;

				for (Double n : list) {
					noteEleve += n;
				}
				moyenneNoteEleve = noteEleve / list.size();
				DecimalFormat df = new DecimalFormat("####0.00");
				String MoyenneArrondie = df.format(moyenneNoteEleve);
				labelMoy.setText("Moyenne Mathématiques : " + MoyenneArrondie);
			}

			return moyenneNoteEleve;
		}

	public void AfficherNotes() {
		choiceBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				LinkedList<Double> list = null;
				switch (choiceBox.getValue()) {

				case "1 : Mathematiques":
					listView.getItems().clear();
					listDate.getItems().clear();
					try {
						list = (LinkedList<Double>) EtudiantDaoJDBC.getNote(etu,
								Integer.parseInt(choiceBox.getValue().substring(0, 1)));
						ObservableList<Date> data1 = FXCollections.<Date>observableArrayList();
						Collection<Date> dates = EtudiantDaoJDBC.getNoteAndDate(etu, Integer.parseInt(choiceBox.getValue().substring(0, 1)))
								.values();

						data1.addAll(dates);
						listDate.setItems(data1);
					} catch (NumberFormatException | SQLException e) {
						e.printStackTrace();
					}
					for (Double l : list) {
						listView.getItems().add(l);
					}
					afficheMoyenneClasseMath();
					break;
				case "2 : Français":
					listView.getItems().clear();
					listDate.getItems().clear();
					try {
						list = (LinkedList<Double>) EtudiantDaoJDBC.getNote(etu,
								Integer.parseInt(choiceBox.getValue().substring(0, 1)));
						ObservableList<Date> data1 = FXCollections.<Date>observableArrayList();
						Collection<Date> dates = EtudiantDaoJDBC.getNoteAndDate(etu, Integer.parseInt(choiceBox.getValue().substring(0, 1)))
								.values();

						data1.addAll(dates);
						listDate.setItems(data1);
					} catch (NumberFormatException | SQLException e) {
						e.printStackTrace();
					}
					for (Double l : list) {
						listView.getItems().add(l);
					}
					afficheMoyenneClasseFrancais();
					break;
				case "3 : Anglais":
					listView.getItems().clear();
					listDate.getItems().clear();
					try {
						list = (LinkedList<Double>) EtudiantDaoJDBC.getNote(etu,
								Integer.parseInt(choiceBox.getValue().substring(0, 1)));
						ObservableList<Date> data1 = FXCollections.<Date>observableArrayList();
						Collection<Date> dates = EtudiantDaoJDBC.getNoteAndDate(etu, Integer.parseInt(choiceBox.getValue().substring(0, 1)))
								.values();

						data1.addAll(dates);
						listDate.setItems(data1);
					} catch (NumberFormatException | SQLException e) {
						e.printStackTrace();
					}
					for (Double l : list) {
						listView.getItems().add(l);
					}
					afficheMoyenneClasseAnglais();
					break;

				case "4 : Histoire":
					listView.getItems().clear();
					listDate.getItems().clear();
					try {
						list = (LinkedList<Double>) EtudiantDaoJDBC.getNote(etu,
								Integer.parseInt(choiceBox.getValue().substring(0, 1)));
						ObservableList<Date> data1 = FXCollections.<Date>observableArrayList();
						Collection<Date> dates = EtudiantDaoJDBC.getNoteAndDate(etu, Integer.parseInt(choiceBox.getValue().substring(0, 1)))
								.values();

						data1.addAll(dates);
						listDate.setItems(data1);
					} catch (NumberFormatException | SQLException e) {
						e.printStackTrace();
					}
					for (Double l : list) {
						listView.getItems().add(l);
					}
					afficheMoyenneClasseHistoire();
					break;
				}
			}
		});
	}
	
	public void onAjoutClickEtudiant(ActionEvent event) throws IOException {

		BorderPane fxmlLoader = FXMLLoader.load(getClass().getResource("Ajout.fxml"));
		mainPane.getChildren().setAll(fxmlLoader);
	}

	public void onShowListClick(ActionEvent event) throws IOException {
		BorderPane fxmlLoader = FXMLLoader.load(getClass().getResource("Table.fxml"));
		mainPane.getChildren().setAll(fxmlLoader);
	}

	@FXML
	public void onBtnCancel() {
		cancel.setOnAction((event) -> {
			Platform.exit();

		});

	}
	
	public void onclickSubMenuClose() {
		Platform.exit();
	}

	@FXML
	public void onBtnLogout() throws IOException {
		Alert alert = new Alert(AlertType.CONFIRMATION, "Voulez-vous vraiment vous d�connecter ?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			BorderPane fxmlLoader = FXMLLoader.load(getClass().getResource("Connexion.fxml"));
			mainPane.getChildren().setAll(fxmlLoader);
		} else {
			alert.close();
		}
	}

	@FXML
	public void onShowHelp(ActionEvent event) throws IOException {

		BorderPane fxmlLoader = FXMLLoader.load(getClass().getResource("Aide2.fxml"));
		mainPane.getChildren().setAll(fxmlLoader);
	}
	
}

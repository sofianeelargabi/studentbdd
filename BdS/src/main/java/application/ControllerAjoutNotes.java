package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class ControllerAjoutNotes implements Initializable {
	int testIndex;
	Etudiant etu;
	@FXML
	AnchorPane centerPane;
	@FXML
	BorderPane mainPane;
	@FXML
	MenuBar barMenu;
	@FXML
	private ImageView tphoto;

	@FXML
	private DatePicker datepick;
	@FXML
	ListView<Double> listView;
	@FXML
	private TextField nom, prenom, url;
	@FXML
	private TextField tprenom, dateField, textNotes;
	@FXML
	private Button add;
	@FXML
	private Button cancel;
	@FXML
	private ChoiceBox<String> choiceBox;

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
		} catch (SQLException e) {
			e.printStackTrace();
		}

		AfficherNotes();
		System.out.println(etu+"ici");
		prenom.setText(etu.getPrenomProperty());
		nom.setText(etu.getNomProperty());
		dateField.setText(etu.getDateProperty());
		Image image = new Image(etu.getFile());
		tphoto.setImage(image);
		prenom.setDisable(true);
		nom.setDisable(true);
		dateField.setDisable(true);
		dateField.setEditable(false);
		dateField.setEditable(false);
//		if (Integer.parseInt(choiceBox.getValue()) == 1) {
//			textNotes.setPromptText("Mathematiques");
//		}
//		choiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
//
//			if (newVal == "1") {
//				textNotes.setPromptText("Mathematiques");
//			} else if (newVal == "2") {
//				textNotes.setPromptText("Français");
//			} else if (newVal == "3") {
//				textNotes.setPromptText("Anglais");
//			} else if (newVal == "4") {
//				textNotes.setPromptText("Histoire");
//			}
//		});
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
		Alert alert = new Alert(AlertType.CONFIRMATION, "Voulez-vous vraiment vous déconnecter ?");
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

	public void onBtnAjout() {

		Double val = 0.;
		switch (choiceBox.getValue()) {
		case "1 : Mathematiques":
			try {
				try {
					for (Etudiant ex : EtudiantDaoJDBC.getListeEtudiant()) {
						if ( ex.getIdProperty()==etu.getIdProperty()&&!textNotes.getText().isEmpty()) {
							val = Double.parseDouble(textNotes.getText());

							if (val < 0 || val > 20) {
								Alert alert = new Alert(AlertType.ERROR, "La note doit être comprise entre 0 et 20",
										ButtonType.OK);
								alert.showAndWait();
							} else {
								Alert alert = new Alert(AlertType.CONFIRMATION,
										"Voulez-vous vraiment ajouter cette note ?", ButtonType.OK);
								alert.showAndWait();
								if (alert.getResult() == ButtonType.OK) {

									Double d = Double.parseDouble(textNotes.getText());

									// etu.getMath().get(Integer.parseInt(choiceBox.getValue())).add(d);
									// l1.getMath().get(Integer.parseInt(choiceBox.getValue())).add(d);
									EtudiantDaoJDBC f = new EtudiantDaoJDBC();
									try {
										LocalDate now = LocalDate.now();
										Date date = f.convertToDateViaSqlDate(now);
										f.addNote(ex, d, (Integer.parseInt(choiceBox.getValue().substring(0, 1))),date);
									} catch (SQLException e) {
										e.printStackTrace();
									}

									textNotes.setText("");
									listView.getItems().clear();
									List<Double> list = EtudiantDaoJDBC.getNote(ex,
											Integer.parseInt(choiceBox.getValue().substring(0, 1)));
									for (Double l : list) {
										listView.getItems().add(l);
									}

									System.out.println(ex);
								}
							}
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (NumberFormatException e) {
				Alert alert = new Alert(AlertType.ERROR, "Saisie non valide", ButtonType.OK);
				alert.showAndWait();
			}
			break;

		case "2 : Français":
			try {
				try {
					for (Etudiant ex : EtudiantDaoJDBC.getListeEtudiant()) {
						if ( ex.getIdProperty()==etu.getIdProperty()&&!textNotes.getText().isEmpty()) {
							val = Double.parseDouble(textNotes.getText());

							if (val < 0 || val > 20) {
								Alert alert = new Alert(AlertType.ERROR, "La note doit être comprise entre 0 et 20",
										ButtonType.OK);
								alert.showAndWait();
							} else {
								Alert alert = new Alert(AlertType.CONFIRMATION,
										"Voulez-vous vraiment ajouter cette note ?", ButtonType.OK);
								alert.showAndWait();
								if (alert.getResult() == ButtonType.OK) {

									Double d = Double.parseDouble(textNotes.getText());

									EtudiantDaoJDBC f = new EtudiantDaoJDBC();
									try {
										LocalDate now = LocalDate.now();
										Date date = f.convertToDateViaSqlDate(now);
										f.addNote(ex, d, (Integer.parseInt(choiceBox.getValue().substring(0, 1))),date);
									} catch (SQLException e) {
										e.printStackTrace();
									}

									textNotes.setText("");
									listView.getItems().clear();
									List<Double> list = EtudiantDaoJDBC.getNote(ex,
											Integer.parseInt(choiceBox.getValue().substring(0, 1)));
									for (Double l : list) {
										listView.getItems().add(l);
									}

									System.out.println(ex);
								}
							}
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (NumberFormatException e) {
				Alert alert = new Alert(AlertType.ERROR, "Saisie non valide", ButtonType.OK);
				alert.showAndWait();
			}
			break;
		case "3 : Anglais":
			try {
				try {
					for (Etudiant ex : EtudiantDaoJDBC.getListeEtudiant()) {
						if ( ex.getIdProperty()==etu.getIdProperty()&&!textNotes.getText().isEmpty()) {
							val = Double.parseDouble(textNotes.getText());

							if (val < 0 || val > 20) {
								Alert alert = new Alert(AlertType.ERROR, "La note doit être comprise entre 0 et 20",
										ButtonType.OK);
								alert.showAndWait();
							} else {
								Alert alert = new Alert(AlertType.CONFIRMATION,
										"Voulez-vous vraiment ajouter cette note ?", ButtonType.OK);
								alert.showAndWait();
								if (alert.getResult() == ButtonType.OK) {

									Double d = Double.parseDouble(textNotes.getText());

									// etu.getMath().get(Integer.parseInt(choiceBox.getValue())).add(d);
									// l1.getMath().get(Integer.parseInt(choiceBox.getValue())).add(d);
									EtudiantDaoJDBC f = new EtudiantDaoJDBC();
									try {
										LocalDate now = LocalDate.now();
										Date date = f.convertToDateViaSqlDate(now);
										f.addNote(ex, d, (Integer.parseInt(choiceBox.getValue().substring(0, 1))),date);
									} catch (SQLException e) {
										e.printStackTrace();
									}

									textNotes.setText("");
									listView.getItems().clear();
									List<Double> list = EtudiantDaoJDBC.getNote(ex,
											Integer.parseInt(choiceBox.getValue().substring(0, 1)));
									for (Double l : list) {
										listView.getItems().add(l);
									}

									System.out.println(ex);
								}
							}
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (NumberFormatException e) {
				Alert alert = new Alert(AlertType.ERROR, "Saisie non valide", ButtonType.OK);
				alert.showAndWait();
			}
			break;

		case "4 : Histoire":
			try {
				try {
					for (Etudiant ex : EtudiantDaoJDBC.getListeEtudiant()) {
						if ( ex.getIdProperty()==etu.getIdProperty()&&!textNotes.getText().isEmpty()) {
							val = Double.parseDouble(textNotes.getText());

							if (val < 0 || val > 20) {
								Alert alert = new Alert(AlertType.ERROR, "La note doit être comprise entre 0 et 20",
										ButtonType.OK);
								alert.showAndWait();
							} else {
								Alert alert = new Alert(AlertType.CONFIRMATION,
										"Voulez-vous vraiment ajouter cette note ?", ButtonType.OK);
								alert.showAndWait();
								if (alert.getResult() == ButtonType.OK) {

									Double d = Double.parseDouble(textNotes.getText());

									EtudiantDaoJDBC f = new EtudiantDaoJDBC();
									try {
										LocalDate now = LocalDate.now();
										Date date = f.convertToDateViaSqlDate(now);
										f.addNote(ex, d, (Integer.parseInt(choiceBox.getValue().substring(0, 1))),date);
									} catch (SQLException e) {
										e.printStackTrace();
									}

									textNotes.setText("");
									listView.getItems().clear();
									List<Double> list = EtudiantDaoJDBC.getNote(ex,
											Integer.parseInt(choiceBox.getValue().substring(0, 1)));
									for (Double l : list) {
										listView.getItems().add(l);
									}

									System.out.println(ex);
								}
							}
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (NumberFormatException e) {
				Alert alert = new Alert(AlertType.ERROR, "Saisie non valide", ButtonType.OK);
				alert.showAndWait();
			}
			break;
		}

	}

	public void AfficherNotes() {
		choiceBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				LinkedList<Double> list = null;
				switch (choiceBox.getValue()) {
				
				case "1 : Mathematiques":
					listView.getItems().clear();
					
					try {
						list = (LinkedList<Double>) EtudiantDaoJDBC.getNote(etu,
								Integer.parseInt(choiceBox.getValue().substring(0, 1)));
					} catch (NumberFormatException | SQLException e) {
						e.printStackTrace();
					}
					for (Double l : list) {
						listView.getItems().add(l);
					}
					
					break;
				case "2 : Français":
					listView.getItems().clear();
					
					try {
						list = (LinkedList<Double>) EtudiantDaoJDBC.getNote(etu,
								Integer.parseInt(choiceBox.getValue().substring(0, 1)));
					} catch (NumberFormatException | SQLException e) {
						e.printStackTrace();
					}
					for (Double l : list) {
						listView.getItems().add(l);
					}
					
					break;
				case "3 : Anglais":
					listView.getItems().clear();
					
					try {
						list = (LinkedList<Double>) EtudiantDaoJDBC.getNote(etu,
								Integer.parseInt(choiceBox.getValue().substring(0, 1)));
					} catch (NumberFormatException | SQLException e) {
						e.printStackTrace();
					}
					for (Double l : list) {
						listView.getItems().add(l);
					}
				
					break;
				
				case "4 : Histoire":
					listView.getItems().clear();
					try {
						list = (LinkedList<Double>) EtudiantDaoJDBC.getNote(etu,
								Integer.parseInt(choiceBox.getValue().substring(0, 1)));
					} catch (NumberFormatException | SQLException e) {
						e.printStackTrace();
					}
					for (Double l : list) {
						listView.getItems().add(l);
					}
					
					break;
				}
			}
		});
	}

}

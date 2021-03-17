package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class ControllerModifNotes implements Initializable {
	int testIndex;
	Etudiant etu;
	@FXML
	ImageView photo;
	@FXML
	TextField name, surname, naiss;
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
	private Button cancel, display, modify;
	@FXML
	private ChoiceBox<String> choiceBox;
	@FXML
	private Label label1;

	@FXML
	private ListView<Integer> listView1;

	public void initialize(URL location, ResourceBundle resources) {
		choiceBox.getItems().addAll("1 : Mathematiques", "2 : Français", "3 : Anglais", "4 : Histoire");
		choiceBox.setValue("1 : Mathematiques");
		
		
		testIndex = ControllerList.selectIndex;
		try {
			etu = EtudiantDaoJDBC.getListeEtudiant().get(testIndex);

			ObservableList<Double> data = FXCollections.<Double>observableArrayList();
			Collection<Double> notes = EtudiantDaoJDBC.getNoteAndId(etu, Integer.parseInt(choiceBox.getValue().substring(0, 1)))
					.values();

			data.addAll(notes);

			listView.setItems(data);
			
			ObservableList<Date> data2 = FXCollections.<Date>observableArrayList();
			Collection<Date> dates = EtudiantDaoJDBC.getNoteAndDate(etu, Integer.parseInt(choiceBox.getValue().substring(0, 1)))
					.values();

			data2.addAll(dates);

			listDate.setItems(data2);

			HashMap<Integer, Double> hash = EtudiantDaoJDBC.getNoteAndId(etu, Integer.parseInt(choiceBox.getValue().substring(0, 1)));
			Iterator<Map.Entry<Integer, Double>> itr = hash.entrySet().iterator();
			ObservableList<Integer> data1 = FXCollections.<Integer>observableArrayList();

			while (itr.hasNext()) {
				Map.Entry<Integer, Double> entry = itr.next();
				data1.add(entry.getKey());
			}
			listView1.setItems(data1);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		AfficherNotes();
		afficheMoyenneClasseMath();
		
		Image image = new Image(etu.getFile());
		photo.setImage(image);
		name.setText(etu.getNomProperty());
		name.setDisable(true);
		name.setEditable(false);
		surname.setText(etu.getPrenomProperty());
		surname.setDisable(true);
		surname.setEditable(false);
		naiss.setText(etu.getDateProperty());
		naiss.setDisable(true);
		naiss.setEditable(false);

//		if (Integer.parseInt(choiceBox.getValue().substring(0, 1)) == 1) {
//			label1.setText("Mathematiques");
//		}
		choiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
			HashMap<Integer, Double> hash = null;
			if (newVal == "1 : Mathematiques") {

				try {
					hash = EtudiantDaoJDBC.getNoteAndId(etu, Integer.parseInt(choiceBox.getValue().substring(0, 1)));
				} catch (NumberFormatException | SQLException e) {
					e.printStackTrace();
				}
				Iterator<Map.Entry<Integer, Double>> itr = hash.entrySet().iterator();

				ObservableList<Integer> data1 = FXCollections.<Integer>observableArrayList();

				while (itr.hasNext()) {
					Map.Entry<Integer, Double> entry = itr.next();
					data1.add(entry.getKey());
				}
				listView1.setItems(data1);
				//label1.setText("Mathematiques");
			} 
			else if (newVal == "2 : Français") {

				try {
					hash = EtudiantDaoJDBC.getNoteAndId(etu, Integer.parseInt(choiceBox.getValue().substring(0, 1)));
				} catch (NumberFormatException | SQLException e) {
					e.printStackTrace();
				}
				Iterator<Map.Entry<Integer, Double>> itr = hash.entrySet().iterator();

				ObservableList<Integer> data1 = FXCollections.<Integer>observableArrayList();

				while (itr.hasNext()) {
					Map.Entry<Integer, Double> entry = itr.next();
					data1.add(entry.getKey());
				}
				listView1.setItems(data1);
				//label1.setText("Français");
			} else if (newVal == "3 : Anglais") {

				try {
					hash = EtudiantDaoJDBC.getNoteAndId(etu, Integer.parseInt(choiceBox.getValue().substring(0, 1)));
				} catch (NumberFormatException | SQLException e) {
					e.printStackTrace();
				}
				Iterator<Map.Entry<Integer, Double>> itr = hash.entrySet().iterator();

				ObservableList<Integer> data1 = FXCollections.<Integer>observableArrayList();

				while (itr.hasNext()) {
					Map.Entry<Integer, Double> entry = itr.next();
					data1.add(entry.getKey());
				}
				listView1.setItems(data1);
				//label1.setText("Anglais");
			} else if (newVal == "4 : Histoire") {

				try {
					hash = EtudiantDaoJDBC.getNoteAndId(etu, Integer.parseInt(choiceBox.getValue().substring(0, 1)));
				} catch (NumberFormatException | SQLException e) {
					e.printStackTrace();
				}
				Iterator<Map.Entry<Integer, Double>> itr = hash.entrySet().iterator();

				ObservableList<Integer> data1 = FXCollections.<Integer>observableArrayList();

				while (itr.hasNext()) {
					Map.Entry<Integer, Double> entry = itr.next();
					data1.add(entry.getKey());
				}
				listView1.setItems(data1);
				//label1.setText("Histoire");
			}
		});
	}

	@FXML
	public void modifierNotes() {

		System.out.println("here");
		listView1.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (e.getClickCount() == 2) {
					if (listView1.getSelectionModel().getSelectedIndex() != -1) {
						System.out.println("here1");
						switch (choiceBox.getValue()) {
						case "1 : Mathematiques":
							System.out.println("ici Math");
							TextInputDialog dialog = new TextInputDialog();
							dialog.setTitle("Modification étudiant");
							dialog.setHeaderText("Veuillez entrer la nouvelle note");
							dialog.setContentText("Note:");

							Optional<String> result = dialog.showAndWait();

							try {
								LinkedList<Double> list = null;
								if (result.isPresent()) {
									Alert alert = new Alert(AlertType.CONFIRMATION,
											"Voulez-vous vraiment modifier cette note ?", ButtonType.YES,
											ButtonType.CANCEL);
									alert.showAndWait();

									if (alert.getResult() == ButtonType.YES) {
										Double val = Double.parseDouble(result.get());
										System.out.println(val);

										if (val >= 0D && val <= 20D) {

											try {
												System.out.println("bonjour");
												EtudiantDaoJDBC f = new EtudiantDaoJDBC();
												Integer s = listView1.getSelectionModel().getSelectedItem();

												System.out.println(s);
												f.updateNote(etu, val, s);
												
												
											} catch (SQLException e1) {
												e1.printStackTrace();
											}

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
											} catch (SQLException e1) {
												e1.printStackTrace();
											}
											for (Double l : list) {
												listView.getItems().add(l);
											}
											afficheMoyenneClasseMath();
										} else {
											Alert alert1 = new Alert(AlertType.ERROR,
													"La note doit être comprise entre 0 et 20", ButtonType.OK);
											alert1.showAndWait();
										}
									}
								}
							} catch (NumberFormatException ex) {
								Alert alert = new Alert(AlertType.ERROR, "Saisie non valide", ButtonType.OK);
								alert.showAndWait();
							}
							break;

						case "2 : Français":
							System.out.println("ici Francais");
							TextInputDialog dialogF = new TextInputDialog();
							dialogF.setTitle("Modification étudiant");
							dialogF.setHeaderText("Veuillez entrer la nouvelle note");
							dialogF.setContentText("Note:");

							Optional<String> resultF = dialogF.showAndWait();

							try {
								LinkedList<Double> list = null;
								if (resultF.isPresent()) {
									Alert alert = new Alert(AlertType.CONFIRMATION,
											"Voulez-vous vraiment modifier cette note ?", ButtonType.YES,
											ButtonType.CANCEL);
									alert.showAndWait();

									if (alert.getResult() == ButtonType.YES) {
										Double val = Double.parseDouble(resultF.get());
										System.out.println(val);

										if (val >= 0D && val <= 20D) {

											try {
												System.out.println("bonjour");
												EtudiantDaoJDBC f = new EtudiantDaoJDBC();
												Integer s = listView1.getSelectionModel().getSelectedItem();

												System.out.println(s);
												f.updateNote(etu, val, s);
											} catch (SQLException e1) {
												e1.printStackTrace();
											}

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
											} catch (SQLException e1) {
												e1.printStackTrace();
											}
											for (Double l : list) {
												listView.getItems().add(l);
											}
											afficheMoyenneClasseFrancais();
										} else {
											Alert alert1 = new Alert(AlertType.ERROR,
													"La note doit être comprise entre 0 et 20", ButtonType.OK);
											alert1.showAndWait();
										}
									}
								}
							} catch (NumberFormatException ex) {
								Alert alert = new Alert(AlertType.ERROR, "Saisie non valide", ButtonType.OK);
								alert.showAndWait();
							}
							break;

						case "3 : Anglais":
							System.out.println("ici Anglais");
							TextInputDialog dialogA = new TextInputDialog();
							dialogA.setTitle("Modification étudiant");
							dialogA.setHeaderText("Veuillez entrer la nouvelle note");
							dialogA.setContentText("Note:");

							Optional<String> resultA = dialogA.showAndWait();

							try {
								LinkedList<Double> list = null;
								if (resultA.isPresent()) {
									Alert alert = new Alert(AlertType.CONFIRMATION,
											"Voulez-vous vraiment modifier cette note ?", ButtonType.YES,
											ButtonType.CANCEL);
									alert.showAndWait();

									if (alert.getResult() == ButtonType.YES) {
										Double val = Double.parseDouble(resultA.get());
										System.out.println(val);

										if (val >= 0D && val <= 20D) {

											try {
												System.out.println("bonjour");
												EtudiantDaoJDBC f = new EtudiantDaoJDBC();
												Integer s = listView1.getSelectionModel().getSelectedItem();

												System.out.println(s);
												f.updateNote(etu, val, s);
											} catch (SQLException e1) {
												e1.printStackTrace();
											}

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
											} catch (SQLException e1) {
												e1.printStackTrace();
											}
											for (Double l : list) {
												listView.getItems().add(l);
											}
											afficheMoyenneClasseMath();
										} else {
											Alert alert1 = new Alert(AlertType.ERROR,
													"La note doit être comprise entre 0 et 20", ButtonType.OK);
											alert1.showAndWait();
										}
									}
								}
							} catch (NumberFormatException ex) {
								Alert alert = new Alert(AlertType.ERROR, "Saisie non valide", ButtonType.OK);
								alert.showAndWait();
							}
							break;
						case "4 : Histoire":
							System.out.println("ici Histoire");
							TextInputDialog dialogH = new TextInputDialog();
							dialogH.setTitle("Modification étudiant");
							dialogH.setHeaderText("Veuillez entrer la nouvelle note");
							dialogH.setContentText("Note:");

							Optional<String> resultH = dialogH.showAndWait();

							try {
								LinkedList<Double> list = null;
								if (resultH.isPresent()) {
									Alert alert = new Alert(AlertType.CONFIRMATION,
											"Voulez-vous vraiment modifier cette note ?", ButtonType.YES,
											ButtonType.CANCEL);
									alert.showAndWait();

									if (alert.getResult() == ButtonType.YES) {
										Double val = Double.parseDouble(resultH.get());
										System.out.println(val);

										if (val >= 0D && val <= 20D) {

											try {
												EtudiantDaoJDBC f = new EtudiantDaoJDBC();
												Integer s = listView1.getSelectionModel().getSelectedItem();

												System.out.println(s);
												f.updateNote(etu, val, s);
											} catch (SQLException e1) {
												e1.printStackTrace();
											}

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
											} catch (SQLException e1) {
												e1.printStackTrace();
											}
											for (Double l : list) {
												listView.getItems().add(l);
											}
											afficheMoyenneClasseMath();
										} else {
											Alert alert1 = new Alert(AlertType.ERROR,
													"La note doit être comprise entre 0 et 20", ButtonType.OK);
											alert1.showAndWait();
										}
									}
								}
							} catch (NumberFormatException ex) {
								Alert alert = new Alert(AlertType.ERROR, "Saisie non valide", ButtonType.OK);
								alert.showAndWait();
							}
							break;

						}

					}

				}
			}
		});

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

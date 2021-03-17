package application;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

import fr.formation.afpa.dao.ProfesseurDaoJDBC;
import fr.formation.afpa.domain.Professeur;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.effect.DropShadow;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class ControllerInscription implements Initializable {
	@FXML
	AnchorPane centerPane;
	@FXML
	BorderPane mainPane;
	@FXML
	MenuBar barMenu;
	@FXML
	TextField nom, prenom, login, mdp, confirm;
	@FXML
	DatePicker date;
	@FXML
	Button connexion;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@FXML
	public void onShowHelp(ActionEvent event) throws IOException {

		BorderPane fxmlLoader = FXMLLoader.load(getClass().getResource("Aide.fxml"));
		mainPane.getChildren().setAll(fxmlLoader);
	}

	@FXML
	private void onBtnInscriptionClick(ActionEvent event) throws IOException {
		try {

			ProfesseurDaoJDBC f = new ProfesseurDaoJDBC();
			String hashpass = null;
			DropShadow ds = new DropShadow();
			ds.setColor(Color.DARKGRAY);
			if (!nom.getText().isEmpty() && !prenom.getText().isEmpty() && date.getValue() != null
					&& nom.getText().chars().allMatch(Character::isAlphabetic)
					&& prenom.getText().chars().allMatch(Character::isAlphabetic) && login.getText().length() > 3
					&& !mdp.getText().isEmpty() && !confirm.getText().isEmpty() && mdp.getText().length()>3 &&confirm.getText().length()>3) {
				if (f.comparePass(mdp.getText(), confirm.getText()) == true) {
					try {
						hashpass = f.Sha256(mdp.getText());
					} catch (Exception e) {
						e.printStackTrace();
					}
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.FRANCE);
					String formattedDate = (date.getValue()).format(formatter);
					Professeur prof = new Professeur(nom.getText(), prenom.getText(), formattedDate, login.getText(),
							hashpass);

					f.inscription(prof);
					nom.setText("");
					prenom.setText("");
					date.setValue(null);
					login.setText("");
					mdp.setText("");
					confirm.setText("");
					Alert alert = new Alert(AlertType.INFORMATION,
							"Félicitations, votre compte a été créé avec succès ! Cliquez sur le bouton connexion pour vous authentifier");
					alert.showAndWait();
				} else {
					Alert alert = new Alert(AlertType.ERROR, "Les mots de passe ne correspondent pas",
							ButtonType.OK);
					alert.showAndWait();
				}
			} else if (nom.getText() == null || nom.getText().equals("") || nom.getText().isEmpty()) {
				nom.setEffect(ds);
				Alert alert = new Alert(AlertType.ERROR, "Le champ nom est vide", ButtonType.OK);
				alert.showAndWait();

			} else if (!nom.getText().chars().allMatch(Character::isAlphabetic)) {
				nom.setEffect(ds);
				Alert alert = new Alert(AlertType.ERROR, "Le nom ne doit contenir que des lettres", ButtonType.OK);
				alert.showAndWait();

			} else if (prenom.getText() == null || prenom.getText().equals("") || prenom.getText().isEmpty()) {
				prenom.setEffect(ds);
				Alert alert = new Alert(AlertType.ERROR, "Le champ prénom est vide", ButtonType.OK);
				alert.showAndWait();

			} else if (!prenom.getText().chars().allMatch(Character::isAlphabetic)) {
				prenom.setEffect(ds);
				Alert alert = new Alert(AlertType.ERROR, "Le prénom ne doit contenir que des lettres", ButtonType.OK);
				alert.showAndWait();

			} else if (date.getValue() == null) {
				date.setEffect(ds);
				Alert alert = new Alert(AlertType.ERROR, "Le champ date est vide", ButtonType.OK);
				alert.showAndWait();

			}else if (login.getText().length()<=3) {
				login.setEffect(ds);
				Alert alert = new Alert(AlertType.ERROR, "Le login doit comporter au moins 4 caractères", ButtonType.OK);
				alert.showAndWait();
			}
			else if (mdp.getText().length()<=3) {
				mdp.setEffect(ds);
				Alert alert = new Alert(AlertType.ERROR, "Le mot de passe doit comporter au moins 4 caractères", ButtonType.OK);
				alert.showAndWait();
			}
			else if (confirm.getText().length()<=3) {
				confirm.setEffect(ds);
				Alert alert = new Alert(AlertType.ERROR, "Le mot de passe doit comporter au moins 4 caractères", ButtonType.OK);
				alert.showAndWait();
			}
		} catch (NullPointerException e) {

		}
	}
//	}

	@FXML
	public void onConnex(ActionEvent event) throws IOException {

		BorderPane fxmlLoader = FXMLLoader.load(getClass().getResource("Connexion.fxml"));
		mainPane.getChildren().setAll(fxmlLoader);
	}

	public void onclickSubMenuClose() {
		Platform.exit();
	}

	public static void showStage() {
		Dialog<String> dialog = new Dialog<String>();
		dialog.setTitle("Dialog");
		ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);
		dialog.setContentText("Votre login et/ou mdp est incorrect");
		dialog.getDialogPane().getButtonTypes().add(type);
		dialog.showAndWait();

	}

}

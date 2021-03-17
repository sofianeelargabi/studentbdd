package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import fr.formation.afpa.dao.EtudiantDaoJDBC;
import fr.formation.afpa.domain.Etudiant;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ControllerAjout implements Initializable {

	@FXML
	AnchorPane centerPane;
	@FXML
	BorderPane mainPane;
	@FXML
	MenuBar barMenu;
	@FXML
	private Button browse, cancel, save;
	@FXML
	private DatePicker datepick;
	@FXML
	// private Label labelPhoto;
	private ImageView image;
	@FXML
	private TextField nom, prenom, url;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

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
	public void onBtnSave() {
		save.setOnAction((event) -> {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.FRANCE);

			DropShadow ds = new DropShadow();
			ds.setColor(Color.RED);
			if (image.getImage() == null) {
				ds.setColor(Color.RED);
				
				Alert alert = new Alert(AlertType.ERROR, "Le champ photo est vide", ButtonType.OK);
				alert.showAndWait();
				System.out.println("Button clicked");
			} else if (!nom.getText().isEmpty() && !prenom.getText().isEmpty() && datepick.getValue() != null
					&& nom.getText().chars().allMatch(Character::isAlphabetic)
					&& prenom.getText().chars().allMatch(Character::isAlphabetic) && !image.getImage().equals(null)) {
				String formattedDate = (datepick.getValue()).format(formatter);
				EtudiantDaoJDBC etu = new EtudiantDaoJDBC();
				
				int math = 1 ;
				int francais = 2;
				int anglais = 3;
				int histoire = 4;
			
			
				LinkedList <Double> note1 = new LinkedList <Double>();
				LinkedList <Double> note2 = new LinkedList <Double>();
				LinkedList <Double> note3 = new LinkedList <Double>();
				LinkedList <Double> note4 = new LinkedList <Double>();
				HashMap <Integer,LinkedList <Double>> hMath = new HashMap<Integer,LinkedList <Double>>();
				HashMap <Integer,LinkedList <Double>> hFrancais = new HashMap<Integer,LinkedList <Double>>();
				HashMap <Integer,LinkedList <Double>> hAnglais = new HashMap<Integer,LinkedList <Double>>();
				HashMap <Integer,LinkedList <Double>> hHistoire = new HashMap<Integer,LinkedList <Double>>();
				hMath.put(math, note1);
				hFrancais.put(francais, note2);
				hAnglais.put(anglais, note3);
				hHistoire.put(histoire, note4);
				
				Etudiant etudiant = new Etudiant(nom.getText(), prenom.getText(), formattedDate, chemin, hMath,hFrancais,hAnglais,hHistoire);
				EtudiantDaoJDBC.listeEtudiant.add(etudiant);
				
				
				
				for (Etudiant etudi : EtudiantDaoJDBC.listeEtudiant) {
					System.out.println(etudi);
				}
				try {
					etu.add(etudiant);
					
					System.out.println("arrivé");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				nom.setText("");
				nom.setEffect(null);
				prenom.setText("");
				prenom.setEffect(null);
				datepick.setValue(null);
				datepick.setEffect(null);
				image.setImage(null);
				
				url.setText("");
				url.setVisible(false);
				Alert alert = new Alert(AlertType.INFORMATION, "Etudiant enregistré avec succès", ButtonType.OK);
				alert.showAndWait();

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

			} else if (datepick.getValue() == null) {
				datepick.setEffect(ds);
				Alert alert = new Alert(AlertType.ERROR, "Le champ date est vide", ButtonType.OK);
				alert.showAndWait();

			}
		});

	}

	@FXML
	public void onBtnCancel() {
		cancel.setOnAction((event) -> {
			Platform.exit();

		});

	}

	String chemin;

	public void getTheUserFilePath() {
		Stage stage = (Stage) mainPane.getScene().getWindow();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Upload File Path");
		fileChooser.getExtensionFilters()
				.addAll(new FileChooser.ExtensionFilter("IMAGE FILES", "*.jpg", "*.png", "*.gif"));

		File file = fileChooser.showOpenDialog(stage);

		if (file != null) {
			try {
				BufferedImage bufferedImage = ImageIO.read(file);
				WritableImage picture = SwingFXUtils.toFXImage(bufferedImage, null);
				image.setImage(picture);
				chemin = file.toURI().toURL().toString();
				url.setText(chemin);
				url.setVisible(true);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} else {
			Alert alert = new Alert(AlertType.NONE, "Aucun fichier choisi", ButtonType.OK);
			alert.showAndWait();
			// Alert alert = new Alert(AlertType.CONFIRMATION, "Aucun fichier choisi",
			// ButtonType.OK, ButtonType.NO, ButtonType.CANCEL);
			// alert.showAndWait();
		}

	}

	public void onclickSubMenuClose() {
		Platform.exit();
	}
	@FXML
	public void onBtnLogout() throws IOException {
		Alert alert = new Alert(AlertType.CONFIRMATION, "Voulez-vous vraiment vous déconnecter ?");
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get()==ButtonType.OK) {
		BorderPane fxmlLoader = FXMLLoader.load(getClass().getResource("Connexion.fxml"));
		mainPane.getChildren().setAll(fxmlLoader);
		}
		else {
			alert.close();
		}
	}
	@FXML
	public void onShowHelp(ActionEvent event) throws IOException {

		BorderPane fxmlLoader = FXMLLoader.load(getClass().getResource("Aide2.fxml"));
		mainPane.getChildren().setAll(fxmlLoader);
	}


}

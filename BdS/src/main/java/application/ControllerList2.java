package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicLong;

import fr.formation.afpa.dao.EtudiantDaoJDBC;
import fr.formation.afpa.domain.Etudiant;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class ControllerList2 implements Initializable {
	@FXML
	private ImageView tphoto;
	@FXML
	private TextField tdate;
	@FXML
	private TextField tprenom;
	@FXML
	private DatePicker dateText;
	@FXML
	private TextField tnom, searchBar;
	@FXML
	private ChoiceBox<String> choiceBox;
	@FXML
	AnchorPane centerPane;
	@FXML
	BorderPane mainPane;
	@FXML
	MenuBar barMenu;
	
	@FXML
	TableColumn<Etudiant, Long> colId;
	@FXML
	TableColumn<Etudiant, String> colNom;
	@FXML
	TableColumn<Etudiant, String> colPrenom;
	@FXML
	TableColumn<Etudiant, String> colDate;
	@FXML
	TableColumn<Etudiant, String> colNotes;
	@FXML
	private Button modify;

	static Etudiant etuSelect;
	static int selectIndex;
	 //private final static int dataSize = 50;
	 private final static int rowsPerPage = 10;
    @FXML
    Pagination pagination;
    @FXML
	TableView<Etudiant> table;
    private final List<Etudiant> data = createData();
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		

		addButtonToTable();
		addButtonNotesToTable();
		colId.setCellValueFactory(new PropertyValueFactory<>("idProperty"));
		colNom.setCellValueFactory(new PropertyValueFactory<>("nomProperty"));
		colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenomProperty"));
		colDate.setCellValueFactory(new PropertyValueFactory<>("dateProperty"));
		//pagination.setPageCount(count);
		pagination.setPageFactory(this::createPage);
		
	//	setTableContent(EtudiantDaoJDBC.restitutionEtudiant());
		choiceBox.getItems().addAll("Nom", "Pr�nom");
		choiceBox.setValue("Nom");
		
	 

		choiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {// reset table et la search bar qd le nouveau
																									//choix et selectionn�
			if (newVal != null) {
				searchBar.setText("");
			}
		});

		if (Etudiant.incrementId==false) {
		Long higherid=1L;
		try {
			for (Etudiant stud : EtudiantDaoJDBC.getListeEtudiant()) {
			if (higherid<stud.getIdProperty()) 
				higherid=stud.getIdProperty();
			
			Etudiant.NEXT_ID= new AtomicLong (higherid+1) ;

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("incremetation");
		Etudiant.incrementId=true;
		}

	}

    public List <Etudiant> createData(){
    	
    	List <Etudiant> data = new ArrayList<>();
	//	for (int i = 0; i < dataSize; i++) {
            try {
				data.addAll(EtudiantDaoJDBC.getListeEtudiant());
			} catch (SQLException e) {
				e.printStackTrace();
			}
         
      //  }
		
    	return data;
    }
    private Node createPage(int pageIndex) {
    	try {
        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, data.size());
        table.setItems(FXCollections.observableArrayList(data.subList(fromIndex, toIndex)));

     
    	}
    	catch(IllegalArgumentException e){
    		 Alert alert = new Alert(AlertType.WARNING, "Il n'ya plus d'étudiants", ButtonType.OK);
			 alert.showAndWait();
    		
    	}
    	   return new BorderPane(table);
    }
	public void initialize2(URL location, ResourceBundle resources) {

		colId.setCellValueFactory(new PropertyValueFactory<>("idProperty"));
		colNom.setCellValueFactory(new PropertyValueFactory<>("nomProperty"));
		colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenomProperty"));
		colDate.setCellValueFactory(new PropertyValueFactory<>("dateProperty"));

		try {
			setTableContent(EtudiantDaoJDBC.getListeEtudiant());
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void setTableContent(List<Etudiant> etu) {

		ObservableList<Etudiant> data = FXCollections.<Etudiant>observableArrayList();
		FilteredList<Etudiant> filteredListEtudiant = new FilteredList<Etudiant>(data, p -> true);// donne les donn�es � la filteredlist

		data.addAll(etu);
		table.setItems(filteredListEtudiant);
		searchBar.setPromptText("Recherchez ici!");
		searchBar.textProperty().addListener((obs, oldValue, newValue) -> {
			switch (choiceBox.getValue())// Switch sur les valeurs de la choicebox
			{
			case "Nom":
				filteredListEtudiant
						.setPredicate(p -> p.getNomProperty().toLowerCase().contains(newValue.toLowerCase().trim()));// filtre par nom																						// name
				break;
			case "Pr�nom":
				filteredListEtudiant
						.setPredicate(p -> p.getPrenomProperty().toLowerCase().contains(newValue.toLowerCase().trim()));// filtre par pr�nom
																						
				break;

			}
		});
	}

	@FXML
	public void onShowHelp(ActionEvent event) throws IOException {

		BorderPane fxmlLoader = FXMLLoader.load(getClass().getResource("Aide2.fxml"));
		mainPane.getChildren().setAll(fxmlLoader);
	}

	@FXML
	public void onShowListClick(ActionEvent event) throws IOException {

		BorderPane fxmlLoader = FXMLLoader.load(getClass().getResource("Table.fxml"));
		mainPane.getChildren().setAll(fxmlLoader);
	}

	@FXML
	public void onAjoutClickEtudiant(ActionEvent event) throws IOException {

		BorderPane fxmlLoader = FXMLLoader.load(getClass().getResource("Ajout.fxml"));
		mainPane.getChildren().setAll(fxmlLoader);
	}

	private void addButtonToTable() {

		TableColumn<Etudiant, Void> colBtn = new TableColumn<Etudiant, Void>("Action");

		Callback<TableColumn<Etudiant, Void>, TableCell<Etudiant, Void>> cellFactory = new Callback<TableColumn<Etudiant, Void>, TableCell<Etudiant, Void>>() {

			public TableCell<Etudiant, Void> call(final TableColumn<Etudiant, Void> param) {
				final TableCell<Etudiant, Void> cell = new TableCell<Etudiant, Void>() {

					private final Button btn = new Button();

					{
						File file2 = new File("modify.png");
						Image img2 = new Image(file2.toURI().toString(), 80, 20, true, true);
						ImageView view2 = new ImageView(img2);
						view2.setPreserveRatio(true);
						btn.setGraphic(view2);
						btn.setTooltip(new Tooltip("Modifier �tudiant"));
						btn.setOnAction((ActionEvent event) -> {
							selectIndex = getTableRow().getIndex();
							etuSelect = table.getSelectionModel().getSelectedItem();
							System.out.println("double clique :" + etuSelect);

							BorderPane modifetu = null;
							try {
								modifetu = FXMLLoader.load(getClass().getResource("Modif.fxml"));
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							mainPane.getChildren().setAll(modifetu);
						});
					}
					private final Button btn2 = new Button();

					{
						File file = new File("bin.png");
						Image img = new Image(file.toURI().toString(), 80, 20, true, true);
						ImageView view = new ImageView(img);
						view.setPreserveRatio(true);
						btn2.setGraphic(view);
						btn2.setTooltip(new Tooltip("Supprimer �tudiant"));
						btn2.setOnAction((ActionEvent event) -> {
							Alert alert = new Alert(AlertType.CONFIRMATION,
									"Voulez-vous vraiment supprimer cet étudiant");

							Optional<ButtonType> result = alert.showAndWait();
							if (result.get() == ButtonType.OK) {
								selectIndex = getTableRow().getIndex();
								etuSelect = table.getSelectionModel().getSelectedItem();
								System.out.println("double clique :" + etuSelect);
								EtudiantDaoJDBC.listeEtudiant.remove(selectIndex);
								EtudiantDaoJDBC f = new EtudiantDaoJDBC();
								f.add();
								initialize2(null, null);
							} else {

								alert.close();
							}
						});
					}
					HBox pane = new HBox(btn, btn2);

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(pane);

						}
					}
				};
				return cell;
			}
		};

		colBtn.setCellFactory(cellFactory);

		table.getColumns().add(colBtn);

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

	private void addButtonNotesToTable() {
		Callback<TableColumn<Etudiant, String>, TableCell<Etudiant, String>> cellFactory = new Callback<TableColumn<Etudiant, String>, TableCell<Etudiant, String>>() {

			public TableCell<Etudiant, String> call(final TableColumn<Etudiant, String> param) {
				final TableCell<Etudiant, String> cell = new TableCell<Etudiant, String>() {

					private final Button btn = new Button();
					{
						File file = new File("bd\\plus icon.png");
						Image img = new Image(file.toURI().toString(), 80, 20, true, true);
						ImageView view = new ImageView(img);
						view.setPreserveRatio(true);
						btn.setGraphic(view);
						btn.setTooltip(new Tooltip("Ajouter note(s)"));
						btn.setOnAction((ActionEvent event) -> {
							selectIndex = getTableRow().getIndex();
							etuSelect = table.getSelectionModel().getSelectedItem();

							BorderPane modifetu = null;
							try {
								modifetu = FXMLLoader.load(getClass().getResource("AjoutNotes.fxml"));
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							mainPane.getChildren().setAll(modifetu);
						});

					}
					private final Button btn2 = new Button();
					{
						File file = new File("bd\\list icon.png");
						Image img = new Image(file.toURI().toString(), 80, 20, true, true);
						ImageView view = new ImageView(img);
						view.setPreserveRatio(true);
						btn2.setGraphic(view);
						btn2.setTooltip(new Tooltip("Afficher note(s)"));
						btn2.setOnAction((ActionEvent event) -> {
							selectIndex = getTableRow().getIndex();
							etuSelect = table.getSelectionModel().getSelectedItem();

							BorderPane modifetu = null;
							try {
								modifetu = FXMLLoader.load(getClass().getResource("AfficherNotes.fxml"));
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							mainPane.getChildren().setAll(modifetu);
						});
					}
					private final Button btn3 = new Button();
					{
						File file = new File("modify.png");
						Image img = new Image(file.toURI().toString(), 80, 20, true, true);
						ImageView view = new ImageView(img);
						view.setPreserveRatio(true);
						btn3.setGraphic(view);
						btn3.setTooltip(new Tooltip("Modifier note(s)"));
						btn3.setOnAction((ActionEvent event) -> {
							selectIndex = getTableRow().getIndex();
							etuSelect = table.getSelectionModel().getSelectedItem();

							BorderPane modifetu = null;
							try {
								modifetu = FXMLLoader.load(getClass().getResource("ModifNotes.fxml"));
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							mainPane.getChildren().setAll(modifetu);
						});
					}
					private final Button btn4 = new Button();
					{
						File file = new File("bin.png");
						Image img = new Image(file.toURI().toString(), 80, 20, true, true);
						ImageView view = new ImageView(img);
						view.setPreserveRatio(true);
						btn4.setGraphic(view);
						btn4.setTooltip(new Tooltip("Supprimer note(s)"));
						btn4.setOnAction((ActionEvent event) -> {
							selectIndex = getTableRow().getIndex();
							etuSelect = table.getSelectionModel().getSelectedItem();

							BorderPane modifetu = null;
							try {
								modifetu = FXMLLoader.load(getClass().getResource("SupprNotes.fxml"));
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							mainPane.getChildren().setAll(modifetu);
						});
					}
					HBox pane = new HBox(btn,btn2,btn3,btn4);

					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(pane);
						}
					}
				};
				return cell;
			}
		};

		colNotes.setCellFactory(cellFactory);

	}
	
}
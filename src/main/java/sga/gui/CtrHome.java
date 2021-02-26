package sga.gui;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import sga.core.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Esta classe é um Controlador JavaFX.
 * Nela temos acesso aos elementos da interface gráfica definidos no arquivo fxml.
 **/
public class CtrHome implements Initializable {
    // Global Elements
    @FXML
    private Pane globalContentPanel;
    @FXML
    private Button globalMenuButtonHome;
    @FXML
    private Button globalMenuButtonCourses;
    @FXML
    private Button globalMenuButtonCoffees;
    @FXML
    private Button globalMenuButtonMembers;
    @FXML
    private Button globalMenuButtonStages;
    @FXML
    private Button globalMenuButtonSettings;
    @FXML
    private Button globalMenuButtonExit;

    // Home Panel
    @FXML
    private Pane homePanel;
    @FXML
    private Pane homeInfoStages;
    @FXML
    private Pane homeInfoCourses;
    @FXML
    private Pane homeInfoMembers;
    @FXML
    private Pane homeInfoCoffees;
    @FXML
    private Label homeInfoStagesCount;
    @FXML
    private Label homeInfoCoursesCount;
    @FXML
    private Label homeInfoCoffeesCount;
    @FXML
    private Label homeInfoMembersCount;

    // Courses Panel
    @FXML
    private Pane coursesPanel;
    @FXML
    private VBox coursesList;

    // Coffees Panel
    @FXML
    private Pane coffeesPanel;
    @FXML
    private VBox coffeesList;

    // Members Panel
    @FXML
    private Pane membersPanel;
    @FXML
    private VBox membersList;
    @FXML
    private Button membersButtonRegister;

    // Stages Panel
    @FXML
    private Pane stagesPanel;

    // Settings Panel
    @FXML
    private Pane settingsPanel;
    @FXML
    private CheckBox settingsCheckboxEraseDatabase;
    @FXML
    private Button settingsButtonSave;
    @FXML
    private Button settingsButtonAbout;

    // Course Registration Panel
    @FXML
    private Pane courseRegistrationPanel;
    @FXML
    private Button courseRegistrationButtonSave;

    // Coffee Registration Panel
    @FXML
    private Pane coffeeRegistrationPanel;
    @FXML
    private Button coffeeRegistrationButtonSave;

    // Member Registration Panel
    @FXML
    private Pane memberRegistrationPanel;
    @FXML
    private TextField memberRegistrationName;
    @FXML
    private TextField memberRegistrationSurname;
    @FXML
    private Button memberRegistrationButtonSave;

    // Data Access Objects
    private Dao<Member> membersDao;
    private Dao<Room> coursesDao;
    private Dao<Room> coffeesDao;

    //
    int stageCount = 2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String connectionString = "mongodb://localhost:27017/test";
        try {
            MongoClient mongoClient = MongoClients.create(connectionString);
            MongoDatabase db = mongoClient.getDatabase("test");
            membersDao = new MemberDao(db.getCollection("members"));
            coursesDao = new RoomDao(db.getCollection("courses"));
            coffeesDao = new RoomDao(db.getCollection("coffees"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        showPanelHome();
    }

    public void callbackHandleClicks(ActionEvent actionEvent) {
        // Mudar o painel em exibição.
        if (actionEvent.getSource() == globalMenuButtonHome) {
            showPanelHome();
        }
        if (actionEvent.getSource() == globalMenuButtonCourses) {
            showPanelCourses();
        }
        if (actionEvent.getSource() == globalMenuButtonCoffees) {
            showPanelCoffees();
        }
        if (actionEvent.getSource() == globalMenuButtonMembers) {
            showPanelMembers();
        }
        if (actionEvent.getSource() == globalMenuButtonSettings) {
            showPanelSettings();
        }
        if (actionEvent.getSource() == membersButtonRegister) {
            showMemberRegistrationPanel();
        }

        // Fechar o programa a pedido do usuário.
        if (actionEvent.getSource() == globalMenuButtonExit) {
            Platform.exit();
        }
    }

    /**
     * TODO: Salvar o último código disponível para cadastro.
     */
    public void callbackRegistration(ActionEvent actionEvent) {
        if (actionEvent.getSource() == memberRegistrationButtonSave) {
            String name = memberRegistrationName.getText();
            String surname = memberRegistrationSurname.getText();
            String id = "2021" + String.format("%04d", membersDao.count() + 1);
            Member member = new Member(id, name, surname);
            membersDao.create(member);

            showPanelMembers();
        }
    }

    public void callbackSaveSettings(ActionEvent actionEvent) {
        ButtonType buttonConfirm = new ButtonType("Sim");
        ButtonType buttonCancel = new ButtonType("Não");

        if (settingsCheckboxEraseDatabase.isSelected()) {
            String alertText = "Você tem certeza que deseja apagar todos os dados?";
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    alertText, buttonCancel, buttonConfirm);
            alert.setHeaderText("Confirmação");
            alert.setTitle("");
            alert.showAndWait();
            if (alert.getResult() == buttonConfirm) {
                MemberDao unsafeMembersDao = (MemberDao) membersDao;
                RoomDao unsafeCoursesDao = (RoomDao) coursesDao;
                RoomDao unsafeCoffeesDao = (RoomDao) coffeesDao;
                unsafeMembersDao.deleteAll();
                unsafeCoursesDao.deleteAll();
                unsafeCoffeesDao.deleteAll();
            }
            settingsCheckboxEraseDatabase.setSelected(false);
        }
    }

    public static void hideChildren(Pane parent) {
        for (Node node : parent.getChildren()) {
            node.setVisible(false);
            node.managedProperty().bind(node.visibleProperty());
        }
    }

    public void showPanelHome() {
        homeInfoStagesCount.setText(String.valueOf(stageCount));
        homeInfoCoursesCount.setText(String.valueOf(coursesDao.count()));
        homeInfoCoffeesCount.setText(String.valueOf(coffeesDao.count()));
        homeInfoMembersCount.setText(String.valueOf(membersDao.count()));

        hideChildren(globalContentPanel);
        homePanel.setVisible(true);
        homePanel.toFront();
    }

    public void showPanelCourses() {
        hideChildren(globalContentPanel);
        coursesPanel.setVisible(true);
        coursesPanel.toFront();
    }

    public void showPanelCoffees() {
        hideChildren(globalContentPanel);
        coffeesPanel.setVisible(true);
        coffeesPanel.toFront();
    }

    public void showPanelMembers() {
        membersList.getChildren().clear();
        List<Member> members = membersDao.readAll();
        for (Member member : members) {
            HBox hBox = JavaFxElements.createMember(member);
            VBox vBox = (VBox) hBox.getChildren().get(1);
            Button btn = (Button) vBox.getChildren().get(0);
            btn.setOnMousePressed(event -> showMemberDetails(member));
            membersList.getChildren().add(hBox);
        }

        hideChildren(globalContentPanel);
        membersPanel.setVisible(true);
        membersPanel.toFront();
    }

    private void showPanelSettings() {
        hideChildren(globalContentPanel);
        settingsPanel.setVisible(true);
        settingsPanel.toFront();
    }

    public void showMemberDetails(Member member) {
        System.out.println(member.toString());
        memberRegistrationName.setText(member.name());
        memberRegistrationName.setDisable(true);
        memberRegistrationSurname.setText(member.surname());
        memberRegistrationSurname.setDisable(true);
        showMemberRegistrationPanel();
    }

    private void showCourseRegistrationPanel() {
        hideChildren(globalContentPanel);
        courseRegistrationPanel.setVisible(true);
        courseRegistrationPanel.toFront();
    }

    private void showCoffeeRegistrationPanel() {
        hideChildren(globalContentPanel);
        coffeeRegistrationPanel.setVisible(true);
        coffeeRegistrationPanel.toFront();
    }

    private void showMemberRegistrationPanel() {
        hideChildren(globalContentPanel);
        memberRegistrationPanel.setVisible(true);
        memberRegistrationPanel.toFront();
    }
}

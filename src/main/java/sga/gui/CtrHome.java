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
import sga.util.Pair;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    private Button globalMenuButtonSettings;
    @FXML
    private Button globalMenuButtonExit;

    // Home Panel
    @FXML
    private Pane homePanel;
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
    @FXML
    private Button coursesButtonRegister;

    // Coffees Panel
    @FXML
    private Pane coffeesPanel;
    @FXML
    private VBox coffeesList;
    @FXML
    private Button coffeesButtonRegister;

    // Members Panel
    @FXML
    private Pane membersPanel;
    @FXML
    private VBox membersList;
    @FXML
    private Button membersButtonRegister;

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
    private Pane courseRegistPanel;
    @FXML
    private TextField courseRegistName;
    @FXML
    private TextField courseRegistCapacity;
    @FXML
    private ComboBox memberRegistDropCourse1;
    @FXML
    private ComboBox memberRegistDropCourse2;
    @FXML
    private ComboBox memberRegistDropCoffee1;
    @FXML
    private ComboBox memberRegistDropCoffee2;
    @FXML
    private Button courseRegistButtonSave;

    // Coffee Registration Panel
    @FXML
    private Pane coffeeRegistPanel;
    @FXML
    private TextField coffeeRegistName;
    @FXML
    private TextField coffeeRegistCapacity;
    @FXML
    private Button coffeeRegistButtonSave;

    // Member Registration Panel
    @FXML
    private Pane memberRegistPanel;
    @FXML
    private TextField memberRegistName;
    @FXML
    private TextField memberRegistSurname;
    @FXML
    private Button memberRegistButtonSave;


    // Member Detailed Panel
    @FXML
    private Pane memberDetailPanel;
    @FXML
    private TextField memberDetailName;
    @FXML
    private TextField memberDetailSurname;
    @FXML
    private Button memberDetailButtonEdit;
    @FXML
    private ComboBox memberDetailDropCourse1;
    @FXML
    private ComboBox memberDetailDropCourse2;
    @FXML
    private ComboBox memberDetailDropCoffee1;
    @FXML
    private ComboBox memberDetailDropCoffee2;

    // Course/Coffee Detailed Panel
    @FXML
    private Pane roomDetailPanel;
    @FXML
    private Label roomDetailTitle;
    @FXML
    private VBox roomDetailMemberList;
    @FXML
    private Button roomDetailButtonNextStage;

    // Data Access Objects
    private Dao<Member> membersDao;
    private Dao<Room> coursesDao;
    private Dao<Room> coffeesDao;

    // Constants
    int stageCount = 2;
    public static final String DEFAULT_COMBOBOX_VALUE = "Selecionar Automaticamente";


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String connectionString = "mongodb://localhost:27017/test";
        try {
            MongoClient client = MongoClients.create(connectionString);
            MongoDatabase db = client.getDatabase("test");
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
            showCoursesPanel();
        }
        if (actionEvent.getSource() == globalMenuButtonCoffees) {
            showCoffeesPanel();
        }
        if (actionEvent.getSource() == globalMenuButtonMembers) {
            showMembersPanel();
        }
        if (actionEvent.getSource() == globalMenuButtonSettings) {
            showPanel(settingsPanel);
        }
        if (actionEvent.getSource() == coursesButtonRegister) {
            showCourseRegistPanel();
        }
        if (actionEvent.getSource() == coffeesButtonRegister) {
            showCoffeeRegistPanel();
        }
        if (actionEvent.getSource() == membersButtonRegister) {
            showMemberRegistPanel();
        }

        // Fechar o programa a pedido do usuário.
        if (actionEvent.getSource() == globalMenuButtonExit) {
            Platform.exit();
        }
    }

    public void showErrorPopup(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setTitle("");
        alert.setHeaderText("Dados Inválidos");
        alert.showAndWait();
    }

    public void callbackRegistration(ActionEvent actionEvent) {
        if (actionEvent.getSource() == courseRegistButtonSave) {
            System.out.println("Cadastrando Curso!");
            String id = "SALA" + String.format("%03d", coursesDao.count() + 1);
            String name = courseRegistName.getText();
            String cap = courseRegistCapacity.getText();
            if (name == "") {
                showErrorPopup("Favor preencher corretamente o nome da sala.");
                return;
            }
            if (cap == "" || !cap.matches("-?\\d+")) {
                showErrorPopup("Favor preencher corretamente a capacidade da sala.");
                return;
            }
            coursesDao.create(new Room(id, name, Integer.parseInt(cap)));
            showCoursesPanel();
        }
        if (actionEvent.getSource() == coffeeRegistButtonSave) {
            System.out.println("Cadastrando Café!");
            String id = "CAFE" + String.format("%03d", coffeesDao.count() + 1);
            String name = coffeeRegistName.getText();
            String cap = coffeeRegistCapacity.getText();
            if (name == "") {
                showErrorPopup("Favor preencher corretamente o nome do café.");
                return;
            }
            if (cap == "" || !cap.matches("-?\\d+")) {
                showErrorPopup("Favor preencher corretamente a capacidade do café.");
                return;
            }
            coffeesDao.create(new Room(id, name, Integer.parseInt(cap)));
            showCoffeesPanel();
        }
        if (actionEvent.getSource() == memberRegistButtonSave) {
            String name = memberRegistName.getText();
            String surname = memberRegistSurname.getText();
            if (name == "") {
                showErrorPopup("Favor preencher corretamente o nome do membro.");
                return;
            }
            if (surname == "") {
                showErrorPopup("Favor preencher corretamente o sobrenome do membro.");
                return;
            }

            // TODO: Salvar o último código de membro disponível para cadastro.
            String id = "2021" + String.format("%04d", membersDao.count() + 1);

            List<Pair<String, String>> stages = new ArrayList<>();
            stages.add(new Pair(
                getRoomForStage(0, coursesDao, id),
                getRoomForStage(0, coffeesDao, id)
            ));
            stages.add(new Pair(
                getRoomForStage(1, coursesDao, id),
                getRoomForStage(1, coffeesDao, id)
            ));

            Member member = new Member(id, name, surname, stages);
            membersDao.create(member);

            showMembersPanel();
        }
    }

    public String getRoomForStage(int stageId, Dao<Room> rooms, String member_id) {
        int minMemberCount = Integer.MAX_VALUE;
        Room selectedRoom = null;
        for (Room room : rooms.readAll()) {
            int memberCount = room.member_ids().get(stageId).size();
            if (memberCount < minMemberCount) {
                System.out.println(room.name());
                System.out.println("memberCount" + Integer.toString(memberCount)
                        + " | minMemberCount" + Integer.toString(minMemberCount));
                minMemberCount = memberCount;
                selectedRoom = room;
            }
        }
        if (selectedRoom != null) {
            selectedRoom.addMember(stageId, member_id);
            rooms.update(selectedRoom);
            return selectedRoom.id();
        }

        return "";
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

    private void showPanel(Pane panel) {
        hideChildren(globalContentPanel);
        panel.setVisible(true);
        panel.toFront();
    }

    public void showPanelHome() {
        homeInfoStagesCount.setText(String.valueOf(stageCount));
        homeInfoCoursesCount.setText(String.valueOf(coursesDao.count()));
        homeInfoCoffeesCount.setText(String.valueOf(coffeesDao.count()));
        homeInfoMembersCount.setText(String.valueOf(membersDao.count()));

        showPanel(homePanel);
    }

    private void showCoursesPanel() {
        coursesList.getChildren().clear();
        List<Room> courses = coursesDao.readAll();
        for (Room course : courses) {
            HBox hBox = JavaFxElements.createRoom(course);
            VBox vBox = (VBox) hBox.getChildren().get(1);
            Button btn = (Button) vBox.getChildren().get(0);
            btn.setOnMousePressed(event -> showRoomDetails(course));
            coursesList.getChildren().add(hBox);
        }
        showPanel(coursesPanel);
    }

    private void showCoffeesPanel() {
        coffeesList.getChildren().clear();
        List<Room> coffees = coffeesDao.readAll();
        for (Room coffee : coffees) {
            HBox hBox = JavaFxElements.createRoom(coffee);
            VBox vBox = (VBox) hBox.getChildren().get(1);
            Button btn = (Button) vBox.getChildren().get(0);
            btn.setOnMousePressed(event -> showRoomDetails(coffee));
            coffeesList.getChildren().add(hBox);
        }
        showPanel(coffeesPanel);
    }

    public void showMembersPanel() {
        membersList.getChildren().clear();
        List<Member> members = membersDao.readAll();
        for (Member member : members) {
            HBox hBox = JavaFxElements.createMember(member);
            VBox vBox = (VBox) hBox.getChildren().get(1);
            Button btn = (Button) vBox.getChildren().get(0);
            btn.setOnMousePressed(event -> showMemberDetails(member));
            membersList.getChildren().add(hBox);
        }
        showPanel(membersPanel);
    }

    public void showMemberDetails(Member member) {
        System.out.println(member.toString());
        memberDetailName.setText(member.name());
        memberDetailName.setDisable(true);
        memberDetailSurname.setText(member.surname());
        memberDetailSurname.setDisable(true);

        clearAndFillComboboxRoom(memberDetailDropCourse1, coursesDao.readAll());
        clearAndFillComboboxRoom(memberDetailDropCourse2, coursesDao.readAll());
        clearAndFillComboboxRoom(memberDetailDropCoffee1, coffeesDao.readAll());
        clearAndFillComboboxRoom(memberDetailDropCoffee2, coffeesDao.readAll());

        memberDetailDropCourse1.setValue(member.stages().get(0).first());
        memberDetailDropCoffee1.setValue(member.stages().get(0).second());
        memberDetailDropCourse2.setValue(member.stages().get(1).first());
        memberDetailDropCoffee2.setValue(member.stages().get(1).second());

        showPanel(memberDetailPanel);
    }

    public void showRoomDetails(Room course) {
        roomDetailMemberList.getChildren().clear();
        List<String> member_ids = course.member_ids().get(0);
        for (String m_id : member_ids) {
            Member member = membersDao.read(m_id).get();
            HBox hBox = JavaFxElements.createMember(member);
            VBox vBox = (VBox) hBox.getChildren().get(1);
            Button btn = (Button) vBox.getChildren().get(0);
            btn.setOnMousePressed(event -> showMemberDetails(member));
            roomDetailMemberList.getChildren().add(hBox);
        }
        showPanel(roomDetailPanel);
    }

    void clearAndFillComboboxRoom(ComboBox comboBox, List<Room> rooms) {
        comboBox.getItems().clear();
        comboBox.getItems().add(DEFAULT_COMBOBOX_VALUE);
        comboBox.getSelectionModel().select(0);

        for (Room room : rooms) {
            comboBox.getItems().add(room.id());
        }
    }

    private void showMemberRegistPanel() {
        memberRegistName.setText("");
        memberRegistSurname.setText("");
        clearAndFillComboboxRoom(memberRegistDropCourse1, coursesDao.readAll());
        clearAndFillComboboxRoom(memberRegistDropCourse2, coursesDao.readAll());
        clearAndFillComboboxRoom(memberRegistDropCoffee1, coffeesDao.readAll());
        clearAndFillComboboxRoom(memberRegistDropCoffee2, coffeesDao.readAll());
        memberRegistDropCourse1.setDisable(true);
        memberRegistDropCourse2.setDisable(true);
        memberRegistDropCoffee1.setDisable(true);
        memberRegistDropCoffee2.setDisable(true);

        showPanel(memberRegistPanel);
    }

    private void showCourseRegistPanel() {
        courseRegistName.setText("Sala #" + String.format("%02d", coursesDao.count() + 1));
        showPanel(courseRegistPanel);
    }

    private void showCoffeeRegistPanel() {
        coffeeRegistName.setText("Café #" + String.format("%02d", coffeesDao.count() + 1));
        showPanel(coffeeRegistPanel);
    }
}

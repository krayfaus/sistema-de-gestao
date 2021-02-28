package sga.gui;

import sga.core.Member;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import sga.core.Room;

import static javafx.scene.layout.Region.USE_PREF_SIZE;
import static sga.gui.JavaFxUtils.getResourcePath;

public class JavaFxElements {
    public static Separator createSeparator(int width, int height) {
        Separator separator = new Separator();
        separator.setOrientation(Orientation.VERTICAL);
        separator.setPrefWidth(width);
        separator.setPrefHeight(height);
        separator.setMaxWidth(USE_PREF_SIZE);
        separator.setMinWidth(USE_PREF_SIZE);
        separator.setMaxHeight(USE_PREF_SIZE);
        separator.setMinHeight(USE_PREF_SIZE);
        separator.setValignment(VPos.CENTER);

        return separator;
    }

    public static Label createLabel(String text) {
        return new Label(text);
    }

    public static HBox createRoom(Room room) {
        HBox in_hbox = new HBox();
        HBox ex_hbox = new HBox();
        VBox bt_vbox = new VBox();

        int sp = 40, w1 = 600, w2 = 695, h = 53;
        Color textColor = Color.web("0xb7c3d7",1.0);

        Label code = createLabel(room.id());
        code.setPadding(new Insets(0,sp,0,sp));
        code.setMinWidth(30);
        code.setTextFill(textColor);

        Label name = createLabel(room.name());
        name.setPadding(new Insets(0,sp,0,sp));
        name.setMinWidth(400);
        name.setMaxWidth(400);
        name.setTextFill(textColor);


        Separator sep = createSeparator(1, sp);
        sep.setPadding(new Insets(0,sp,0,sp));

        in_hbox.setAlignment(Pos.CENTER_LEFT);
        in_hbox.setPrefHeight(h);
        in_hbox.setPrefWidth(w1);
        in_hbox.getChildren().add(code);

        in_hbox.getChildren().add(sep);
        in_hbox.getChildren().add(name);

        ex_hbox.setStyle("-fx-background-color: #02030A; -fx-background-radius: 5; -fx-background-insets: 0;");
        ex_hbox.setPrefHeight(h);
        ex_hbox.setPrefWidth(w2);
        ex_hbox.getChildren().add(in_hbox);

        Button more = new Button("detalhes");
        more.getStylesheets().add(getResourcePath("button_style.css"));

        bt_vbox.setAlignment(Pos.CENTER);
        bt_vbox.getChildren().add(more);
        ex_hbox.getChildren().add(bt_vbox);

        return ex_hbox;
    }

    public static HBox createMember(Member member) {
        HBox in_hbox = new HBox();
        HBox ex_hbox = new HBox();
        VBox bt_vbox = new VBox();

        int spacing = 40;
        Color textColor = Color.web("0xb7c3d7",1.0);
        Label code = createLabel(member.id());
        Label name = createLabel(member.fullName());
        code.setPadding(new Insets(0,40,0,40));
        name.setPadding(new Insets(0,40,0,40));
        code.setMinWidth(30);
        name.setMinWidth(400);
        name.setMaxWidth(400);

        code.setTextFill(textColor);
        name.setTextFill(textColor);

        Separator sep = createSeparator(1, 40);
        sep.setPadding(new Insets(0,40,0,40));

        in_hbox.setAlignment(Pos.CENTER_LEFT);
        in_hbox.setPrefHeight(53);
        in_hbox.setPrefWidth(600);
        in_hbox.getChildren().add(code);

        in_hbox.getChildren().add(sep);
        in_hbox.getChildren().add(name);

        ex_hbox.setStyle("-fx-background-color: #02030A; -fx-background-radius: 5; -fx-background-insets: 0;");
        ex_hbox.setPrefHeight(53);
        ex_hbox.setPrefWidth(695);
        ex_hbox.getChildren().add(in_hbox);

        Button more = new Button("detalhes");
        more.getStylesheets().add(getResourcePath("button_style.css"));

        bt_vbox.setAlignment(Pos.CENTER);
        bt_vbox.getChildren().add(more);

        ex_hbox.getChildren().add(bt_vbox);

        return ex_hbox;
    }
}
//Written by: Nick Lewandowski
//This code is the Main GUI for my project as well as some additional functions.
package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Array;


public class GUI extends Application {
     HBox hb1 = new HBox();
     HBox hb2 = new HBox();

    TableView<Person> table = new TableView<>();
    ObservableList<Person> data = FXCollections.observableArrayList();

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());

        try {
            Controller.readPhoneBook();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        stage.setTitle("Phonebook");
        stage.setWidth(550);
        stage.setHeight(550);

        Label label = new Label("Phone Book");
        label.setFont(new Font("Arial", 20));
        label.setTranslateX(198);

        table.setEditable(true);



        TableColumn firstNameCol = new TableColumn("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));

        firstNameCol.setMinWidth(100);

        TableColumn lastNameCol = new TableColumn("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));
        lastNameCol.setMinWidth(100);

        TableColumn numberCol = new TableColumn("Phone Number");
        numberCol.setCellValueFactory(new PropertyValueFactory<Person, String>("number"));
        numberCol.setMinWidth(100);

        TableColumn noteCol = new TableColumn("note");
        noteCol.setCellValueFactory(new PropertyValueFactory<Person, String>("note"));
        noteCol.setMinWidth(200);

        table.setItems(data);
        table.getColumns().addAll(firstNameCol, lastNameCol, numberCol, noteCol);

        TextField addFirstName = new TextField();
        addFirstName.setMaxWidth(firstNameCol.getPrefWidth());
        addFirstName.setPromptText("First Name");

        TextField addLastName = new TextField();
        addLastName.setMaxWidth(lastNameCol.getPrefWidth());
        addLastName.setPromptText("Last Name");

        TextField addNumber = new TextField();
        addNumber.setMaxWidth(numberCol.getPrefWidth());
        addNumber.setPromptText("Number");

        TextField addNote = new TextField();
        addNote.setMaxWidth(noteCol.getPrefWidth());
        addNote.setPromptText("note");

        TextField searchEntry = new TextField();
        searchEntry.setMaxWidth(noteCol.getPrefWidth());
        searchEntry.setPromptText("Search");
        searchEntry.setTranslateY(10);


        //"Add" Button to add entries
        Button addButton = new Button("Add");
        addButton.setOnAction(new EventHandler <ActionEvent> () {
            @Override
            public void handle(ActionEvent e) {

                data.add(new Person(
                        addFirstName.getText(),
                        addLastName.getText(),
                        addNumber.getText(),
                        addNote.getText()));
                addFirstName.clear();
                addLastName.clear();
                addNumber.clear();
                addNote.clear();

            }
        });

        //"Delete" Button to delete entries
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent d) {
                data.remove(table.getItems());
                data.remove(table.getSelectionModel().getSelectedItem());

            }
        });

        //"Alphabetize" Button to alphabetize entries
        Button alphabetizeButton = new Button("Alphabetize");
        alphabetizeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent a) {
                table.getSortOrder().addAll(firstNameCol,lastNameCol,numberCol,noteCol);
            }
        });

        //"Search" Button to search and select and entry
        Button SearchButton =  new Button("Search Entry");
        SearchButton.setTranslateX(0);
        SearchButton.setTranslateY(10);
        SearchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            }
        });

        //"SaveandQuit" Button to Save the application and quit out
        Button SaveandQuitButton = new Button("Save/Quit");
        SaveandQuitButton.setTranslateX(259);
        SaveandQuitButton.setPrefWidth(80);
        SaveandQuitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent sq) {

                Person[] SaveandQuit = new Person[data.size()];
                for(int i = 0; i < data.size(); i++){
                    SaveandQuit[i] = data.get(i);
                }
                FileOutputStream out = null;

                try {
                    out = new FileOutputStream("PhoneBook.txt");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                PrintStream P = new PrintStream( out );

                for (int i=0; i < data.size() ; i++) {
                    P.println( SaveandQuit[i].getFirstName() + "\t"  + SaveandQuit[i].getLastName() + "\t" + SaveandQuit[i].getNumber() +
                            "\t" + SaveandQuit[i].getNote());
                }
                Platform.exit();
            }
        });

        hb1.getChildren().addAll(addFirstName, addLastName, addNumber,addNote, addButton, deleteButton, alphabetizeButton);
        hb1.setSpacing(3);

        hb2.getChildren().addAll(searchEntry,SearchButton, SaveandQuitButton);
        hb2.setSpacing(3);



        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table, hb1, hb2);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }
}
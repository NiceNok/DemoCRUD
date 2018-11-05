package sample.Controllers;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.Const;
import sample.Models.ModelTable;
import sample.Models.ModelTableProd;

import static sample.DBConn.getConnection;


public class MainController implements Initializable {

    @FXML
    TableView<ModelTable> table;
    @FXML
    TableColumn<ModelTable, String> souv_id;
    @FXML
    TableColumn<ModelTable, String> souv_name;
    @FXML
    TableColumn<ModelTable, String>  producer;
    @FXML
    TableColumn<ModelTable, String>  year;
    @FXML
    TableColumn<ModelTable, String>  price;

    @FXML
    TableView<ModelTableProd> table1;
    @FXML
    TableColumn<ModelTableProd, String> prod_id;
    @FXML
    TableColumn<ModelTableProd, String> prod_name;
    @FXML
    TableColumn<ModelTableProd, String> country;


    @FXML
    Button addProd, addSouv;

    public static ObservableList<ModelTable> oblist = FXCollections.observableArrayList();
    public static ObservableList<ModelTableProd> oblist1 = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //DB

        try {
            Connection con = getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM " + Const.SOUV_TABLE);
            while (rs.next()) {
                oblist.add(new ModelTable(
                        rs.getInt(Const.SOUV_ID),
                        rs.getString(Const.SOUV_NAME),
                        rs.getString(Const.SOUV_PROD),
                        rs.getInt(Const.SOUV_YEAR),
                        rs.getDouble(Const.SOUV_PRICE)));
            }
        }catch (SQLException ex){
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("SQLExc");
        }

        try {
            Connection con = getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM " + Const.PROD_TABLE);
            while (rs.next()) {
                oblist1.add(new ModelTableProd(
                        rs.getInt(Const.PROD_ID),
                        rs.getString(Const.PROD_NAME),
                        rs.getString(Const.PROD_COUNTRY)));
            }
        }catch (SQLException ex){
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("SQLExc");
        }

        //main table

        prod_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        prod_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        country.setCellValueFactory(new PropertyValueFactory<>("country"));

        souv_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        souv_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        producer.setCellValueFactory(new PropertyValueFactory<>("producer"));
        year.setCellValueFactory(new PropertyValueFactory<>("year"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        table.setEditable(true);
        table1.setEditable(true);


        table.setItems(oblist);
        table1.setItems(oblist1);

        MakeEditable();
        ButtonsHandler();
        ContextMenu();
    }

    private void ButtonsHandler(){
        addSouv.setOnAction(event -> {
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddSouvenir.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setTitle("Adding souvenir");
                stage.setScene(new Scene(root1));
                stage.show();
                stage.setResizable(false);
            }
            catch (IOException exc){
                System.out.println("error");
            }
        }); addProd.setOnAction(event -> {
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddProducer.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setTitle("Adding souvenir");
                stage.setScene(new Scene(root1));
                stage.show();
                stage.setResizable(false);
            }
            catch (IOException exc){
                System.out.println("error");
            }
        });
    }
    private void ContextMenu(){
        MenuItem editor = new MenuItem ("Edit");
        editor.setOnAction(event -> {
            ModelTable selectedItem = table.getSelectionModel().getSelectedItem();

        });
        MenuItem mi1 = new MenuItem("Delete stroke");
        mi1.setOnAction(event -> {
            System.out.println("deleted");
            Object selectedItem = table.getSelectionModel().getSelectedItem();
            table.getItems().remove(selectedItem);
        });

        ContextMenu menu = new ContextMenu();
        menu.getItems().addAll(mi1,editor);
        table.setContextMenu(menu);
        //----------------------------------
        MenuItem mi11 = new MenuItem("Delete stroke");
        mi11.setOnAction(event -> {
            System.out.println("deleted");
            Object selectedItem1 = table1.getSelectionModel().getSelectedItem();
            table1.getItems().remove(selectedItem1);
        });

        ContextMenu menu1 = new ContextMenu();
        menu1.getItems().add(mi11);
        table1.setContextMenu(menu1);
    }
    private   void MakeEditable(){
        //table producers
        prod_name.setCellValueFactory(
                new PropertyValueFactory<ModelTableProd, String>("name"));
        prod_name.setCellFactory(TextFieldTableCell.forTableColumn());
        prod_name.setOnEditCommit(
                new EventHandler<CellEditEvent<ModelTableProd, String>>() {
                    @Override
                    public void handle(CellEditEvent<ModelTableProd, String> t) {
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setName(t.getNewValue());
                    }
                }
        );



        country.setCellValueFactory(
                new PropertyValueFactory<ModelTableProd, String>("country"));
        country.setCellFactory(TextFieldTableCell.forTableColumn());
        country.setOnEditCommit(
                new EventHandler<CellEditEvent<ModelTableProd, String>>() {
                    @Override
                    public void handle(CellEditEvent<ModelTableProd, String> t) {
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setCountry(t.getNewValue());
                    }
                }
        );


        //table souvenirs
        souv_name.setCellValueFactory(
                new PropertyValueFactory<ModelTable, String>("name"));
        souv_name.setCellFactory(TextFieldTableCell.forTableColumn());
        souv_name.setOnEditCommit(
                new EventHandler<CellEditEvent<ModelTable, String>>() {
                    @Override
                    public void handle(CellEditEvent<ModelTable, String> t) {
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setName(t.getNewValue());
                    }
                }
        );
        producer.setCellValueFactory(
                new PropertyValueFactory<ModelTable, String>("producer"));
        producer.setCellFactory(TextFieldTableCell.forTableColumn());
        producer.setOnEditCommit(
                new EventHandler<CellEditEvent<ModelTable, String>>() {
                    @Override
                    public void handle(CellEditEvent<ModelTable, String> t) {
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setProducer(t.getNewValue());
                    }
                }
        );
       


    }
}

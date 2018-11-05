package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sample.Const;
import sample.DBConn;
import sample.Models.ModelTable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static sample.Controllers.MainController.oblist;
import static sample.DBConn.getConnection;


public class SouvController{
    @FXML
    Button addSouv;
    @FXML
    TextField id, name, producer, year, price;

    @FXML
    void initialize() {
        DBConn dbconn = new DBConn();

        addSouv.setOnAction(event->{
            Integer t1 = Integer.parseInt(id.getText());
            Integer t2 = Integer.parseInt(year.getText());
            Double t3 = Double.parseDouble(price.getText());
            dbconn.addSouvenir(
                    name.getText(),
                    producer.getText(),
                    t2,
                    t3);
            try {
                Connection con = getConnection();
                ResultSet rs = con.createStatement().executeQuery("SELECT * FROM " + Const.SOUV_TABLE);
                if (rs.last()) {
                    oblist.add(new ModelTable(
                            rs.getInt(Const.SOUV_ID),
                            rs.getString(Const.SOUV_NAME),
                            rs.getString(Const.SOUV_PROD),
                            rs.getInt(Const.SOUV_YEAR),
                            rs.getDouble(Const.SOUV_PRICE)));
                }
            }catch (SQLException ex){
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

    }}

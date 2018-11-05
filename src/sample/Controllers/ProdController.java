package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sample.Const;
import sample.DBConn;
import sample.Models.ModelTable;
import sample.Models.ModelTableProd;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static sample.Controllers.MainController.oblist;
import static sample.Controllers.MainController.oblist1;
import static sample.DBConn.getConnection;

public class ProdController{
    @FXML
    Button addProd;
    @FXML
    TextField id, name, country;

    @FXML
    void initialize() {
        DBConn dbconn = new DBConn();

        addProd.setOnAction(event->{
            Integer t1 = Integer.parseInt(id.getText());
            dbconn.addProducer(

                    name.getText(),
                    country.getText());
            try {
                Connection con = getConnection();
                ResultSet rs = con.createStatement().executeQuery("SELECT * FROM " + Const.PROD_TABLE);
                if (rs.last()) {
                    oblist1.add(new ModelTableProd(
                            rs.getInt(Const.PROD_NAME),
                            rs.getString(Const.PROD_NAME),
                            rs.getString(Const.PROD_COUNTRY)));
                }
            }catch (SQLException ex){
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

    }}


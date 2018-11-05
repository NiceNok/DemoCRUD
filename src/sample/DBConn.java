package sample;

import java.sql.*;

public class DBConn {
    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3305/Souvenirs?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
        return connection;
    }

    public void addSouvenir(String name, String producer, int year, double price) {

        String insert = "INSERT INTO " + Const.SOUV_TABLE + "(" +
                Const.SOUV_NAME + "," +
                Const.SOUV_PROD + "," +
                Const.SOUV_YEAR + "," +
                Const.SOUV_PRICE + ")" +
                "VALUES(?,?,?,?)";
        try {
            PreparedStatement prst = getConnection().prepareStatement(insert);
            prst.setString(1, name);
            prst.setString(2, producer);
            prst.setInt(3,year);
            prst.setDouble(4, price);

            prst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error");
        }
    }

    public void addProducer(String name, String country) {

        String insert = "INSERT INTO " + Const.PROD_TABLE + "(" +
                Const.PROD_NAME + "," +
                Const.PROD_COUNTRY + ")" +

                "VALUES(?,?)";
        try {
            PreparedStatement prst = getConnection().prepareStatement(insert);
            prst.setString(1, name);
            prst.setString(2, country);


            prst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error");
        }
    }
   public void countPile(String name, Integer am){

        String upd = "UPDATE Piles SET amount =? WHERE cargo =?";


        try {
            PreparedStatement prss = getConnection().prepareStatement(upd);
            prss.setInt(1,am);
            prss.setString(2,name);

            prss.executeUpdate();
        } catch (Exception e) {
            System.out.println("fiasco");
        }
    }
}
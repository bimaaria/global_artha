/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bima PC
 */
public class koneksi {
    private static Connection koneksi;
    public static Connection conn(){
        if(koneksi==null){
            try{
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                koneksi=DriverManager.getConnection("jdbc:mysql://localhost:3306/tokolaptop","root","");
            }catch (SQLException ex){
                Logger.getLogger(koneksi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return koneksi;
    }
}

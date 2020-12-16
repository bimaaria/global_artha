/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package laporan;
import java.io.File;
import java.sql.Connection;
import koneksi.koneksi;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Bima PC
 */
public class fungsicetak {
    public fungsicetak(String namareport){
        try{
            koneksi objkoneksi=new koneksi();
            Connection conn=objkoneksi.connect();
            File report_File=new File(namareport);
            JasperReport jasperReport=(JasperReport)JRLoader.loadObject(report_File.getPath());
            JasperPrint jasperPrint=JasperFillManager.fillReport(jasperReport, null, conn);
            JasperViewer.viewReport(jasperPrint, false);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
}

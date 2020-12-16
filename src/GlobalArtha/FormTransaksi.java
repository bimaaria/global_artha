/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GlobalArtha;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import laporan.fungsicetak;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Bima PC
 */
public class FormTransaksi extends javax.swing.JFrame {
private DefaultTableModel TabModel;
    Connection conn;
    Statement stm;
    ResultSet rs;
    /**
     * Creates new form FormTransaksi
     */
    public FormTransaksi() {
        initComponents();
        tStok.setVisible(false);
        SiapIsi(false);
        TombolNormal();
        
        Object header[]={"ID BARANG","MEREK","HARGA","JUMLAH","SUBTOTAL"};
        TabModel=new DefaultTableModel(null, header);
    }

    public Connection setKoneksi(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn=DriverManager.getConnection("jdbc:mysql://localhost/tokolaptop","root","");
            stm=conn.createStatement();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Koneksi Gagal:"+e);
        }
        return conn;
    }
    
     private void SiapIsi(boolean a){
        tIdTransaksi.setEnabled(a);
        tIdBarang.setEnabled(a);
        tMerek.setEnabled(a);
        tHarga.setEnabled(a);
        tJumlah.setEnabled(a);
        tSubTotal.setEnabled(a);
        tTotal.setEnabled(a);
        tBayar.setEnabled(a);
        tKembali.setEnabled(a);
        tStok.setEnabled(a);
        
    }
     
     private void TombolNormal(){
        bOn.setEnabled(true);
        bTambah.setEnabled(false);
        bProses.setEnabled(false);        
        bCariBarang.setEnabled(false);
    }
     
      private void bersih(){
        tIdTransaksi.setText("");
        tIdBarang.setText("");
        tMerek.setText("");
        tHarga.setText("");
        tJumlah.setText("");
        tSubTotal.setText("0");
        tTotal.setText("0");
        tBayar.setText("0");
        tKembali.setText("");
        tStok.setText("");
        
    }
      
      private void notransaksi(){
       try{
           setKoneksi();
           String sql="select right(id_transaksi,2)+1 from penjualan";
           ResultSet rs=stm.executeQuery(sql);
           if(rs.next()){
           rs.last();
           String no=rs.getString(1);
           while (no.length()<3){
               no="0"+no;
               tIdTransaksi.setText("TR"+no);}
       }
           else
           {
                   tIdTransaksi.setText("TR001");
       }
       } catch (Exception e)
       {
    }
    }
      
      private void proses(){
        try{
           Date skrg=new Date();
           SimpleDateFormat frm=new SimpleDateFormat("yyyy-MM-dd");
           String tanggal=frm.format(skrg); 
 
            int t = tblKeranjang.getRowCount();
             for(int i=0;i<t;i++)    
            {
            String id_barang=tblKeranjang.getValueAt(i, 0).toString();
            String merek=tblKeranjang.getValueAt(i, 1).toString();
            int harga= Integer.parseInt(tblKeranjang.getValueAt(i, 2).toString());
            int jumlah= Integer.parseInt(tblKeranjang.getValueAt(i, 3).toString());            
            int subtot= Integer.parseInt(tblKeranjang.getValueAt(i, 4).toString());
         
            String sql ="insert into penjualan values('"+tIdTransaksi.getText()
                    +"','"+tanggal+"','"
                    +id_barang+"','"
                    +merek+"','"
                    +harga+"','"
                    +jumlah+"','"
                    +subtot+"','"
                    +tTotal.getText()+"','"
                    +tBayar.getText()+"','"
                    +tKembali.getText()+"')";
            
             stm.executeUpdate(sql);
             
            }         
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "SIMPAN TRANSAKSI PENJUALAN GAGAL");
        }
        
    }
      
      private void perbaruistok(){
        try{
            setKoneksi();
            String sql="update barang set stok='"+tStok.getText()
                    +"' where id_barang='"+tIdBarang.getText()+"'";
            stm.executeUpdate(sql);
            JOptionPane.showMessageDialog(null,"Stok Diperbarui","",JOptionPane.INFORMATION_MESSAGE);
        } 
        catch(Exception e){
        }
        tabeltransaksi();
        
    }
      
      public void tabeltransaksi(){
        Object header[]={"ID TRANSAKSI","TANGGAL","ID BARANG","MEREK","HARGA","JUMLAH","SUBTOTAL","TOTAL","BAYAR","KEMBALIAN"};
        DefaultTableModel data=new DefaultTableModel(null,header);
        tblTransaksi.setModel(data);
        setKoneksi();
        String sql="select*from penjualan";
        try {
            ResultSet rs=stm.executeQuery(sql);
            while (rs.next())
            {
                String kolom1=rs.getString(1);
                String kolom2=rs.getString(2);
                String kolom3=rs.getString(3);
                String kolom4=rs.getString(4);
                String kolom5=rs.getString(5);
                String kolom6=rs.getString(6);
                String kolom7=rs.getString(7);
                String kolom8=rs.getString(8);
                String kolom9=rs.getString(9);
                String kolom10=rs.getString(10);
                
                String kolom[]={kolom1,kolom2,kolom3,kolom4,kolom5,kolom6,kolom7,kolom8,kolom9,kolom10,};
                data.addRow(kolom);
            }
        } catch (Exception e) {
        }
    }
      
      public void tabelinventory(){
        Object header[]={"ID BARANG","NAMA","HARGA","STOK"};
        DefaultTableModel data=new DefaultTableModel(null,header);
        tabelinventory.setModel(data);
        setKoneksi();
        String sql="select*from barang";
        try {
            ResultSet rs=stm.executeQuery(sql);
            while (rs.next())
            {
                String kolom1=rs.getString(1);
                String kolom2=rs.getString(2);
                String kolom3=rs.getString(3);
                String kolom4=rs.getString(4);
                
                String kolom[]={kolom1,kolom2,kolom3,kolom4};
                data.addRow(kolom);
            }
        } catch (Exception e) {
        }
    }
      
      public void hitungstok(){
        int jumlahbeli=Integer.parseInt(tJumlah.getText());
        int stok=Integer.parseInt(tStok.getText());
        
        int total=jumlahbeli-stok;
        tStok.setText(Integer.toString(total));
    }
      
      public void ambildata() {
        try {
            tblKeranjang.setModel(TabModel);
                String kolom1 = tIdBarang.getText();
                String kolom2 = tMerek.getText();
                String kolom3 = tHarga.getText();
                String kolom4 = tJumlah.getText();
                String kolom5 = tSubTotal.getText();
                String[] kolom = {kolom1, kolom2, kolom3, kolom4, kolom5,};
                TabModel.addRow(kolom);
          }
          catch (Exception ex) {
              JOptionPane.showMessageDialog(null, "Data gagal disimpan");
          }     
    }
      
      public void nota(){
          
//          java.sql.Connection con = null;
//          try {
//              String jdbcDriver = "com.mysql.jdbc.Driver";
//              Class.forName(jdbcDriver);
//              
//              String url = "jdbc:mysql://localhost:3306/tokolaptop";
//              String user = "root";
//              String pass = "";
//              
//              con = DriverManager.getConnection(url, user, pass);
//              Statement stm = con.createStatement();
//              
//              try{
//                  String report = ("./src/report/Nota.jrxml");
//                  HashMap hash = new HashMap();
//                  
//                  hash.put("kode", tIdTransaksi.getText());
//                  JasperReport JRpt = JasperCompileManager.compileReport(report);
//                  JasperPrint Jprint = JasperFillManager.fillReport(JRpt, hash, con);
//                  JasperViewer.viewReport(Jprint, false);
//              }catch (Exception rptexcpt){
//                  System.out.println("Report Gagal Karena : " + rptexcpt);
//              }
//          }catch (Exception e){
//              System.out.println(e);
//          }

java.sql.Connection con = null;
          try {
              String jdbcDriver = "com.mysql.jdbc.Driver";
              Class.forName(jdbcDriver);
              
              String url = "jdbc:mysql://localhost:3306/tokolaptop";
              String user = "root";
              String pass = "";
              
              con = DriverManager.getConnection(url, user, pass);
              Statement stm = con.createStatement();
              
              try{
                  String RealPath ="src/icon/";
                  String report = ("./src/report/Nota.jrxml");
                  HashMap hash = new HashMap();
                  
                  hash.put("kode", tIdTransaksi.getText());
                  hash.put("RealPath",RealPath);
                  InputStream gambar;
                  gambar = getClass().getResourceAsStream("logo.png");
                  JasperReport JRpt = JasperCompileManager.compileReport(report);
                  JasperPrint Jprint = JasperFillManager.fillReport(JRpt, hash, con);
                  JasperViewer.viewReport(Jprint, false);
              }catch (Exception rptexcpt){
                  System.out.println("Report Gagal Karena : " + rptexcpt);
              }
          }catch (Exception e){
              System.out.println(e);
          }
      }
     

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialogtabelinventory = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        txpencarianinventory = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelinventory = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        bPencarian = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jDialogtabeltransaksi = new javax.swing.JDialog();
        jPanel6 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblTransaksi = new javax.swing.JTable();
        tCariTransaksi = new javax.swing.JTextField();
        bCetak = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        bExit = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        tIdTransaksi = new javax.swing.JTextField();
        tIdBarang = new javax.swing.JTextField();
        tMerek = new javax.swing.JTextField();
        tHarga = new javax.swing.JTextField();
        tJumlah = new javax.swing.JTextField();
        tSubTotal = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblKeranjang = new javax.swing.JTable();
        bOn = new javax.swing.JButton();
        bTambah = new javax.swing.JButton();
        bCariBarang = new javax.swing.JButton();
        bTransaksi = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        tTotal = new javax.swing.JTextField();
        tBayar = new javax.swing.JTextField();
        tKembali = new javax.swing.JTextField();
        bProses = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        bHapus = new javax.swing.JButton();
        tStok = new javax.swing.JTextField();
        bKeluar = new javax.swing.JButton();
        bBalik = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        bMinim = new javax.swing.JButton();

        jDialogtabelinventory.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jDialogtabelinventory.setBackground(new java.awt.Color(0, 0, 51));
        jDialogtabelinventory.setMinimumSize(new java.awt.Dimension(1000, 700));
        jDialogtabelinventory.setModal(true);
        jDialogtabelinventory.setResizable(false);

        jPanel4.setBackground(new java.awt.Color(32, 191, 107));
        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));

        jPanel5.setBackground(new java.awt.Color(32, 191, 107));
        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));

        txpencarianinventory.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        txpencarianinventory.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txpencarianinventory.setText("KOLOM PENCARIAN");
        txpencarianinventory.setPreferredSize(new java.awt.Dimension(87, 30));
        txpencarianinventory.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txpencarianinventoryKeyPressed(evt);
            }
        });

        tabelinventory.setAutoCreateRowSorter(true);
        tabelinventory.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        tabelinventory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabelinventory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelinventoryMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tabelinventory);

        jLabel14.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("TABEL BARANG");

        bPencarian.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        bPencarian.setText("CARI");
        bPencarian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bPencarianActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel14)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(txpencarianinventory, javax.swing.GroupLayout.PREFERRED_SIZE, 850, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                                .addComponent(bPencarian)))))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txpencarianinventory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bPencarian))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 537, Short.MAX_VALUE)
                .addContainerGap())
        );

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/delete-icon.png"))); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("GLOBAL ARTHA COMPUTER");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jDialogtabelinventoryLayout = new javax.swing.GroupLayout(jDialogtabelinventory.getContentPane());
        jDialogtabelinventory.getContentPane().setLayout(jDialogtabelinventoryLayout);
        jDialogtabelinventoryLayout.setHorizontalGroup(
            jDialogtabelinventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jDialogtabelinventoryLayout.setVerticalGroup(
            jDialogtabelinventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialogtabelinventoryLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jDialogtabeltransaksi.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jDialogtabeltransaksi.setBackground(new java.awt.Color(0, 0, 51));
        jDialogtabeltransaksi.setMinimumSize(new java.awt.Dimension(1000, 700));
        jDialogtabeltransaksi.setModal(true);
        jDialogtabeltransaksi.setResizable(false);

        jPanel6.setBackground(new java.awt.Color(32, 191, 107));

        jPanel8.setBackground(new java.awt.Color(32, 191, 107));
        jPanel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));

        tblTransaksi.setAutoCreateRowSorter(true);
        tblTransaksi.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        tblTransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTransaksiMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblTransaksi);

        tCariTransaksi.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        tCariTransaksi.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tCariTransaksi.setText("CARI BERDASARKAN ID");
        tCariTransaksi.setPreferredSize(new java.awt.Dimension(87, 30));
        tCariTransaksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tCariTransaksiKeyPressed(evt);
            }
        });

        bCetak.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        bCetak.setText("CARI");
        bCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCetakActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("TABEL TRANSAKSI");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane4)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel8Layout.createSequentialGroup()
                        .addComponent(tCariTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 850, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(bCetak, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addGap(31, 31, 31)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tCariTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(bCetak, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE)
                .addContainerGap())
        );

        bExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/delete-icon.png"))); // NOI18N
        bExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bExitActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("GLOBAL ARTHA COMPUTER");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bExit))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bExit)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jDialogtabeltransaksiLayout = new javax.swing.GroupLayout(jDialogtabeltransaksi.getContentPane());
        jDialogtabeltransaksi.getContentPane().setLayout(jDialogtabeltransaksiLayout);
        jDialogtabeltransaksiLayout.setHorizontalGroup(
            jDialogtabeltransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jDialogtabeltransaksiLayout.setVerticalGroup(
            jDialogtabeltransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(32, 191, 107));

        jPanel2.setBackground(new java.awt.Color(32, 191, 107));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));

        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("ID TRANSAKSI");

        jLabel4.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("ID BARANG");

        jLabel5.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("MEREK");

        jLabel6.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("HARGA");

        jLabel7.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("JUMLAH");

        jLabel8.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("SUBTOTAL");

        tJumlah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tJumlahKeyPressed(evt);
            }
        });

        tblKeranjang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblKeranjang);

        bOn.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        bOn.setText("ON");
        bOn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bOnActionPerformed(evt);
            }
        });

        bTambah.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        bTambah.setText("TAMBAH");
        bTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTambahActionPerformed(evt);
            }
        });

        bCariBarang.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        bCariBarang.setText("CARI");
        bCariBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCariBarangActionPerformed(evt);
            }
        });

        bTransaksi.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        bTransaksi.setText("TABEL TRANSAKSI");
        bTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTransaksiActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("TOTAL");

        jLabel10.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("BAYAR");

        jLabel11.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("KEMBALI");

        tBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tBayarKeyPressed(evt);
            }
        });

        bProses.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        bProses.setText("PROSES");
        bProses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bProsesActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("KERANJANG");

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("FORM TRANSAKSI");

        bHapus.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        bHapus.setText("HAPUS");
        bHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bHapusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(bTransaksi))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tIdTransaksi)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(tSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                            .addComponent(tIdBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(bCariBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(tMerek, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(bHapus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(bTambah, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addComponent(bOn, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(35, 35, 35)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 110, Short.MAX_VALUE)
                                .addComponent(tTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11))
                                .addGap(93, 93, 93)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tBayar, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                    .addComponent(tKembali))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bProses))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(29, 29, 29)
                        .addComponent(bOn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(tIdTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bCariBarang)
                            .addComponent(tIdBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(37, 37, 37)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tMerek, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addGap(41, 41, 41)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bTambah)
                            .addComponent(bTransaksi))))
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(bHapus))
                    .addComponent(tTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bProses))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(tBayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(tKembali, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        tStok.setText("STOK");

        bKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/delete-icon.png"))); // NOI18N
        bKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bKeluarActionPerformed(evt);
            }
        });

        bBalik.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/arrow.png"))); // NOI18N
        bBalik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bBalikActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("GLOBAL ARTHA COMPUTER");

        bMinim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-minimize-window-16.png"))); // NOI18N
        bMinim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bMinimActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(25, 25, 25)
                        .addComponent(tStok, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bBalik)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bMinim)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bKeluar))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tStok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12)))
                    .addComponent(bKeluar)
                    .addComponent(bBalik)
                    .addComponent(bMinim))
                .addGap(8, 8, 8)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tabelinventoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelinventoryMouseClicked
        // TODO add your handling code here:
        int baris = tabelinventory.getSelectedRow();
        tIdBarang.setText(tabelinventory.getModel().getValueAt(baris, 0).toString());
        tMerek.setText(tabelinventory.getModel().getValueAt(baris, 1).toString());
        tHarga.setText(tabelinventory.getModel().getValueAt(baris, 2).toString());
        tStok.setText(tabelinventory.getModel().getValueAt(baris, 3).toString());
        jDialogtabelinventory.dispose();
    }//GEN-LAST:event_tabelinventoryMouseClicked

    private void txpencarianinventoryKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txpencarianinventoryKeyPressed
        // TODO add your handling code here:
        Object header[]={"ID BARANG","NAMA","HARGA","STOK"};
        DefaultTableModel data=new DefaultTableModel(null,header);
        tabelinventory.setModel(data);
        setKoneksi();
        String sql="Select * from barang where id_barang like '%" + txpencarianinventory.getText() + "%'" + "or merk_barang like '%" + txpencarianinventory.getText()+"%'";
        try {
            ResultSet rs=stm.executeQuery(sql);
            while (rs.next())
            {
                String kolom1=rs.getString(1);
                String kolom2=rs.getString(2);
                String kolom3=rs.getString(3);
                String kolom4=rs.getString(4);

                String kolom[]={kolom1,kolom2,kolom3,kolom4};
                data.addRow(kolom);
            }

        } catch (Exception e) {
        }
    }//GEN-LAST:event_txpencarianinventoryKeyPressed

    private void bCariBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCariBarangActionPerformed
        // TODO add your handling code here:
        jDialogtabelinventory.setLocationRelativeTo(null);
        tabelinventory();
        jDialogtabelinventory.setVisible(true);
    }//GEN-LAST:event_bCariBarangActionPerformed

    private void bTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTambahActionPerformed
        // TODO add your handling code here:
        int harga=Integer.parseInt(tHarga.getText());
        int jml=Integer.parseInt(tJumlah.getText());
        int stok=Integer.parseInt(tStok.getText());
        int total=Integer.parseInt(tTotal.getText());

        if(jml>stok){
            JOptionPane.showMessageDialog(null, "Stok barang tidak mencukupi");
        }else{

            int subtot=harga*jml;
            tSubTotal.setText(Integer.toString(subtot));

            int hasilstok=stok-jml;
            tStok.setText(Integer.toString(hasilstok));

            int totbay=total+(harga*jml);
            tTotal.setText(Integer.toString(totbay));

            ambildata();
            
        }
    }//GEN-LAST:event_bTambahActionPerformed

    private void bOnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bOnActionPerformed
        // TODO add your handling code here:
        if(bOn.getText().equalsIgnoreCase("ON")){
            bOn.setText("REFRESH");
            bersih();
            SiapIsi(true);
          
            notransaksi();
            bTambah.setEnabled(true);
            bCariBarang.setEnabled(true);
            bOn.setEnabled(true);
            bProses.setEnabled(true);
            
        } else{
            bOn.setText("ON");
            bersih();
            SiapIsi(false);
            TombolNormal();
            tabelinventory();
            TabModel.getDataVector().removeAllElements();
            TabModel.fireTableDataChanged();
        }
    }//GEN-LAST:event_bOnActionPerformed

    private void bTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTransaksiActionPerformed
        // TODO add your handling code here:
        jDialogtabeltransaksi.setLocationRelativeTo(null);
        tabeltransaksi();
        jDialogtabeltransaksi.setVisible(true);
    }//GEN-LAST:event_bTransaksiActionPerformed

    private void bProsesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bProsesActionPerformed
        // TODO add your handling code here:
        if(tIdTransaksi.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Lengkapi inputan penjualan barang");
        } else{
            proses();
            int pesan=JOptionPane.showConfirmDialog(null, "Print Out Nota?","Print",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);

            if(pesan==JOptionPane.YES_OPTION){
                nota();
            }else {
                JOptionPane.showMessageDialog(null, "Simpan Transaksi Berhasil");
            }
            perbaruistok();
            bersih();    
        } 
    }//GEN-LAST:event_bProsesActionPerformed

    private void tBayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tBayarKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            int total=Integer.parseInt(tTotal.getText());
            int bayar=Integer.parseInt(tBayar.getText());
            if(bayar<total){
                JOptionPane.showMessageDialog(null, "Jumlah bayar tidak mencukupi");
                tBayar.requestFocus();
            } else{
                int kembali=bayar-total;
                tKembali.setText(Integer.toString(kembali));
            }
        }
    }//GEN-LAST:event_tBayarKeyPressed

    private void tJumlahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tJumlahKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            int harga=Integer.parseInt(tHarga.getText());
            int jml=Integer.parseInt(tJumlah.getText());
            int stok=Integer.parseInt(tStok.getText());
            int total=Integer.parseInt(tTotal.getText());

            if(jml>stok){
            JOptionPane.showMessageDialog(null, "Stok barang tidak mencukupi");
            }else{

                int subtot=harga*jml;
                tSubTotal.setText(Integer.toString(subtot));

                int hasilstok=stok-jml;
                tStok.setText(Integer.toString(hasilstok));

                int totbay=total+(harga*jml);
                tTotal.setText(Integer.toString(totbay));

                ambildata();
            
            }
        }
    }//GEN-LAST:event_tJumlahKeyPressed

    private void bPencarianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPencarianActionPerformed
        // TODO add your handling code here:
        Object header[]={"ID BARANG","NAMA","HARGA","STOK"};
        DefaultTableModel data=new DefaultTableModel(null,header);
        tabelinventory.setModel(data);
        setKoneksi();
        String sql="Select * from barang where id_barang like '%" + txpencarianinventory.getText() + "%'" + "or merk_barang like '%" + txpencarianinventory.getText()+"%'";
        try {
            ResultSet rs=stm.executeQuery(sql);
            while (rs.next())
            {
                String kolom1=rs.getString(1);
                String kolom2=rs.getString(2);
                String kolom3=rs.getString(3);
                String kolom4=rs.getString(4);

                String kolom[]={kolom1,kolom2,kolom3,kolom4};
                data.addRow(kolom);
            }

        } catch (Exception e) {
        }
    }//GEN-LAST:event_bPencarianActionPerformed

    private void bCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCetakActionPerformed
        // TODO add your handling code here:
        Object header[]={"ID TRANSAKSI","TANGGAL","ID BARANG","MEREK","HARGA","JUMLAH","SUBTOTAL","TOTAL","BAYAR","KEMBALI"};
        DefaultTableModel data=new DefaultTableModel(null,header);
        tblTransaksi.setModel(data);
        setKoneksi();
        String sql="Select * from penjualan where id_transaksi like '%" + tCariTransaksi.getText() + "%'";
        try {
            ResultSet rs=stm.executeQuery(sql);
            while (rs.next())
            {
                String kolom1=rs.getString(1);
                String kolom2=rs.getString(2);
                String kolom3=rs.getString(3);
                String kolom4=rs.getString(4);
                String kolom5=rs.getString(5);
                String kolom6=rs.getString(6);
                String kolom7=rs.getString(7);
                String kolom8=rs.getString(8);
                String kolom9=rs.getString(9);
                String kolom10=rs.getString(10);

                String kolom[]={kolom1,kolom2,kolom3,kolom4,kolom5,kolom6,kolom7,kolom8,kolom9,kolom10,};
                data.addRow(kolom);
            }

        } catch (Exception e) {
        }
    }//GEN-LAST:event_bCetakActionPerformed

    private void tCariTransaksiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tCariTransaksiKeyPressed
        // TODO add your handling code here:
        Object header[]={"ID TRANSAKSI","TANGGAL","ID BARANG","MEREK","HARGA","JUMLAH","SUBTOTAL","TOTAL","BAYAR","KEMBALI"};
        DefaultTableModel data=new DefaultTableModel(null,header);
        tblTransaksi.setModel(data);
        setKoneksi();
        String sql="Select * from penjualan where id_transaksi like '%" + tCariTransaksi.getText() + "%'";
        try {
            ResultSet rs=stm.executeQuery(sql);
            while (rs.next())
            {
                String kolom1=rs.getString(1);
                String kolom2=rs.getString(2);
                String kolom3=rs.getString(3);
                String kolom4=rs.getString(4);
                String kolom5=rs.getString(5);
                String kolom6=rs.getString(6);
                String kolom7=rs.getString(7);
                String kolom8=rs.getString(8);
                String kolom9=rs.getString(9);
                String kolom10=rs.getString(10);

                String kolom[]={kolom1,kolom2,kolom3,kolom4,kolom5,kolom6,kolom7,kolom8,kolom9,kolom10,};
                data.addRow(kolom);
            }

        } catch (Exception e) {
        }
    }//GEN-LAST:event_tCariTransaksiKeyPressed

    private void tblTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTransaksiMouseClicked
        // TODO add your handling code here:

        int baris = tblTransaksi.getSelectedRow();
        tIdTransaksi.setText(tblTransaksi.getModel().getValueAt(baris, 0).toString());
        tIdBarang.setText(tblTransaksi.getModel().getValueAt(baris, 2).toString());
        tMerek.setText(tblTransaksi.getModel().getValueAt(baris, 3).toString());
        tHarga.setText(tblTransaksi.getModel().getValueAt(baris, 4).toString());
        tJumlah.setText(tblTransaksi.getModel().getValueAt(baris, 5).toString());
        tSubTotal.setText(tblTransaksi.getModel().getValueAt(baris, 6).toString());
        tTotal.setText(tblTransaksi.getModel().getValueAt(baris, 7).toString());
        tBayar.setText(tblTransaksi.getModel().getValueAt(baris, 8).toString());
        tKembali.setText(tblTransaksi.getModel().getValueAt(baris, 9).toString());
        jDialogtabeltransaksi.dispose();
        nota();
    }//GEN-LAST:event_tblTransaksiMouseClicked

    private void bExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bExitActionPerformed
        // TODO add your handling code here:
        jDialogtabeltransaksi.setVisible(false);
    }//GEN-LAST:event_bExitActionPerformed

    private void bBalikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bBalikActionPerformed
        // TODO add your handling code here:
        new MainMenu().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_bBalikActionPerformed

    private void bKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bKeluarActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_bKeluarActionPerformed

    private void bMinimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bMinimActionPerformed
        // TODO add your handling code here:
        setState(ICONIFIED);
    }//GEN-LAST:event_bMinimActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        jDialogtabelinventory.setVisible(false);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void bHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bHapusActionPerformed
        // TODO add your handling code here:
        int ok = JOptionPane.showConfirmDialog(null, "Anda Yakin Ingin Menghapus Data Ini?","Konfirmasi Dialog", JOptionPane.YES_NO_OPTION);
        if (ok==0){
            String sql ="delete from penjualan where id_transaksi='"+tIdTransaksi.getText()+"'";
            try{
                PreparedStatement stat = conn.prepareStatement(sql);
                stat.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
                bersih();
                tIdTransaksi.requestFocus();
                
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Data Gagal Dihapus" +e);
            }
        }
    }//GEN-LAST:event_bHapusActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormTransaksi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bBalik;
    private javax.swing.JButton bCariBarang;
    private javax.swing.JButton bCetak;
    private javax.swing.JButton bExit;
    private javax.swing.JButton bHapus;
    private javax.swing.JButton bKeluar;
    private javax.swing.JButton bMinim;
    private javax.swing.JButton bOn;
    private javax.swing.JButton bPencarian;
    private javax.swing.JButton bProses;
    private javax.swing.JButton bTambah;
    private javax.swing.JButton bTransaksi;
    private javax.swing.JButton jButton4;
    private javax.swing.JDialog jDialogtabelinventory;
    private javax.swing.JDialog jDialogtabeltransaksi;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField tBayar;
    private javax.swing.JTextField tCariTransaksi;
    private javax.swing.JTextField tHarga;
    private javax.swing.JTextField tIdBarang;
    private javax.swing.JTextField tIdTransaksi;
    private javax.swing.JTextField tJumlah;
    private javax.swing.JTextField tKembali;
    private javax.swing.JTextField tMerek;
    private javax.swing.JTextField tStok;
    private javax.swing.JTextField tSubTotal;
    private javax.swing.JTextField tTotal;
    private javax.swing.JTable tabelinventory;
    private javax.swing.JTable tblKeranjang;
    private javax.swing.JTable tblTransaksi;
    private javax.swing.JTextField txpencarianinventory;
    // End of variables declaration//GEN-END:variables
}

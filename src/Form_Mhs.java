/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * 
 */
import javax.swing.JOptionPane;
import com.mysql.jdbc.*;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;

public class Form_Mhs extends javax.swing.JFrame {
private JDateChooser chooser;
    /**
     * Creates new form Form_Mhs
     */
    public Form_Mhs() {
        initComponents();
        setTitle("FORM DATA MAHASISWA");
        this.setLocation(200, 100);
        Koneksi_db.openConnection();
        refreshSiswa();
        btn_edit.setEnabled(false);
    }
    
    private void simpanSiswa(){
    String sql="Insert into data_mhs values(?,?,?,?,?,?,?)";
    String jkel="";
    
    try{
        PreparedStatement st= (com.mysql.jdbc.PreparedStatement)
        Koneksi_db.conn.prepareStatement(sql);
        
        st.setString(1, txtNim.getText());
        st.setString(2, txtNamaMhs.getText());
        if (RbLk.isSelected()==true)jkel="Laki-laki";
        else
            if (RbPr.isSelected()==true)jkel="Perempuan";
        st.setString(3, jkel);
        st.setString(4, txtTl.getText());
        String tanggal = new SimpleDateFormat("yyyy-MM-dd").format(Kalender.getDate());
        st.setString(5, tanggal);
        st.setString(6, TaAlamat.getText());
        st.setString(7, CmbKls.getSelectedItem().toString());
        st.execute();
                JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
    } catch (SQLException e){
        JOptionPane.showMessageDialog(null, e);
        System.out.println(e);
    
    }
        }
    
    private void editsiswa(){
    String sql= 
            "update data_mhs set nis=?,nm_siswa=?,jns_kel=?,tmpt_lhr=?,tgl_lhr=?,alamat=?,kelas=? where nis='"+txtNim.getText()+"'";
    
    try{
        PreparedStatement st= (com.mysql.jdbc.PreparedStatement)
        Koneksi_db.conn.prepareStatement(sql);
        String jkel="";
        st.setString(1, txtNim.getText());
        st.setString(2, txtNamaMhs.getText());
        if (RbLk.isSelected()==true)jkel="Laki-laki";
        else
            if (RbPr.isSelected()==true)jkel="Perempuan";
        st.setString(3, jkel);
        st.setString(4, txtTl.getText());
        String tanggal = new SimpleDateFormat("yyyy-MM-dd").format(Kalender.getDate());
        st.setString(5, tanggal);
        st.setString(6, TaAlamat.getText());
        st.setString(7, CmbKls.getSelectedItem().toString());
        st.execute();
                JOptionPane.showMessageDialog(null, "Data Berhasil Diedit");
    } catch (SQLException e){
        JOptionPane.showMessageDialog(null, e);
    
    }
    }
    
    private void hapusSiswa(String NIS){
        String sql="delete from data_mhs where nis='"+NIS+"'";
        Statement st;
        
        try{
            st= (com.mysql.jdbc.PreparedStatement)
            Koneksi_db.conn.prepareStatement(sql);
            st.execute(sql);
        
        } catch (SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
                
                }
    }
    
    private void tampilData(String NIS){
        Statement st;
        java.sql.ResultSet rs;
        try{
        st = (Statement) Koneksi_db.conn.createStatement();
        String sql="Select * from data_mhs where nis='"+NIS+"'";
        st.execute(sql);
        rs = st.getResultSet();
        int baris=0;
        rs.beforeFirst();
        while (rs.next()){
            baris = rs.getRow();
        }
        if (baris>0){
        rs.beforeFirst();
        rs.next();
        
        txtNim.setText(rs.getString("nis"));
        txtNamaMhs.setText(rs.getString("nm_siswa"));
        if (rs.getString("jns_kel").equals("Laki-laki")){
            RbLk.setSelected(true);
        }else
            RbPr.setSelected(true);
        txtTl.setText(rs.getString("tmpt_lhr"));
        Kalender.setDate(rs.getDate("tgl_lhr"));
        TaAlamat.setText(rs.getString("alamat"));
        CmbKls.setSelectedItem(rs.getString("kelas"));
        
        }
        
        JOptionPane.showMessageDialog(null, "Data Ditemukan");
        btn_edit.setEnabled(true);       
        } catch (java.sql.SQLException e){
        JOptionPane.showMessageDialog(null, e.getMessage());
        }
    
    }
    
    public void refreshSiswa(){
        Statement st;
        java.sql.ResultSet rs;
        try{
            st=(Statement) Koneksi_db.conn.createStatement();
            String sql="Select *from data_mhs order by nis ASC";
            st.execute(sql);
            rs= st.getResultSet();
            String[]Header=
            {"nis","nm_siswa","jns_kel","tmpt_lhr","tgl_lhr","alamat","kelas"};
            
        int baris=0;
        rs.beforeFirst();
        while (rs.next()){
            baris = rs.getRow();
        }
            Object[][] dttbl=new Object[baris][7];
            rs.beforeFirst();
            int curbaris=0;
            while(rs.next()){
            dttbl[curbaris][0]=rs.getString("nis");
            dttbl[curbaris][1]=rs.getString("nm_siswa");
            dttbl[curbaris][2]=rs.getString("jns_kel");
            dttbl[curbaris][3]=rs.getString("tmpt_lhr");
            dttbl[curbaris][4]=rs.getString("tgl_lhr");
            dttbl[curbaris][5]=rs.getString("alamat");
            dttbl[curbaris][6]=rs.getString("kelas");
            curbaris++;
            }
            jTable2.setModel(new DefaultTableModel(dttbl,Header));
        }
            catch(java.sql.SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
            }
        
        }
    
    private void cleardata(){
        txtNim.setText("");
        txtNamaMhs.setText("");
        bgJK.clearSelection();
        txtTl.setText("");
        TaAlamat.setText("");
        CmbKls.setSelectedIndex(-1);
        txtNim.requestFocus();
        btn_edit.setEnabled(false);
        txtNim.setEnabled(true);
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgJK = new javax.swing.ButtonGroup();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btn_tambah = new javax.swing.JButton();
        btn_simpan = new javax.swing.JButton();
        btn_cari = new javax.swing.JButton();
        btn_edit = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        btn_keluar = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtTl = new javax.swing.JTextField();
        txtNim = new javax.swing.JTextField();
        txtNamaMhs = new javax.swing.JTextField();
        Kalender = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        TaAlamat = new javax.swing.JTextArea();
        CmbKls = new javax.swing.JComboBox<>();
        RbLk = new javax.swing.JRadioButton();
        RbPr = new javax.swing.JRadioButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(153, 153, 255));

        jPanel2.setBackground(new java.awt.Color(102, 102, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setText("FORM DATA MAHASISWA ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(166, 166, 166)
                .addComponent(jLabel1)
                .addContainerGap(177, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(19, 19, 19))
        );

        btn_tambah.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btn_tambah.setText("Tambah");
        btn_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahActionPerformed(evt);
            }
        });

        btn_simpan.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btn_simpan.setText("Simpan");
        btn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_simpanActionPerformed(evt);
            }
        });

        btn_cari.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btn_cari.setText("Cari");
        btn_cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cariActionPerformed(evt);
            }
        });

        btn_edit.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btn_edit.setText("Edit");
        btn_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editActionPerformed(evt);
            }
        });

        btn_hapus.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btn_hapus.setText("Hapus");
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
            }
        });

        btn_keluar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btn_keluar.setText("Keluar");
        btn_keluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_keluarActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Kelas");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("NIM ");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("Nama Mahasiswa");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("Jenis Kelamin");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setText("Tempat Lahir");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setText("Tanggal Lahir");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setText("Alamat");

        txtTl.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        txtNim.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        txtNamaMhs.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        TaAlamat.setColumns(20);
        TaAlamat.setRows(5);
        jScrollPane1.setViewportView(TaAlamat);

        CmbKls.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        CmbKls.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A", "B", "C", "D", "E" }));
        CmbKls.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CmbKlsActionPerformed(evt);
            }
        });

        bgJK.add(RbLk);
        RbLk.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        RbLk.setText("Laki-Laki");

        bgJK.add(RbPr);
        RbPr.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        RbPr.setText("Perempuan");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "NIM", "Nama Mahasiswa", "Jenis Kelamin", "Tempat Lahir", "Tanggal Lahir", "Alamat", "Kelas"
            }
        ));
        jScrollPane3.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel13))
                                .addGap(34, 34, 34)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtNim, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNamaMhs, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(RbLk)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(RbPr))
                                    .addComponent(txtTl, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(75, 75, 75)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btn_tambah)
                                    .addComponent(btn_simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btn_hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btn_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btn_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btn_keluar, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(62, 62, 62)
                                .addComponent(Kalender, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addGap(110, 110, 110)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(121, 121, 121)
                                .addComponent(CmbKls, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 636, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(3, 3, 3))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(13, 13, 13)
                                .addComponent(jLabel11)
                                .addGap(13, 13, 13)
                                .addComponent(jLabel12)
                                .addGap(13, 13, 13)
                                .addComponent(jLabel13))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(btn_tambah)
                                        .addGap(11, 11, 11)
                                        .addComponent(btn_simpan)
                                        .addGap(7, 7, 7)
                                        .addComponent(btn_hapus))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(btn_cari)
                                        .addGap(11, 11, 11)
                                        .addComponent(btn_edit)
                                        .addGap(7, 7, 7)
                                        .addComponent(btn_keluar))))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(txtNim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNamaMhs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(RbLk)
                            .addComponent(RbPr))
                        .addGap(18, 18, 18)
                        .addComponent(txtTl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(Kalender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(CmbKls, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 590));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahActionPerformed
        // TODO add your handling code here:
        this.requestFocus();
        cleardata();
    }//GEN-LAST:event_btn_tambahActionPerformed

    private void btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed
        // TODO add your handling code here:
        if(txtNim.getText().equals("")){
            JOptionPane.showMessageDialog(null, "NIM kosong");
            txtNim.requestFocus();
        } 
        else if(txtNamaMhs.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Nama Mahasiswa kosong");
            txtNamaMhs.requestFocus();
        } 
        else if((RbLk.isSelected() == false) && (RbPr.isSelected()== false)){
            JOptionPane.showMessageDialog(null, "Jenis Kelamin kosong");
            RbLk.requestFocus();
        }
        if (txtTl.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Tempat Lahir kosong");
            txtTl.requestFocus();
        } 
        else if (TaAlamat.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Alamat kosong");
            TaAlamat.requestFocus();
        } 
        else {
            refreshSiswa();
            simpanSiswa();
            refreshSiswa();
        }
    }//GEN-LAST:event_btn_simpanActionPerformed

    private void btn_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cariActionPerformed
        // TODO add your handling code here:
        String strInput = JOptionPane.showInputDialog("Masukkan NIM :");
        txtNim.setEnabled(false);
        tampilData(strInput);
    }//GEN-LAST:event_btn_cariActionPerformed

    private void btn_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editActionPerformed
        // TODO add your handling code here:
        editsiswa();
         refreshSiswa();
    }//GEN-LAST:event_btn_editActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        // TODO add your handling code here:
        String strInput = JOptionPane.showInputDialog("Silahkan Masukkan NIM :");
        hapusSiswa(strInput);
        if (strInput.isEmpty()==false){
            int jwb = JOptionPane.showConfirmDialog(null, "Apakah yakin menghapus data?","Konfirmasi", JOptionPane.YES_NO_OPTION);
            hapusSiswa(strInput);
        }
        JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
        refreshSiswa();
        cleardata();
    
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void btn_keluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_keluarActionPerformed
        // TODO add your handling code here:
        int jawab = javax.swing.JOptionPane.showConfirmDialog(null, "Yakin akan Keluar?", "konfirmasi", javax.swing.JOptionPane.YES_NO_OPTION);
        if(jawab==0){
            this.dispose();
        }
    }//GEN-LAST:event_btn_keluarActionPerformed

    private void CmbKlsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CmbKlsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CmbKlsActionPerformed

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
            java.util.logging.Logger.getLogger(Form_Mhs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Form_Mhs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Form_Mhs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Form_Mhs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Form_Mhs().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CmbKls;
    private com.toedter.calendar.JDateChooser Kalender;
    private javax.swing.JRadioButton RbLk;
    private javax.swing.JRadioButton RbPr;
    private javax.swing.JTextArea TaAlamat;
    private javax.swing.ButtonGroup bgJK;
    private javax.swing.JButton btn_cari;
    private javax.swing.JButton btn_edit;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_keluar;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField txtNamaMhs;
    private javax.swing.JTextField txtNim;
    private javax.swing.JTextField txtTl;
    // End of variables declaration//GEN-END:variables
}

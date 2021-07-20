/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timetable.adminFrame;

import java.awt.Color;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import timetable.JConnection;

/**
 *
 * @author Pritam Bera
 */
public class editRemoveSubjects extends javax.swing.JFrame {

    /**
     * Creates new form editRemoveSubjects
     */
    Connection conn = null;
    int college_id,dept_id,year_id;
    String year;
    
    
    private void setIconImage(){
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("logo.png")));
    }
    
    public void searchCollege(String name){
        try{
            
            PreparedStatement pst = null;
            pst = conn.prepareStatement("select college_id,dept_id from admins where name = ?");
            pst.setString(1, name);
            ResultSet rs = pst.executeQuery();
            
            if(rs.next())
            {
                college_id = rs.getInt("college_id");
                dept_id = rs.getInt("dept_id");
            }
            
            
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    private void fillCombo()
    {
        try{
            
            PreparedStatement pst = null;
            ResultSet rs = null;
            pst = conn.prepareStatement("select class from study_years where college_id=? and dept_id=? order by case "
                    + "class when 'FE' then 1 when 'SE' then 2 when 'TE' then 3 else 4 end ");
            pst.setInt(1,college_id);
            pst.setInt(2,dept_id);
            rs = pst.executeQuery();
            
            while(rs.next())
            {
                System.out.println(rs.getString("class"));
                String studyYear=rs.getString("class");
               
                if(studyYear.equals("FE"))
                    jComboBox1.addItem("First Year");
                else if(studyYear.equals("SE"))
                    jComboBox1.addItem("Second Year");
                else if(studyYear.equals("TE"))
                    jComboBox1.addItem("Third Year");
                else if(studyYear.equals("BE"))
                    jComboBox1.addItem("Final Year");
                   
            }
        }catch(Exception e){
            //System.out.println("pritam");
            JOptionPane.showMessageDialog(null,e);
        }
        
    }
    
    public ArrayList<subjectClass> subList(){
        ArrayList<subjectClass> subList = new ArrayList<>();
        
        try{
            
            PreparedStatement pst = null;
            PreparedStatement pst2 = null;

            String selectedItem = (String)jComboBox1.getSelectedItem();
            System.out.println(selectedItem);
            
            if(selectedItem.equals("First Year"))
            {
                year = "FE";
                pst2 = conn.prepareStatement("select study_year_id from study_years where class = 'FE' and college_id = ? and dept_id = ?");
                pst2.setInt(1, college_id);
                pst2.setInt(2,dept_id);
                ResultSet rs2 = pst2.executeQuery();
                if(rs2.next()){
                    year_id = rs2.getInt("study_year_id");
                }
                pst = conn.prepareStatement("select distinct type,subject,sub_abbreviation,sub_load from teacher_allocation "
                        + "where study_year_id = ? and college_id = ? and dept_id = ?");
                pst.setInt(1, year_id);
                pst.setInt(2, college_id);
                pst.setInt(3, dept_id);
            }
            if(selectedItem.equals("Second Year"))
            {
                year = "SE";
                pst2 = conn.prepareStatement("select study_year_id from study_years where class = 'SE' and college_id = ? and dept_id = ?");
                pst2.setInt(1, college_id);
                pst2.setInt(2,dept_id);
                ResultSet rs2 = pst2.executeQuery();
                if(rs2.next()){
                    year_id = rs2.getInt("study_year_id");
                }
                pst = conn.prepareStatement("select distinct type,subject,sub_abbreviation,sub_load from teacher_allocation "
                        + "where study_year_id = ? and college_id = ? and dept_id = ?");
                pst.setInt(1, year_id);
                pst.setInt(2, college_id);
                pst.setInt(3, dept_id);
            }
            if(selectedItem.equals("Third Year"))
            {
                year = "TE";
                pst2 = conn.prepareStatement("select study_year_id from study_years where class = 'TE' and college_id = ? and dept_id = ?");
                pst2.setInt(1, college_id);
                pst2.setInt(2,dept_id);
                ResultSet rs2 = pst2.executeQuery();
                if(rs2.next()){
                    year_id = rs2.getInt("study_year_id");
                }
                pst = conn.prepareStatement("select distinct type,subject,sub_abbreviation,sub_load from teacher_allocation "
                        + "where study_year_id = ? and college_id = ? and dept_id = ?");
                pst.setInt(1, year_id);
                pst.setInt(2, college_id);
                pst.setInt(3, dept_id);
            }
            if(selectedItem.equals("Final Year"))
            {
                year = "BE";
                pst2 = conn.prepareStatement("select study_year_id from study_years where class = 'BE' and college_id = ? and dept_id = ?");
                pst2.setInt(1, college_id);
                pst2.setInt(2,dept_id);
                ResultSet rs2 = pst2.executeQuery();
                if(rs2.next()){
                    year_id = rs2.getInt("study_year_id");
                    System.out.println(year_id);
                }
                pst = conn.prepareStatement("select distinct type,subject,sub_abbreviation,sub_load from teacher_allocation "
                        + "where study_year_id = ? and college_id = ? and dept_id = ?");
                pst.setInt(1, year_id);
                pst.setInt(2, college_id);
                pst.setInt(3, dept_id);
            }
            
            //System.out.println(selectedRowIndex);

            ResultSet rs = pst.executeQuery();
            subjectClass subReq;
            if(rs.next())
            {
                rs.previous();
                while(rs.next()){
                    subReq = new subjectClass(year,rs.getString("type"),
                            rs.getString("subject"),rs.getString("sub_abbreviation"),
                            rs.getInt("sub_load"));
                    subList.add(subReq);
                }

            }
            else
            {
                JOptionPane.showMessageDialog(null,"No Entries Found in the table","MESSAGE",JOptionPane.INFORMATION_MESSAGE);
                
            }
            
            System.out.println(subList);
            
            
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
            
        return subList;
    }
    
    public void showsubs(){
        ArrayList<subjectClass> List = subList();
        DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
        int rows = model.getRowCount();
        for(int i = rows-1 ; i >=0 ; i--){
            model.removeRow(i);
        }
        Object column[] = new Object[4]; 
        for(int i = 0; i<List.size(); i++){
    
            column[0] = List.get(i).getType();
            column[1] = List.get(i).getSubject();
            column[2] = List.get(i).getSubAbbr();
            column[3] = List.get(i).getSubLoad();
           
            model.addRow(column);
        }
    }
    
    private void update()
    {
        try{
            conn = JConnection.ConnecrDb();
            PreparedStatement pst = null;
            PreparedStatement pst2 = null;
            String selectedItem = (String)jComboBox1.getSelectedItem();
            if(selectedItem.equals("First Year"))
                year = "FE";
            else if(selectedItem.equals("Second Year"))
                year = "SE";
            else if(selectedItem.equals("Third Year"))
                year = "TE";
            else if(selectedItem.equals("Final Year"))
                year = "BE";
            
            
            pst2 = conn.prepareStatement("select study_year_id from study_years where class = ? and college_id = ? and dept_id = ?");
            pst2.setString(1, year);
            pst2.setInt(2, college_id);
            pst2.setInt(3, dept_id);
            ResultSet rs2 = pst2.executeQuery();
            
            if(rs2.next()){
                year_id = rs2.getInt("study_year_id");
                System.out.println(year_id);
            }    
            
            DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
            int selectedRowIndex = jTable1.getSelectedRow();
            if(selectedRowIndex == -1){
                JOptionPane.showMessageDialog(null, "SELECT ROW FIRST", "ALERT", JOptionPane.ERROR_MESSAGE);
            }
            else{
                String type1 = model.getValueAt(selectedRowIndex, 0).toString();
                String subject1 = model.getValueAt(selectedRowIndex, 1).toString();
            
                String type2 = jTextField2.getText();
                String subject2 = jTextField3.getText();
                String sub_abbr2 = jTextField4.getText();
                int sub_load2 = Integer.parseInt(jTextField5.getText());
            
                pst = conn.prepareStatement("update teacher_allocation set type = ? , subject = ?, sub_abbreviation = ?, sub_load = ? where study_year_id = ? and type = ? and subject = ? and college_id = ? and dept_id = ?");
                pst.setString(1, type2);
                pst.setString(2, subject2);
                pst.setString(3, sub_abbr2);
                pst.setInt(4, sub_load2);
                pst.setInt(5, year_id);
                pst.setString(6, type1);
                pst.setString(7, subject1);
                pst.setInt(8, college_id);
                pst.setInt(9, dept_id);
            
                pst.execute();
            
                JOptionPane.showMessageDialog(null, "Successfully Updated", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                nonactivate2();
                showsubs();
            }
            
            
            //conn.close();
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    private void remove()
    {
        try{
            conn = JConnection.ConnecrDb();
            PreparedStatement pst = null;
            PreparedStatement pst2 = null;
            String selectedItem = (String)jComboBox1.getSelectedItem();
            if(selectedItem.equals("First Year"))
                year = "FE";
            else if(selectedItem.equals("Second Year"))
                year = "SE";
            else if(selectedItem.equals("Third Year"))
                year = "TE";
            else if(selectedItem.equals("Final Year"))
                year = "BE";
            
            
            pst2 = conn.prepareStatement("select study_year_id from study_years where class = ? and college_id = ? and dept_id = ?");
            pst2.setString(1, year);
            pst2.setInt(2, college_id);
            pst2.setInt(3, dept_id);
            ResultSet rs2 = pst2.executeQuery();
            
            if(rs2.next()){
                year_id = rs2.getInt("study_year_id");
                System.out.println(year_id);
            }    
            
            DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
            int selectedRowIndex = jTable1.getSelectedRow();
            if(selectedRowIndex == -1){
                JOptionPane.showMessageDialog(null, "SELECT ROW FIRST", "ALERT", JOptionPane.ERROR_MESSAGE);
            }
            else{
                String type1 = model.getValueAt(selectedRowIndex, 0).toString();
                String subject1 = model.getValueAt(selectedRowIndex, 1).toString();

                pst = conn.prepareStatement("DELETE FROM teacher_allocation where study_year_id = ? and type = ? and subject = ? and college_id = ? and dept_id = ?");
                pst.setInt(1, year_id);
                pst.setString(2, type1);
                pst.setString(3, subject1);
                pst.setInt(4, college_id);
                pst.setInt(5, dept_id);
            
                pst.execute();
            
                JOptionPane.showMessageDialog(null, "Successfully Removed", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                nonactivate2();
                showsubs();
            }
            
            
            //conn.close();
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    public void nonactivate(){
        jLabel1.setVisible(false);
        jLabel6.setVisible(false);
    }
    
    private void nonactivate2()
    {
        jTextField3.setText("");
        jTextField2.setText("");
        jTextField4.setText("");
        jTextField5.setText("");
    }
    
    public void setColor(JPanel p) {
        p.setBackground(new Color(36,47,65));
        p.setBorder(BorderFactory.createLineBorder(new Color(97,212,195), 2));
  
    }
    
    public void resetColor(JPanel p1) {
        p1.setBackground(new Color(97,212,195));   
    }
    
    public editRemoveSubjects() {
        initComponents();
    }
    
    public editRemoveSubjects(String name) {
        initComponents();
        setIconImage();
        JTableHeader Theader = jTable1.getTableHeader();
        Theader.setOpaque(false);
        Theader.setBackground(new Color(36,47,65));
        Theader.setForeground(new Color(255,255,255));
        nonactivate();
        conn = JConnection.ConnecrDb();
        
        searchCollege(name);
        fillCombo();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("AppName");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(36, 47, 65));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("EDIT / REMOVE SUBJECTS");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 10, 340, 40));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 970, 60));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Year" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 50, 300, 30));

        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setText("Please select year");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 53, -1, 20));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Select Year");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 50, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Type :");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 450, 50, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Subject:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 450, 60, 30));

        jTextField2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 480, 150, 40));

        jTextField3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 480, 260, 40));

        jPanel4.setBackground(new java.awt.Color(97, 212, 195));
        jPanel4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPanel4KeyPressed(evt);
            }
        });
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setBackground(new java.awt.Color(255, 255, 255));
        jLabel14.setFont(new java.awt.Font("Corbel Light", 1, 16)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Update");
        jLabel14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel14MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel14MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel14MouseExited(evt);
            }
        });
        jPanel4.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 130, 40));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 530, 130, 40));

        jPanel5.setBackground(new java.awt.Color(97, 212, 195));
        jPanel5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPanel5KeyPressed(evt);
            }
        });
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setBackground(new java.awt.Color(255, 255, 255));
        jLabel15.setFont(new java.awt.Font("Corbel Light", 1, 16)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Remove");
        jLabel15.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel15MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel15MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel15MouseExited(evt);
            }
        });
        jPanel5.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 130, 40));

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 530, 130, 40));

        jLabel6.setForeground(new java.awt.Color(255, 0, 0));
        jLabel6.setText("Please select row first ! Do not write on your own");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 490, -1, 20));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "TYPE", "SUBJECT", "SUBJECT_ABBR", "SUBJECT_LOAD"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setRowHeight(40);
        jTable1.setSelectionBackground(new java.awt.Color(97, 212, 195));
        jTable1.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 910, 360));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Subject Abbreviation:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 530, 150, 30));

        jTextField4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 560, 260, 40));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Subject Load:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 530, 150, 30));

        jTextField5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField5.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 560, 150, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 970, 620));

        setSize(new java.awt.Dimension(978, 714));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:

        int selectedRowIndex = jComboBox1.getSelectedIndex();
        if(selectedRowIndex == -1 || selectedRowIndex == 0)
        {
            DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
            int rows = model.getRowCount();
            for(int i = rows-1 ; i >=0 ; i--){
                model.removeRow(i);
            nonactivate();
            jTextField2.setText("");
            jTextField3.setText("");
            jTextField4.setText("");
            jTextField5.setText("");
            }
        }
        else
        {
            jTextField2.setText("");
            jTextField3.setText("");
            jTextField4.setText("");
            jTextField5.setText("");
            showsubs();
        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked
        // TODO add your handling code here:
        nonactivate();
        int selectedComboIndex = jComboBox1.getSelectedIndex();
        if( selectedComboIndex == -1 || selectedComboIndex == 0){
            nonactivate();
            jLabel1.setVisible(true);
        }
        else if (jTextField2.getText().equals("") || jTable1.getSelectedRow() == -1){
            nonactivate();
            jLabel6.setVisible(true);
        }
        else{
            update();
        }
    }//GEN-LAST:event_jLabel14MouseClicked

    private void jLabel14MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseEntered
        // TODO add your handling code here:
        setColor(jPanel4);
    }//GEN-LAST:event_jLabel14MouseEntered

    private void jLabel14MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseExited
        // TODO add your handling code here:
        resetColor(jPanel4);
    }//GEN-LAST:event_jLabel14MouseExited

    private void jPanel4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanel4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel4KeyPressed

    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseClicked
        // TODO add your handling code here:
        nonactivate();
        if(jTextField3.getText().equals(""))
        {
            jLabel1.setVisible(true);
        }
        else
        {
            remove();
            //DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
            //int selectedRowIndex = jTable1.getSelectedRow();
            //model.removeRow(selectedRowIndex);
        }
    }//GEN-LAST:event_jLabel15MouseClicked

    private void jLabel15MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseEntered
        // TODO add your handling code here:
        setColor(jPanel5);
    }//GEN-LAST:event_jLabel15MouseEntered

    private void jLabel15MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseExited
        // TODO add your handling code here:
        resetColor(jPanel5);
    }//GEN-LAST:event_jLabel15MouseExited

    private void jPanel5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanel5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel5KeyPressed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
        int selectedRowIndex = jTable1.getSelectedRow();
        jTextField2.setText(model.getValueAt(selectedRowIndex, 0).toString());
        jTextField3.setText(model.getValueAt(selectedRowIndex, 1).toString());
        jTextField4.setText(model.getValueAt(selectedRowIndex, 2).toString());
        jTextField5.setText(model.getValueAt(selectedRowIndex, 3).toString());
    }//GEN-LAST:event_jTable1MouseClicked

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
            java.util.logging.Logger.getLogger(editRemoveSubjects.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(editRemoveSubjects.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(editRemoveSubjects.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(editRemoveSubjects.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new editRemoveSubjects().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables
}

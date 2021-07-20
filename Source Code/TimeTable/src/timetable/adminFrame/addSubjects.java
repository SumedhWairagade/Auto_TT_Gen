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
public class addSubjects extends javax.swing.JFrame {

    /** Creates new form addSubjects */
    String teacherName,college,year,name,code,department,type,room = "";
    int divisions,batches,actionPerformed = 0,college_id,dept_id,load,year_id;
    Connection conn = null;
    
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
    
    public void searchCommonEntries(){
        try{
            
            PreparedStatement pst = null;  
            PreparedStatement pst2 = null; 

            year = (String)jComboBox3.getSelectedItem();
            type = (String)jComboBox4.getSelectedItem();
            name = jTextField2.getText();
            code = jTextField1.getText();
            load = Integer.parseInt(jTextField3.getText());
            
            if(year.equals("First Year"))
                year = "FE";
            else if(year.equals("Second Year"))
                year = "SE";
            else if(year.equals("Third Year"))
                year = "TE";   
            else if(year.equals("Final Year"))
                year = "BE";  
            
            pst2 = conn.prepareStatement("select study_year_id from study_years where college_id = ? and dept_id = ? and class = ? ");
            pst2.setInt(1, college_id);
            pst2.setInt(2, dept_id);
            pst2.setString(3,year);
            ResultSet rs2 = pst2.executeQuery();
            if(rs2.next()){
                year_id = rs2.getInt("study_year_id");
            }
            
            pst = conn.prepareStatement("select distinct subject from teacher_allocation where college_id = ? and dept_id = ? and study_year_id = ? and subject = ? and type = ?");

            pst.setInt(1, college_id);
            pst.setInt(2, dept_id);
            pst.setInt(3, year_id);
            pst.setString(4, name);
            pst.setString(5, type);
          
            ResultSet rs = pst.executeQuery();
            
            if(rs.next()){
                JOptionPane.showMessageDialog(null,"Record already exists with same name","MESSAGE",JOptionPane.INFORMATION_MESSAGE);
                jTextField1.setText("");
                jTextField2.setText("");
                jTextField3.setText("");
            }
            else{
                addSubToDb();
            }

        }catch(Exception e){
            
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    public void searchDivAndBatches(){
        try{
            
            PreparedStatement pst = null;
         
            pst = conn.prepareStatement("select study_year_id,no_of_divisions,no_of_batches_in_each_division from study_years where college_id=? and dept_id=? and class=?");
            
            pst.setInt(1, college_id);
            pst.setInt(2, dept_id);
            pst.setString(3, year);
  
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                divisions = rs.getInt("no_of_divisions");
                batches = rs.getInt("no_of_batches_in_each_division");
                year_id = rs.getInt("study_year_id");
            }
            //System.out.println(divisions+batches);

        }catch(Exception e){
            
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    public void addSubToDb(){
        try{
            
            PreparedStatement pst = null;
            
            year = (String)jComboBox3.getSelectedItem();
            type = (String)jComboBox4.getSelectedItem();
            name = jTextField2.getText();
            code = jTextField1.getText();
            load = Integer.parseInt(jTextField3.getText());
            
            if(year.equals("First Year"))
                year = "FE";
            else if(year.equals("Second Year"))
                year = "SE";
            else if(year.equals("Third Year"))
                year = "TE";   
            else if(year.equals("Final Year"))
                year = "BE";  
            
            //System.out.println(year +" "+ type +" "+ name +" "+ code +" "+ load +" "+ divisions +" "+ batches);
            
            searchDivAndBatches();
            int i,j;
            if(type.equals("Theory")){
                for(i=1;i<=divisions;i++){
                    //System.out.println("PRIRT");
                    pst = conn.prepareStatement("insert into teacher_allocation (college_id,dept_id,study_year_id,division,batch,type,subject,sub_abbreviation,sub_load) values (?,?,?,?,?,?,?,?,?)");
                    int batch_no = 0;
                    pst.setInt(1, college_id);
                    pst.setInt(2, dept_id);
                    pst.setInt(3, year_id);
                    pst.setInt(4, i);
                    pst.setInt(5, batch_no);
                    pst.setString(6, type);
                    pst.setString(7, name);
                    pst.setString(8, code);
                    pst.setInt(9, load);
                    
                    pst.execute();
                }
            }
            else{
                
                /*for(i=0;i<jTable1.getRowCount();i++){
                    Boolean checked = Boolean.valueOf(jTable1.getValueAt(i, 2).toString());
                    String name = jTable1.getValueAt(i, 1).toString();
                    if(checked){
                        room = room + name + ",";
                    }
                }*/
                
                for(i=1;i<=divisions;i++){
                    for(j=1;j<=batches;j++){
                        pst = conn.prepareStatement("insert into teacher_allocation (college_id,dept_id,study_year_id,division,batch,type,subject,sub_abbreviation,sub_load) values (?,?,?,?,?,?,?,?,?)");
            
                        pst.setInt(1, college_id);
                        pst.setInt(2, dept_id);
                        pst.setInt(3, year_id);
                        pst.setInt(4, i);
                        pst.setInt(5,j);
                        pst.setString(6, type);
                        pst.setString(7, name);
                        pst.setString(8, code);
                        pst.setInt(9, load);
                        
                    
                        pst.execute();
                    }
                    
                }
            }

            JOptionPane.showMessageDialog(null,"Information Added","MESSAGE",JOptionPane.INFORMATION_MESSAGE);
            jTextField1.setText("");
            jTextField2.setText("");
            jTextField3.setText("");

            //conn.close();
        }catch(Exception e){
            
            JOptionPane.showMessageDialog(null,e);
        }
        
    }
    
    private void fillCombo1()
    {
        try{
            
            PreparedStatement pst = null;
            ResultSet rs = null;
            pst = conn.prepareStatement("select class from study_years where college_id=? and dept_id=?");
            pst.setInt(1,college_id);
            pst.setInt(2,dept_id);
            rs = pst.executeQuery();
            
            while(rs.next())
            {
                
                String studyYear=rs.getString("class");
               
                if(studyYear.equals("FE"))
                    jComboBox3.addItem("First Year");
                else if(studyYear.equals("SE"))
                    jComboBox3.addItem("Second Year");
                else if(studyYear.equals("TE"))
                    jComboBox3.addItem("Third Year");
                else if(studyYear.equals("BE"))
                    jComboBox3.addItem("Final Year");
                   
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
        
    }
    
    /*public ArrayList<Infrastructure> infraList(){
        ArrayList<Infrastructure> infraList = new ArrayList<>();
        
        try{
            
            PreparedStatement pst = null;

            pst = conn.prepareStatement("select number,name from infrastructure where college_id = ? and dept_id = ? and type = ? order by number");
            pst.setInt(1, college_id);
            pst.setInt(2, dept_id);
            pst.setString(3, "Practical Lab");

            ResultSet rs = pst.executeQuery();
            Infrastructure infrastructure;
            if(rs.next())
            {
                rs.previous();
                while(rs.next()){
                    infrastructure = new Infrastructure("Practical Lab",rs.getInt("number"),rs.getString("name"));
                    infraList.add(infrastructure);
                }

            }
            else
            {
                JOptionPane.showMessageDialog(null,"No Entries Found in the table","MESSAGE",JOptionPane.INFORMATION_MESSAGE);
                
            }
            
            
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
            
        return infraList;
    }
    
    public void showLabs(){
        ArrayList<Infrastructure> List = infraList();
        DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
        Object column[] = new Object[2]; 
        for(int i = 0; i<List.size(); i++){
            //column[0] = List.get(i).getType();
            column[0] = List.get(i).getNumber();
            column[1] = List.get(i).getName();
            model.addRow(column);
        }
    }*/
    
    public addSubjects() {
        initComponents();
        setIconImage();
        jLabel18.setVisible(false);
        jLabel20.setVisible(false);
        jLabel23.setVisible(false);
    }
    
    public addSubjects(String name) {
        initComponents();
        setIconImage();
        conn = JConnection.ConnecrDb();
        
        jLabel18.setVisible(false);
        jLabel20.setVisible(false);
        jLabel23.setVisible(false);
        teacherName = name;
        searchCollege(name);
        fillCombo1();
 
    }
    
    public void setColor(JPanel p) {
        p.setBackground(new Color(36,47,65));
        p.setBorder(BorderFactory.createLineBorder(new Color(97,212,195), 2));
  
    }
    
    public void resetColor(JPanel p1) {
        p1.setBackground(new Color(97,212,195));   
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("AppName");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(36, 47, 65));
        jPanel2.setMaximumSize(new java.awt.Dimension(400, 60));
        jPanel2.setMinimumSize(new java.awt.Dimension(400, 60));
        jPanel2.setPreferredSize(new java.awt.Dimension(400, 60));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("ADD SUBJECT");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, -1, 40));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 370, -1));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel16.setText("Select Year ");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 310, 30));

        jComboBox3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox3MouseClicked(evt);
            }
        });
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 300, 30));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel17.setText("Subject Name");
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 310, 30));

        jTextField2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel1.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 300, 30));

        jLabel20.setForeground(new java.awt.Color(255, 0, 51));
        jLabel20.setText("Enter number");
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, 300, 20));

        jTextField1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel1.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 330, 300, 30));

        jLabel18.setForeground(new java.awt.Color(255, 0, 51));
        jLabel18.setText("Enter Abbreviation");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 360, 300, 20));

        jPanel7.setBackground(new java.awt.Color(97, 212, 195));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setFont(new java.awt.Font("Corbel Light", 1, 16)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("ADD");
        jLabel13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel13MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel13MouseExited(evt);
            }
        });
        jPanel7.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 180, 40));

        jPanel1.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 510, 180, 40));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel19.setText("Select Subject Type ");
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 310, 30));

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Theory", "Practical" }));
        jComboBox4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox4MouseClicked(evt);
            }
        });
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 300, 30));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel21.setText("Subject Abbreviation");
        jPanel1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, 310, 30));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel22.setText("Subject Load Per Week");
        jPanel1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 390, 310, 30));

        jTextField3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel1.add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 420, 300, 30));

        jLabel23.setForeground(new java.awt.Color(255, 0, 51));
        jLabel23.setText("Enter Load");
        jPanel1.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 450, 300, 20));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 370, 580));

        setSize(new java.awt.Dimension(382, 678));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox3MouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_jComboBox3MouseClicked

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
       
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
        // TODO add your handling code here:
        if(jTextField2.getText().equals("") && !(jTextField1.getText().equals("")) && !(jTextField3.getText().equals(""))){
            jLabel20.setVisible(true);
            jLabel18.setVisible(false);
            jLabel23.setVisible(false);
        }
        else if(jTextField1.getText().equals("") && !(jTextField2.getText().equals("")) && !(jTextField3.getText().equals(""))){
            jLabel18.setVisible(true);
            jLabel20.setVisible(false);
            jLabel23.setVisible(false);
        }
        else if(jTextField3.getText().equals("") && !(jTextField1.getText().equals("")) && !(jTextField2.getText().equals(""))){
            jLabel18.setVisible(false);
            jLabel20.setVisible(false);
            jLabel23.setVisible(true);
        }
        else if(jTextField2.getText().equals("") && jTextField1.getText().equals("") && jTextField3.getText().equals("")){
            jLabel18.setVisible(true);
            jLabel20.setVisible(true);
            jLabel23.setVisible(true);
        }
        else if(jTextField2.getText().equals("") && jTextField1.getText().equals("") && !(jTextField3.getText().equals(""))){
            jLabel18.setVisible(true);
            jLabel20.setVisible(true);
            jLabel23.setVisible(false);
        }
        else if(jTextField2.getText().equals("") && !(jTextField1.getText().equals("")) && jTextField3.getText().equals("")){
            jLabel18.setVisible(false);
            jLabel20.setVisible(true);
            jLabel23.setVisible(true);
        }
        else if(!(jTextField2.getText().equals("")) && jTextField1.getText().equals("") && jTextField3.getText().equals("")){
            jLabel18.setVisible(true);
            jLabel20.setVisible(false);
            jLabel23.setVisible(true);
        }
        
        else{
            jLabel18.setVisible(false);
            jLabel20.setVisible(false);
            jLabel23.setVisible(false);
            searchCommonEntries();
            
        }

    }//GEN-LAST:event_jLabel13MouseClicked

    private void jLabel13MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseEntered
        // TODO add your handling code here:
        setColor(jPanel7);
    }//GEN-LAST:event_jLabel13MouseEntered

    private void jLabel13MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseExited
        // TODO add your handling code here:
        resetColor(jPanel7);
    }//GEN-LAST:event_jLabel13MouseExited

    private void jComboBox4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox4MouseClicked

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        // TODO add your handling code here:
        /*actionPerformed = 1;
        if(jComboBox4.getSelectedItem().equals("Practical"))
        {
                showLabs();
        }
        else{
            
            DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
            while(model.getRowCount() > 0){
                for(int i = 0; i < model.getRowCount(); i++){
                    model.removeRow(i);
                }
                    
            }
        }*/
        
    }//GEN-LAST:event_jComboBox4ActionPerformed

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
            java.util.logging.Logger.getLogger(addSubjects.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(addSubjects.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(addSubjects.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(addSubjects.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new addSubjects().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables

}

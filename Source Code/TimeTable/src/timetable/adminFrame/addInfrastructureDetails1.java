/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timetable.adminFrame;

import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.sql.*;
import javax.swing.JOptionPane;
import timetable.JConnection;
import timetable.TeacherDashboard;

/**
 *
 * @author Pritam Bera
 */
public class addInfrastructureDetails1 extends javax.swing.JFrame {

    /**
     * Creates new form addInfrastructureDetails1
     */
    
    String teacherName,type,name;
    int college_id,dept_id;
    int number;
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

            type = (String)jComboBox3.getSelectedItem();
            number = Integer.parseInt(jTextField2.getText());
            name = jTextField1.getText();

            pst = conn.prepareStatement("select number,name,type from infrastructure where type = ? and number = ? and "
                    + "college_id = ? and dept_id = ? or type = ? and name = ? and college_id = ? and dept_id = ?");

            pst.setString(1,type);
            pst.setInt(2, number);
            pst.setInt(3, college_id);
            pst.setInt(4, dept_id);
            
            pst.setString(5, type);
            pst.setString(6, name);
            pst.setInt(7, college_id);
            pst.setInt(8, dept_id);
            
            ResultSet rs = pst.executeQuery();
            
            if(rs.next()){
                JOptionPane.showMessageDialog(null,"Record already exists with same name or number","MESSAGE",JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                addInfraToDb();
            }

        }catch(Exception e){
            
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    public void addInfraToDb(){
        try{
            
            PreparedStatement pst = null;
            
            type = (String)jComboBox3.getSelectedItem();
            number = Integer.parseInt(jTextField2.getText());
            name = jTextField1.getText();

            pst = conn.prepareStatement("insert into infrastructure (type,number,name,college_id,dept_id) values (?,?,?,?,?)");
            
            
            pst.setString(1, type);
            pst.setInt(2, number);
            pst.setString(3, name);
            pst.setInt(4, college_id);
            pst.setInt(5, dept_id);
            
            pst.execute();
            JOptionPane.showMessageDialog(null,"Information Added","MESSAGE",JOptionPane.INFORMATION_MESSAGE);

        }catch(Exception e){
            
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    public addInfrastructureDetails1() {
        initComponents();
        setIconImage();
        
        jLabel18.setVisible(false);
        jLabel20.setVisible(false);
        
    }
    
    public addInfrastructureDetails1(String name) {
        initComponents();
        setIconImage();
        conn = JConnection.ConnecrDb();
        teacherName = name;
        searchCollege(teacherName);
        jLabel18.setVisible(false);
        jLabel20.setVisible(false);
        
    }
    
    public void setColor(JPanel p) {
        p.setBackground(new Color(36,47,65));
        p.setBorder(BorderFactory.createLineBorder(new Color(97,212,195), 2));
  
    }
    
    public void resetColor(JPanel p1) {
        p1.setBackground(new Color(97,212,195));   
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("AppName");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        jPanel6.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 320, 180, 40));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel15.setText("Classroom Name");
        jPanel6.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 310, 30));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel16.setText("Select Classroom or Practical Lab ?");
        jPanel6.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 310, 30));

        jTextField1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel6.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, 300, 30));

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Classroom", "Practical Lab" }));
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
        jPanel6.add(jComboBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 300, 30));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel17.setText("Classroom Number");
        jPanel6.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 310, 30));

        jTextField2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel6.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 300, 30));

        jLabel18.setForeground(new java.awt.Color(255, 0, 51));
        jLabel18.setText("Enter name");
        jPanel6.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, 300, 20));

        jLabel20.setForeground(new java.awt.Color(255, 0, 51));
        jLabel20.setText("Enter number");
        jPanel6.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, 300, 20));

        getContentPane().add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 370, 390));

        jPanel1.setBackground(new java.awt.Color(36, 47, 65));
        jPanel1.setMaximumSize(new java.awt.Dimension(439, 551));
        jPanel1.setMinimumSize(new java.awt.Dimension(439, 551));
        jPanel1.setPreferredSize(new java.awt.Dimension(439, 551));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("ADD INFRASTRUCTURE DETAILS");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, -1, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 370, 60));

        setSize(new java.awt.Dimension(382, 488));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
        // TODO add your handling code here:
        if(jTextField2.getText().equals("") && !(jTextField1.getText().equals(""))){
            jLabel20.setVisible(true);
            jLabel18.setVisible(false);
        }
        else if(jTextField1.getText().equals("") && !(jTextField2.getText().equals(""))){
            jLabel18.setVisible(true);
            jLabel20.setVisible(false);
        }
        else if(jTextField2.getText().equals("") && jTextField1.getText().equals("")){
            jLabel18.setVisible(true);
            jLabel20.setVisible(true);
        }
        else{
            searchCommonEntries();
            
            this.dispose();
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

    private void jComboBox3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox3MouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jComboBox3MouseClicked

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
        String type = (String) jComboBox3.getSelectedItem();
        jLabel17.setText(type + " Number");
        jLabel15.setText(type + " Name");
    }//GEN-LAST:event_jComboBox3ActionPerformed

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
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(addInfrastructureDetails1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(addInfrastructureDetails1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(addInfrastructureDetails1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(addInfrastructureDetails1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new addInfrastructureDetails1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}

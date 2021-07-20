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
import java.util.regex.Pattern;
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
public class editRemoveTimeSlots extends javax.swing.JFrame {

    /**
     * Creates new form editRemoveTimeSlots
     */
    
    Connection conn = null;
    int college_id,dept_id;
    
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
    
    public editRemoveTimeSlots() {
        initComponents();
    }
    
    public editRemoveTimeSlots(String name) {
        initComponents();
        setIconImage();
        JTableHeader Theader = jTable1.getTableHeader();
        Theader.setOpaque(false);
        Theader.setBackground(new Color(36,47,65));
        Theader.setForeground(new Color(255,255,255));
        nonactivate();
        conn = JConnection.ConnecrDb();
        searchCollege(name);
    }
    
    private void nonactivate()
    {
        jLabel1.setVisible(false);
        jLabel6.setVisible(false);
        jLabel8.setVisible(false);
    }
    
    private void nonactivate2()
    {
        jLabel7.setText("");
        jTextField2.setText("");
    }
    
    public void setColor(JPanel p) {
        p.setBackground(new Color(36,47,65));
        p.setBorder(BorderFactory.createLineBorder(new Color(97,212,195), 2));
  
    }
    
    public void resetColor(JPanel p1) {
        p1.setBackground(new Color(97,212,195));   
    }
    
    private void update()
    {
        try{
            conn = JConnection.ConnecrDb();
            PreparedStatement pst = null;
            
            DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
            int selectedRowIndex = jTable1.getSelectedRow();
            if(selectedRowIndex == -1){
                JOptionPane.showMessageDialog(null, "SELECT ROW FIRST", "ALERT", JOptionPane.ERROR_MESSAGE);
            }
            else{
                String type1 = model.getValueAt(selectedRowIndex, 0).toString();
                String slot1 = model.getValueAt(selectedRowIndex, 1).toString();
            
                String type2 = jLabel7.getText();
                String slot2 = jTextField2.getText();
            
                pst = conn.prepareStatement("update time_slots set type = ? , slots = ? where type = ? and slots = ? and college_id = ? and dept_id = ?");
                pst.setString(1, type2);
                pst.setString(2, slot2);
                pst.setString(3, type1);
                pst.setString(4, slot1);
                pst.setInt(5, college_id);
                pst.setInt(6, dept_id);
            
                pst.execute();
            
                JOptionPane.showMessageDialog(null, "Successfully Updated", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                nonactivate2();
                
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
            
            DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
            int selectedRowIndex = jTable1.getSelectedRow();
            if(selectedRowIndex == -1){
                JOptionPane.showMessageDialog(null, "SELECT ROW FIRST", "ALERT", JOptionPane.ERROR_MESSAGE);
            }
            else{
                String type1 = model.getValueAt(selectedRowIndex, 0).toString();
                String slot1 = model.getValueAt(selectedRowIndex, 1).toString();

                pst = conn.prepareStatement("DELETE FROM time_slots where type = ? and slots = ? and college_id = ? and dept_id = ?");
                pst.setString(1, type1);
                pst.setString(2, slot1);
                pst.setInt(3, college_id);
                pst.setInt(4, dept_id);
            
                pst.execute();
            
                JOptionPane.showMessageDialog(null, "Successfully Removed", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                nonactivate2();
                
            }
            
            
            //conn.close();
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    public ArrayList<timeSlotsClass> timeList(){
        ArrayList<timeSlotsClass> timeList = new ArrayList<>();
        
        try{
            
            PreparedStatement pst = null;

           
            int selectedRowIndex = combobox1.getSelectedIndex();
            if(selectedRowIndex == 1)
            {
                pst = conn.prepareStatement("select type,slots from time_slots where type = 'Theory' and college_id = ? and dept_id = ?");
                pst.setInt(1, college_id);
                pst.setInt(2, dept_id);
            }
            if(selectedRowIndex == 2)
            {
                pst = conn.prepareStatement("select type,slots from time_slots where type = 'Practical' and college_id = ? and dept_id = ?");
                pst.setInt(1, college_id);
                pst.setInt(2, dept_id);
            }
            
            System.out.println(selectedRowIndex);

            ResultSet rs = pst.executeQuery();
            timeSlotsClass timeReq;
            if(rs.next())
            {
                rs.previous();
                while(rs.next()){
                    timeReq = new timeSlotsClass(rs.getString("type"),rs.getString("slots"));
                    timeList.add(timeReq);
                }

            }
            else
            {
                JOptionPane.showMessageDialog(null,"No Entries Found in the table","MESSAGE",JOptionPane.INFORMATION_MESSAGE);
                
            }
            
            System.out.println(timeList);
            
            
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
            
        return timeList;
    }
    
    public void showtime(){
        ArrayList<timeSlotsClass> List = timeList();
        DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
        int rows = model.getRowCount();
        for(int i = rows-1 ; i >=0 ; i--){
            model.removeRow(i);
        }
        Object column[] = new Object[2]; 
        for(int i = 0; i<List.size(); i++){
            column[0] = List.get(i).getType();
            column[1] = List.get(i).getTime();
            model.addRow(column);
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

        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        combobox1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("AppName");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(36, 47, 65));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("EDIT / REMOVE TIME SLOTS");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 10, 340, 40));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 760, 60));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        combobox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Time Slot Type", "Theory", "Practical" }));
        combobox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combobox1ActionPerformed(evt);
            }
        });
        jPanel1.add(combobox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, 300, 30));

        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setText("Please select infrastructure type");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Select Time Slot Type: ");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, -1, -1));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Type", "Slots"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 30, 330, 380));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Slots:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 210, 50, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Type:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 130, 60, 30));

        jTextField2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField2.setToolTipText("e.g. 8:45 - 9:45 or 10:45 - 11:45");
        jTextField2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 240, 260, 40));

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 260, 30));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 170, 260, 30));

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

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 350, 130, 40));

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

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 350, 130, 40));

        jLabel6.setForeground(new java.awt.Color(255, 0, 0));
        jLabel6.setText("Please select row first ! Do not write on your own");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 320, -1, -1));

        jLabel8.setForeground(new java.awt.Color(255, 0, 0));
        jLabel8.setText("Please enter in a valid format ( Refer tooltip )");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 280, 260, 20));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 760, 450));

        setSize(new java.awt.Dimension(773, 548));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void combobox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combobox1ActionPerformed
        // TODO add your handling code here:

        int selectedRowIndex = combobox1.getSelectedIndex();
        if(selectedRowIndex == -1 )
        {

        }
        else if(selectedRowIndex == 0){
            
            DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
            int rows = model.getRowCount();
            for(int i = rows-1 ; i >=0 ; i--){
                model.removeRow(i);
            }
        }
        else
        {
            nonactivate();
            showtime();
        }
    }//GEN-LAST:event_combobox1ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
        int selectedRowIndex = jTable1.getSelectedRow();
        jLabel7.setText(model.getValueAt(selectedRowIndex, 0).toString());
        jTextField2.setText(model.getValueAt(selectedRowIndex, 1).toString());
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked
        // TODO add your handling code here:
        nonactivate();
        if(combobox1.getSelectedIndex() == -1 || combobox1.getSelectedIndex() == 0){
            jLabel1.setVisible(true);
        }
        if(jLabel7.getText().equals(""))
        {
            jLabel6.setVisible(true);
        }
        else
        {
            if(!(Pattern.matches("[0-9]{1,2}+[:]{1}+[0-9]{2}+[ ]{1}+[-]{1}+[ ]{1}+[0-9]{1,2}+[:]{1}+[0-9]{2}+$", jTextField2.getText()))){
                jLabel8.setVisible(true);
            }
            else{
                update();
                showtime();
            }
            
            
            //combobox1.setSelectedIndex(0);
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
        if(jLabel7.getText().equals(""))
        {
            jLabel1.setVisible(true);
        }
        else
        {
            remove();
            DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
            int selectedRowIndex = jTable1.getSelectedRow();
            model.removeRow(selectedRowIndex);
            //combobox1.setSelectedIndex(0);
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
            java.util.logging.Logger.getLogger(editRemoveTimeSlots.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(editRemoveTimeSlots.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(editRemoveTimeSlots.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(editRemoveTimeSlots.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new editRemoveTimeSlots().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> combobox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}

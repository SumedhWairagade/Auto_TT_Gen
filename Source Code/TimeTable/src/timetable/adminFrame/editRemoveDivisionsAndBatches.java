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
import timetable.JConnection;

/**
 *
 * @author soura
 */
public class editRemoveDivisionsAndBatches extends javax.swing.JFrame {
        /**
     * Creates new form editRemoveDivisionsAndBatches
     */
    Connection conn = null;
    String college,year ;
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
    
    public editRemoveDivisionsAndBatches() {
        initComponents();
        nonactivate();
    }
    
    public editRemoveDivisionsAndBatches(String name) {
        initComponents();
        setIconImage();
        nonactivate();
        conn = JConnection.ConnecrDb();
        searchCollege(name);
    }

    private void nonactivate()
    {
        jLabel6.setVisible(false);
    }
    
    private void nonactivate2()
    {
        jTextField5.setText("");
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
            int selectedRowIndex1 = combobox2.getSelectedIndex();
            if(selectedRowIndex1 == 1)
                year = "SE";
            else if(selectedRowIndex1 == 2)
                year = "TE";
            else if(selectedRowIndex1 == 3)
                year = "BE";
            
            DefaultTableModel model = (DefaultTableModel)jTable2.getModel();
            int selectedRowIndex = jTable2.getSelectedRow();
            if(selectedRowIndex == -1){
                JOptionPane.showMessageDialog(null, "SELECT ROW FIRST", "ALERT", JOptionPane.ERROR_MESSAGE);
            }
            else{
                int nod1 = Integer.parseInt(model.getValueAt(selectedRowIndex, 0).toString());
                int nob1 = Integer.parseInt(model.getValueAt(selectedRowIndex, 1).toString());
            
                int nod2 = Integer.parseInt(jTextField5.getText());
                int nob2 = Integer.parseInt(jTextField2.getText());
            
                pst = conn.prepareStatement("update study_years set no_of_divisions = ? , no_of_batches_in_each_division = ? where class = ? and college_id = ? and dept_id = ?");
                pst.setInt(1, nod2);
                pst.setInt(2, nob2);
                pst.setString(3, year);
                pst.setInt(4, college_id);
                pst.setInt(5, dept_id);
            
                pst.execute();
            
                JOptionPane.showMessageDialog(null, "Successfully Updated", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                nonactivate2();
            
            //conn.close();
            }
            
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    private void remove()
    {
        try{
            conn = JConnection.ConnecrDb();
            PreparedStatement pst = null;
            int selectedRowIndex1 = combobox2.getSelectedIndex();
            if(selectedRowIndex1 == 1)
                year = "SE";
            else if(selectedRowIndex1 == 2)
                year = "TE";
            else if(selectedRowIndex1 == 3)
                year = "BE";
            
            
            DefaultTableModel model = (DefaultTableModel)jTable2.getModel();
            int selectedRowIndex = jTable2.getSelectedRow();
            if(selectedRowIndex == -1){
                JOptionPane.showMessageDialog(null, "SELECT ROW FIRST", "ALERT", JOptionPane.ERROR_MESSAGE);
            }
            else{
                int nod1 = Integer.parseInt(model.getValueAt(selectedRowIndex, 0).toString());
                int nob1 = Integer.parseInt(model.getValueAt(selectedRowIndex, 1).toString());

                pst = conn.prepareStatement("DELETE FROM study_years where class = ? and college_id = ? and dept_id = ?");
                pst.setString(1,year);
                pst.setInt(2, college_id);
                pst.setInt(3, dept_id);
            
                pst.execute();
            
                JOptionPane.showMessageDialog(null, "Successfully Removed", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                nonactivate2();
            
                //conn.close();
            }
            
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    public ArrayList<StudyYears> DivBatList(){
        ArrayList<StudyYears> DivBatList = new ArrayList<>();
        
        try{
            
            PreparedStatement pst = null;

           
            int selectedRowIndex = combobox2.getSelectedIndex();
            if(selectedRowIndex == 1)
            {
                pst = conn.prepareStatement("select class, no_of_divisions,no_of_batches_in_each_division from study_years where class = 'SE' and college_id = ? and dept_id = ?");
                pst.setInt(1, college_id);
                pst.setInt(2, dept_id);
            }
            if(selectedRowIndex == 2)
            {
                pst = conn.prepareStatement("select class, no_of_divisions,no_of_batches_in_each_division from study_years where class = 'TE' and college_id = ? and dept_id = ?");
                pst.setInt(1, college_id);
                pst.setInt(2, dept_id);
            }
             if(selectedRowIndex == 3)
            {
                pst = conn.prepareStatement("select class, no_of_divisions,no_of_batches_in_each_division from study_years where class = 'BE' and college_id = ? and dept_id = ?");
                pst.setInt(1, college_id);
                pst.setInt(2, dept_id);
            }
            
            
            System.out.println(selectedRowIndex);

            ResultSet rs = pst.executeQuery();
            StudyYears DivBatReq;
            if(rs.next())
            {
                rs.previous();
                while(rs.next()){
                    DivBatReq = new StudyYears(rs.getString("class"),rs.getInt("no_of_divisions"),rs.getInt("no_of_batches_in_each_division"));
                    DivBatList.add(DivBatReq);
                }

            }
            else
            {
                JOptionPane.showMessageDialog(null,"No Entries Found in the table","MESSAGE",JOptionPane.INFORMATION_MESSAGE);
                
            }
            
            System.out.println(DivBatList);
            
            
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
            
        return DivBatList;
    }
    
    public void showDivBat(){
        ArrayList<StudyYears> List = DivBatList();
        DefaultTableModel model = (DefaultTableModel)jTable2.getModel();
        int rows = model.getRowCount();
        for(int i = rows-1 ; i >=0 ; i--){
            model.removeRow(i);
        }
        Object column[] = new Object[2]; 
        for(int i = 0; i<List.size(); i++){
            column[0] = List.get(i).get_No_of_Divisions();
            column[1] = List.get(i).get_No_of_batches_in_each_division();
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

        jPanel4 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        combobox2 = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();

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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("AppName");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(36, 47, 65));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("EDIT / REMOVE DIVISIONS AND BATCHES");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 10, 360, 40));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 760, 60));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        combobox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Year", "SE", "TE", "BE" }));
        combobox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combobox2ActionPerformed(evt);
            }
        });
        jPanel1.add(combobox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 300, 30));

        jLabel6.setForeground(new java.awt.Color(255, 0, 0));
        jLabel6.setText("Please select class");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, -1, -1));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No. of Divisions", "No. of Batches per Division"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setResizable(false);
            jTable2.getColumnModel().getColumn(1).setResizable(false);
        }

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 50, 330, 370));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Number of Batches per Division:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 220, 210, 30));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Number of Divisions:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, 140, 30));

        jTextField5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField5.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, 300, 40));

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
        jLabel15.setText("Update");
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

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 380, 130, 40));

        jPanel8.setBackground(new java.awt.Color(97, 212, 195));
        jPanel8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPanel8KeyPressed(evt);
            }
        });
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel16.setBackground(new java.awt.Color(255, 255, 255));
        jLabel16.setFont(new java.awt.Font("Corbel Light", 1, 16)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Remove");
        jLabel16.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel16MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel16MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel16MouseExited(evt);
            }
        });
        jPanel8.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 130, 40));

        jPanel1.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 380, 130, 40));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Select Year: ");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, -1, -1));

        jTextField2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 250, 300, 40));

        jLabel7.setForeground(new java.awt.Color(255, 0, 0));
        jLabel7.setText("Please select row first ! Do not write on your own");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 350, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 760, 480));

        setSize(new java.awt.Dimension(774, 573));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked
        // TODO add your handling code here:
        nonactivate();
        if(jTextField5.getText().equals(""))
        {
            jLabel6.setVisible(true);
        }
        else
        {
            update();
            showDivBat();
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

    private void combobox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combobox2ActionPerformed
        // TODO add your handling code here:

        int selectedRowIndex = combobox2.getSelectedIndex();
        if(selectedRowIndex == -1 || selectedRowIndex == 0)
        {
                DefaultTableModel model = (DefaultTableModel)jTable2.getModel();
                int rows = model.getRowCount();
                for(int i = rows-1 ; i >=0 ; i--){
                    model.removeRow(i);
        }
        }
        else
        {
            showDivBat();
        }
    }//GEN-LAST:event_combobox2ActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel)jTable2.getModel();
        int selectedRowIndex = jTable2.getSelectedRow();
        jTextField5.setText(model.getValueAt(selectedRowIndex, 0).toString());
        jTextField2.setText(model.getValueAt(selectedRowIndex, 1).toString());
    }//GEN-LAST:event_jTable2MouseClicked

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseClicked
        // TODO add your handling code here:
        nonactivate();
        if(jTextField5.getText().equals(""))
        {
            jLabel6.setVisible(true);
        }
        else
        {
            update();
            showDivBat();
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

    private void jLabel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseClicked
        // TODO add your handling code here:
        nonactivate();
        if(jTextField5.getText().equals(""))
        {
            jLabel6.setVisible(true);
        }
        else
        {
            remove();
            DefaultTableModel model = (DefaultTableModel)jTable2.getModel();
            int selectedRowIndex = jTable2.getSelectedRow();
            model.removeRow(selectedRowIndex);
        }
    }//GEN-LAST:event_jLabel16MouseClicked

    private void jLabel16MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseEntered
        // TODO add your handling code here:
        setColor(jPanel8);
    }//GEN-LAST:event_jLabel16MouseEntered

    private void jLabel16MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseExited
        // TODO add your handling code here:
        resetColor(jPanel8);
    }//GEN-LAST:event_jLabel16MouseExited

    private void jPanel8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanel8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel8KeyPressed

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
            java.util.logging.Logger.getLogger(editRemoveDivisionsAndBatches.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(editRemoveDivisionsAndBatches.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(editRemoveDivisionsAndBatches.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(editRemoveDivisionsAndBatches.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new editRemoveDivisionsAndBatches().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> combobox2;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables
}

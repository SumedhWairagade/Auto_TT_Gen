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
public class editRemoveTeacherLabAllocation extends javax.swing.JFrame {

    /**
     * Creates new form editRemoveTeacherLabAllocation
     */
    
    String teacherName,college,year,type,subject;
    int college_id,dept_id,division,batch,year_id;
    Connection conn = null;
    
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
    
    public ArrayList<teacherLab> teacherLabList(){
        ArrayList<teacherLab> teacherLabList = new ArrayList<>();
        
        try{
            
            PreparedStatement pst = null;

            pst = conn.prepareStatement("select study_years.class,teacher_allocation.division,teacher_allocation.batch,teacher_allocation.type,"
                    + "teacher_allocation.subject,teacher_allocation.sub_abbreviation,teacher_allocation.allocated_teachers,"
                    + "teacher_allocation.room from teacher_allocation "
                    + "inner join study_years on teacher_allocation.study_year_id = study_years.study_year_id "
                    + "where teacher_allocation.college_id = ? and teacher_allocation.dept_id = ? "
                    + "order by (case study_years.class when 'FE' then 1 when 'SE' then 2 when 'TE' then 3 else 4 end),"
                    + "(case teacher_allocation.type when 'Theory' then 1 else 2 end),teacher_allocation.division asc,"
                    + "teacher_allocation.batch asc");
            pst.setInt(1, college_id);
            pst.setInt(2,dept_id);

            ResultSet rs = pst.executeQuery();
            teacherLab teacherLabs;
            if(rs.next())
            {
                
                rs.previous();
                while(rs.next()){
                    teacherLabs = new teacherLab(rs.getString("study_years.class"),rs.getInt("division"),rs.getInt("batch"),rs.getString("teacher_allocation.type"),
                            rs.getString("teacher_allocation.subject"),rs.getString("teacher_allocation.sub_abbreviation"),
                            rs.getString("teacher_allocation.allocated_teachers"),rs.getString("teacher_allocation.room"));
                    teacherLabList.add(teacherLabs);
                }

            }
            else
            {
                JOptionPane.showMessageDialog(null,"No Entries Found in the table","MESSAGE",JOptionPane.INFORMATION_MESSAGE);
                
            }
            
           // conn.close();
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
            
        return teacherLabList;
    }
    
    public void showTeacherLabAlloc(){
        ArrayList<teacherLab> List = teacherLabList();
        DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
        int rows = model.getRowCount();
        for(int i = rows-1 ; i >=0 ; i--){
            model.removeRow(i);
        }
        Object column[] = new Object[8]; 
        for(int i = 0; i<List.size(); i++){
            column[0] = List.get(i).getYear();
            column[1] = List.get(i).getDivision();
            column[2] = List.get(i).getBatch();
            column[3] = List.get(i).getType();
            column[4] = List.get(i).getSubject();
            column[5] = List.get(i).getSubAbbr();
            column[6] = List.get(i).getAllocTeacher();
            column[7] = List.get(i).getAllocLabs();
            
          
            model.addRow(column);
        }
    }
    
    private void setIconImage(){
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("logo.png")));
    }
    
    public void setColor(JPanel p) {
        p.setBackground(new Color(36,47,65));
        p.setBorder(BorderFactory.createLineBorder(new Color(97,212,195), 2));
  
    }
    
    public void resetColor(JPanel p1) {
        p1.setBackground(new Color(97,212,195));   
    }
    
    public void getYearID(){
        try{
            
            PreparedStatement pst = null;
            pst = conn.prepareStatement("select study_year_id from study_years where college_id = ? and dept_id = ? and class = ?");
            pst.setInt(1, college_id);
            pst.setInt(2, dept_id);
            pst.setString(3, year);
            ResultSet rs = pst.executeQuery();
            
            if(rs.next())
            {
                year_id = rs.getInt("study_year_id");
            }
     
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    public void remove(){
        try{
            System.out.println("jdbksdbvbsdjvbsbdv");
            PreparedStatement pst = null;
            pst = conn.prepareStatement("update teacher_allocation set allocated_teachers = '-',room = '-' where college_id = ? "
                    + "and dept_id = ? and study_year_id = ? and division = ? and batch = ? and type = ? and subject = ?");
            
            //System.out.println(college_id + " " + dept_id +" "+year_id +" "+division+" "+batch+" "+type+" "+subject);
            pst.setInt(1, college_id);
            pst.setInt(2, dept_id);
            pst.setInt(3, year_id);
            pst.setInt(4, division);
            pst.setInt(5, batch);
            pst.setString(6, type);
            pst.setString(7, subject);
            
            pst.execute();
            JOptionPane.showMessageDialog(null,"Allocation Removed","MESSAGE",JOptionPane.INFORMATION_MESSAGE);
            showTeacherLabAlloc();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    public editRemoveTeacherLabAllocation() {
        initComponents();
    }
    
    public editRemoveTeacherLabAllocation(String name) {
        initComponents();
        setIconImage();
        teacherName = name;
        conn = JConnection.ConnecrDb();
        searchCollege(name);
        JTableHeader Theader = jTable1.getTableHeader();
        Theader.setOpaque(false);
        Theader.setBackground(new Color(36,47,65));
        Theader.setForeground(new Color(255,255,255));
        jLabel4.setVisible(false);
        showTeacherLabAlloc();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("AppName");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBackground(new java.awt.Color(36, 47, 65));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("REMOVE TEACHER/LAB ALLOCATION");
        jPanel6.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 10, -1, 40));

        jPanel5.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 950, 60));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "YEAR", "DIVISION", "BATCH", "TYPE", "SUBJECT", "SUBJECT_ABBR", "ALLOC_TEACHER", "ALLOC_LABS"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setRowHeight(40);
        jTable1.setSelectionBackground(new java.awt.Color(97, 212, 195));
        jTable1.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jScrollPane1.setViewportView(jTable1);

        jPanel5.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 910, 380));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 102));
        jLabel1.setText("This will only remove the Allocated Teachers and Labs");
        jPanel5.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 490, 410, 30));

        jPanel11.setBackground(new java.awt.Color(97, 212, 195));
        jPanel11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPanel11KeyPressed(evt);
            }
        });
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel19.setBackground(new java.awt.Color(255, 255, 255));
        jLabel19.setFont(new java.awt.Font("Corbel Light", 1, 16)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Remove");
        jLabel19.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel19MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel19MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel19MouseExited(evt);
            }
        });
        jPanel11.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 130, 40));

        jPanel5.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 530, 130, 40));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 102));
        jLabel2.setText("Batch 0 Indicates Not Applicable");
        jPanel5.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 70, 240, 30));

        jLabel4.setForeground(new java.awt.Color(255, 0, 0));
        jLabel4.setText("Please Select Row First");
        jPanel5.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 540, 200, 20));

        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 950, 590));

        setSize(new java.awt.Dimension(964, 628));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel19MouseClicked
        // TODO add your handling code here:
        int rowSelected;
        if(jTable1.getSelectedRow() == -1){
            jLabel4.setVisible(true);
        }
        else{
            jLabel4.setVisible(false);
            rowSelected = jTable1.getSelectedRow();
            DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
            year = (String)model.getValueAt(rowSelected, 0);
            division = (int)model.getValueAt(rowSelected, 1);
            batch = (int)model.getValueAt(rowSelected, 2);
            type = (String)model.getValueAt(rowSelected, 3);
            subject = (String)model.getValueAt(rowSelected, 4);
            getYearID();
            remove();
            
            
        }
    }//GEN-LAST:event_jLabel19MouseClicked

    private void jLabel19MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel19MouseEntered
        // TODO add your handling code here:
        setColor(jPanel11);
    }//GEN-LAST:event_jLabel19MouseEntered

    private void jLabel19MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel19MouseExited
        // TODO add your handling code here:
        resetColor(jPanel11);
    }//GEN-LAST:event_jLabel19MouseExited

    private void jPanel11KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanel11KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel11KeyPressed

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
            java.util.logging.Logger.getLogger(editRemoveTeacherLabAllocation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(editRemoveTeacherLabAllocation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(editRemoveTeacherLabAllocation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(editRemoveTeacherLabAllocation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new editRemoveTeacherLabAllocation().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}

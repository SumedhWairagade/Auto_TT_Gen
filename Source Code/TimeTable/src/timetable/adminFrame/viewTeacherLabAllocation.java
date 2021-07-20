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
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import timetable.JConnection;

/**
 *
 * @author Pritam Bera
 */
public class viewTeacherLabAllocation extends javax.swing.JFrame {

    /**
     * Creates new form viewTeacherLabAllocation
     */
    String teacherName,college;
    int college_id,dept_id;
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
            
            conn.close();
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
            
        return teacherLabList;
    }
    
    public void showTeacherLabAlloc(){
        ArrayList<teacherLab> List = teacherLabList();
        DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
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
    
    public viewTeacherLabAllocation() {
        initComponents();
    }
    
    public viewTeacherLabAllocation(String name) {
        initComponents();
        setIconImage();
        teacherName = name;
        conn = JConnection.ConnecrDb();
        searchCollege(name);
        JTableHeader Theader = jTable1.getTableHeader();
        Theader.setOpaque(false);
        Theader.setBackground(new Color(36,47,65));
        Theader.setForeground(new Color(255,255,255));
        
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
        jLabel3.setText("TEACHER/LAB ALLOCATION DETAILS");
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
                false, false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setRowHeight(40);
        jTable1.setSelectionBackground(new java.awt.Color(97, 212, 195));
        jTable1.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jScrollPane1.setViewportView(jTable1);

        jPanel5.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 910, 460));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 102));
        jLabel1.setText("Batch 0 Indicates Not Applicable");
        jPanel5.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 70, 240, 30));

        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 950, 590));

        setSize(new java.awt.Dimension(966, 629));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(viewTeacherLabAllocation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(viewTeacherLabAllocation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(viewTeacherLabAllocation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(viewTeacherLabAllocation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new viewTeacherLabAllocation().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}

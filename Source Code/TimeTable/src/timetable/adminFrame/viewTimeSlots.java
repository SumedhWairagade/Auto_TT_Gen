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
public class viewTimeSlots extends javax.swing.JFrame {

    /**
     * Creates new form viewTimeSlots
     */
    
    String teacherName;
    Connection conn = null;
    int college_id,dept_id;
    
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
    
    public ArrayList<timeSlotsClass> timeList(){
        ArrayList<timeSlotsClass> timeList = new ArrayList<>();
        
        try{
            
            PreparedStatement pst = null;

            pst = conn.prepareStatement("select type,slots from time_slots where college_id = ? and dept_id = ? order by (case type when 'Theory' then 1 else 2 end)");
            pst.setInt(1, college_id);
            pst.setInt(2, dept_id);

            ResultSet rs = pst.executeQuery();
            timeSlotsClass timeSlots;
            if(rs.next())
            {
                rs.previous();
                while(rs.next()){
                    timeSlots = new timeSlotsClass(rs.getString("type"),rs.getString("slots"));
                    timeList.add(timeSlots);
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
            
        return timeList;
    }
    
    public void showTime(){
        ArrayList<timeSlotsClass> List = timeList();
        DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
        Object column[] = new Object[2]; 
        for(int i = 0; i<List.size(); i++){
            column[0] = List.get(i).getType();
            column[1] = List.get(i).getTime();
           
            model.addRow(column);
        }
    }
    
    private void setIconImage(){
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("logo.png")));
    }
    
    public viewTimeSlots() {
        initComponents();
        setIconImage();
    }

    public viewTimeSlots(String name) {
        initComponents();
        setIconImage();
        teacherName = name;
        conn = JConnection.ConnecrDb();
        searchCollege(name);
        JTableHeader Theader = jTable1.getTableHeader();
        Theader.setOpaque(false);
        Theader.setBackground(new Color(36,47,65));
        Theader.setForeground(new Color(255,255,255));
        showTime();
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
        jLabel3.setText("TIME SLOTS");
        jPanel6.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, -1, 40));

        jPanel5.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 380, 60));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "TYPE", "TIME"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setRowHeight(40);
        jTable1.setSelectionBackground(new java.awt.Color(97, 212, 195));
        jTable1.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jScrollPane1.setViewportView(jTable1);

        jPanel5.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 320, 350));

        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 380, 480));

        setSize(new java.awt.Dimension(394, 518));
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
            java.util.logging.Logger.getLogger(viewTimeSlots.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(viewTimeSlots.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(viewTimeSlots.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(viewTimeSlots.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new viewTimeSlots().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
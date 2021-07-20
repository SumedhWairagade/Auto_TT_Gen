/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timetable.teacherFrames;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import timetable.JConnection;
/**
 *
 * @author ASUS
 */
public class teacherViewTimetable extends javax.swing.JFrame {
    String userName, teacherName;
    /**
     * Creates new form teacherViewTimetable
     */
    Connection conn = null;
    int college_id,dept_id;
    
    private void setIconImage(){
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("logo.png")));
    }
    
    public void searchCollege(String name){
        try{
            
            PreparedStatement pst = null;
            pst = conn.prepareStatement("select name,position,college_id,dept_id from teachers where username = ?");
            pst.setString(1, name);
            ResultSet rs = pst.executeQuery();
            
            if(rs.next())
            {
                college_id = rs.getInt("college_id");
                dept_id = rs.getInt("dept_id");
                jLabel5.setText("Name of Staff: "+rs.getString("name"));
                jLabel6.setText("Designation: "+rs.getString("position"));
                
                PreparedStatement pst2 = null;
                pst2 = conn.prepareStatement("select college_name from colleges where college_id = ?");
                pst2.setInt(1, college_id);
                ResultSet rs2 = pst2.executeQuery();
                
                if(rs2.next()){
                    jLabel1.setText(rs2.getString("college_name").toUpperCase());
                }
                
                PreparedStatement pst3 = null;
                pst3 = conn.prepareStatement("select dept_name from departments where college_id = ? and dept_id = ?");
                pst3.setInt(1, college_id);
                pst3.setInt(2, dept_id);
                ResultSet rs3 = pst3.executeQuery();
                
                if(rs3.next()){
                    jLabel2.setText("DEPARTMENT OF "+rs3.getString("dept_name").toUpperCase());
                }
                
            }

        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
        
    public teacherViewTimetable() {
        initComponents();
        setIconImage();   
    }
    
    public teacherViewTimetable(String name){
        initComponents();
        conn = JConnection.ConnecrDb();
        setIconImage();
        searchCollege(name);
        userName=name;
        //jLabel4.setText("TIME - TABLE");
        
        JTableHeader Theader = jTable1.getTableHeader();
        Theader.setOpaque(false);
        Theader.setBackground(new Color(153,0,0));
        Theader.setForeground(Color.WHITE);
        Theader.setFont(new Font("Segoe UI",Font.BOLD,12));
        ((DefaultTableCellRenderer)Theader.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for(int i = 0; i < 6; i++){
            jTable1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        HashMap<String, String> mapBatch = new HashMap<String, String>();
        mapBatch.put("1", "A");
        mapBatch.put("2", "B");
        mapBatch.put("3", "C");
        mapBatch.put("4", "D");
        
        HashMap<String, String>mapDivision = new HashMap<String, String>();
        mapDivision.put("1", "I");
        mapDivision.put("2", "II");
        
        
        HashMap<String, Integer> mapDay = new HashMap<String, Integer>();
        String dayTime="M 8:45-9:45";
        
        mapDay.put("M", 1);
        mapDay.put("T", 2);
        mapDay.put("W", 3);
        mapDay.put("TH", 4);
        mapDay.put("F", 5);
        
        HashMap<String, Integer> mapTime = new HashMap<String, Integer>();

        mapTime.put("8:45 - 9:45", 0);
        mapTime.put("9:45 - 10:45", 1);
        mapTime.put("10:45 - 11:00", 2);
        mapTime.put("11:00 - 12:00", 3);
        mapTime.put("12:00 - 1:00", 4);
        mapTime.put("1:00 - 2:00", 5);
        mapTime.put("2:00 - 3:00", 6);
        mapTime.put("3:00 - 4:00", 7);
        mapTime.put("4:00 - 5:00", 8);
        mapTime.put("8:45 - 10:45", 1);
        mapTime.put("11:00 - 1:00", 4);
        mapTime.put("2:00 - 4:00", 7);
        
        /*jTable1.setValueAt("Monday", 0, 1);
        jTable1.setValueAt("Tuesday", 0, 2);
        jTable1.setValueAt("Wednesday", 0, 3);   
        jTable1.setValueAt("Thursday", 0, 4);
        jTable1.setValueAt("Friday", 0, 5);*/

        jTable1.getColumnModel().getColumn(0).setHeaderValue("Time/Day");
        jTable1.getColumnModel().getColumn(1).setHeaderValue("Monday");
        jTable1.getColumnModel().getColumn(2).setHeaderValue("Tuesday");
        jTable1.getColumnModel().getColumn(3).setHeaderValue("Wednesday");
        jTable1.getColumnModel().getColumn(4).setHeaderValue("Thursday");
        jTable1.getColumnModel().getColumn(5).setHeaderValue("Friday");
        
        jTable1.setValueAt("8:45-9:45", 0, 0);
        jTable1.setValueAt("9:45-10:45", 1, 0);
        jTable1.setValueAt("10:45-11:00", 2, 0);
        jTable1.setValueAt("11:00-12:00", 3, 0);
        jTable1.setValueAt("12:00-1:00", 4, 0);
        jTable1.setValueAt("1:00-2:00", 5, 0);
        jTable1.setValueAt("2:00-3:00", 6, 0);
        jTable1.setValueAt("3:00-4:00", 7, 0);
        jTable1.setValueAt("4:00-5:00", 8, 0);

        
        String[] splitString = null;
        String year, division, subj, room, resultString, tempTeacher, batch;
        String[] x, y, z, w, p, t;
        int index = 0;
        // SQL Query Here
        try
         {   
             
            
            PreparedStatement stmt1 = conn.prepareStatement("select name from teachers where username = ?");
            stmt1.setString(1,userName);
            ResultSet rs1 = stmt1.executeQuery();
            rs1.next();
            
            teacherName = rs1.getString("name");
            
            PreparedStatement stmt = conn.prepareStatement("select year_study, btch, division, subj, room, teacher, time_meet from table_time where teacher LIKE ? and college_id = ? and dept_id = ?"); 
            String temp = "%" + teacherName + "%";
            stmt.setString(1, temp);
            stmt.setInt(2, college_id);
            stmt.setInt(3, dept_id);
            ResultSet rs=stmt.executeQuery();
            
            
            while(rs.next()) {
                index = 0;
                tempTeacher = rs.getString("teacher");
                x = tempTeacher.split(", ");
                System.out.println(x.length);
                for(index = 0; index < 4; index++) {
                    if(x[index].equals(teacherName))
                        break;
                 }
                year = rs.getString("year_study");        
                division = rs.getString("division");
                
                subj = rs.getString("subj");
                z = subj.split(", ");
                t = z[index].split(" ");
                
                room = rs.getString("room");
                y = room.split(", ");
                
                
                if(x.length == 4) {
                    batch = rs.getString("btch");
                    w = batch.split(", ");
                    p = w[index].split(" ");
                    
                    resultString = year + " - " + mapDivision.get(division) + " : " + mapBatch.get(p[2]) + " : " + t[0] + " : " + y[index];
                    
                    dayTime = rs.getString("time_meet");
                    splitString = dayTime.split("\\s",2);
                    jTable1.setValueAt(resultString, mapTime.get(splitString[1]) - 1 , mapDay.get(splitString[0]));
                    jTable1.setValueAt(resultString, mapTime.get(splitString[1]), mapDay.get(splitString[0]));
                }
                else{
                    
                    resultString = year + " - " + mapDivision.get(division) + " : " + t[0] + " : " + y[index];
                    dayTime = rs.getString("time_meet");
                    splitString = dayTime.split("\\s",2);
                    jTable1.setValueAt(resultString, mapTime.get(splitString[1]), mapDay.get(splitString[0]));
                }
                    
                
                
            }
            //con.close();  
        }
         catch(Exception e){ System.out.println(e);}
        
        //jTable1.setTableHeader(null);
        jTable1.setRowHeight(40);//setting row width

        jTable1.getColumnModel().getColumn(0).setPreferredWidth(100);//column width setting
        
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(100);
        
        
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("AppName");
        setMinimumSize(new java.awt.Dimension(850, 600));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(36, 47, 65));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("TIME-TABLE");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 10, -1, 40));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 860, 60));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        jTable1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setShowGrid(true);
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setFont(new java.awt.Font("Corbel", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 153));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel2.setFont(new java.awt.Font("Corbel", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 0, 51));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jPanel4.setBackground(new java.awt.Color(232, 188, 11));

        jLabel4.setBackground(new java.awt.Color(153, 153, 153));
        jLabel4.setFont(new java.awt.Font("Corbel", 1, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("TIME - TABLE");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(217, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(213, 213, 213))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel5.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        jLabel5.setText("jLabel5");

        jLabel6.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("jLabel6");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(220, 220, 220)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(220, 220, 220)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(140, 140, 140)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 860, 560));

        jPanel17.setBackground(new java.awt.Color(97, 212, 195));
        jPanel17.setMaximumSize(new java.awt.Dimension(180, 40));
        jPanel17.setMinimumSize(new java.awt.Dimension(180, 40));
        jPanel17.setPreferredSize(new java.awt.Dimension(190, 40));
        jPanel17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel17MouseClicked(evt);
            }
        });
        jPanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Download PDF");
        jLabel9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel9MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel9MouseExited(evt);
            }
        });
        jPanel17.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 40));

        jPanel1.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 640, 140, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 860, 720));

        setSize(new java.awt.Dimension(873, 757));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel9MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseEntered
        // TODO add your handling code here:
        setColor(jPanel17);
    }//GEN-LAST:event_jLabel9MouseEntered

    private void jLabel9MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseExited
        // TODO add your handling code here:
        resetColor(jPanel17);
    }//GEN-LAST:event_jLabel9MouseExited

    private void jPanel17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel17MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel17MouseClicked

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel9MouseClicked

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
            java.util.logging.Logger.getLogger(teacherViewTimetable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(teacherViewTimetable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(teacherViewTimetable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(teacherViewTimetable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new teacherViewTimetable("Dr. Mrs. A. A. Agarkar").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}

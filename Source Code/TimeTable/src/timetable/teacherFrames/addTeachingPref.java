/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timetable.teacherFrames;

import java.awt.Color;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import timetable.JConnection;

/**
 *
 * @author Pritam Bera
 */
public class addTeachingPref extends javax.swing.JFrame {

    /**
     * Creates new form addTeachingPref
     */
    String teacherName,subject,year1,year2,year3,subject1,subject2,subject3,not1,not2,not3,choice1,choice2,choice3;
    int college_id,dept_id,year_id;
    Connection conn = null;
    
    private void setIconImage(){
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("logo.png")));
    }
    
    public void searchCollege(String name){
        try{
            
            PreparedStatement pst = null;
            pst = conn.prepareStatement("select name,college_id,dept_id from teachers where username = ?");
            pst.setString(1, name);
            ResultSet rs = pst.executeQuery();
            
            if(rs.next())
            {
                college_id = rs.getInt("college_id");
                dept_id = rs.getInt("dept_id");
                teacherName = rs.getString("name");
            }
            
            
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    private void fillComboYear()
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
               
                if(studyYear.equals("FE")){
                    jComboBox3.addItem("First Year");
                    jComboBox5.addItem("First Year");
                    jComboBox7.addItem("First Year");
                }
                    
                else if(studyYear.equals("SE")){
                    jComboBox3.addItem("Second Year"); 
                    jComboBox5.addItem("Second Year"); 
                    jComboBox7.addItem("Second Year"); 
                    
                }
                    
                else if(studyYear.equals("TE")){
                    jComboBox3.addItem("Third Year"); 
                    jComboBox5.addItem("Third Year");
                    jComboBox7.addItem("Third Year");
                }
                    
                else if(studyYear.equals("BE")){
                    jComboBox3.addItem("Final Year"); 
                    jComboBox5.addItem("Final Year");
                    jComboBox7.addItem("Final Year");
                }
                    
                   
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
        
    }
    
    public void setColor(JPanel p) {
        p.setBackground(new Color(36,47,65));
        p.setBorder(BorderFactory.createLineBorder(new Color(97,212,195), 2));
  
    }
    
    private void fillSubject(String selectedItem,String combobox)
    {
        if(selectedItem.equals("First Year"))
            selectedItem = "FE";
        else if(selectedItem.equals("Second Year"))
            selectedItem = "SE";
        else if(selectedItem.equals("Third Year"))
            selectedItem = "TE";
        else if(selectedItem.equals("Final Year"))
            selectedItem = "BE";
        try{
            
            PreparedStatement pst = null;
            PreparedStatement pst2 = null;
            ResultSet rs = null;
            ResultSet rs2 = null;
            pst2 = conn.prepareStatement("select study_year_id from study_years where college_id = ? and dept_id = ? and class  = ?");
            pst2.setInt(1, college_id);
            pst2.setInt(2,dept_id);
            pst2.setString(3, selectedItem);
            rs2 = pst2.executeQuery();
            if(rs2.next())
            {
                year_id = rs2.getInt("study_year_id");
                //System.out.println("jsjhvdjvcjdsvjh");
            }
            
            pst = conn.prepareStatement("select subject from teacher_allocation where college_id=? and dept_id=? and study_year_id = ? and type = 'Theory'");
            pst.setInt(1,college_id);
            pst.setInt(2,dept_id);
            pst.setInt(3, year_id);
            rs = pst.executeQuery();
            
            while(rs.next())
            {
                
                subject = rs.getString("subject");
                if(combobox.equals("combobox3"))
                    jComboBox4.addItem(subject);
                else if(combobox.equals("combobox5"))
                    jComboBox6.addItem(subject);
                if(combobox.equals("combobox7"))
                    jComboBox8.addItem(subject);
                
                
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
        
    }
    
    public void searchAlreadyAdded(){
        try{
            
            PreparedStatement pst = null;
            pst = conn.prepareStatement("select * from teaching_preference where name = ? and college_id = ? and dept_id = ?");
            pst.setString(1, teacherName);
            pst.setInt(2, college_id);
            pst.setInt(3, dept_id);
            ResultSet rs = pst.executeQuery();
            
            if(rs.next())
            {
                JOptionPane.showMessageDialog(null,"You have already submitted preferences","MESSAGE",JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
            }
            else{
                submitPref();
            }
    
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    public void submitPref(){
        
       
        if(jComboBox3.getSelectedItem().equals("First Year"))
            year1 = "FE";
        else if(jComboBox3.getSelectedItem().equals("Second Year"))
            year1 = "SE";
        else if(jComboBox3.getSelectedItem().equals("Third Year"))
            year1 = "TE";
        else if(jComboBox3.getSelectedItem().equals("Final Year"))
            year1 = "BE";
        
        if(jComboBox5.getSelectedItem().equals("First Year"))
            year2 = "FE";
        else if(jComboBox5.getSelectedItem().equals("Second Year"))
            year2 = "SE";
        else if(jComboBox5.getSelectedItem().equals("Third Year"))
            year2 = "TE";
        else if(jComboBox5.getSelectedItem().equals("Final Year"))
            year2 = "BE";
        
        if(jComboBox7.getSelectedItem().equals("First Year"))
            year3 = "FE";
        else if(jComboBox7.getSelectedItem().equals("Second Year"))
            year3 = "SE";
        else if(jComboBox7.getSelectedItem().equals("Third Year"))
            year3 = "TE";
        else if(jComboBox7.getSelectedItem().equals("Final Year"))
            year3 = "BE";
        
        subject1 = (String)jComboBox4.getSelectedItem();
        subject2 = (String)jComboBox6.getSelectedItem();
        subject3 = (String)jComboBox8.getSelectedItem();
        
        not1 = (String)jComboBox1.getSelectedItem();
        not2 = (String)jComboBox2.getSelectedItem();
        not3 = (String)jComboBox9.getSelectedItem();
        
        choice1 = year1 + " : " + subject1;
        choice2 = year2 + " : " + subject2;
        choice3 = year3 + " : " + subject3;
        
        try{
            
            PreparedStatement pst = null;
            pst = conn.prepareStatement("insert into teaching_preference(college_id,dept_id,name,subject_choice_1,no_of_times_1,subject_choice_2,no_of_times_2,subject_choice_3,no_of_times_3) values (?,?,?,?,?,?,?,?,?)");
            pst.setInt(1,college_id);
            pst.setInt(2,dept_id);
            pst.setString(3, teacherName);
            pst.setString(4, choice1);
            pst.setString(5, not1);
            pst.setString(6, choice2);
            pst.setString(7, not2);
            pst.setString(8, choice3);
            pst.setString(9, not3);
            
            pst.execute();
            JOptionPane.showMessageDialog(null,"Teaching Preferences Submitted Successfully","MESSAGE",JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
            
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    public void resetColor(JPanel p1) {
        p1.setBackground(new Color(97,212,195));   
    }
    
    public addTeachingPref() {
        initComponents();
    }
    
    public addTeachingPref(String name) {
        initComponents();
        setIconImage();
        conn = JConnection.ConnecrDb();
        
        searchCollege(name);
        fillComboYear();
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
        jLabel16 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jPanel7 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        jComboBox6 = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jComboBox7 = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        jComboBox8 = new javax.swing.JComboBox<>();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox9 = new javax.swing.JComboBox<>();

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
        jLabel3.setText("SUBMIT TEACHING PREFERENCE");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 10, -1, 40));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 890, -1));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel16.setText("Number of Times Taught");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 60, 240, 30));

        jComboBox3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox3ItemStateChanged(evt);
            }
        });
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
        jPanel1.add(jComboBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 220, 30));

        jPanel7.setBackground(new java.awt.Color(97, 212, 195));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setFont(new java.awt.Font("Corbel Light", 1, 16)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("SUBMIT");
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

        jPanel1.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 450, 180, 40));

        jLabel17.setFont(new java.awt.Font("Cambria", 3, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(36, 47, 65));
        jLabel17.setText("SUBJECT CHOICE 1 :");
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 220, 30));

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
        jPanel1.add(jComboBox4, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 100, 300, 30));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel18.setText("Select Year ");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 220, 30));

        jLabel19.setFont(new java.awt.Font("Cambria", 3, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(36, 47, 65));
        jLabel19.setText("SUBJECT CHOICE 2 :");
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 220, 30));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel20.setText("Select Year ");
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, 220, 30));

        jComboBox5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox5ItemStateChanged(evt);
            }
        });
        jComboBox5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox5MouseClicked(evt);
            }
        });
        jComboBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox5ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 220, 30));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel21.setText("Select Subject");
        jPanel1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 200, 300, 30));

        jComboBox6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox6MouseClicked(evt);
            }
        });
        jComboBox6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox6ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox6, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 240, 300, 30));

        jLabel22.setFont(new java.awt.Font("Cambria", 3, 18)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(36, 47, 65));
        jLabel22.setText("SUBJECT CHOICE 3 :");
        jPanel1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, 220, 30));

        jLabel23.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel23.setText("Select Year ");
        jPanel1.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, 220, 30));

        jComboBox7.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox7ItemStateChanged(evt);
            }
        });
        jComboBox7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox7MouseClicked(evt);
            }
        });
        jComboBox7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox7ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 380, 220, 30));

        jLabel24.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel24.setText("Select Subject");
        jPanel1.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 340, 300, 30));

        jComboBox8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox8MouseClicked(evt);
            }
        });
        jComboBox8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox8ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox8, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 380, 300, 30));

        jLabel25.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel25.setText("Select Subject");
        jPanel1.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 60, 300, 30));

        jLabel26.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel26.setText("Number of Times Taught");
        jPanel1.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 200, 240, 30));

        jLabel27.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel27.setText("Number of Times Taught");
        jPanel1.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 340, 240, 30));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "more than 3" }));
        jPanel1.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 100, 230, 30));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "more than 3" }));
        jPanel1.add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 240, 230, 30));

        jComboBox9.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "more than 3" }));
        jPanel1.add(jComboBox9, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 380, 230, 30));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 890, 520));

        setSize(new java.awt.Dimension(902, 618));
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
        searchAlreadyAdded();
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
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void jComboBox5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox5MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox5MouseClicked

    private void jComboBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox5ActionPerformed

    private void jComboBox6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox6MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox6MouseClicked

    private void jComboBox6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox6ActionPerformed

    private void jComboBox7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox7MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox7MouseClicked

    private void jComboBox7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox7ActionPerformed

    private void jComboBox8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox8MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox8MouseClicked

    private void jComboBox8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox8ActionPerformed

    private void jComboBox3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox3ItemStateChanged
        // TODO add your handling code here:
        jComboBox4.removeAllItems();
        String selectedItem = (String)jComboBox3.getSelectedItem();
        fillSubject(selectedItem,"combobox3");
    }//GEN-LAST:event_jComboBox3ItemStateChanged

    private void jComboBox5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox5ItemStateChanged
        // TODO add your handling code here:
        jComboBox6.removeAllItems();
        String selectedItem = (String)jComboBox5.getSelectedItem();
        fillSubject(selectedItem,"combobox5");
    }//GEN-LAST:event_jComboBox5ItemStateChanged

    private void jComboBox7ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox7ItemStateChanged
        // TODO add your handling code here:
        jComboBox8.removeAllItems();
        String selectedItem = (String)jComboBox7.getSelectedItem();
        fillSubject(selectedItem,"combobox7");
    }//GEN-LAST:event_jComboBox7ItemStateChanged

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
            java.util.logging.Logger.getLogger(addTeachingPref.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(addTeachingPref.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(addTeachingPref.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(addTeachingPref.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new addTeachingPref().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JComboBox<String> jComboBox7;
    private javax.swing.JComboBox<String> jComboBox8;
    private javax.swing.JComboBox<String> jComboBox9;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel7;
    // End of variables declaration//GEN-END:variables
}

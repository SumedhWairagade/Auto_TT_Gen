/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timetable.Panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import timetable.JConnection;

/**
 *
 * @author ASUS
 */
public class studenthome extends javax.swing.JPanel {

    /**
     * Creates new form studenthome
     */
    
    Connection conn = null;
    int college_id,dept_id,nod;
    
    private void fillCollege()
    {
        try{
            
            
            PreparedStatement pst = null;
            ResultSet rs = null;
            pst = conn.prepareStatement("select college_name from colleges");
            rs = pst.executeQuery();
            
            while(rs.next())
            {
                String college_name=rs.getString("college_name");
                jComboBox5.addItem(college_name);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
        
    }
    
    public void searchCollegeId(String SelectedCollege){
        try{
            
            
            PreparedStatement pst = null;
            ResultSet rs = null;
            pst = conn.prepareStatement("select college_id from colleges where college_name = ?");
            pst.setString(1, SelectedCollege);
            rs = pst.executeQuery();
            
            if(rs.next())
            {
                college_id=rs.getInt("college_id");
                
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    private void fillDepartment()
    {
        try{
            
            
            PreparedStatement pst = null;
            ResultSet rs = null;
            pst = conn.prepareStatement("select dept_name from departments where college_id = ?");
            pst.setInt(1, college_id);
            rs = pst.executeQuery();
            
            while(rs.next())
            {
                String dept_name=rs.getString("dept_name");
                jComboBox1.addItem(dept_name);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
        
    }
    
    public void searchDeptId(String SelectedDepartment){
        try{
            
            
            PreparedStatement pst = null;
            ResultSet rs = null;
            pst = conn.prepareStatement("select dept_id from departments where dept_name = ?");
            pst.setString(1, SelectedDepartment);
            rs = pst.executeQuery();
            
            if(rs.next())
            {
                dept_id=rs.getInt("dept_id");
                
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    private void fillYear()
    {
        try{
            
            
            PreparedStatement pst = null;
            ResultSet rs = null;
            pst = conn.prepareStatement("select class from study_years where college_id = ? and dept_id = ?");
            pst.setInt(1, college_id);
            pst.setInt(2, dept_id);
            rs = pst.executeQuery();
            
            while(rs.next())
            {
                String study_year = rs.getString("class");
                if(study_year.equals("FE"))
                    jComboBox3.addItem("First Year");
                else if(study_year.equals("SE"))
                    jComboBox3.addItem("Second Year");
                else if(study_year.equals("TE"))
                    jComboBox3.addItem("Third Year");
                else
                    jComboBox3.addItem("Final Year");
                
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
        
    }
    
    private void fillDivision(String SelectedYear)
    {
        try{
            
            
            PreparedStatement pst = null;
            ResultSet rs = null;
            pst = conn.prepareStatement("select no_of_divisions from study_years where college_id = ? and dept_id = ? and class = ?");
            pst.setInt(1, college_id);
            pst.setInt(2, dept_id);
            pst.setString(3, SelectedYear);
            rs = pst.executeQuery();
            
            if(rs.next())
            {
                nod = rs.getInt("no_of_divisions");
                for(int i = 1; i<=nod; i++){
                    jComboBox4.addItem(""+i+"");
                }
                
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
        
    }
    
    public void setTimetable(String selectedCollege,String selectedDept,String selectedYear,String selectedDiv){
        
        searchCollegeId(selectedCollege);
        searchDeptId(selectedDept);
        
        int i, rowCount = jTable1.getRowCount();
        for(i = 0;i<rowCount;i++){
            jTable1.setValueAt("", i, 1);
            jTable1.setValueAt("", i, 2);
            jTable1.setValueAt("", i, 3);
            jTable1.setValueAt("", i, 4);
            jTable1.setValueAt("", i, 5);
        }
        
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
        
        HashMap<String, String> mapBatch = new HashMap<String, String>();
        mapBatch.put("1", "A");
        mapBatch.put("2", "B");
        mapBatch.put("3", "C");
        mapBatch.put("4", "D");
        
        HashMap<String, String>mapDivision = new HashMap<String, String>();
        mapDivision.put("1", "I");
        mapDivision.put("2", "II");
        
        /*jTable1.setValueAt("Monday", 0, 1);
        jTable1.setValueAt("Tuesday", 0, 2);
        jTable1.setValueAt("Wednesday", 0, 3);   
        jTable1.setValueAt("Thursday", 0, 4);
        jTable1.setValueAt("Friday", 0, 5);*/

        jTable1.getColumnModel().getColumn(0).setHeaderValue("<html>Time/Day");
        jTable1.getColumnModel().getColumn(1).setHeaderValue("<html>Monday");
        jTable1.getColumnModel().getColumn(2).setHeaderValue("<html>Tuesday");
        jTable1.getColumnModel().getColumn(3).setHeaderValue("<html>Wednesday");
        jTable1.getColumnModel().getColumn(4).setHeaderValue("<html>Thursday");
        jTable1.getColumnModel().getColumn(5).setHeaderValue("<html>Friday");
        
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
        String year, division, subj, room, resultString, tempTeacher,tempRoom,teacher, batch;
        String[] x, y, z, t, w, p;
        int index = 0;
        // SQL Query Here
        
        try
         {     

                PreparedStatement stmt = conn.prepareStatement("select btch, subj, room, teacher, time_meet from table_time where year_study = ? and division = ? and college_id = ? and dept_id = ?"); 
                
                stmt.setString(1, selectedYear);
                stmt.setInt(2, Integer.parseInt(selectedDiv));
                stmt.setInt(3, college_id);
                stmt.setInt(4, dept_id);
                ResultSet rs=stmt.executeQuery();
            
                if(rs.next()){
                    rs.previous();
                    while(rs.next()) {
    
                        if(rs.getString("btch").equals("-")) {
                        
                            tempTeacher = rs.getString("teacher");
                        
                            subj = rs.getString("subj");
                            z = subj.split(" ");
                        
                            room = rs.getString("room");
                        
                            resultString = z[0] + " : " + tempTeacher + " : " + room;
                        
                            dayTime = rs.getString("time_meet");
                            splitString = dayTime.split("\\s",2);
                        
                            jTable1.setValueAt("<html>"+resultString, mapTime.get(splitString[1]), mapDay.get(splitString[0]));
                        }
                        else{
                    
                            resultString = "";
                            tempTeacher = rs.getString("teacher");
                            x = tempTeacher.split(", ");
                        
                            subj = rs.getString("subj");
                            z = subj.split(", ");
                        
                            room = rs.getString("room");
                            y = room.split(", ");
                        
                            batch = rs.getString("btch");
                            w = batch.split(", ");
                        
                            for(int k = 0; k < w.length ; k++){
                            
                                p = w[k].split(" ");
                                t = z[k].split(" ");
                            
                                if( k == w.length - 1)
                                    resultString = resultString + mapBatch.get(p[2]) + " : " + t[0] + " : " + y[k] + " : " + x[k];
                                else
                                    resultString = resultString + mapBatch.get(p[2]) + " : " + t[0] + " : " + y[k] + " : " + x[k] + "<br>";
                            }
                            dayTime = rs.getString("time_meet");
                            splitString = dayTime.split("\\s",2);
                            jTable1.setValueAt("<html>"+resultString, mapTime.get(splitString[1]) - 1 , mapDay.get(splitString[0]));
                            jTable1.setValueAt("<html>"+resultString, mapTime.get(splitString[1]), mapDay.get(splitString[0]));
                        }
      
                    }
                    
                    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                    centerRenderer.setVerticalAlignment(JLabel.TOP);
                    for(int counter = 0; counter < 6; counter++){
                        jTable1.getColumnModel().getColumn(counter).setCellRenderer(centerRenderer);
                    }
                    
                    jTable1.setRowHeight(150);//setting row width
                    jTable1.getColumnModel().getColumn(0).setPreferredWidth(100);//column width setting
                    jTable1.getColumnModel().getColumn(1).setPreferredWidth(100);
                    jTable1.getColumnModel().getColumn(2).setPreferredWidth(100);
                    jTable1.getColumnModel().getColumn(3).setPreferredWidth(100);
                    jTable1.getColumnModel().getColumn(4).setPreferredWidth(100);
                    jTable1.getColumnModel().getColumn(5).setPreferredWidth(100);
                    jScrollPane1.setVisible(true);
                }
                else{
                    JOptionPane.showMessageDialog(null,"Time-Table Doesn't Exists","ERROR",JOptionPane.ERROR_MESSAGE);
                }
           
        }
         catch(Exception e){ 
             
             System.out.println(e);
         }
    }

    public studenthome() {
        initComponents();
        conn = JConnection.ConnecrDb();
        nonactivate();
        fillCollege();
        JTableHeader Theader = jTable1.getTableHeader();
        Theader.setOpaque(false);
        Theader.setBackground(new Color(153,0,0));
        Theader.setForeground(Color.WHITE);
        Theader.setFont(new Font("Segoe UI",Font.BOLD,12));
        ((DefaultTableCellRenderer)Theader.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
    }
    
    public void nonactivate()
    {        
        jLabel22.setVisible(false);
        jLabel24.setVisible(false);
        jLabel26.setVisible(false);
        jLabel28.setVisible(false);  
        jLabel5.setVisible(false);
        jScrollPane1.setVisible(false);
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

        jPanel3 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel28 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox<>();

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setMinimumSize(new java.awt.Dimension(100, 100));
        jPanel3.setPreferredSize(new java.awt.Dimension(988, 551));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel13.setBackground(new java.awt.Color(97, 212, 195));
        jPanel13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel13.setMaximumSize(new java.awt.Dimension(180, 40));
        jPanel13.setMinimumSize(new java.awt.Dimension(180, 40));
        jPanel13.setPreferredSize(new java.awt.Dimension(190, 40));
        jPanel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel13MouseClicked(evt);
            }
        });
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setBackground(new java.awt.Color(97, 212, 195));
        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("View Timetable");
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel6MouseExited(evt);
            }
        });
        jPanel13.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 40));

        jPanel3.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 410, -1, -1));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        jTable1.setSelectionBackground(new java.awt.Color(36, 47, 65));
        jTable1.setShowGrid(true);
        jScrollPane1.setViewportView(jTable1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 121, 700, 410));

        jLabel1.setFont(new java.awt.Font("Corbel", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 153));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 0, 430, 30));

        jLabel2.setFont(new java.awt.Font("Corbel", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 0, 51));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 30, 430, 30));

        jPanel4.setBackground(new java.awt.Color(232, 188, 11));

        jLabel4.setBackground(new java.awt.Color(153, 153, 153));
        jLabel4.setFont(new java.awt.Font("Corbel", 1, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("TIME - TABLE");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(137, 137, 137)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(143, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 710, -1));

        jLabel5.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        jLabel5.setText("jLabel5");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 340, 30));

        jPanel3.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 0, 710, 540));

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
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel9MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel9MouseExited(evt);
            }
        });
        jPanel17.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 40));

        jPanel3.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 470, -1, -1));

        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });
        jPanel3.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, 220, 40));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setText("Select College:");
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, -1, -1));

        jLabel22.setForeground(new java.awt.Color(255, 0, 0));
        jLabel22.setText("Please select college");
        jPanel3.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, -1, 20));

        jLabel23.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel23.setText("Select Department");
        jPanel3.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, -1, -1));

        jLabel24.setForeground(new java.awt.Color(255, 0, 0));
        jLabel24.setText("Please select department");
        jPanel3.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 170, -1, 20));

        jLabel25.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel25.setText("Select Year:");
        jPanel3.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 200, -1, -1));

        jComboBox3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox3ItemStateChanged(evt);
            }
        });
        jPanel3.add(jComboBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 230, 220, 40));

        jLabel26.setForeground(new java.awt.Color(255, 0, 0));
        jLabel26.setText("Please select year");
        jPanel3.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 270, -1, 20));

        jLabel27.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel27.setText("Select Division:");
        jPanel3.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 300, -1, -1));

        jPanel3.add(jComboBox4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 330, 220, 40));

        jLabel28.setForeground(new java.awt.Color(255, 0, 0));
        jLabel28.setText("Please select division");
        jPanel3.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 370, -1, 20));

        jComboBox5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox5ItemStateChanged(evt);
            }
        });
        jPanel3.add(jComboBox5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 220, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 541, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    int height = 0;
    private void jPanel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel13MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel13MouseClicked

    private void jPanel17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel17MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel17MouseClicked

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        // TODO add your handling code here:
        nonactivate();
        if(jComboBox5.getSelectedIndex() == -1)
        {
            jLabel22.setVisible(true);
        }
        if(jComboBox1.getSelectedIndex() == -1)
        {
            jLabel24.setVisible(true);
        }
        if(jComboBox3.getSelectedIndex() == -1)
        {
            jLabel26.setVisible(true);
        }
        if(jComboBox4.getSelectedIndex() == -1)
        {
            jLabel28.setVisible(true);
        }
        else
        {
            String year = "";
            jLabel1.setText((String)jComboBox5.getSelectedItem());
            jLabel2.setText("Department of "+(String)jComboBox1.getSelectedItem());
            if(jComboBox3.getSelectedItem().equals("First Year"))
                year = "FE";
            else if(jComboBox3.getSelectedItem().equals("Second Year"))
                year = "SE";
            else if(jComboBox3.getSelectedItem().equals("Third Year"))
                year = "TE";
            else if(jComboBox3.getSelectedItem().equals("Final Year"))
                year = "BE";

            jLabel5.setText( "Class : " + year + " " + jComboBox4.getSelectedItem());
            jLabel5.setVisible(true);
            
            String selectedCollege = (String)jComboBox5.getSelectedItem();
            String selectedDept = (String)jComboBox1.getSelectedItem();
            String selectedYear = "";
            
            if(jComboBox3.getSelectedItem().equals("First Year"))
                selectedYear = "FE";
            else if(jComboBox3.getSelectedItem().equals("Second Year"))
                selectedYear = "SE";
            else if(jComboBox3.getSelectedItem().equals("Third Year"))
                selectedYear = "TE";
            else if(jComboBox3.getSelectedItem().equals("Final Year"))
                selectedYear = "BE";
            
            String selectedDiv = (String)jComboBox4.getSelectedItem();
            
            setTimetable(selectedCollege, selectedDept, selectedYear, selectedDiv);
            
        }
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseEntered
        // TODO add your handling code here:
        setColor(jPanel13);
    }//GEN-LAST:event_jLabel6MouseEntered

    private void jLabel6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseExited
        // TODO add your handling code here:
        resetColor(jPanel13);
    }//GEN-LAST:event_jLabel6MouseExited

    private void jLabel9MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseEntered
        // TODO add your handling code here:
        setColor(jPanel17);
    }//GEN-LAST:event_jLabel9MouseEntered

    private void jLabel9MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseExited
        // TODO add your handling code here:
        resetColor(jPanel17);
    }//GEN-LAST:event_jLabel9MouseExited

    private void jComboBox5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox5ItemStateChanged
        // TODO add your handling code here:
        jComboBox1.removeAllItems();
        
        String SelectedCollege = (String)jComboBox5.getSelectedItem();
        searchCollegeId(SelectedCollege);
        fillDepartment();
    }//GEN-LAST:event_jComboBox5ItemStateChanged

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        // TODO add your handling code here:
        jComboBox3.removeAllItems();
        if(jComboBox5.getSelectedIndex() == -1 || jComboBox1.getSelectedIndex() == -1 ){
            
        }
        else{
            String SelectedDepartment = (String)jComboBox1.getSelectedItem();
            searchDeptId(SelectedDepartment);
            fillYear();
        }
        
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jComboBox3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox3ItemStateChanged
        // TODO add your handling code here:
        jComboBox4.removeAllItems();
        if(jComboBox5.getSelectedIndex() == -1 || jComboBox1.getSelectedIndex() == -1 || jComboBox3.getSelectedIndex() == -1){
            
        }
        else{
            String SelectedYear = (String)jComboBox3.getSelectedItem();
        if(SelectedYear.equals("First Year"))
            SelectedYear = "FE";
        else if(SelectedYear.equals("Second Year"))
            SelectedYear = "SE";
        else if(SelectedYear.equals("Third Year"))
            SelectedYear = "TE";
        else
            SelectedYear = "BE";
        fillDivision(SelectedYear);
        }
        
    }//GEN-LAST:event_jComboBox3ItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}

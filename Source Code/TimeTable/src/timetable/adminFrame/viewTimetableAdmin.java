/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timetable.adminFrame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
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
 * @author Pritam Bera
 */
public class viewTimetableAdmin extends javax.swing.JFrame {

    /**
     * Creates new form viewTimetableAdmin
     */
    Connection conn = null;
    String teacherName,position;
    int college_id,dept_id,pracLoad = 0,theoryLoad = 0;
    
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
    
    public void fillClassroom(){
        try{
            
            PreparedStatement pst = null;
            pst = conn.prepareStatement("select name from infrastructure where type = 'Classroom' and college_id = ? and dept_id = ?");
            pst.setInt(1, college_id);
            pst.setInt(2, dept_id);
            ResultSet rs = pst.executeQuery();
            
            while(rs.next())
            {
                jComboBox2.addItem(rs.getString("name"));
            }
     
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    public void fillTeachers(){
        try{
            
            PreparedStatement pst = null;
            pst = conn.prepareStatement("select name from teachers where college_id = ? and dept_id = ?");
            pst.setInt(1, college_id);
            pst.setInt(2, dept_id);
            ResultSet rs = pst.executeQuery();
            
            while(rs.next())
            {
                jComboBox2.addItem(rs.getString("name"));
            }
     
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    public void fillStudyYears(){
        try{
            
            PreparedStatement pst = null;
            pst = conn.prepareStatement("select class,no_of_divisions from study_years where college_id = ? and dept_id = ?");
            pst.setInt(1, college_id);
            pst.setInt(2, dept_id);
            ResultSet rs = pst.executeQuery();
            int i;
            while(rs.next())
            {
                int nod = rs.getInt("no_of_divisions");
                for(i = 1; i <= nod; i++){
                    jComboBox2.addItem(rs.getString("class")+" "+i);
                }
                
            }
     
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    public void fillLabs(){
        try{
            
            PreparedStatement pst = null;
            pst = conn.prepareStatement("select name from infrastructure where type = 'Practical Lab' and college_id = ? and dept_id = ?");
            pst.setInt(1, college_id);
            pst.setInt(2, dept_id);
            ResultSet rs = pst.executeQuery();
            
            while(rs.next())
            {
                jComboBox2.addItem(rs.getString("name"));
            }
     
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    public void setCollegeAndDeptName(){
        
        try{
                PreparedStatement pst = null;
                pst = conn.prepareStatement("select college_name from colleges where college_id = ?");
                pst.setInt(1, college_id);
                ResultSet rs = pst.executeQuery();
                
                if(rs.next()){
                    jLabel1.setText(rs.getString("college_name").toUpperCase());
                }
                
                PreparedStatement pst3 = null;
                pst3 = conn.prepareStatement("select dept_name from departments where college_id = ? and dept_id = ?");
                pst3.setInt(1, college_id);
                pst3.setInt(2, dept_id);
                ResultSet rs3 = pst3.executeQuery();
                
                if(rs3.next()){
                    jLabel2.setText("DEPARTMENT OF "+rs3.getString("dept_name").toUpperCase());
                }
            }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    public void retrieveDesign(String selectedTeacher){
        try{
                PreparedStatement pst = null;
                pst = conn.prepareStatement("select position from teachers where name = ? and college_id = ? and dept_id = ?");
                pst.setString(1, selectedTeacher);
                pst.setInt(2, college_id);
                pst.setInt(3, dept_id);
                ResultSet rs = pst.executeQuery();
                
                if(rs.next()){
                    position = rs.getString("position");
                }
            }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    public void setLabels(String selectedFilter,String selectedItem){
        
        if(selectedFilter.equals("Classroom")){
            jLabel5.setText("Classroom: "+selectedItem);
            jLabel6.setText("");
        }
        else if(selectedFilter.equals("Individual")){
            jLabel5.setText("Name of Staff: "+selectedItem);
            retrieveDesign(selectedItem);
            jLabel6.setText("Designation: "+position);
        }
        else if(selectedFilter.equals("Labwise")){
            jLabel5.setText("Laboratory: "+selectedItem);
            jLabel6.setText("");
        }
        else{
            jLabel5.setText("Class: "+selectedItem);
            jLabel6.setText("");
        }
    }
    
    public void setDownLabels(String selectedFilter,String selectedItem){
        
        int i,j,count=0,total, rowCount = jTable1.getRowCount();
        
        DecimalFormat df = new DecimalFormat("0.00");
        for(i = 0;i<rowCount;i++){
            for(j=1; j<6 ; j++){
                if(jTable1.getValueAt(i,j).equals(null) || jTable1.getValueAt(i,j).equals("")){
                    count++;
                }
            } 
        }
        count = count - 10;
        total = (rowCount * 5)-10;
        if(selectedFilter.equals("Classroom")){
            jLabel8.setText("Total Theory Load: "+(total - count)+" hours");
            jLabel7.setText("Classroom Utilization: "+ df.format((((float)(total - count)/total)*100)) + " %");
            jLabel10.setText("");
        }
        else if(selectedFilter.equals("Labwise")){
            jLabel8.setText("Total Practical Load: "+(total - count)+" hours");
            jLabel7.setText("Laboratory Utilization: "+ df.format((((float)(total - count)/total)*100)) + " %");
            jLabel10.setText("");
        }
        else if(selectedFilter.equals("Individual")){
            jLabel8.setText("Total Theory Load: "+theoryLoad+" hours");
            jLabel10.setText("Total Practical Load: "+pracLoad+" hours");
            jLabel7.setText("Total Load: "+(theoryLoad + pracLoad)+" hours");
        }
        else{
            jLabel8.setText("Total Theory Load: "+theoryLoad+" hours");
            jLabel10.setText("Total Practical Load: "+pracLoad+" hours");
            jLabel7.setText("Total Load: "+(theoryLoad + pracLoad)+" hours");
        }
        
    }
    
    public void setTimetable(String selectedFilter,String selectedItem){
        
        //System.out.println("ksbjksbkvbskb");
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
             if(selectedFilter.equals("Classroom")){
                 
                PreparedStatement stmt = conn.prepareStatement("select year_study, division, subj, teacher, time_meet from table_time where room = ? and college_id = ? and dept_id = ?"); 
                
                stmt.setString(1, selectedItem);
                stmt.setInt(2, college_id);
                stmt.setInt(3, dept_id);
                ResultSet rs=stmt.executeQuery();
            
            
                while(rs.next()) {
                    
                    tempTeacher = rs.getString("teacher");
                    year = rs.getString("year_study");        
                    division = rs.getString("division");
                
                    subj = rs.getString("subj");
                    z = subj.split(" ");

                    resultString = year + " - " + mapDivision.get(division) + " : " + z[0] + " : " + tempTeacher;
                    
                    dayTime = rs.getString("time_meet");
                    splitString = dayTime.split("\\s",2);
                    jTable1.setValueAt("<html>"+resultString, mapTime.get(splitString[1]), mapDay.get(splitString[0]));
                    
                    
                }
                //System.out.println("helllloooooo");
                setDownLabels(selectedFilter,selectedItem);
                
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(JLabel.CENTER);
                for(int counter = 0; counter < 6; counter++){
                    jTable1.getColumnModel().getColumn(counter).setCellRenderer(centerRenderer);
                }
                
                jTable1.setRowHeight(50);//setting row width
                jTable1.getColumnModel().getColumn(0).setPreferredWidth(100);//column width setting
                jTable1.getColumnModel().getColumn(1).setPreferredWidth(100);
                jTable1.getColumnModel().getColumn(2).setPreferredWidth(100);
                jTable1.getColumnModel().getColumn(3).setPreferredWidth(100);
                jTable1.getColumnModel().getColumn(4).setPreferredWidth(100);
                jTable1.getColumnModel().getColumn(5).setPreferredWidth(100);
            }
            else if(selectedFilter.equals("Individual")){
                 
                pracLoad = 0;
                theoryLoad = 0;
                
                PreparedStatement stmt = conn.prepareStatement("select year_study,btch, division, subj, room, teacher, time_meet from table_time where teacher LIKE ? and college_id = ? and dept_id = ?"); 
                String temp = "%" + selectedItem + "%";
                stmt.setString(1, temp);
                stmt.setInt(2, college_id);
                stmt.setInt(3, dept_id);
                ResultSet rs=stmt.executeQuery();
            
                //System.out.println("helllloooooooooo");
            
                while(rs.next()) {
                    index = 0;
                    tempTeacher = rs.getString("teacher");
                    x = tempTeacher.split(", ");
                    System.out.println(x.length);
                    
                    for(index = 0; index < 4; index++) {
                        if(x[index].equals(selectedItem))
                            break;
                    }
                    
                    System.out.println("Index :"+index);
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
                        jTable1.setValueAt("<html>"+resultString, mapTime.get(splitString[1]) - 1 , mapDay.get(splitString[0]));
                        jTable1.setValueAt("<html>"+resultString, mapTime.get(splitString[1]), mapDay.get(splitString[0]));
                        pracLoad +=2; 
                    }
                    else{
                    
                        resultString = year + " - " + mapDivision.get(division) + " : " + t[0] + " : " + y[index];
                        dayTime = rs.getString("time_meet");
                        splitString = dayTime.split("\\s",2);
                        jTable1.setValueAt("<html>"+resultString, mapTime.get(splitString[1]), mapDay.get(splitString[0]));
                        theoryLoad++;
                    }
                }
                setDownLabels(selectedFilter,selectedItem);
                
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(JLabel.CENTER);
                for(int counter = 0; counter < 6; counter++){
                    jTable1.getColumnModel().getColumn(counter).setCellRenderer(centerRenderer);
                }
                
                jTable1.setRowHeight(50);//setting row width
                jTable1.getColumnModel().getColumn(0).setPreferredWidth(100);//column width setting
                jTable1.getColumnModel().getColumn(1).setPreferredWidth(100);
                jTable1.getColumnModel().getColumn(2).setPreferredWidth(100);
                jTable1.getColumnModel().getColumn(3).setPreferredWidth(100);
                jTable1.getColumnModel().getColumn(4).setPreferredWidth(100);
                jTable1.getColumnModel().getColumn(5).setPreferredWidth(100);
            }
            else if(selectedFilter.equals("Classwise")){
                 
                pracLoad = 0;
                theoryLoad = 0;
                String selectedClass[] = null;
                
                if(selectedItem != null){
                    
                    selectedClass = selectedItem.split(" ");
                
                    PreparedStatement stmt = conn.prepareStatement("select btch, subj, room, teacher, time_meet from table_time where year_study = ? and division = ? and college_id = ? and dept_id = ?"); 
                
                    stmt.setString(1, selectedClass[0]);
                    stmt.setInt(2, Integer.parseInt(selectedClass[1]));
                    stmt.setInt(3, college_id);
                    stmt.setInt(4, dept_id);
                    ResultSet rs=stmt.executeQuery();
            
                    //System.out.println("helllloooooooooo");
            
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
                            theoryLoad++;
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
                            pracLoad +=2; 
                        }
      
                    }
                    setDownLabels(selectedFilter,selectedItem);
                    
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
                }
                
            }
            else{
                
                PreparedStatement stmt = conn.prepareStatement("select year_study,btch, division, room, subj, teacher, time_meet from table_time where room LIKE ? and college_id = ? and dept_id = ?"); 
                String temp = "%" + selectedItem + "%";
                stmt.setString(1, temp);
                stmt.setInt(2, college_id);
                stmt.setInt(3, dept_id);
                ResultSet rs=stmt.executeQuery();
            
            
                while(rs.next()) {
                    
                    index = 0;
                    tempRoom = rs.getString("room");
                    x = tempRoom.split(", ");
                    System.out.println(x.length);
                    
                    for(index = 0; index < 4; index++) {
                        if(x[index].equals(selectedItem))
                            break;
                    }
                    
                    //System.out.println("Index :"+index);
                    year = rs.getString("year_study");        
                    division = rs.getString("division");
                
                    subj = rs.getString("subj");
                    z = subj.split(", ");
                    t = z[index].split(" ");
                
                    teacher = rs.getString("teacher");
                    y = teacher.split(", ");
                    
                    if(x.length == 4) {
                        batch = rs.getString("btch");
                        w = batch.split(", ");
                        p = w[index].split(" ");
                    
                        resultString = year + " - " + mapDivision.get(division) + " : " + mapBatch.get(p[2]) + " : " + t[0] + " : " + y[index];
                    
                        dayTime = rs.getString("time_meet");
                        splitString = dayTime.split("\\s",2);
                        jTable1.setValueAt("<html>"+resultString, mapTime.get(splitString[1]) - 1 , mapDay.get(splitString[0]));
                        jTable1.setValueAt("<html>"+resultString, mapTime.get(splitString[1]), mapDay.get(splitString[0]));
                    }
                    else{
                    
                        resultString = year + " - " + mapDivision.get(division) + " : " + t[0] + " : " + y[index];
                        dayTime = rs.getString("time_meet");
                        splitString = dayTime.split("\\s",2);
                        jTable1.setValueAt("<html>"+resultString, mapTime.get(splitString[1]), mapDay.get(splitString[0]));
                    }
                }
                setDownLabels(selectedFilter,selectedItem);
                
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(JLabel.CENTER);
                for(int counter = 0; counter < 6; counter++){
                    jTable1.getColumnModel().getColumn(counter).setCellRenderer(centerRenderer);
                }
                
                jTable1.setRowHeight(50);//setting row width
                jTable1.getColumnModel().getColumn(0).setPreferredWidth(100);//column width setting
                jTable1.getColumnModel().getColumn(1).setPreferredWidth(100);
                jTable1.getColumnModel().getColumn(2).setPreferredWidth(100);
                jTable1.getColumnModel().getColumn(3).setPreferredWidth(100);
                jTable1.getColumnModel().getColumn(4).setPreferredWidth(100);
                jTable1.getColumnModel().getColumn(5).setPreferredWidth(100);
            }
            
        }
         catch(Exception e){ System.out.println(e);}
    }
    
    public void setColor(JPanel p) {
        p.setBackground(new Color(36,47,65));
        p.setBorder(BorderFactory.createLineBorder(new Color(97,212,195), 2));
  
    }
    
    public void resetColor(JPanel p1) {
        p1.setBackground(new Color(97,212,195));   
    }
    
    public viewTimetableAdmin() {
        initComponents();
    }
    
    public viewTimetableAdmin(String name) {
        initComponents();
        conn = JConnection.ConnecrDb();
        setIconImage();
        teacherName=name;
        searchCollege(name);
        fillClassroom();
        setCollegeAndDeptName();
        
        JTableHeader Theader = jTable1.getTableHeader();
        Theader.setOpaque(false);
        Theader.setBackground(new Color(153,0,0));
        Theader.setForeground(Color.WHITE);
        Theader.setFont(new Font("Segoe UI",Font.BOLD,12));
        ((DefaultTableCellRenderer)Theader.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        String selectedItem = (String)jComboBox2.getSelectedItem();
        //setTimetable("Classroom",selectedItem);
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
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("AppName");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(36, 47, 65));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("VIEW TIME-TABLE");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 10, -1, 40));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 870, 60));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 149, 800, 391));

        jLabel1.setFont(new java.awt.Font("Corbel", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 153));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, 430, 30));

        jLabel2.setFont(new java.awt.Font("Corbel", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 0, 51));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 40, 430, 30));

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

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, -1, -1));

        jLabel5.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        jLabel5.setText("jLabel5");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 340, 20));

        jLabel6.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("jLabel6");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 120, 320, 20));

        jPanel5.setBackground(new java.awt.Color(153, 0, 0));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("jLabel7");
        jPanel5.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 0, 290, 30));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("jLabel8");
        jPanel5.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 290, 30));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("jLabel10");
        jPanel5.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 0, 180, 30));

        jPanel3.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 540, 800, 30));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 850, 580));

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

        jPanel1.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 720, 140, 40));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Classroom", "Individual", "Classwise", "Labwise" }));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 90, 250, 30));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel18.setText("View By");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 60, 250, 30));

        jComboBox2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox2ItemStateChanged(evt);
            }
        });
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 90, 250, 30));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel19.setText("Select Classroom");
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 60, 250, 30));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 870, 770));

        setSize(new java.awt.Dimension(883, 805));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel9MouseClicked

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

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jComboBox2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox2ItemStateChanged
        // TODO add your handling code here: 
    }//GEN-LAST:event_jComboBox2ItemStateChanged

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
        String selectedFilter = (String)jComboBox1.getSelectedItem();
        String selectedItem = (String)jComboBox2.getSelectedItem();
        setLabels(selectedFilter,selectedItem);
        setTimetable(selectedFilter,selectedItem);
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        jComboBox2.removeAllItems();
        
        String selectedFilter = (String)jComboBox1.getSelectedItem();
        if(selectedFilter.equals("Classroom")){
            jLabel19.setText("Select Classroom");
            fillClassroom();
        }
        else if(selectedFilter.equals("Individual")){
            jLabel19.setText("Select Teacher");
            fillTeachers();
        }
        else if(selectedFilter.equals("Classwise")){
            jLabel19.setText("Select Class");
            fillStudyYears();    
        }
        else{
            jLabel19.setText("Select Lab");
            fillLabs();
        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

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
            java.util.logging.Logger.getLogger(viewTimetableAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(viewTimetableAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(viewTimetableAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(viewTimetableAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new viewTimetableAdmin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}

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
public class addTeacherLabAllocation extends javax.swing.JFrame {

    /**
     * Creates new form addTeacherLabAllocation
     */
    String teacherName,college,year,type,subject,labs,teachers;
    int college_id,dept_id,nod,nob,year_id,division,batch,load;
    int flag = 0;
    
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
    
    public void setColor(JPanel p) {
        p.setBackground(new Color(36,47,65));
        p.setBorder(BorderFactory.createLineBorder(new Color(97,212,195), 2));
  
    }
    
    public void resetColor(JPanel p1) {
        p1.setBackground(new Color(97,212,195));   
    }
    
    public ArrayList<allocateTeacher> teacherList(){
        ArrayList<allocateTeacher> teacherList = new ArrayList<>();
        
        try{
            
            PreparedStatement pst = null;
            pst = conn.prepareStatement("select name from teachers where college_id = ? and dept_id = ?");
            pst.setInt(1, college_id);
            pst.setInt(2, dept_id);
           
            ResultSet rs = pst.executeQuery();
            allocateTeacher teacherReq;
            if(rs.next())
            {
                rs.previous();
                while(rs.next()){
                    teacherReq = new allocateTeacher(rs.getString("name"));
                    teacherList.add(teacherReq);
                }

            }
            else
            {
                JOptionPane.showMessageDialog(null,"No Entries Found in the table","MESSAGE",JOptionPane.INFORMATION_MESSAGE);
                
            }
            
            System.out.println(teacherList);
            
            
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
            
        return teacherList;
    }
    
    public void fillTeachers(){
        ArrayList<allocateTeacher> List = teacherList();
        DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
        /*int rows = model.getRowCount();
        for(int i = rows-1 ; i >=0 ; i--){
            model.removeRow(i);
        }*/
        Object column[] = new Object[1]; 
        for(int i = 0; i<List.size(); i++){
            column[0] = List.get(i).getTeachers();
            model.addRow(column);
        }
    }
    
    public ArrayList<allocateLabs> labList(){
        ArrayList<allocateLabs> labList = new ArrayList<>();
        
        try{
            
            PreparedStatement pst = null;
            pst = conn.prepareStatement("select name from infrastructure where college_id = ? and dept_id = ? and type = 'Practical Lab'");
            pst.setInt(1, college_id);
            pst.setInt(2, dept_id);
           
            ResultSet rs = pst.executeQuery();
            allocateLabs labReq;
            if(rs.next())
            {
                rs.previous();
                while(rs.next()){
                    labReq = new allocateLabs(rs.getString("name"));
                    labList.add(labReq);
                }

            }
            else
            {
                JOptionPane.showMessageDialog(null,"No Entries Found in the table","MESSAGE",JOptionPane.INFORMATION_MESSAGE);
                
            }
            
            System.out.println(labList);
            
            
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
            
        return labList;
    }
    
    public void fillLabs(){
        ArrayList<allocateLabs> List = labList();
        DefaultTableModel model = (DefaultTableModel)jTable2.getModel();
        int rows = model.getRowCount();
        for(int i = rows-1 ; i >=0 ; i--){
            model.removeRow(i);
        }
        Object column[] = new Object[1]; 
        for(int i = 0; i<List.size(); i++){
            column[0] = List.get(i).getLabs();
            model.addRow(column);
        }
    }
    
    private void fillYear()
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
               
                if(studyYear.equals("FE"))
                    jComboBox3.addItem("First Year");
                else if(studyYear.equals("SE"))
                    jComboBox3.addItem("Second Year");
                else if(studyYear.equals("TE"))
                    jComboBox3.addItem("Third Year");
                else if(studyYear.equals("BE"))
                    jComboBox3.addItem("Final Year");
                   
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
        
    }
    
    private void fillDivisionAndBatch(String selectedItem)
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
            ResultSet rs = null;
            pst = conn.prepareStatement("select no_of_divisions,no_of_batches_in_each_division from study_years where college_id=? and dept_id=? and class = ?");
            pst.setInt(1,college_id);
            pst.setInt(2,dept_id);
            pst.setString(3, selectedItem);
            rs = pst.executeQuery();
            
            while(rs.next())
            {
                
                nod = rs.getInt("no_of_divisions");
                nob = rs.getInt("no_of_batches_in_each_division");
                for(int i = 1;i<=nod;i++){
                    jComboBox6.addItem(""+i+"");
                } 
                for(int j = 0;j<=nob;j++){
                    jComboBox7.addItem(""+j+"");
                }
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
        
    }
    
    private void fillSubjects(String selectedItem,int selectedDiv,int selectedBatch,String selectedType)
    {
        jComboBox5.removeAllItems();
        if(selectedItem.equals("First Year"))
            selectedItem = "FE";
        else if(selectedItem.equals("Second Year"))
            selectedItem = "SE";
        else if(selectedItem.equals("Third Year"))
            selectedItem = "TE";
        else if(selectedItem.equals("Final Year"))
            selectedItem = "BE";
        try{
            //System.out.println(selectedItem+" "+selectedDiv+" "+selectedBatch+" "+selectedType);
            
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
            
            pst = conn.prepareStatement("select subject from teacher_allocation where college_id=? and dept_id=? and division = ? and batch = ? and type = ?  and study_year_id = ? ");
            pst.setInt(1,college_id);
            pst.setInt(2,dept_id);
            pst.setInt(3,selectedDiv);
            pst.setInt(4,selectedBatch);
            pst.setString(5,selectedType);
            pst.setInt(6, year_id);
            rs = pst.executeQuery();
            
            while(rs.next())
            {
                
                String subject=rs.getString("subject");
               
                jComboBox5.addItem(subject);
                   
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
        
    }
    
    public void allocateTheoryTeacher(){
        try{
            int rowSelected = -1;
           
            PreparedStatement pst = null;
            ResultSet rs = null;
            
            year = (String)jComboBox3.getSelectedItem();
            if(year.equals("First Year"))
                year = "FE";
            else if(year.equals("Second Year"))
                year = "SE";
            else if(year.equals("Third Year"))
                year = "TE";
            else if(year.equals("Final Year"))
                year = "BE";
            
            pst = conn.prepareStatement("select study_year_id from study_years where college_id = ? and dept_id = ? and class = ?");
            pst.setInt(1, college_id);
            pst.setInt(2, dept_id);
            pst.setString(3, year);
            
            rs = pst.executeQuery();
            if(rs.next()){
                year_id = rs.getInt("study_year_id");
            }
            
            division = Integer.parseInt((String)jComboBox6.getSelectedItem());
            batch = Integer.parseInt((String)jComboBox7.getSelectedItem());
            type = (String)jComboBox4.getSelectedItem();
            subject = (String)jComboBox5.getSelectedItem();
            
            
            DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
            for(int i = 0; i < model.getRowCount(); i++){
                boolean allocated = true;
                if(model.getValueAt(i, 1) == null || ((boolean)model.getValueAt(i, 1)) == false)
                    allocated = false;
                if(allocated)
                {
                    rowSelected = i; 
                    break;
                }          
            }
            teachers = (String)model.getValueAt(rowSelected, 0);
            

            pst = conn.prepareStatement("update teacher_allocation set allocated_teachers = ? where college_id = ? and "
                    + "dept_id = ? and study_year_id = ? and division = ? and batch = ? and type = ? and subject = ?");
            
            pst.setString(1, teachers);
            pst.setInt(2, college_id);
            pst.setInt(3, dept_id);
            pst.setInt(4, year_id);
            pst.setInt(5, division);
            pst.setInt(6, batch);
            pst.setString(7,type);
            pst.setString(8, subject);
            
            pst.execute();
            JOptionPane.showMessageDialog(null,"Information Added","MESSAGE",JOptionPane.INFORMATION_MESSAGE);

        }catch(Exception e){
            
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    public void checkLoad(){
        try{
            int rowSelected = -1;
           
            PreparedStatement pst = null;
            ResultSet rs = null;
            
            year = (String)jComboBox3.getSelectedItem();
            if(year.equals("First Year"))
                year = "FE";
            else if(year.equals("Second Year"))
                year = "SE";
            else if(year.equals("Third Year"))
                year = "TE";
            else if(year.equals("Final Year"))
                year = "BE";
            
            pst = conn.prepareStatement("select study_year_id from study_years where college_id = ? and dept_id = ? and class = ?");
            pst.setInt(1, college_id);
            pst.setInt(2, dept_id);
            pst.setString(3, year);
            
            rs = pst.executeQuery();
            if(rs.next()){
                year_id = rs.getInt("study_year_id");
            }
            
            division = Integer.parseInt((String)jComboBox6.getSelectedItem());
            batch = Integer.parseInt((String)jComboBox7.getSelectedItem());
            type = (String)jComboBox4.getSelectedItem();
            subject = (String)jComboBox5.getSelectedItem();

            pst = conn.prepareStatement("select sub_load from teacher_allocation where college_id = ? and "
                    + "dept_id = ? and study_year_id = ? and division = ? and batch = ? and type = ? and subject = ?");
            
            
            pst.setInt(1, college_id);
            pst.setInt(2, dept_id);
            pst.setInt(3, year_id);
            pst.setInt(4, division);
            pst.setInt(5, batch);
            pst.setString(6,type);
            pst.setString(7, subject);
            
            System.out.println(college_id+" "+dept_id+" "+year_id+" "+division+" "+batch+" "+type+" "+subject);
            rs = pst.executeQuery();
            if(rs.next()){
                //System.out.println("dhvbsbdkvsdbkvbsdkbvksdbvkb");
                load = rs.getInt("sub_load");
            }
            System.out.println(load+"in");

        }catch(Exception e){
            
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    public void allocatePractical(){
        try{
            ArrayList<Integer> rowSelected = new ArrayList<>();
           
            PreparedStatement pst = null;
            ResultSet rs = null;
            teachers = "";
            labs = "";
            
            year = (String)jComboBox3.getSelectedItem();
            if(year.equals("First Year"))
                year = "FE";
            else if(year.equals("Second Year"))
                year = "SE";
            else if(year.equals("Third Year"))
                year = "TE";
            else if(year.equals("Final Year"))
                year = "BE";
            
            pst = conn.prepareStatement("select study_year_id from study_years where college_id = ? and dept_id = ? and class = ?");
            pst.setInt(1, college_id);
            pst.setInt(2, dept_id);
            pst.setString(3, year);
            
            rs = pst.executeQuery();
            if(rs.next()){
                year_id = rs.getInt("study_year_id");
            }
            
            division = Integer.parseInt((String)jComboBox6.getSelectedItem());
            batch = Integer.parseInt((String)jComboBox7.getSelectedItem());
            type = (String)jComboBox4.getSelectedItem();
            subject = (String)jComboBox5.getSelectedItem();
            
            
            DefaultTableModel model1 = (DefaultTableModel)jTable1.getModel();
            for(int i = 0; i < model1.getRowCount(); i++){
                boolean allocated1 = true;
                if(model1.getValueAt(i, 1) == null || ((boolean)model1.getValueAt(i, 1)) == false)
                    allocated1 = false;
                if(allocated1)
                {
                    rowSelected.add(i); 
                    
                }          
            }
            
            for(int i = 0; i<rowSelected.size(); i++){
                if(i == (rowSelected.size()-1))
                    teachers = teachers+(String)model1.getValueAt(rowSelected.get(i), 0);
                else
                    teachers = teachers+(String)model1.getValueAt(rowSelected.get(i), 0)+",";
            }
            
            rowSelected.clear();
            DefaultTableModel model2 = (DefaultTableModel)jTable2.getModel();
            for(int i = 0; i < model2.getRowCount(); i++){
                boolean allocated2 = true;
                if(model2.getValueAt(i, 1) == null)
                    allocated2 = false;
                if(allocated2)
                {
                    rowSelected.add(i);
                }          
            }
            
            for(int i = 0; i<rowSelected.size(); i++){
                if(i == (rowSelected.size()-1))
                    labs = labs+(String)model2.getValueAt(rowSelected.get(i), 0);
                else
                    labs = labs+(String)model2.getValueAt(rowSelected.get(i), 0)+",";
            }
            

            pst = conn.prepareStatement("update teacher_allocation set allocated_teachers = ?,room = ? where college_id = ? and "
                    + "dept_id = ? and study_year_id = ? and division = ? and batch = ? and type = ? and subject = ?");
            
            pst.setString(1, teachers);
            pst.setString(2, labs);
            pst.setInt(3, college_id);
            pst.setInt(4, dept_id);
            pst.setInt(5, year_id);
            pst.setInt(6, division);
            pst.setInt(7, batch);
            pst.setString(8,type);
            pst.setString(9, subject);
            
            pst.execute();
            JOptionPane.showMessageDialog(null,"Information Added","MESSAGE",JOptionPane.INFORMATION_MESSAGE);

        }catch(Exception e){
            
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    public void nonactivate(){
        jLabel1.setVisible(false);
        jLabel2.setVisible(false);
        jLabel4.setVisible(false);
        jLabel6.setVisible(false);
        jLabel7.setVisible(false);
        jLabel9.setVisible(false);
    }
    
    public addTeacherLabAllocation() {
        initComponents();
    }
    
    public addTeacherLabAllocation(String name) {
        initComponents();
        setIconImage();
        nonactivate();
        teacherName = name;
        conn = JConnection.ConnecrDb();
        searchCollege(name);
        fillYear();
        fillTeachers();
        JTableHeader Theader = jTable1.getTableHeader();
        Theader.setOpaque(false);
        Theader.setBackground(new Color(36,47,65));
        Theader.setForeground(new Color(255,255,255));
        
        Theader = jTable2.getTableHeader();
        Theader.setOpaque(false);
        Theader.setBackground(new Color(36,47,65));
        Theader.setForeground(new Color(255,255,255));
 
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
        jLabel17 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox<>();
        jComboBox6 = new javax.swing.JComboBox<>();
        jComboBox7 = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel18 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

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
        jLabel3.setText("ALLOCATE TEACHERS/LABS");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 10, -1, 40));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 860, -1));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel16.setText("Allocate Teachers");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 30, 310, 30));

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
        jPanel1.add(jComboBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 300, 30));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel17.setText("Select Division");
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 310, 30));

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

        jPanel1.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 450, 180, 40));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel19.setText("Select Subject Type ");
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, 310, 30));

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Theory", "Practical" }));
        jComboBox4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox4ItemStateChanged(evt);
            }
        });
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
        jPanel1.add(jComboBox4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, 300, 30));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel21.setText("Select Batch");
        jPanel1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, 310, 30));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel22.setText("Select Subject");
        jPanel1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 360, 310, 30));

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
        jPanel1.add(jComboBox5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 390, 300, 30));

        jComboBox6.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox6ItemStateChanged(evt);
            }
        });
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
        jPanel1.add(jComboBox6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 300, 30));

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
        jPanel1.add(jComboBox7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, 300, 30));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "TEACHER NAME", "ALLOCATE"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

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

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 70, -1, 170));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel18.setText("Select Year ");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 310, 30));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel20.setText("Allocate Labs");
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 260, 310, 30));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PRACTICAL LAB", "ALLOCATE"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 300, -1, 180));

        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setText("Please Select Batch 0");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, -1, 20));

        jLabel2.setForeground(new java.awt.Color(255, 0, 0));
        jLabel2.setText("No Subjects Available");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 420, 110, 20));

        jLabel4.setForeground(new java.awt.Color(255, 0, 0));
        jLabel4.setText("Please Allocate Teacher");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 240, 110, 20));

        jLabel5.setFont(new java.awt.Font("Tahoma", 3, 10)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 0, 102));
        jLabel5.setText("Please Allocate only 1 Teacher for Theory Subject");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 40, 270, 20));

        jLabel6.setForeground(new java.awt.Color(255, 0, 51));
        jLabel6.setText("Please Allocate only 1 Teacher for Theory Subject");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 240, 250, 20));

        jLabel7.setForeground(new java.awt.Color(255, 0, 0));
        jLabel7.setText("Please select proper batch");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 130, 20));
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 240, 360, 20));

        jLabel9.setForeground(new java.awt.Color(255, 0, 0));
        jLabel9.setText("Please allocate labs");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 480, 100, 20));
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 480, 330, 20));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 860, 510));

        setSize(new java.awt.Dimension(872, 608));
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
        nonactivate();
        int count = 0;
        int count2 = 0;
        String typeSelected = (String)jComboBox4.getSelectedItem();
        int batchSelected = Integer.parseInt((String)jComboBox7.getSelectedItem());
        if(typeSelected.equals("Theory") && batchSelected != 0){
            jLabel1.setVisible(true);
        }
        
        if(typeSelected.equals("Practical") && batchSelected == 0){
            jLabel7.setVisible(true);
        }
        
        if(typeSelected.equals("Theory") && batchSelected == 0 || typeSelected.equals("Practical") && batchSelected != 0 ){
            if(jComboBox5.getSelectedIndex() == -1){
                jLabel2.setVisible(true);
            }
        }
        if(typeSelected.equals("Theory") && batchSelected == 0 && jComboBox5.getSelectedIndex() != -1){
            DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
            for(int i = 0; i < model.getRowCount(); i++){
                boolean allocated = true;
                if(model.getValueAt(i, 1) == null || ((boolean)model.getValueAt(i, 1)) == false)
                    allocated = false;
                
                if(allocated)
                {
                    count++;  
                }
                
            }
           
            if(count == 0){
                jLabel4.setVisible(true);
            }
            if(count > 0){
                nonactivate();
                jLabel6.setVisible(true);
            }
            if(count == 1){
                nonactivate();
                allocateTheoryTeacher();
            }
        }
        
        if(typeSelected.equals("Practical") && batchSelected != 0 && jComboBox5.getSelectedIndex() != -1){
            DefaultTableModel model1 = (DefaultTableModel)jTable1.getModel();
            for(int i = 0; i < model1.getRowCount(); i++){
                boolean allocated1 = true;
                if(model1.getValueAt(i, 1) == null || ((boolean)model1.getValueAt(i, 1)) == false)
                    allocated1 = false;
                
                if(allocated1)
                {
                    count++;  
                }
                
            }
           
            if(count == 0){
                jLabel4.setVisible(true);
            }
            else{
                checkLoad();
                System.out.println(load);
                if(count < load){
                    jLabel8.setText("Load : "+load+". Thus allocate Number of Teachers >= Load");
                    jLabel8.setForeground(Color.red);
                }
                    
                else if(count >= load){
                    
                    DefaultTableModel model2 = (DefaultTableModel)jTable2.getModel();
                    for(int i = 0; i < model2.getRowCount(); i++){
                        boolean allocated2 = true;
                        if(model2.getValueAt(i, 1) == null || ((boolean)model2.getValueAt(i, 1)) == false)
                            allocated2 = false;
                
                            if(allocated2)
                            {
                                count2++;  
                            }
                
                        }
                        if(count2 == 0){
                            jLabel9.setVisible(true);
                        }
                        else if(count2 < load){
                            jLabel10.setText("Load : "+load+". Thus allocate Number of Labs >= Load");
                            jLabel10.setForeground(Color.red);
                        }
                            
                        else if(count2 >= load){
                            allocatePractical();
                        }
                    }
            }
            /*if(count > 0){
                nonactivate();
                jLabel6.setVisible(true);
            }
            if(count == 1){
                nonactivate();
                allocateTheoryTeacher();
            }*/
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

    private void jComboBox4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox4MouseClicked

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        // TODO add your handling code here:
        /*actionPerformed = 1;
        if(jComboBox4.getSelectedItem().equals("Practical"))
        {
            showLabs();
        }
        else{

            DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
            while(model.getRowCount() > 0){
                for(int i = 0; i < model.getRowCount(); i++){
                    model.removeRow(i);
                }

            }
        }*/

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

    private void jComboBox3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox3ItemStateChanged
        // TODO add your handling code here:
        jComboBox5.removeAllItems();
        jComboBox6.removeAllItems();
        jComboBox7.removeAllItems();
        String selectedItem = (String)jComboBox3.getSelectedItem();
        fillDivisionAndBatch(selectedItem);
        int selectedDiv = Integer.parseInt((String)jComboBox6.getSelectedItem());
        int selectedBatch = Integer.parseInt((String)jComboBox7.getSelectedItem());
        String selectedType = (String)jComboBox4.getSelectedItem();
        //System.out.println(selectedItem+" "+selectedDiv+" "+selectedBatch+" "+selectedType);
        fillSubjects(selectedItem,selectedDiv,selectedBatch,selectedType);
    }//GEN-LAST:event_jComboBox3ItemStateChanged

    private void jComboBox6ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox6ItemStateChanged
        jComboBox5.removeAllItems();
        if(jComboBox3.getSelectedIndex() == -1 || jComboBox4.getSelectedIndex() == -1 || jComboBox6.getSelectedIndex() == -1 || jComboBox7.getSelectedIndex() == -1){
            
        }
        else{
            String selectedItem = (String)jComboBox3.getSelectedItem();
        
            int selectedDiv = Integer.parseInt((String)jComboBox6.getSelectedItem());
            int selectedBatch = Integer.parseInt((String)jComboBox7.getSelectedItem());
            String selectedType = (String)jComboBox4.getSelectedItem();
            //System.out.println(selectedItem+" "+selectedDiv+" "+selectedBatch+" "+selectedType);
            fillSubjects(selectedItem,selectedDiv,selectedBatch,selectedType);
        }
        
        
    }//GEN-LAST:event_jComboBox6ItemStateChanged

    private void jComboBox7ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox7ItemStateChanged
        jComboBox5.removeAllItems();
        if(jComboBox3.getSelectedIndex() == -1 || jComboBox4.getSelectedIndex() == -1 || jComboBox6.getSelectedIndex() == -1 || jComboBox7.getSelectedIndex() == -1){
            
        }
        else{
            String selectedItem = (String)jComboBox3.getSelectedItem();
        
            int selectedDiv = Integer.parseInt((String)jComboBox6.getSelectedItem());
            int selectedBatch = Integer.parseInt((String)jComboBox7.getSelectedItem());
            String selectedType = (String)jComboBox4.getSelectedItem();
            //System.out.println(selectedItem+" "+selectedDiv+" "+selectedBatch+" "+selectedType);
            fillSubjects(selectedItem,selectedDiv,selectedBatch,selectedType);
        }
        
        if( jComboBox4.getSelectedIndex() == -1 || jComboBox7.getSelectedIndex() == -1){
            
        }
        else{
            String typeSelected = (String)jComboBox4.getSelectedItem();
            int batchSelected = Integer.parseInt((String)jComboBox7.getSelectedItem());
            if( batchSelected != 0 && typeSelected.equals("Practical")){
                fillLabs();
            }
            else{
                DefaultTableModel model = (DefaultTableModel)jTable2.getModel();
                int rows = model.getRowCount();
                for(int i = rows-1 ; i >=0 ; i--){
                    model.removeRow(i);
                }
            }
        }
        
    }//GEN-LAST:event_jComboBox7ItemStateChanged

    private void jComboBox4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox4ItemStateChanged
        jComboBox5.removeAllItems();
        if(jComboBox3.getSelectedIndex() == -1 || jComboBox4.getSelectedIndex() == -1 || jComboBox6.getSelectedIndex() == -1 || jComboBox7.getSelectedIndex() == -1){
            
        }
        else{
            String selectedItem = (String)jComboBox3.getSelectedItem();
        
            int selectedDiv = Integer.parseInt((String)jComboBox6.getSelectedItem());
            int selectedBatch = Integer.parseInt((String)jComboBox7.getSelectedItem());
            String selectedType = (String)jComboBox4.getSelectedItem();
            //System.out.println(selectedItem+" "+selectedDiv+" "+selectedBatch+" "+selectedType);
            fillSubjects(selectedItem,selectedDiv,selectedBatch,selectedType);
        }
        
        if( jComboBox4.getSelectedIndex() == -1 || jComboBox7.getSelectedIndex() == -1){
            
        }
        else{
            String typeSelected = (String)jComboBox4.getSelectedItem();
            int batchSelected = Integer.parseInt((String)jComboBox7.getSelectedItem());
            if( batchSelected != 0 && typeSelected.equals("Practical")){
                fillLabs();
            }
            else{
                DefaultTableModel model = (DefaultTableModel)jTable2.getModel();
                int rows = model.getRowCount();
                for(int i = rows-1 ; i >=0 ; i--){
                    model.removeRow(i);
                }
            }
        }
        
    }//GEN-LAST:event_jComboBox4ItemStateChanged

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
       
        
    }//GEN-LAST:event_jTable1MouseClicked

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
            java.util.logging.Logger.getLogger(addTeacherLabAllocation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(addTeacherLabAllocation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(addTeacherLabAllocation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(addTeacherLabAllocation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new addTeacherLabAllocation().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JComboBox<String> jComboBox7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables
}

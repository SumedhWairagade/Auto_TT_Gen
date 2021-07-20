/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timetable.adminFrame;

import domain.Batches;
import domain.Room;
import domain.Slot;
import domain.Subject;
import domain.Teacher;
import genealgo.Data;
//import genealgo.GeneAlgo;
import genealgo.GeneticAlgorithm;
import genealgo.Population;
import genealgo.Schedule;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;
import timetable.JConnection;
import java.util.List;

/**
 *
 * @author sumed
 */

public class generateTimetable extends javax.swing.JFrame {

    /**
     * Creates new form generateTimetable
     */
    Connection conn = null;
    String teacherName;
    int college_id,dept_id;
    
    public static final double MUTATION_RATE = 0.03;
    public static final int POPULATION_SIZE = 10;
    public static final double CROSSOVER_RATE = 0.9;
    public static final int TOURNAMENT_SELECTION_SIZE = 3;
    public static final int NUMB_OF_ELITE_SCHEDULES = 1;
    //private int scheduleNumb = 0;
    private int classNumb = 1;
    private Data data;
    
    private void insertTablee(Schedule schedule, int generation){
        ArrayList<Slot> classes = schedule.getClasses();
        Connection conn = JConnection.ConnecrDb(); 
        
        classes.forEach(x -> {
            StringBuffer batch= new StringBuffer("");
            ArrayList<Batches> batches = x.getBatch();
            
            batches.forEach(y -> {
                
               batch.append(", "+y.getName());
            });
            
            //System.out.println(batch);
            
            StringBuffer course= new StringBuffer("");
            ArrayList<Subject> courses = x.getCourse();
            
            courses.forEach(y -> {
                
               course.append(", "+y.getName());
            });
            
            //System.out.println(course);
            
            StringBuffer room= new StringBuffer("");
            ArrayList<Room> rooms = x.getRoom();
            
            rooms.forEach(y -> {
                
               room.append(", "+y.getNumber());
            });
            
            //System.out.println(room);
            
            StringBuffer instructor= new StringBuffer("");
            ArrayList<Teacher> instructors = x.getInstructor();
            
            instructors.forEach(y -> {
                
               instructor.append(", "+y.getName());
            });
            
            //System.out.println(instructor);
            //System.out.println(x.getStdYr().toString());
            //System.out.println(x.getMeetingTime().toString());
            try {
                //String query = "insert into table_time(slot_id, typ, year_study, btch, subj, roome, teacher, time_meet) values ("+(x.getId()+1)+", "+x.getType()+", ["+x.getStdYr()+"], ["+x.getBatch()+"], ["+x.getCourse()+"], ["+x.getRoom()+"], ["+x.getInstructor()+"], ["+x.getMeetingTime()+"])";
                PreparedStatement preparedStmt = conn.prepareStatement("insert into table_time (college_id, dept_id, slot_id, typ, year_study, division, btch, subj, room, teacher, time_meet) values (?,?,?,?,?,?,?,?,?,?,?)");
                preparedStmt.setInt(1,1);
                preparedStmt.setInt(2,1);
                preparedStmt.setInt(3, x.getId()+1);
                preparedStmt.setString(4, x.getType());
                preparedStmt.setString(5, x.getStdYr().toString().substring(0, 2));
                preparedStmt.setString(6, x.getStdYr().toString().substring(3));
                preparedStmt.setString(7, batch.toString().substring(2));
                preparedStmt.setString(8, course.toString().substring(2));
                preparedStmt.setString(9, room.toString().substring(2));
                preparedStmt.setString(10, instructor.toString().substring(2));
                preparedStmt.setString(11, x.getMeetingTime().toString());
                preparedStmt.execute();
                //JOptionPane.showMessageDialog(null, "Data added");
            } catch (SQLException |HeadlessException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        });
    } 
    
    private void printScheduleAsTable(Schedule schedule, int generation){
        ArrayList<Slot> classes = schedule.getClasses();
        
        System.out.print("\n                       ");
        System.out.println("Slot No. # | Type   |   Class | Batch  |   Subject | Room |      Teacher        "
                + "| Meeting Time");
        System.out.print("                       ");
        System.out.print("-------------------------------------------------------------------------------------");
        System.out.println("--------------------------------------------------------------------------------------------");
        classes.forEach(x -> {
            /*int majorIndex = data.getStdYrs().indexOf(x.getStdYr());
            String type = x.getType();
            boolean cs = x.getBatch().get(0).getName().equals("-");
            int batchIndex;
            if(cs)
            {
                batchIndex = -2;
            }
            else
            {
                batchIndex = data.getBatches().indexOf(x.getBatch());
                //System.out.println(batchIndex);
            }
            int coursesIndex = data.getCourses("both").indexOf(x.getCourse());
            int roomsIndex=0;
            if(type=="T")
            {
                roomsIndex = data.getRooms("Classroom").indexOf(x.getRoom());
            }
            else
            {
                String ds = new String(x.getRoom().getNumber());
                int lp = data.getRooms("Practical Lab").size();
                for(int c=0;c<lp;c++)
                {
                    if(data.getRooms("Practical Lab").get(c).getNumber().equals(ds))
                    {
                        roomsIndex=c;
                    }
                };
            }

            int instructorsIndex = data.getInstructors().indexOf(x.getInstructor());
            int meetingTimeIndex = data.getMeetingTimes("both").indexOf(x.getMeetingTime());*/
            //System.out.print("                       ");
            System.out.print(String.format("  %1$02d  ", x.getId()+1) + "  | ");
            System.out.print(String.format("%1$4s", x.getType()) + "       | ");
            System.out.print(String.format("%1$4s", x.getStdYr()) + "       | ");
            if(x.getBatch().get(0).getName().equals("-"))
            {
                System.out.print("-" + " | ");
            }
            else
            {
                System.out.print(String.format("%1$4s", x.getBatch()) + "       | ");
            }
            
            System.out.print(String.format("%1$21s", x.getCourse())+"             | ");
            
            System.out.print(String.format("%1$10s", x.getRoom()) + "     | ");
            
            System.out.print(String.format("%1$15s", x.getInstructor()) + "        |");
            System.out.println(x.getMeetingTime());
            //classNumb++;
            
        });
        if(schedule.getFitness() == 1){
            System.out.println("> Solution Found in "+ (generation) +" generations");
        }
    }
    
    private void printAvailableData(){
        System.out.println("Available Classes ==>");
        data.getStdYrs().forEach(x -> System.out.println("name: "+x.getName()+", subjects: "+x.getCourses()));
        
        System.out.println("\nAvailable Theory Subjects ==>");
        data.getCourses("Theory").forEach(x -> System.out.println("course #: "+x.getNumber()+", name: "+x.getName()+", teacher: "+x.getInstructors()));
        
        System.out.println("\nAvailable Practical Subjects ==>");
        data.getCourses("Practical").forEach(x -> System.out.println("course #: "+x.getNumber()+", name: "+x.getName()+", teacher: "+x.getInstructors()+", rooms: "+x.getRoom()));
        
        System.out.println("\nAvailable Class rooms ==>");
        data.getRooms("Classroom").forEach(x -> System.out.println("room #: "+x.getNumber()));
        
        System.out.println("\nAvailable Practical Labs ==>");
        data.getRooms("Practical Lab").forEach(x -> System.out.println("lab #: "+x.getNumber()));
        
        System.out.println("\nAvailable Teachers ==>");
        data.getInstructors().forEach(x -> System.out.println("id: "+x.getId()+", name: "+x.getName()));
        
        System.out.println("\nAvailable Theory Time Slots ==>");
        data.getMeetingTimes("Theory").forEach(x -> System.out.println("id: "+x.getId()+", time slots: "+x.getTime()));
        
        System.out.println("\nAvailable Practical Time Slots ==>");
        data.getMeetingTimes("Practical").forEach(x -> System.out.println("id: "+x.getId()+", time slots: "+x.getTime()));
        
        System.out.print("-------------------------------------------------------------------------------------");
        System.out.println("--------------------------------------------------------------------------------------------");
    }
    
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
        p.setBorder(BorderFactory.createLineBorder(new Color(255,102,102), 2));
  
    }
    
    public void resetColor(JPanel p1) {
        p1.setBackground(new Color(255,102,102));   
    }
    
    public generateTimetable() {
        initComponents();
    }

    public generateTimetable(String name) {
        initComponents();
        setIconImage();
        conn = JConnection.ConnecrDb();
        teacherName = name;
        searchCollege(name);
        (t=new aTask()).execute();
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
        jLabel16 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("AppName");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 520, 30));

        jPanel3.setBackground(new java.awt.Color(255, 102, 102));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Corbel Light", 1, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("STOP EXECUTION");
        jLabel7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel7MouseExited(evt);
            }
        });
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 180, 40));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 140, 180, 40));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 520, 30));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 520, 190));

        jPanel2.setBackground(new java.awt.Color(36, 47, 65));
        jPanel2.setMaximumSize(new java.awt.Dimension(400, 60));
        jPanel2.setMinimumSize(new java.awt.Dimension(400, 60));
        jPanel2.setPreferredSize(new java.awt.Dimension(400, 60));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("GENERATING TIMETABLE");
        jLabel3.setToolTipText("");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, -1, 40));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 520, -1));

        setSize(new java.awt.Dimension(532, 285));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        // TODO add your handling code here:
        t.cancel(true);

    }//GEN-LAST:event_jLabel7MouseClicked

    private void jLabel7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseEntered
        // TODO add your handling code here:
        setColor(jPanel3);
    }//GEN-LAST:event_jLabel7MouseEntered

    private void jLabel7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseExited
        // TODO add your handling code here:
        resetColor(jPanel3);
    }//GEN-LAST:event_jLabel7MouseExited

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
            java.util.logging.Logger.getLogger(generateTimetable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(generateTimetable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(generateTimetable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(generateTimetable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new generateTimetable().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    // End of variables declaration//GEN-END:variables

    private aTask t;
    int flag = 1;
    private class aTask extends SwingWorker<Void, String>{
        @Override
        protected Void doInBackground() throws InterruptedException{
            //GeneAlgo geneAlgo = new GeneAlgo();
        
            //geneAlgo.data = new Data();
            data = new Data();
        
            int generationNumber = 1;
        
            //geneAlgo.printAvailableData();
            printAvailableData();
        
            //System.out.print(generationNumber+",");
            /*System.out.println("> Generation # "+generationNumber);
            System.out.print("  Schedule # |                                                  ");
            System.out.print("[class  ,   subject ,   room    ,   teacher ,   time slot]            ");
            System.out.println("| Fitness | Conflicts");
            System.out.print("-------------------------------------------------------------------------------------");
            System.out.println("--------------------------------------------------------------------------------------------");*/
        
            //GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(geneAlgo.data);
            GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(data);
        
            //Population population = new Population(GeneAlgo.POPULATION_SIZE, geneAlgo.data).sortByFitness();
            Population population = new Population(POPULATION_SIZE, data).sortByFitness();
        
            /*population.getSchedules().forEach(schedule -> System.out.println("         "+geneAlgo.scheduleNumb++ +
                    "     | "+ schedule + " | " +
                    String.format("%.5f", schedule.getFitness()) +
                    " | "+schedule.getNumbOfConflicts()));*/
            //geneAlgo.printScheduleAsTable(population.getSchedules().get(0), generationNumber);
        
            //geneAlgo.classNumb = 1;
            classNumb = 1;
        
            while(population.getSchedules().get(0).getFitness() != 1.0){
                /*System.out.println("> Generation # "+ ++generationNumber);
                System.out.print("  Schedule # |                                                  ");
                System.out.print("[class  ,   subject ,   room    ,   teacher ,   time slot]            ");
                System.out.println("| Fitness | Conflicts");
                System.out.print("-------------------------------------------------------------------------------------");
                System.out.println("--------------------------------------------------------------------------------------------");*/
                population = geneticAlgorithm.evolve(population).sortByFitness();
                //geneAlgo.scheduleNumb = 0;
                /*population.getSchedules().forEach(schedule -> System.out.println("         "+geneAlgo.scheduleNumb++ +
                                                                             "     | "+ schedule + " | " +
                                                                             String.format("%.5f", schedule.getFitness()) +
                                                                             " | "+schedule.getNumbOfConflicts()));*/
                System.out.println(generationNumber++ +","+population.getSchedules().get(0).getNumbOfConflicts());
                publish("Generation number : "+ generationNumber+", Conflicts : "+population.getSchedules().get(0).getNumbOfConflicts());
                if(this.isCancelled()){
                    flag = 0;
                    break;
                }
                    
                //jLabel16.setText(++generationNumber+","+population.getSchedules().get(0).getNumbOfConflicts());
            
                /*if(population.getSchedules().get(0).getNumbOfConflicts()<7)
                        geneAlgo.printScheduleAsTable(population.getSchedules().get(0), generationNumber);*/
            
                //geneAlgo.classNumb = 1;
                classNumb = 1;
            }
            
            if(flag == 0){
                
            }
            else{
                // display timetable in table when fitness will become 1.0
                //geneAlgo.insertTablee(population.getSchedules().get(0), generationNumber);
                insertTablee(population.getSchedules().get(0), generationNumber);
        
                //geneAlgo.printScheduleAsTable(population.getSchedules().get(0), generationNumber);
                printScheduleAsTable(population.getSchedules().get(0), generationNumber);
                Thread.sleep(100);
            }
            
            
            return null;
        }
        
        @Override
        protected void done(){ 
            
            if(this.isCancelled()){
                
                jLabel17.setText("PROCESS STOPPED");
            }
                
            else{
                jLabel17.setText("EUREKA !!! Solution Found ! ");
            }
        }
        
        @Override
        protected void process(List<String> msgs){
            for(String line : msgs){
                jLabel16.setText(line);
            }
        }
    }
}

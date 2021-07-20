package genealgo;

import domain.Batches;
import domain.Subject;
import domain.Teacher;
import domain.TimeSlots;
import domain.Room;
import domain.Class;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JOptionPane;
import timetable.JConnection;


public class Data {
    
    private ArrayList<Room> rooms = new ArrayList<>();
    private ArrayList<Room> labs = new ArrayList<>();
    private ArrayList<Room> roomsAndLabs = new ArrayList<>();
    private ArrayList<Teacher> instructors = new ArrayList<>();
    private ArrayList<Subject> courses = new ArrayList<>();
    private ArrayList<Subject> pract_courses = new ArrayList<>();
    private ArrayList<Subject> theoryAndPractical = new ArrayList<>();
    private ArrayList<Class> stdYrs = new ArrayList<>();
    private ArrayList<Batches> batches = new ArrayList<>();
    private ArrayList<TimeSlots> meetingTimes = new ArrayList<>();
    private ArrayList<TimeSlots> pract_meetingTimes = new ArrayList<>();
    private ArrayList<TimeSlots> theoryAndPractical_meethingTimes = new ArrayList<>();
    private int numberOfClasses = 0;
    
    Connection conn = null;
            
    public Data()
    {
        conn = JConnection.ConnecrDb();
        initialize(); 
    } 
    
    private Data initialize()
    {  
        
        // ADDING CLASSROOMS AND LABS
         try{
                PreparedStatement pst = null;
                pst = conn.prepareStatement("select name,type from infrastructure");
                ResultSet rs = pst.executeQuery();

                Room rm;
                if(rs.next())
                {
                    rs.previous();
                    while(rs.next())
                    { 
                        String name = rs.getString("name");
                        String type = rs.getString("type");
                        if(type.equals("Classroom"))
                        {
                            rm = new Room(name);
                            rooms.add(rm);
                            roomsAndLabs.add(rm);
                        }
                        else
                        {
                            rm = new Room(name);
                            labs.add(rm);
                            roomsAndLabs.add(rm);
                        }
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"No Entries Found in the table","MESSAGE",JOptionPane.INFORMATION_MESSAGE);  
                }


            }catch(Exception e)
            {
                JOptionPane.showMessageDialog(null,e);
            }
         
         
         // ADDING TIMESLOTS
         int id=1;
         String days[]=new String[7];
         try{
                PreparedStatement pst2 = null;
                pst2 = conn.prepareStatement("select abbreviation from working_days");
                ResultSet rs2 = pst2.executeQuery();
                
                int i=0;
                if(rs2.next())
                {
                    rs2.previous();
                    while(rs2.next())
                    { 
                        days[i++] = rs2.getString("abbreviation");
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"No Entries Found in the table","MESSAGE",JOptionPane.INFORMATION_MESSAGE);  
                }
                
                PreparedStatement pst = conn.prepareStatement("select type,slots from time_slots");
                ResultSet rs = pst.executeQuery();

                TimeSlots ts;
                if(rs.next())
                {
                    rs.previous();
                    while(rs.next())
                    { 
                        String mt = rs.getString("slots");
                        String type = rs.getString("type");
                        
                        if(type.equals("Theory"))
                        {
                            for(int j=0;j<i;j++)
                            {
                                String tm = days[j]+" "+mt;
                                ts = new TimeSlots(id,tm);
                                id++;
                                meetingTimes.add(ts);
                                theoryAndPractical_meethingTimes.add(ts);
                            }
                        }
                        else
                        {
                            for(int j=0;j<i;j++)
                            {
                                String tm = days[j]+" "+mt;
                                ts = new TimeSlots(id,tm);
                                id++;
                                pract_meetingTimes.add(ts);
                                theoryAndPractical_meethingTimes.add(ts);
                            }
                        }
                       
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"No Entries Found in the table","MESSAGE",JOptionPane.INFORMATION_MESSAGE);  
                }

                
            }catch(Exception e)
            {
                JOptionPane.showMessageDialog(null,e);
            }
                
        
        
        // ADDING TEACHERS
     try{
            PreparedStatement pst = null;
            pst = conn.prepareStatement("select id,name from teachers");
            ResultSet rs = pst.executeQuery();

            Teacher t;
            if(rs.next())
            {
                rs.previous();
                while(rs.next())
                { 
                    t = new Teacher(rs.getInt("id"),rs.getString("name"));
                    instructors.add(t);
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null,"No Entries Found in the table","MESSAGE",JOptionPane.INFORMATION_MESSAGE);  
            }


        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,e);
        }
         
         
        // ADDING THEORY AND PRACTICAL SUBJECTS
         try{
             
                PreparedStatement pst = conn.prepareStatement("select id,division,batch,type,sub_abbreviation,allocated_teachers,sub_load,room from teacher_allocation");
                ResultSet rs = pst.executeQuery();

                
                if(rs.next())
                {
                    rs.previous();
                    while(rs.next())
                    { 
                        Subject ts;
                        int id2 = rs.getInt("id");
                        int division = rs.getInt("division");
                        int batch = rs.getInt("batch");
                        String type = rs.getString("type");
                        String sub_abbreviation = rs.getString("sub_abbreviation");
                        String allocated_teachers = rs.getString("allocated_teachers");
                        String sub_teachers[] = allocated_teachers.split(",");
                        int sub_load = rs.getInt("sub_load");
                        String str="";
                        
                        if(type.equals("Theory"))
                        {
                            ArrayList<Teacher> sub_teachers_list = new ArrayList<>();
                            str = sub_abbreviation+" "+division;
                                 for(Teacher d : instructors)
                                {
                                    if(d.getName().equals(sub_teachers[0]))
                                    {   
                                        sub_teachers_list.add(d);
                                        break;
                                    }
                                }
                            ts = new Subject(id2,str,sub_teachers_list,sub_load,rooms);
                            courses.add(ts);
                            theoryAndPractical.add(ts);
                        }
                        else
                        {
                            
                            for(int b=1;b<=sub_load;b++)
                            {  
                                ArrayList<Teacher> sub_teachers_list = new ArrayList<>();
                                ArrayList<Room> sub_rooms = new ArrayList<>();
                                str = sub_abbreviation+" "+division+" "+batch+" "+b;
                                
                                
                                for(int z=0;z<sub_teachers.length;z++)
                                {
                                    for(Teacher d : instructors)
                                    {
                                        if(d.getName().equals(sub_teachers[z]))
                                        { 
                                            sub_teachers_list.add(d);
                                            break;
                                        }
                                    }
                                }
                                
                                String room = rs.getString("room");
                                String roomlist[] = room.split(",");
                                sub_rooms.clear();
                                for(int f=0;f<roomlist.length;f++)
                                {
                                    sub_rooms.add(new Room(roomlist[f]));
                                }
                                
                                ts = new Subject(id2,str,sub_teachers_list,sub_load,sub_rooms);
                                
                                pract_courses.add(ts);
                                theoryAndPractical.add(ts);
                            }
                        }   
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"No Entries Found in the table","MESSAGE",JOptionPane.INFORMATION_MESSAGE);  
                }

                
            }catch(Exception e)
            {
                
                JOptionPane.showMessageDialog(null,e);
            }
         
         
         
         // ADDING BATCHES AND THEIR PRACTICAL SUBJECTS
         try{
             
                PreparedStatement pst2 = conn.prepareStatement("select distinct study_year_id,division,batch from teacher_allocation where type=?");
                pst2.setString(1,"Practical");
                ResultSet rs2 = pst2.executeQuery();
                
                
                if(rs2.next())
                {
                    rs2.previous();
                    while(rs2.next())
                    { 
                        
                        int class2 = rs2.getInt("study_year_id");
                        int division = rs2.getInt("division");
                        int batch = rs2.getInt("batch");
                        
                        int count=0;
                        PreparedStatement pst = conn.prepareStatement("select sub_abbreviation,sub_load from teacher_allocation where type=? and study_year_id=? and division=? and batch=?");
                        pst.setString(1,"Practical");
                        pst.setInt(2,class2);
                        pst.setInt(3,division);
                        pst.setInt(4,batch);
                        ResultSet rs = pst.executeQuery();
                        
                        ArrayList<Subject> temp_sub = new ArrayList<>();
                        temp_sub.clear();
                        if(rs.next())
                        {
                            rs.previous();
                            while(rs.next())
                            {                 
                                String sub_abbreviation = rs.getString("sub_abbreviation");
                                int load = rs.getInt("sub_load");
                                for(int b=1;b<=load;b++)
                                {
                                    count++;
                                    String str5 = sub_abbreviation+" "+division+" "+batch+" "+b;

                                    for(Subject d : pract_courses)
                                    {
                                        if(d.getName() != null && d.getName().contains(str5))
                                        {
                                            temp_sub.add(d);
                                            
                                        }
                                    }
                                }
                            }
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null,"No Entries Found in the table","MESSAGE",JOptionPane.INFORMATION_MESSAGE);  
                        }
                        
                        String classname="";
                        try{
                            PreparedStatement pst6 = null;
                            pst6 = conn.prepareStatement("select class from study_years where study_year_id=?");
                            pst6.setInt(1,class2);
                            ResultSet rs6 = pst6.executeQuery();

                            if(rs6.next())
                            {
                                rs6.previous();
                                while(rs6.next())
                                { 
                                    classname = rs6.getString("class");
                                    
                                }
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null,"No Entries Found in the table","MESSAGE",JOptionPane.INFORMATION_MESSAGE);  
                            }


                        }catch(Exception e)
                        {
                            JOptionPane.showMessageDialog(null,e);
                        }
                        String batch_name = classname+" "+division+" "+batch; 
                        Batches ts = new Batches(batch_name,temp_sub,count);
                        batches.add(ts);
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"No Entries Found in the table","MESSAGE",JOptionPane.INFORMATION_MESSAGE);  
                }

                
            }catch(Exception e)
            {
                JOptionPane.showMessageDialog(null,e);
            }
         
         

        // ADDING STUDY_YEARS
        try{   
             
                PreparedStatement pst = conn.prepareStatement("select study_year_id,class,no_of_divisions from study_years");
                ResultSet rs = pst.executeQuery();
                                
                if(rs.next())
                {
                    rs.previous();
                    while(rs.next())
                    { 
                        int local_class_id = rs.getInt("study_year_id");
                        String local_class = rs.getString("class");
                        int nod = rs.getInt("no_of_divisions");
                        for(int i=1;i<=nod;i++)
                        {
                            
                            PreparedStatement pst2 = conn.prepareStatement("select sub_abbreviation from teacher_allocation where study_year_id=? and division=? and type=?");
                            pst2.setInt(1,local_class_id);
                            pst2.setInt(2,i);
                            pst2.setString(3,"Theory");
                            ResultSet rs2 = pst2.executeQuery();

                            String str2="";
                            ArrayList<Subject> temp_sub = new ArrayList<>();
                            temp_sub.clear();
                            if(rs2.next())
                            {
                                rs2.previous();
                                while(rs2.next())
                                { 
                                    String sub = rs2.getString("sub_abbreviation");
                                        str2 = sub+" "+i;
                                        for(Subject d : courses)
                                        {
                                            if(d.getName() != null && d.getName().contains(str2))
                                            {
                                                temp_sub.add(d);
                                            }
                                        }
                                }
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null,"No Entries Found in the table","MESSAGE",JOptionPane.INFORMATION_MESSAGE);  
                            }
                            
                            
                            
                            
                            PreparedStatement pst3 = conn.prepareStatement("select distinct batch from teacher_allocation where study_year_id=? and division=? and type=?");
                            pst3.setInt(1,local_class_id);
                            pst3.setInt(2,i);
                            pst3.setString(3,"Practical");
                            ResultSet rs3 = pst3.executeQuery();
                            
                            String str4="";
                            ArrayList<Batches> temp_batch = new ArrayList<>();
                            temp_batch.clear();
                            if(rs3.next())
                            {
                                rs3.previous();
                                while(rs3.next())
                                {
                                    int batch = rs3.getInt("batch");
                                        str4 = local_class+" "+i+" "+batch;
                                        for(Batches d : batches)
                                        {
                                            if(d.getName() != null && d.getName().contains(str4))
                                            {
                                                temp_batch.add(d);
                                            }
                                        }
                                }
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null,"No Entries Found in the table","MESSAGE",JOptionPane.INFORMATION_MESSAGE);  
                            }
                            
                            String str3 = local_class+" "+i;
                            
                            Class st = new Class(str3,temp_sub,temp_batch);
                            stdYrs.add(st);
                            
                            
                        }
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"No Entries Found in the table","MESSAGE",JOptionPane.INFORMATION_MESSAGE);  
                }
                
                
            }catch(Exception e)
            {
                JOptionPane.showMessageDialog(null,e);
            }

        stdYrs.forEach(x -> numberOfClasses+= x.getCourses().size());
        
        return this;
    }
 
    
    public ArrayList<Room> getRooms(String type)
    {
        if(type.equals("Classroom"))
            return rooms;
        else if(type.equals("Practical Lab"))
            return labs;
        else
            return roomsAndLabs;
    }
    public ArrayList<Teacher> getInstructors(){ return instructors; }
    public ArrayList<Subject> getCourses(String type)
    {
        if(type.equals("Theory"))
            return courses; 
        else if(type.equals("Practical"))
            return pract_courses;
        else
            return theoryAndPractical;
    }
    public ArrayList<Batches> getBatches()
    { 
        return batches; 
    }
    public ArrayList<Class> getStdYrs(){ return stdYrs; }
    public ArrayList<TimeSlots> getMeetingTimes(String type)
    { 
        if(type.equals("Theory"))
            return meetingTimes; 
        else if(type.equals("Practical"))
            return pract_meetingTimes;
        else
            return theoryAndPractical_meethingTimes; 
    }
    public int getNumberOfClasses(){ return this.numberOfClasses; }
}

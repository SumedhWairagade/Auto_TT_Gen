/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timetable.adminFrame;

/**
 *
 * @author Pritam Bera
 */
public class teacherLab {
    private String year,type,subject,sub_abbreviation,teacher_allocation,room;
    int division,batch;
    
    public teacherLab(String year,int division,int batch,String type,String subject,String sub_abbreviation,String teacher_allocation,String room){
        this.year = year;
        this.division = division;
        this.batch = batch;
        this.type = type;
        this.subject = subject;
        this.sub_abbreviation = sub_abbreviation;
        this.teacher_allocation = teacher_allocation;
        this.room = room;
       
    }
    
    public String getYear(){
        return year;
    }
    
    public int getDivision(){
        return division;
    }
    
    public int getBatch(){
        return batch;
    }
    
    public String getType(){
        return type;
    }
    
    public String getSubject(){
        return subject;
    }
    
    public String getSubAbbr(){
        return sub_abbreviation;
    }
    
    public String getAllocTeacher(){
        return teacher_allocation;
    }
    
    public String getAllocLabs(){
        return room;
    }
    
   
    
}

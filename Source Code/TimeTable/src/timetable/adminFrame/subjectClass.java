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
public class subjectClass {
    private String year,type,subject,sub_abbreviation;
    private int sub_load;
    
    
    public subjectClass(String year,String type,String subject,String sub_abbreviation,int sub_load){
        this.year = year;
        this.type = type;
        this.subject = subject;
        this.sub_abbreviation = sub_abbreviation;
        this.sub_load = sub_load;
        
        
    }
    
    public String getYear(){
        return year;
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
    
    public int getSubLoad(){
        return sub_load;
    }
    
  
}

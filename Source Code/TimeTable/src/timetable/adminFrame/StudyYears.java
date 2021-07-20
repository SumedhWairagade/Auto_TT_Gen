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
public class StudyYears {
    private String studyYear;
    private int nod,nob;
    
    public StudyYears(String studyYear,int nod,int nob){
        this.studyYear = studyYear;
        this.nod = nod;
        this.nob = nob;
      
    }
    
    public String getClass1(){
        return studyYear;
    }
    
    public int get_No_of_Divisions(){
        return nod;
    }
    
    public int get_No_of_batches_in_each_division(){
        return nob;
    }
}

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
public class TeachingPref {
    
    private String name,sub_choice_1,no_of_times_taught_1,sub_choice_2,no_of_times_taught_2,sub_choice_3,no_of_times_taught_3;
    
    public TeachingPref(String name,String sub_choice_1,String no_of_times_taught_1,String sub_choice_2,String no_of_times_taught_2,String sub_choice_3,String no_of_times_taught_3){
        this.name = name;
        this.sub_choice_1 = sub_choice_1;
        this.no_of_times_taught_1 = no_of_times_taught_1;
        this.sub_choice_2 = sub_choice_2;
        this.no_of_times_taught_2 = no_of_times_taught_2;
        this.sub_choice_3 = sub_choice_3;
        this.no_of_times_taught_3 = no_of_times_taught_3;
    }
    
    public String getName(){
        return name;
    }
    
    public String getSubChoice1(){
        return sub_choice_1;
    }
    
    public String getNoOfTimesTaught1(){
        return no_of_times_taught_1;
    }
    
    public String getSubChoice2(){
        return sub_choice_2;
    }
    
    public String getNoOfTimesTaught2(){
        return no_of_times_taught_2;
    }
    
    public String getSubChoice3(){
        return sub_choice_3;
    }
    
    public String getNoOfTimesTaught3(){
        return no_of_times_taught_3;
    }
    
}

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
public class timeSlotsClass {
    private String type,time;

    public timeSlotsClass (String type,String time){
       
        this.time = time;
        this.type = type;
    }
    
    public String getType(){
        return type;
    }
    
    public String getTime(){
        return time;
    }
}

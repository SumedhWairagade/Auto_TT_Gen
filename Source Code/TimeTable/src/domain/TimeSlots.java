/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author Pritam Bera
 */
public class TimeSlots {
    
    private int id;
    private String time;
    
    public TimeSlots(int id, String time){
        this.id = id;
        this.time = time;
    }
    
    public int getId(){ return id; }
    public String getTime(){ return time; }
    public String toString(){ return time; }
}

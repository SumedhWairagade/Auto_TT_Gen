/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.ArrayList;

/**
 *
 * @author Pritam Bera
 */
public class Slot {
    
    private int id;
    private Class stdYr;
    private ArrayList<Subject> course = new ArrayList<>();
    private ArrayList<Teacher> instructor = new ArrayList<>();
    private TimeSlots meetingTime;
    private ArrayList<Room> room = new ArrayList<>();
    private ArrayList<Batches> batch = new ArrayList<>();
    private String type;
    
    public Slot(int id, Class stdYr, ArrayList<Subject> course,String type){
        this.id = id;
        this.course = course;
        this.stdYr = stdYr;
        this.type=type;
    }
    
    public void setInstructor(ArrayList<Teacher> instructor){ this.instructor = instructor; }
    public void setMeetingTime(TimeSlots meetingTime){ this.meetingTime = meetingTime; }
    public void setRoom(ArrayList<Room> room){ this.room = room; }
    public void setBatch(ArrayList<Batches> batch){ this.batch = batch; }
    public void setCourse(ArrayList<Subject> course){ this.course = course; }
    
    public int getId(){ return id; }
    public Class getStdYr(){ return stdYr; }
    public ArrayList<Subject> getCourse(){ return course; }
    public ArrayList<Teacher> getInstructor(){ return instructor; }
    public TimeSlots getMeetingTime(){ return meetingTime; }
    public ArrayList<Room> getRoom(){ return room; }
    public ArrayList<Batches> getBatch(){ return batch; }
    public String getType(){ return type; }
    
    public String toString(){
        return "[" + stdYr.getName() + "," + course
                + "," + 
                room + "," + 
                instructor + "," + 
                meetingTime.getTime() + "]";
    }
}

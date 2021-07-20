/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genealgo;

import domain.Batches;
import domain.Class;
import domain.Room;
import domain.Slot;
import domain.Subject;
import domain.Teacher;
import domain.TimeSlots;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Pritam Bera
 */
public class Schedule {
    
    private ArrayList<Slot> classes;
    private Data data;
    private int classNumb = 0;
    private int numbOfConflicts = 0;
    private boolean isFitnessChanged = true;
    private double fitness = -1;
    
    public Schedule(Data data){
        this.data = data;
        classes = new ArrayList<Slot>(data.getNumberOfClasses());
    }
    
    //Initial Schedule
    public Schedule initialize()
    {
            data.getStdYrs().forEach(stdYr -> 
            {
                stdYr.getCourses().forEach(course -> 
                {
                
                    int load = course.getLoad();
                    for(int i=1;i<=load;i++)
                    {
                        ArrayList<Subject> course_list = new ArrayList<>();
                        ArrayList<Room> room_list = new ArrayList<>();
                        ArrayList<Teacher> teacher_list = new ArrayList<>();
                        ArrayList<Batches> batches_list = new ArrayList<>();
                        
                        course_list.add(course);
                        Slot newClass = new Slot(classNumb++, stdYr, course_list,"T");
                        newClass.setMeetingTime(data.getMeetingTimes("Theory").get((int)(data.getMeetingTimes("Theory").size() * Math.random())));  //set a random given meeting time
                        
                        room_list.add(data.getRooms("Classroom").get((int)(data.getRooms("Classroom").size() * Math.random())));
                        newClass.setRoom(room_list);
                        
                        teacher_list.add(course.getInstructors().get((int)(course.getInstructors().size() * Math.random())));
                        newClass.setInstructor(teacher_list);
                        
                        Batches cs = new Batches("-",stdYr.getCourses(),0);
                        batches_list.add(cs);
                        newClass.setBatch(batches_list);
                        classes.add(newClass);
                    }
                
                });
            
                // ALLOCATING PRACTICAL SLOTS
                int count=stdYr.getBatches().get(0).getCount();
                ArrayList<Subject> sub = stdYr.getCourses();
                for(int h=0;h<count;h++)
                {
                    ArrayList<Subject> course_list = new ArrayList<>();
                    ArrayList<Room> room_list = new ArrayList<>();
                    ArrayList<Teacher> teacher_list = new ArrayList<>();
                    ArrayList<Batches> batches_list = new ArrayList<>();
                        
                    int c = stdYr.getBatches().size();
                    
                    int k=h;
                    for(int d=0;d<c;d++)
                    { 
                        course_list.add(stdYr.getBatches().get(d).getCourses().get(k));
                        teacher_list.add(stdYr.getBatches().get(d).getCourses().get(k).getInstructors().get((int)(stdYr.getBatches().get(d).getCourses().get(k).getInstructors().size() * Math.random())));
                        room_list.add(stdYr.getBatches().get(d).getCourses().get(k).getRoom().get((int)(stdYr.getBatches().get(d).getCourses().get(k).getRoom().size() * Math.random())));
                        batches_list.add(stdYr.getBatches().get(d));
                        
                        k=(k+1)%count;
                    };
                    Slot newClass = new Slot(classNumb, stdYr, course_list,"P");
                    newClass.setMeetingTime(data.getMeetingTimes("Practical").get((int)(data.getMeetingTimes("Practical").size() * Math.random())));
                    newClass.setCourse(course_list);
                    newClass.setInstructor(teacher_list);
                    newClass.setRoom(room_list);
                    newClass.setBatch(batches_list);
                    classes.add(newClass);
                    classNumb++;
                }
            });
            
            
        return this;
    }
    
    public Data getData(){ return data; }
    public int getNumbOfConflicts(){ return numbOfConflicts; }
    
    public ArrayList<Slot> getClasses(){
        isFitnessChanged = true;
        return classes;
    }
    
    public double getFitness(){
        if(isFitnessChanged == true){
            fitness = calculateFitness();
            isFitnessChanged = false;
        }
        return fitness;
    }
    
    private double calculateFitness()
    {
        numbOfConflicts = 0;
        classes.forEach(x -> {
            classes.stream().filter(y -> classes.indexOf(y) >= classes.indexOf(x)).forEach(y -> {
                if(x.getId()!=y.getId())
                {
                    if(x.getType().equals("T") && y.getType().equals("T"))
                    {
                        if(x.getMeetingTime().equals(y.getMeetingTime()))
                        {
                            if(x.getStdYr().equals(y.getStdYr()))
                            {
                               numbOfConflicts++; 
                            }
                            else
                            {
                                if(x.getRoom().equals(y.getRoom())){
                                    numbOfConflicts++;
                                }
                                if(x.getInstructor().equals(y.getInstructor())){
                                    numbOfConflicts++;
                                }
                            }
                        }
                    }
                    else if(x.getType().equals("P") && y.getType().equals("P"))
                    {
                        if(x.getMeetingTime().equals(y.getMeetingTime()))
                        {
                            if(x.getStdYr().equals(y.getStdYr()))
                            {
                               numbOfConflicts++; 
                            }
                            else
                            {
                                for(Room r1: x.getRoom())
                                {
                                    for(Room r2: y.getRoom())
                                    {
                                        if(r1.getNumber().equals(r2.getNumber()))
                                        {
                                            numbOfConflicts++;
                                        }
                                    }
                                }

                                for(Teacher r1: x.getInstructor())
                                {
                                    for(Teacher r2: y.getInstructor())
                                    {
                                        if(r1.getName().equals(r2.getName()))
                                        {
                                            numbOfConflicts++;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    else
                    {
                        if(x.getMeetingTime().getTime().charAt(0)==y.getMeetingTime().getTime().charAt(0))
                        {
                            String time1[] = x.getMeetingTime().getTime().split("-");
                            String time2[] = y.getMeetingTime().getTime().split("-");
                            if(time1[0].equals(time2[0]) || time1[1].equals(time2[1]))
                            {
                                if(x.getClass().equals(y.getClass()))
                                    numbOfConflicts++;
                                
                                else
                                {
                                    for(Teacher r1: x.getInstructor())
                                    {
                                        for(Teacher r2: y.getInstructor())
                                        {
                                            if(r1.getName().equals(r2.getName()))
                                            {
                                                numbOfConflicts++;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if(x.getType().equals("P"))
                {
                    for(int r1=0;r1<x.getRoom().size();r1++)
                    {
                        for(int r2=0;r2<x.getRoom().size();r2++)
                        {
                            if(r1!=r2)
                            {
                                if(x.getRoom().get(r1).getNumber().equals(x.getRoom().get(r2).getNumber()))
                                {
                                    numbOfConflicts++;
                                }
                            }
                        }
                    }
                    for(int r1=0;r1<x.getInstructor().size();r1++)
                    {
                        for(int r2=0;r2<x.getInstructor().size();r2++)
                        {
                            if(r1!=r2)
                            {
                                if(x.getInstructor().get(r1).getName().equals(x.getInstructor().get(r2).getName()))
                                {
                                    numbOfConflicts++;
                                }
                            }
                        }
                    }
                }
            });
        });
        return 1/(double)(numbOfConflicts + 1);
    }
    
    public String toString(){
        String returnValue = new String();
        for( int x = 0; x < classes.size()-1; x++){
            returnValue+= classes.get(x) + ",";
        }
        returnValue+= classes.get(classes.size()-1);
        return returnValue;
    }
}

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
public class Subject{
    
    private int number = 0;
    private String name = null;
    private ArrayList<Teacher> instructors = new ArrayList<>();
    private ArrayList<Room> labs = new ArrayList<>();
    private int load;
    
    public Subject(int number, String name, ArrayList<Teacher> instructors, int load, ArrayList<Room> labs) throws
                          CloneNotSupportedException
    {
        this.number = number;
        this.name = name;
        this.instructors = instructors;
        this.load = load;
        this.labs=labs;
    }
    
    public int getNumber(){ return number; }
    public String getName(){ return name; }
    public ArrayList<Teacher> getInstructors(){ return instructors; }
    public String toString(){ return name; }
    public int getLoad(){ return load; }
    public ArrayList<Room> getRoom(){ return labs; }
}

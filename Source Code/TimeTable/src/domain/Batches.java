/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class Batches {
    private String name;
    private ArrayList<Subject> courses;
    private int count;
    
    public Batches(String name, ArrayList<Subject> courses,int count ){
        this.name = name;
        this.courses = courses;
        this.count=count;
    }

    public String getName(){ return name; }
    public String toString(){ return name; }
    public int getCount(){ return count; }
    public ArrayList<Subject> getCourses()
    { 
            return courses;
    }
}
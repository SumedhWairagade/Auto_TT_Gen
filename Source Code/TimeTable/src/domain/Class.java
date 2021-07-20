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
public class Class {
    
    private String name;
    private ArrayList<Subject> courses;
    private ArrayList<Batches> batches;
    
    public Class(String name, ArrayList<Subject> courses ,ArrayList<Batches> batches){
        this.name = name;
        this.courses = courses;
        this.batches = batches;
    }

    public String getName(){ return name; }
    public ArrayList<Subject> getCourses()
    { 
            return courses;
    }
    public ArrayList<Batches> getBatches()
    { 
            return batches;
    }
    public String toString(){ return name; }
}

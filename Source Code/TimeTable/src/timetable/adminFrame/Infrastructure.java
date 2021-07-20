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
public class Infrastructure {
    private String type,name;
    int number;
    
    public Infrastructure(String type,int number,String name){
        this.type = type;
        this.number = number;
        this.name = name;
      
    }
    
    public String getType(){
        return type;
    }
    
    public int getNumber(){
        return number;
    }
    
    public String getName(){
        return name;
    }
}

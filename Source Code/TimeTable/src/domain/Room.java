/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;
import java.lang.Cloneable;

/**
 *
 * @author Pritam Bera
 */
public class Room implements Cloneable{
    
    private String number;
    public Room(String number){
        this.number = number;
    }
    
    
    public String getNumber(){ return number; }
    
    @Override
    public Object clone() throws CloneNotSupportedException 
    { 
        return super.clone(); 
    } 
    public String toString(){ return number; }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genealgo;

import java.util.ArrayList;
import java.util.stream.IntStream;

/**
 *
 * @author Pritam Bera
 */
public class Population {
    
    private ArrayList<Schedule> schedules;
    
    //Initial Population
    public Population(int size, Data data){
        schedules = new ArrayList<Schedule>(size);
        IntStream.range(0,size).forEach(x -> schedules.add(new Schedule(data).initialize()));   //Creates random schedules
    }
    
    public ArrayList<Schedule> getSchedules(){ return this.schedules; }
    
    public Population sortByFitness(){
        schedules.sort((schedule1,schedule2) -> {
            int returnValue = 0;
            if(schedule1.getFitness() > schedule2.getFitness()){
                returnValue = -1;
            }
            else if(schedule1.getFitness() < schedule2.getFitness()){
                returnValue = 1;
            }
            return returnValue;
        });
        return this;
    }
}

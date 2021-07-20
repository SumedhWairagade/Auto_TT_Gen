/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genealgo;

import java.util.ArrayList;
import java.util.stream.IntStream;
import timetable.adminFrame.generateTimetable;

/**
 *
 * @author Pritam Bera
 */
public class GeneticAlgorithm {
    
    private Data data;
    
    public GeneticAlgorithm(Data data){ this.data = data; }
    
    public Population evolve(Population population){ return mutatePopulation(crossoverPopulation(population)); }
    
    Population crossoverPopulation(Population population){
        
        //creates a population for crossover
        Population crossoverPopulation = new Population(population.getSchedules().size(), data);     
        
        //from the created population of schedules for crossover select the elite schedules
        //elite schedules are the schedules in the current generation with the best fitness values. 
        //These individuals automatically survive to the next generation.
        
        //IntStream.range(0, GeneAlgo.NUMB_OF_ELITE_SCHEDULES).forEach(x -> crossoverPopulation.getSchedules().set(x, population.getSchedules().get(x)));
        IntStream.range(0, generateTimetable.NUMB_OF_ELITE_SCHEDULES).forEach(x -> crossoverPopulation.getSchedules().set(x, population.getSchedules().get(x)));
        
        //for the remaining schedules in the population that are not elite 
        
        //IntStream.range(GeneAlgo.NUMB_OF_ELITE_SCHEDULES, population.getSchedules().size()).forEach(x -> {
        IntStream.range(generateTimetable.NUMB_OF_ELITE_SCHEDULES, population.getSchedules().size()).forEach(x -> {
            
            //the condition satisfy then do crossover
            
            //if(GeneAlgo.CROSSOVER_RATE > Math.random()){
            if(generateTimetable.CROSSOVER_RATE > Math.random()){
                
                Schedule schedule1 = selectTournamentPopulation(population).sortByFitness().getSchedules().get(0);
                Schedule schedule2 = selectTournamentPopulation(population).sortByFitness().getSchedules().get(0);
                
                //does the crossover and sets the crossovered schedule in the crossoveredPopulation in the index position x
                crossoverPopulation.getSchedules().set(x, crossoverSchedule(schedule1,schedule2));
            }
            else{
                
                //doesn't perform the crossover and directly write the schedule in the crossoveredPopulation
                crossoverPopulation.getSchedules().set(x, population.getSchedules().get(x));
            }
        });
        
        return crossoverPopulation;
    }
    
    Population selectTournamentPopulation(Population population){   
        
        //Population tournamentPopulation = new Population(GeneAlgo.TOURNAMENT_SELECTION_SIZE, data);
        Population tournamentPopulation = new Population(generateTimetable.TOURNAMENT_SELECTION_SIZE, data);
        
        //selects number of schedules specified in tournamant selection size 
        
        //IntStream.range(0, GeneAlgo.TOURNAMENT_SELECTION_SIZE).forEach(x -> {
        IntStream.range(0, generateTimetable.TOURNAMENT_SELECTION_SIZE).forEach(x -> {
            
            tournamentPopulation.getSchedules().set(x, population.getSchedules().get((int)(Math.random() * population.getSchedules().size())));
        });
        
        return tournamentPopulation;
    }
    
    Schedule crossoverSchedule(Schedule schedule1, Schedule schedule2){     //does crossover btween two schedules selected in selectTournamentPopulation()
        
        Schedule crossoverSchedule = new Schedule(data).initialize();
        
        //set each slot either from schedule 1 or schedule 2
        IntStream.range(0, crossoverSchedule.getClasses().size()).forEach(x -> {
            if(Math.random() > 0.5){
                
                crossoverSchedule.getClasses().set(x,schedule1.getClasses().get(x));
            }
            else{
                    crossoverSchedule.getClasses().set(x,schedule2.getClasses().get(x));
                
            }
        });
        
        return crossoverSchedule;
    }
    
    Population mutatePopulation(Population population){
        
        Population mutatePopulation = new Population(population.getSchedules().size(), data);
        
        ArrayList<Schedule> schedules = mutatePopulation.getSchedules();
        
        //Directly write the elite schedules to the mutatePopulation
        //IntStream.range(0, GeneAlgo.NUMB_OF_ELITE_SCHEDULES).forEach(x -> schedules.set(x, population.getSchedules().get(x)));
        IntStream.range(0, generateTimetable.NUMB_OF_ELITE_SCHEDULES).forEach(x -> schedules.set(x, population.getSchedules().get(x)));
        
        //Other than elite schedules do mutation
        //IntStream.range(GeneAlgo.NUMB_OF_ELITE_SCHEDULES, population.getSchedules().size()).forEach(x -> {
        IntStream.range(generateTimetable.NUMB_OF_ELITE_SCHEDULES, population.getSchedules().size()).forEach(x -> {    
            
            schedules.set(x, mutateSchedule(population.getSchedules().get(x)));
        });
        
        return mutatePopulation;
    }
    
    Schedule mutateSchedule(Schedule mutateSchedule){     //does mutation on all the schedules of Population in mutatePopulation()
        
        //generates a new schedule
        Schedule schedule = new Schedule(data).initialize();
        
        IntStream.range(0, mutateSchedule.getClasses().size()).forEach(x -> {
            
            //if(GeneAlgo.MUTATION_RATE > Math.random()){
            if(generateTimetable.MUTATION_RATE > Math.random()){
                
                //sets the slots in mutateSchedule from the generated schedule in this function
                mutateSchedule.getClasses().set(x, schedule.getClasses().get(x));
            }
        });
        return mutateSchedule;
    }
}

package org.fleen.samples.fleenRasterCompositionGen.gre;

import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.fleen.samples.fleenRasterCompositionGen.Log;

/*
 * We have 3 classes for handling generate, render and export commands
 * We stick them on a queue so they get handled sequentially, don't impede the ui thread and don't
 * get dropped on exit. 
 */
public class GRECommandManager{
  
  /*
   * ################################
   * COMMAND QUEUE
   * ################################
   */
  
  private static final ScheduledExecutorService sched=Executors.newSingleThreadScheduledExecutor();
  private static GRECommand_Abstract command;
  private static Deque<GRECommand_Abstract> commands=new LinkedList<GRECommand_Abstract>();
  private static final long 
    COMMAND_QUEUE_MANAGER_INIT_DELAY=500,
    COMMAND_QUEUE_MANAGER_PERIODIC_DELAY=20;
  
  public static final void init(){
    sched.scheduleWithFixedDelay(
      new CommandExecutor(),
      COMMAND_QUEUE_MANAGER_INIT_DELAY,
      COMMAND_QUEUE_MANAGER_PERIODIC_DELAY, 
      TimeUnit.MILLISECONDS);}
  
  private static class CommandExecutor extends Thread{
    public void run(){
      if(!commands.isEmpty()){
        command=commands.removeFirst();
        try{
          command.execute();
        }catch(Throwable e){
          e.printStackTrace();}}}}
  
  /*
   * ################################
   * COMMANDS
   * ################################
   */
  
  public static void generate(){
    commands.addLast(new C_Generate());}
  
  public static void renderForViewer(){
    commands.addLast(new C_RenderForViewer());}
  
  public static void renderForExport(){
    commands.addLast(new C_RenderForExport());}
  
  public static void export(){
    commands.addLast(new C_Export());}
  
  public static void generateAndExport(){
    commands.addLast(new C_GenerateAndExport());}
  
  
  

}

package org.fleen.grammarEditor;

import java.awt.EventQueue;
import java.io.File;
import java.net.URLDecoder;
import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.fleen.grammarEditor.bubbleModelEditor.BubbleModelEditor;
import org.fleen.grammarEditor.grammarProject.GrammarProject;
import org.fleen.grammarEditor.jigEditor.JigEditor;
import org.fleen.grammarEditor.overview.C_OverviewRefreshAll;
import org.fleen.grammarEditor.overview.Overview;
import org.fleen.grammarEditor.util.Command_Abstract;
import org.fleen.samples.fleenRasterCompositionGen.FRCG;

/*
 * This is the main class for the Grammar Editor
 * start and stop the application
 * manage the command queue
 * we have 4 subsystems passing commands to each other via the sequencer
 */
public class GE{
  
  /*
   * ################################
   * MAIN STATIC FIELDS
   * ################################
   */
  
  public static final String APPNAME="Fleen Grammar Editor 0.2 pre-alpha";
  
  public static boolean runmain=true;
  public static UIMain uimain=null;
  //SUBSYSTEMS
  //all of the data associated with the grammar that we're editing
  //init to an empty project
  public static GrammarProject grammarproject=new GrammarProject();
  //top level overview of the grammar
  public static Overview overview=new Overview();
  //diamond grid view vertex clicker edit thing + various data 
  public static BubbleModelEditor bubblemodeleditor=new BubbleModelEditor();
  //diamond grid view vertex clicker edit thing + various data 
  public static JigEditor jigeditor=new JigEditor();
  
  /*
   * ################################
   * MAIN
   * ################################
   */
  
  public static final void main(String[] a){
    init();}
  
  /*
   * ################################
   * INIT AND TERM
   * ################################
   */
  
  public static final void init(){
    //set l&f
    try{
      for(LookAndFeelInfo info:UIManager.getInstalledLookAndFeels()){
        if("Nimbus".equals(info.getName())){
          UIManager.setLookAndFeel(info.getClassName());
          break;}}
    }catch(Exception e){}//go with default
    //init uimain
    EventQueue.invokeLater(new Runnable(){
      public void run(){
        try{
          uimain=new UIMain();
          uimain.setExtendedState(UIMain.MAXIMIZED_BOTH);
          uimain.setTitle(APPNAME);
          uimain.setVisible(true);
          command(new C_OverviewRefreshAll());
        }catch(Exception e){
          e.printStackTrace();}}});
    //
    initCommandQueueManager();}
  
  /*
   * wait for the command queue to empty and then exit normally
   * TODO stick this on mainwindowclose or something
   */
  public static final void term(){
    System.out.println("exiting");
    while(!commands.isEmpty()){};
    runmain=false;
    System.exit(0);}
  
  /*
   * ################################
   * COMMAND QUEUE
   * ################################
   */
  
  private static final ScheduledExecutorService sched=Executors.newSingleThreadScheduledExecutor();
  private static Command_Abstract command;
  private static Deque<Command_Abstract> commands=new LinkedList<Command_Abstract>();
  private static final long 
    COMMAND_QUEUE_MANAGER_INIT_DELAY=500,
    COMMAND_QUEUE_MANAGER_PERIODIC_DELAY=20;
  
  //stick a new command on the end of the queue
  public static void command(Command_Abstract c){
    commands.addLast(c);}
  
  //do it with a param
  //janky? perhaps, but it's easy.
  public static void command(Command_Abstract c,Object param){
    c.param=param;
    commands.addLast(c);}
  
  private static void initCommandQueueManager(){
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
   * UTIL
   * ################################
   */
  
  /*
   * returns the directory containing the FRCG binary
   */
  public static final File getLocalDir(){
    String path=FRCG.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    String decodedpath;
    try{
      decodedpath=URLDecoder.decode(path,"UTF-8");
    }catch(Exception x){
      throw new IllegalArgumentException(x);}
    File f=new File(decodedpath);
    if(!f.isDirectory())f=f.getParentFile();
    return f;}

}

package org.fleen.grammarEditor.util;

public abstract class Command_Abstract{
  
  /*
   * ################################
   * FIELDS
   * ################################
   */
  
  //optional param
  public Object param=null;
  
  /*
   * ################################
   * EXECUTION
   * ################################
   */
  
  public final void execute(){
    System.out.println(">>>"+getClass().getSimpleName());
    run();}
  
  protected abstract void run();
  
}

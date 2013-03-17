package org.fleen.samples.fleenRasterCompositionGen.gre;

import org.fleen.samples.fleenRasterCompositionGen.Log;

public abstract class GRECommand_Abstract{
  
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
    Log.m1(getDescription());
    run();}
  
  protected abstract void run();
  
  protected abstract String getDescription();
  
}

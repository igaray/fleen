package org.fleen.samples.fleenRasterCompositionGen;

import java.awt.EventQueue;


/*
 * create a diamond composition
 * from that create a quilt
 * create a colormap too
 * render quilt and colormap to a bitmap image, stick the image in a directory
 * do that a bunch of times
 *  
 */
public class FleenRasterCompositionGen{
  
  /*
   * ################################
   * APP MAIN
   * ################################
   */
  
  public static final String
    TITLE="Fleen Raster Composition Gen",
    VERSION="0.1 alpha";
  
  public static FleenRasterCompositionGen frcg_instance=null;
  
  public static final void main(String[] a){
    frcg_instance=new FleenRasterCompositionGen();}
  
  /*
   * ################################
   * INSTANCE CONSTRUCTOR AND FIELDS
   * ################################
   */
  
  public static final FileWriterPNG FILEWRITER=new FileWriterPNG();
  public UI ui;
  public FRCGParams params;
  
  private FleenRasterCompositionGen(){EventQueue.invokeLater(new Runnable(){
    public void run(){
      try{
        ui=new UI();
        ui.frame.setVisible(true);
        //init the params after the ui is done initializing
        //so we have some ui components to update
        params=new FRCGParams();
      }catch(Exception e){
        e.printStackTrace();}}});}
  
  /*
   * ################################
   * INSTANCE TERMINATOR
   * ################################
   */
  
  static final void terminate(){
    //TODO serialize params
    //make sure all exports are finished writing
  }
  
}

package org.fleen.samples.fleenRasterCompositionGen;

import java.awt.EventQueue;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import org.fleen.samples.fleenRasterCompositionGen.gre.GRECommandManager;

/*
 * create a diamond composition
 * from that create a quilt
 * create a colormap too
 * render quilt and colormap to a bitmap image, stick the image in a directory
 * do that a bunch of times
 *  
 */
public class FRCG{
  
  /*
   * ################################
   * APP MAIN
   * ################################
   */
  
  public static final String
    TITLE="Fleen Raster Composition Gen",
    VERSION="0.1 alpha";
  
  public static FRCG instance=null;
  
  public static final void main(String[] a){
    instance=new FRCG();}
  
  /*
   * ################################
   * INSTANCE FIELDS
   * ################################
   */
  
  public UI ui=null;
  public FRCGParams params=null;
  public Composition composition=null;
  public BufferedImage viewerimage=null,exportimage=null;
  
  /*
   * ################################
   * INSTANCE CONSTRUCTOR
   * ################################
   */
  
  private FRCG(){EventQueue.invokeLater(new Runnable(){
    public void run(){
      try{
        GRECommandManager.init();
        ui=new UI();
        ui.frame.setVisible(true);
        ui.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //init the params after the ui is done initializing
        //so we have some ui components to update
        params=new FRCGParams();
        postMessage(ABOUT_TEXT);
      }catch(Exception e){
        e.printStackTrace();}}});}
  
  /*
   * ################################
   * INSTANCE TERMINATOR
   * ################################
   */
  
  public void terminate(){
    //TODO serialize params
    //make sure all exports are finished writing
    //terminate command queue
  }
  
  /*
   * ################################
   * MESSAGES
   * ################################
   */
  
//  public static final String ABOUT_TEXT=
//    TITLE+" "+VERSION+"\n"+
//    "By John Greene\n"+
//    "john@fleen.org\n"+
//    "http://fleen.org\n"+
//    "==VIEWER CONTROLS==\n"+
//    "ZOOM : Ctrl+Mouse1+Drag\n"+
//    "PAN : Shift+Mouse1+Drag\n";
  
  public static final String ABOUT_TEXT=
      TITLE+" "+VERSION+"\n"+
      "By John Greene\n"+
      "john@fleen.org\n"+
      "http://fleen.org\n";
  
  public void postMessage(String m){
    ui.txtMessage.postMessage("["+System.currentTimeMillis()+"]\n"+m);}
  
}

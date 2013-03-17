package org.fleen.samples.fleenRasterCompositionGen;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
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
  
  //public static final String ABOUT_TEXT=
  //TITLE+" "+VERSION+"\n"+
  //"By John Greene\n"+
  //"john@fleen.org\n"+
  //"http://fleen.org\n"+
  //"==VIEWER CONTROLS==\n"+
  //"ZOOM : Ctrl+Mouse1+Drag\n"+
  //"PAN : Shift+Mouse1+Drag\n";
  
  public static final String INFO_TEXT=
    TITLE+" "+VERSION+"\n"+
    "By John Greene\n"+
    "john@fleen.org\n"+
    "http://fleen.org\n";
  
  public static FRCG instance=null;
  public static boolean debug=true;
  
  public static final int LOGBOX_MAX_CHARS=2000;
  public static final Color 
    LOGBOX_BACKGROUND=new Color(128,128,128),
    LOGBOX_FOREGROUND=new Color(255,255,255);
  public static final Font LOGBOX_FONT=new Font("DejaVu Sans Mono", Font.PLAIN, 14);
  
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
        Log.init(ui.txtLogBox);
        params=new FRCGParams();
        Log.m0(INFO_TEXT);
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
  
}

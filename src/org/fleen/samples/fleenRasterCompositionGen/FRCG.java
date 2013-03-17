package org.fleen.samples.fleenRasterCompositionGen;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URLDecoder;

import javax.swing.JFrame;

import org.fleen.grammarEditor.GE;
import org.fleen.samples.fleenRasterCompositionGen.command.Command;

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
  public FRCGConfig config=null;
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
        Command.init();
        ui=new UI();
        ui.frame.setVisible(true);
        ui.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        Log.init(ui.txtLogBox);
        Log.m0(INFO_TEXT);
        config=initConfig();
        config.initUIComponents();
      }catch(Exception e){
        e.printStackTrace();}}});}
  
  /*
   * ################################
   * INSTANCE TERMINATOR
   * ################################
   */
  
  public static final void terminateInstance(){
    Command.saveConfig();
    while(!Command.idle()){};
    instance.ui.frame.setVisible(false);
    System.exit(0);}
  
  /*
   * ################################
   * CONFIG FILE
   * ################################
   */
  
  private static final String CONFIG_FILE_NAME="frcgconfig";
  
  /*
   * get the config file from the local directory
   * if that fails then create a new one
   */
  private static FRCGConfig initConfig(){
    FRCGConfig c=null;
    File localdir=getLocalDir();
    File[] localfiles=localdir.listFiles(new FileFilter(){
      public boolean accept(File a){
        return a.getName().equals(CONFIG_FILE_NAME);}});
    if(localfiles.length>0)
      c=extractConfigFromFile(localfiles[0]);
    if(c!=null){
      Log.m1("Loaded Config.");
    }else{
      Log.m1("Creating new Config.");
      c=new FRCGConfig();}
    return c;}
  
  //get config from local file
  //return null on fail
  private static final FRCGConfig extractConfigFromFile(File file){
    FileInputStream fis;
    ObjectInputStream ois;
    FRCGConfig c=null;
    try{
      fis=new FileInputStream(file);
      ois=new ObjectInputStream(fis);
      c=(FRCGConfig)ois.readObject();
      ois.close();
    }catch(Exception x){}
    return c;}
  
  public static final void saveConfig(){
    File localdir=getLocalDir();
    File configfile=new File(localdir.getPath()+"/"+CONFIG_FILE_NAME);
    FileOutputStream fos;
    ObjectOutputStream oot;
    try{
      fos=new FileOutputStream(configfile);
      oot=new ObjectOutputStream(fos);
      oot.writeObject(instance.config);
      oot.close();
    }catch(IOException ex){
      System.out.println("Exception in save config.");
      ex.printStackTrace();}}
  
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

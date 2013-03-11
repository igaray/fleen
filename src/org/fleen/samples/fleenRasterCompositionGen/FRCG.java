package org.fleen.samples.fleenRasterCompositionGen;

import java.awt.EventQueue;
import java.awt.image.BufferedImage;

import org.fleen.samples.fleenRasterCompositionGen.renderer.Renderer_Abstract;

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
  
  public FileWriterPNG filewriter=new FileWriterPNG();
  public UI ui;
  public FRCGParams params;
  Composition composition;
  BufferedImage image;
  
  /*
   * ################################
   * INSTANCE CONSTRUCTOR
   * ################################
   */
  
  private FRCG(){EventQueue.invokeLater(new Runnable(){
    public void run(){
      try{
        ui=new UI();
        ui.frame.setVisible(true);
        //init the params after the ui is done initializing
        //so we have some ui components to update
        params=new FRCGParams();
        post(ABOUT_TEXT);
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
  
  /*
   * ################################
   * GENERATE
   * ################################
   */
  
  public void generate(){
    post("started generate");
    composition=new Composition();
    post("finished generate");
    post("start render");
    Renderer_Abstract r=params.getRenderer();
    image=r.render(composition,params.getExportImageScale());
    ui.panViewer.update();
    post("finished render");
  }
  
  /*
   * ################################
   * EXPORT
   * ################################
   */
  
  public void export(){
    post("started export");
    
    post("finished export");
  }
  
  /*
   * ################################
   * GENERATE AND EXPORT
   * ################################
   */
  
  public void generateAndExport(){
    
    post("started generate and export");
    
    post("finished generate and export");
    
  }
  
  /*
   * ################################
   * MESSAGES
   * ################################
   */
  
  public static final String ABOUT_TEXT=
    TITLE+" "+VERSION+"\n"+
    "By John Greene\n"+
    "john@fleen.org\n"+
    "http://fleen.org";
  
  public void post(String s){
    ui.txtMessage.postMessage(s);}
  
}

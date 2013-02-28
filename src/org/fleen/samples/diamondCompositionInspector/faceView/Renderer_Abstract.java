package org.fleen.samples.diamondCompositionInspector.faceView;

import java.awt.image.BufferedImage;


public abstract class Renderer_Abstract implements Renderer{

  /*
   * ################################
   * RENDER
   * ################################
   */
  
  public BufferedImage render(){
    if(viewer==null){
      System.out.println("---null viewer");
      return null;}
    BufferedImage i=getImage();
    return i;}
  
  protected abstract BufferedImage getImage();

  /*
   * ################################
   * VIEWER
   * ################################
   */
  
  protected FaceView viewer;
  
  public void setViewer(FaceView v){
    viewer=v;}
  
  /*
   * ################################
   * STATIC UTIL
   * ################################
   */
  
  

}

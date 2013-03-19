package org.fleen.samples.fleenRasterCompositionGen;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Viewer extends JPanel{
  
  private static final Color COLOR_BACKGROUND=new Color(128,128,128);
  
  public Viewer(){
    super();}
  
  public void update(){
    repaint();}
  
  public void paint(Graphics g){
    g.setColor(COLOR_BACKGROUND);
    g.fillRect(0,0,getWidth(),getHeight());
    if(FRCG.instance!=null&&FRCG.instance.viewerimage!=null){
      g.drawImage(FRCG.instance.viewerimage,0,0,null);}}

}

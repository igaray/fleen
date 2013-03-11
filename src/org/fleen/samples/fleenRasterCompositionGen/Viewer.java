package org.fleen.samples.fleenRasterCompositionGen;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Viewer extends JPanel{
  
  public void update(){
    repaint();}
  
  public void paint(Graphics g){
    if(FRCG.instance!=null&&FRCG.instance.viewerimage!=null){
      g.setColor(Color.yellow);
      g.fillRect(0,0,getWidth(),getHeight());
      g.drawImage(FRCG.instance.viewerimage,0,0,null);}}

}

package org.fleen.samples.fleenRasterCompositionGen;

import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Viewer extends JPanel{
  
  public void update(){
    repaint();}
  
  public void paint(Graphics g){
    if(FRCG.instance.image!=null)
      g.drawImage(FRCG.instance.image,0,0,null);}

}

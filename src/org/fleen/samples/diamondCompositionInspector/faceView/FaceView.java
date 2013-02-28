package org.fleen.samples.diamondCompositionInspector.faceView;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.fleen.core.diamondGrammar.Bubble;
import org.fleen.core.diamondGrammar.DGComposition;
import org.fleen.core.diamondGrammar.Grid;
import org.fleen.samples.diamondCompositionInspector.Util;


/*
 * The viewer renders a Fleen to the screen.
 * It provides UI for controlling a viewing model (transform, viewport, style). 
 * 
 * VIEW MODEL CONTROL BY MOUSE:
 * shift+mouseleftclick+dragupdown to scale
 * alt+mouseleftclick+dragaround to pan
 * click on stuff to get info
 * 
 */

@SuppressWarnings("serial")
public class FaceView extends JPanel{
  
  /*
   * ################################
   * FIELDS
   * ################################
   */
  
  static final double CENTER_AND_FIT_MARGIN=20;//in pixels
  
  DGComposition fleen=null;
  AffineTransform transform=new AffineTransform();
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public FaceView(){
    addMouseListener(new MouseListener0());
    setBackground(new Color(128,128,128));
    this.setBorder(BorderFactory.createLineBorder(Color.black,8));}
  
  public void init(DGComposition fleen){
    this.fleen=fleen;
    centerAndFit();}
  
  /*
   * ################################
   * VIEW GEOMETRY CONTROL INTERFACE
   * ################################
   */
  
  private void initTransform(){
    transform.setToIdentity();
    transform.scale(1,-1);}
   
  void translate(double x,double y){
    transform.translate(x,-y);
  	updateImage();}
   
  void scale(double s){
    transform.scale(s,s);
  	updateImage();}

  /*
   * center and fit the root bubble to the view port
   * if we don't have a root bubble then fit to the root grid
   * this second case is unusual and generally an error but we allow it for our testing
   */
  public void centerAndFit(){
    Grid g=fleen.getRootGrid();
    if(!g.isLeaf()){
      centerAndFitBubble(g.getChildBubbles().get(0));
    }else{
      centerAndFitGrid();}}
  
	void centerAndFitBubble(Bubble b){
		initTransform();
		//scale
    Rectangle2D 
      vb=getBounds(),
      fb=Util.getBubbleData(b).getBounds2D();
		double 
  		sx=(vb.getWidth()-CENTER_AND_FIT_MARGIN*2.0)/fb.getWidth(),
  		sy=(vb.getHeight()-CENTER_AND_FIT_MARGIN*2.0)/fb.getHeight();
	  double scale=Math.min(sx,sy);
	  if(Double.isInfinite(scale))scale=1.0;
	  transform.scale(scale,scale);
	  //center
	  System.out.println("scale="+scale);
		double 
		  cxoff=((vb.getWidth()/scale)+(fb.getWidth()/2))/2,
	    cyoff=((vb.getHeight()/scale)+fb.getHeight())/-2;
	  transform.translate(cxoff,cyoff);
	  //
	  updateImage();}
	
	public static final double GRIDSCALE=1.0/55.0;
	
	//center at 0,0,0,0
	//scale at min(h,w)/12
	void centerAndFitGrid(){
	  System.out.println("center and fit for grid");
	  initTransform();
	  //scale
	  Rectangle2D vb=getBounds();
	  double scale=Math.min(vb.getWidth(),vb.getHeight())*GRIDSCALE;
	  transform.scale(scale,scale);
	  //center
    double 
      cxoff=(vb.getWidth()/scale)/2,
      cyoff=(vb.getHeight()/scale)/-2;
    transform.translate(cxoff,cyoff);
    //
	  updateImage();}
	
  /*
   * ++++++++++++++++++++++++++++++++
   * MOUSE OPS
   * ++++++++++++++++++++++++++++++++
   */
  
	 //drag mode
  static final int 
    DRAGMODE_NONE=0,
    DRAGMODE_PAN=1,
    DRAGMODE_SCALE=2;
  
  private int dragmode=DRAGMODE_NONE;
  private java.awt.Point 
    pmousedown,
    pmouseup;
	
  /*
   * --------------------------------
   * CLASS MouseListener0 START
   * click to select a shape and deselect any prior selection
   * shift-leftclick to add clicked shape to the present selection
   * ctrl-leftclick-drag to pan
   * shift-rightclick-drag to zoom
   */
  class MouseListener0 extends MouseAdapter{
    
    public void mousePressed(MouseEvent e){
      requestFocus();
      e.consume();
      pmousedown=new Point(e.getX(),e.getY());
//      printInfoAtMousedown(mouseDownPoint);
      boolean shift=e.isShiftDown();
      boolean ctrl=e.isControlDown();
      int button=e.getButton();
      if(button==MouseEvent.BUTTON1){
      	//LEFTCLICK + SHIFT
      	if(shift){
      		dragmode=DRAGMODE_PAN;
      	//LEFTCLICK + CTRL
      	}else if(ctrl){
      		dragmode=DRAGMODE_SCALE;}}}
  
    public void mouseReleased(MouseEvent e){
    	pmouseup=e.getPoint();
      if(dragmode!=DRAGMODE_NONE)doDragOp();}
    
  }
  /*
   * CLASS MouseListener0 END
   * --------------------------------
   */
  
  private void doDragOp(){
  	try{
  	double dx=pmouseup.x-pmousedown.x;
  	double dy=pmouseup.y-pmousedown.y;
    if(dragmode==DRAGMODE_PAN){
    	translateByMouse(dx,dy);
    }else{//dragMode==DRAGMODE_SCALE
    	scaleByMouse(dx,dy);}
    dragmode=DRAGMODE_NONE;
  	}catch(Exception e){
  		e.printStackTrace();}}
  
  private void translateByMouse(double dx,double dy){
    System.out.println("translate by mouse");
    double scale=transform.getScaleX();
    translate(dx/scale,dy/scale);}
  
  /*
   * positive drag (downwards) = zoom in, negative drag (upwards) = zoom out
   * full positive drag doubles scale
   * full negative drag halves scale
   */
  private void scaleByMouse(double dx,double dy){
    System.out.println("scale by mouse");
  	double ds;
  	double dragmagnitude=Math.abs(dy)/(double)getHeight();
  	if(dy>0){
  		ds=dragmagnitude+1.0;
  	}else{
  		ds=1.0-(dragmagnitude*0.5);}
  	System.out.println("ds="+ds);
  	scale(ds);}
  
  /*
   * ################
   * PRINT BUBBLE INFO AT MOUSEDOWN
   * ################
   */
  
//  private void printInfoAtMousedown(Point mouseDownPoint){
//    System.out.println("X="+mouseDownPoint.x*scale+" Y="+mouseDownPoint.y*scale);
//    
//    
//  }
  
  /*
   * ################################
   * RENDERERS
   * ################################
   */
  
  Renderer[] renderers=null;
  
  public void setRenderers(Renderer[] r){
    renderers=r;
    for(Renderer a:r)
      a.setViewer(this);}
  
  /*
   * ################################
   * IMAGE
   * ################################
   */
  
  BufferedImage image=null;
  
  void updateImage(){
    new RenderThread().start();
    repaint();}
  
  class RenderThread extends Thread{
    public void start(){
      int w=getWidth(),h=getHeight();
      image=new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
      Graphics2D g=image.createGraphics();
      g.setPaint(new Color(128,128,128));
      g.fillRect(0,0,w,h);
        for(int i=0;i<renderers.length;i++)
          g.drawImage(renderers[i].render(),null,null);}}
  
  public void paint(Graphics g){
    super.paint(g);
    if(image!=null){
      System.out.println("painting image w="+image.getWidth()+" h="+image.getHeight());
      ((Graphics2D)g).drawImage(image,null,null);}}

}
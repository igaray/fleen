package org.fleen.samples.testLoopingTunnelFlowVFrameSequence;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import org.fleen.core.diamondGrammar.Bubble;
import org.fleen.core.diamondGrammar.BubbleModel;
import org.fleen.core.diamondGrammar.DVertex;
import org.fleen.core.diamondGrammar.Foam;
import org.fleen.core.diamondGrammar.Grammar;
import org.fleen.core.diamondGrammar.Grid;
import org.fleen.core.diamondGrammar.Jig;
import org.fleen.core.diamondGrammar.Math2D;
import org.fleen.samples.loopingTunnelFlowVFrameSequence.LoopingTunnelFlowVFrameBlock_Abstract;

/*
 * a frameblock that draws it's form from the TF0000 grammar
 */
public class LoopingTunnelFlowVFrameBlock_TF0000_Short extends LoopingTunnelFlowVFrameBlock_Abstract{
  
  /*
   * ################################
   * FIELDS
   * ################################
   */
  
  private static final long serialVersionUID=1914441161062032826L;
  
  protected static final String 
    IDHEXAGON="FC_hexagon",
    IDTRIANGLE="FC_triangle",
    IDSTAR="FC_star";

  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  LoopingTunnelFlowVFrameBlock_TF0000_Short(double smallestdetail){
    super(smallestdetail);}
  
  /*
   * ################################
   * GEOM
   * ################################
   */
  
  /*
   * we handle FC_hexagon,FC_triangle and FC_star, from the TF0000 grammar
   */
  protected double getIncircleRadius(Bubble bubble){
    double radius;
    double[][] vp=bubble.getVertexPoints2D();
    if(bubble.model.id.equals(IDSTAR)){
      radius=Math2D.getDistance_2Points(0,0,vp[0][0],vp[0][1]);
    }else{//model id == "FC_hexagon" or "FC_triangle"
      double[] mp=Math2D.getPoint_Mid2Points(vp[0][0],vp[0][1],vp[1][0],vp[1][1]);
      radius=Math2D.getDistance_2Points(0,0,mp[0],mp[1]);}
    return radius;}
  
  /*
   * ################################
   * INIT ROOT AND CORE
   * hex star hex
   * ################################
   */
  
  protected void initRootAndCore(){
    Grid rg=new Grid();
    setRootGrid(rg);
    BubbleModel rootbubblemodel=grammar.getBubbleModel(IDHEXAGON);
    DVertex 
      v0=new DVertex(-1,-1,0,4),
      v1=new DVertex(0,0,0,2);
    Bubble rootbubble=new Bubble(
      rg,
      rootbubblemodel,
      Bubble.TYPE_RAFT,
      v0,
      v1,
      true,
      new Foam(),
      0);
    //
    Jig j=grammar.getJig(IDHEXAGON,IDSTAR);
    mouthbubble=j.create(rootbubble).get(0);
    j=grammar.getJig(IDSTAR,IDHEXAGON);
    mouthbubble=j.create(mouthbubble).get(0);
    mouthbubble.capBranch();}
  
  /*
   * ################################
   * GRAMMAR
   * ################################
   */
  
  private static final String GRAMMARPATH=
    "/home/john/projects/FleenCore_2.0/src/org/fleen/loopingTunnelFlowVFrameSequence/test/tunnelflowframeblock0000.grammar";
  
  protected Grammar getGrammar(){
    File selectedfile=new File(GRAMMARPATH);
    FileInputStream fis;
    ObjectInputStream ois;
    Grammar g=null;
    try{
      fis=new FileInputStream(selectedfile);
      ois=new ObjectInputStream(fis);
      g=(Grammar)ois.readObject();
      ois.close();
    }catch(Exception e){
      System.out.println("#^#^# EXCEPTION IN LOAD GRAMMAR #^#^#");
      e.printStackTrace();}
    return g;}

}

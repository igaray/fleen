package org.fleen.samples.genLoopingTunnelFlowFrameSeqImage;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import org.fleen.core.bubbleTree.Foam;
import org.fleen.core.bubbleTree.Grid;
import org.fleen.core.g2D.G2D;
import org.fleen.core.gKis.KVertex;
import org.fleen.core.pGrammatic.BubbleModel;
import org.fleen.core.pGrammatic.GBubble;
import org.fleen.core.pGrammatic.Grammar;
import org.fleen.core.pGrammatic.Jig;
import org.fleen.samples.loopingTunnelFlowVFrameSequence.LoopingTunnelFlowVFrameBlock_Abstract;

/*
 * a frameblock that draws it's form from the TF0000 grammar
 */
public class LoopingTunnelFlowVFrameBlock_TF0000_Tight extends LoopingTunnelFlowVFrameBlock_Abstract{
  
  /*
   * ################################
   * FIELDS
   * ################################
   */
  
  protected static final String 
    IDHEXAGON="FC_hexagon",
    IDTRIANGLE="FC_triangle",
    IDSTAR="FC_star";

  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  LoopingTunnelFlowVFrameBlock_TF0000_Tight(double detailfloor){
    super(detailfloor);}
  
  /*
   * ################################
   * GEOM
   * ################################
   */
  
  /*
   * we handle FC_hexagon,FC_triangle and FC_star, from the TF0000 grammar
   */
  protected double getIncircleRadius(GBubble bubble){
    double radius;
    double[][] vp=bubble.getVertexPoints2D();
    if(bubble.model.id.equals(IDSTAR)){
      radius=G2D.getDistance_2Points(0,0,vp[0][0],vp[0][1]);
    }else{//model id == "FC_hexagon" or "FC_triangle"
      double[] mp=G2D.getPoint_Mid2Points(vp[0][0],vp[0][1],vp[1][0],vp[1][1]);
      radius=G2D.getDistance_2Points(0,0,mp[0],mp[1]);}
    return radius;}
  
  /*
   * ################################
   * INIT ROOT AND CORE
   * ################################
   */
  
  /*
   * the root is the standard root
   * the root bubble is a hexagon, as is the exit bubble
   * in between we use some other shape types.
   * we do it like this because unless the core bubble shape selection is not handled carefully
   * the root and exit bubbles can get misaligned, and because excessive chaos is just wasted effort.
   * 
   * specific forms implemented in subclasses
   */
  /*
   * ################################
   * INIT ROOT AND CORE
   * TODO
   * craft a nicer series of corebubbles
   * This series was just slapped together out of the old frameblocks. 
   * It works but more thought should be given to a final product.
   * ################################
   */
  
  protected void initRootAndCore(){
    Grid rg=new Grid();
    setRootGrid(rg);
    BubbleModel rootbubblemodel=grammar.getBubbleModel(IDHEXAGON);
    KVertex 
      v0=new KVertex(-1,-1,0,4),
      v1=new KVertex(0,0,0,2);
    GBubble rootbubble=new GBubble(
      rg,
      rootbubblemodel,
      GBubble.TYPE_RAFT,
      v0,
      v1,
      true,
      new Foam(),
      0);
    //
    Jig j=grammar.getJig(IDHEXAGON,IDSTAR);
    mouthbubble=j.create(rootbubble).get(0);
    j=grammar.getJig(IDSTAR,IDTRIANGLE);
    mouthbubble=j.create(mouthbubble).get(0);
    j=grammar.getJig(IDTRIANGLE,IDHEXAGON);
    mouthbubble=j.create(mouthbubble).get(0);
    j=grammar.getJig(IDHEXAGON,IDTRIANGLE);
    mouthbubble=j.create(mouthbubble).get(0);
    j=grammar.getJig(IDTRIANGLE,IDHEXAGON);
    mouthbubble=j.create(mouthbubble).get(0);
    j=grammar.getJig(IDHEXAGON,IDHEXAGON);
    mouthbubble=j.create(mouthbubble).get(0);
    mouthbubble.capBranch();}
  
  /*
   * ################################
   * GRAMMAR
   * ################################
   */
  
  private static final String GRAMMARPATH=
    "/home/john/projects/FleenCore_2.0/src/org/fleen/genLoopingTunnelFlowFrameSeqImage/tunnelflowframeblock0000.grammar";
  
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

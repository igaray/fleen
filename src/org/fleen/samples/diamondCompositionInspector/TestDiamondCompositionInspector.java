package org.fleen.samples.diamondCompositionInspector;

import org.fleen.core.diamondGrammar.DGComposition;
import org.fleen.core.diamondGrammar.MathDiamond;
import org.fleen.samples.diamondCompositionInspector.faceView.Renderer;
import org.fleen.samples.diamondCompositionInspector.faceView.Renderer_Bubble1;

/*
 * create a diamond composition and inspect it in detail
 * TODO 
 * load a serialized fleen too
 * a nice export function
 * 
 */
public class TestDiamondCompositionInspector{
  
  static final int WIDTH=128,HEIGHT=64;
  
  static UI ui;
  static DGComposition fleen;
  
  public static final void main(String[] a){
    initUI();
    System.out.println("FLEEN CORE TEST INITIALIZED");}
  
  static void initUI(){
    ui=new UI();
    ui.getFaceview().setRenderers(getRenderers());}
  
  static Renderer[] getRenderers(){
    Renderer[] r={
//      new Renderer_Grid0(),
      new Renderer_Bubble1(),
    };
    return r;}
  
  static void create(){
    System.out.println("###create###");
    fleen=new SampleDiamondComposition();
    ui.getFaceview().init(fleen);
    
    ui.getFaceview().centerAndFit();
    
//    ui.getTreeview().setModel(new DefaultTreeModel(fleen.getRootGrid()));
    
//    Slat_1x1_Splitter_0000 s0=new Slat_1x1_Splitter_0000();
//    Slat_1x1 slat=(Slat_1x1)fleen.getDiamond().root.childbubbles.get(0);
//    s0.diff(fleen.getDiamond(),slat);
    
    
//    ui.getViewer().centerAndFit();
    
    }
  
  static void export(){
    System.out.println("###export###");
    
  }
  
  static void test0(){
    System.out.println("###test0###");
    
    
    
    MathDiamond.TEST_getVertex_VertexVector();
    
    
  }
  
  static void test1(){
    System.out.println("###test1###");
//    
//    int[] v0={-3,-3,0,1};
//    int[] t={2,3};
//    
//    System.out.println("v0 : "+v0[0]+","+v0[1]+","+v0[2]+","+v0[3]);
//    System.out.println("vector dir:"+t[0]+" dis:"+t[1]);
//    //
//    System.out.println("---------------");
//    System.out.println("STANDARD PROCESS");
//    int[] v1a=new int[4];
//    MathDiamond.getVertex_VertexVector(v0[0],v0[1],v0[2],v0[3],t[0],t[1],v1a);
//    System.out.println("v1a : "+v1a[0]+","+v1a[1]+","+v1a[2]+","+v1a[3]);
//    //
//    System.out.println("---------------");
//    System.out.println("ADJ PROCESS");
//    int[] v1b=new int[4];
//    MathDiamond.getVertex_VertexVector_AdjProcessForTest(v0[0],v0[1],v0[2],v0[3],t[0],t[1],v1b);
//    System.out.println("v1b : "+v1b[0]+","+v1b[1]+","+v1b[2]+","+v1b[3]);
  }
  
  static void test2(){
    System.out.println("###test2###");
    
  }
  
  

}

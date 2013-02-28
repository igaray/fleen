package org.fleen.samples.diamondCompositionInspector;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.fleen.core.diamondGrammar.Bubble;
import org.fleen.core.diamondGrammar.BubbleModel;
import org.fleen.core.diamondGrammar.BubbleSignature;
import org.fleen.core.diamondGrammar.DGComposition;
import org.fleen.core.diamondGrammar.Grammar;
import org.fleen.core.diamondGrammar.Grid;
import org.fleen.core.diamondGrammar.Jig;
import org.fleen.core.spring.SP_Basic;
import org.fleen.core.spring.Spring;

public class SampleDiamondComposition extends DGComposition{
  
  SampleDiamondComposition(){
    brain=new SP_Basic(System.currentTimeMillis());
    init();}

  Spring brain;
  
  public Spring getSpring(){
    return brain;}

  private void init(){
    Grid grid=new Grid();
    setRootGrid(grid);
    Grammar grammar=importGrammar();
    BubbleModel bubblemodel=grammar.getBubbleModel("bm_0");
    Bubble b=new Bubble(
      grid,
      bubblemodel);
    List<Bubble> newleafbubbles=new ArrayList<Bubble>();
    newleafbubbles.add(b);
    while(!newleafbubbles.isEmpty())
      doDSLimitedChorussedRandomJigSelectionCultivationCycle(newleafbubbles,grammar,0.1);
    System.out.println("BUBBLECOUNT : "+getBubbles().size());
    System.out.println("LEAFBUBBLECOUNT : "+getLeaves().size());
    }
  
  //returns true if cultivation happened
  private void doDSLimitedChorussedRandomJigSelectionCultivationCycle(
    List<Bubble> leafbubbles,Grammar grammar,double dslimit){
    System.out.println("START doDSLimitedChorussedRandomJigSelectionCultivationCycle");
    Jig j;
    Random random=new Random();
    Map<BubbleSignature,Jig> sigjigs=new Hashtable<BubbleSignature,Jig>();
    double ds;
    List<Bubble> newbubbles=new ArrayList<Bubble>();
    for(Bubble bubble:leafbubbles){
      System.out.println("BUBBLE : "+bubble);
      ds=bubble.getDetailSize();
      if(ds>dslimit){
        System.out.println("get jig");
        j=getJig(bubble,grammar,sigjigs,random);
        if(j!=null)
          newbubbles.addAll(j.create(bubble));}}
    leafbubbles.clear();
    leafbubbles.addAll(newbubbles);
    System.out.println("END doDSLimitedChorussedRandomJigSelectionCultivationCycle");}
  
//  private Jig getJig(Bubble bubble,Grammar grammar,Map<BubbleSignature,Jig> sigjigs,Random random){
//    List<Jig> jigs;
//    Jig j=sigjigs.get(bubble.getSignature());
//    if(j==null){
//      jigs=grammar.getJigs(bubble.model.id);
//      if(jigs.isEmpty())return null;
//      j=jigs.get(random.nextInt(jigs.size()));
//      sigjigs.put(bubble.getSignature(),j);}
//    return j;}
  
  private Jig getJig(Bubble bubble,Grammar grammar,Map<BubbleSignature,Jig> sigjigs,Random random){
    Jig j=sigjigs.get(bubble.getSignature());
    if(j==null){
      if(doABoiler(bubble)){
        j=getBoilerJig(bubble,grammar,random);
        if(j==null)j=getSplitterJig(bubble,grammar,random);
      }else{
        j=getSplitterJig(bubble,grammar,random);
        if(j==null)j=getBoilerJig(bubble,grammar,random);}
      if(j==null)return null;
      sigjigs.put(bubble.getSignature(),j);}
    return j;}
  
  private boolean doABoiler(Bubble b){
    int cl=b.getRaftLevel();
    if(cl==0){
      return false;
    }else if(cl==1){
      return new Random().nextBoolean();
    }else{//>=2
      return true;}}
  
  private Jig getBoilerJig(Bubble bubble,Grammar grammar,Random random){
    List<Jig> jigs=grammar.getJigs(bubble.model.id);
    if(jigs.isEmpty())return null;
    Iterator<Jig> i=jigs.iterator();
    Jig j;
    while(i.hasNext()){
      j=i.next();
      if(j.type==Jig.TYPE_SPLITTER)i.remove();}
    if(jigs.isEmpty())return null;
    j=jigs.get(random.nextInt(jigs.size()));
    return j;}
  
  private Jig getSplitterJig(Bubble bubble,Grammar grammar,Random random){
    List<Jig> jigs=grammar.getJigs(bubble.model.id);
    if(jigs.isEmpty())return null;
    Iterator<Jig> i=jigs.iterator();
    Jig j;
    while(i.hasNext()){
      j=i.next();
      if(j.type==Jig.TYPE_BOILER)i.remove();}
    if(jigs.isEmpty())return null;
    j=jigs.get(random.nextInt(jigs.size()));
    return j;}
  
  private Grammar importGrammar(){
    File selectedfile=new File("/home/john/projects/FleenCore_2.0/src/org/fleen/grammars/simplesymmetric.g");
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

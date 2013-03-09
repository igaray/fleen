package org.fleen.samples.staticCompositionGen;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
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

public class DGC0 extends DGComposition{
  
  DGC0(double detaillimit){
    this.detaillimit=detaillimit;
    init();}

  double detaillimit;

  private void init(){
    Grid grid=new Grid();
    setRootGrid(grid);
    Grammar grammar=importGrammar();
    BubbleModel bubblemodel=grammar.getBubbleModels().get(0);
    new Bubble(
      grid,
      bubblemodel);
    boolean cultivationhappened=true;
    while(cultivationhappened)
      cultivationhappened=doDSLimitedChorussedRandomJigSelectionCultivationCycle(grammar,detaillimit);}
  
  //returns true if cultivation happened
  private boolean doDSLimitedChorussedRandomJigSelectionCultivationCycle(Grammar grammar,double dslimit){
    boolean cultivationhappened=false;
    Jig j;
    Random random=new Random();
    Map<BubbleSignature,Jig> sigjigs=new Hashtable<BubbleSignature,Jig>();
    for(Bubble bubble:getLeaves()){
      if(bubble.getDetailSize()>dslimit){
        j=getJig(bubble,grammar,sigjigs,random);
        if(j!=null){
          cultivationhappened=true;
          j.create(bubble);}}}
    return cultivationhappened;}
  
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
    File selectedfile=new File("/home/john/projects/FleenCore_2.0/src/org/fleen/grammars/nicecomposition.g");
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

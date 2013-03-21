package org.fleen.samples.fleenRasterCompositionGen;

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
import org.fleen.samples.fleenRasterCompositionGen.symmetryController.SymmetryControlFunction_Abstract;

public class Composition extends DGComposition{
  
  double detaillimit;
  SymmetryControlFunction_Abstract scf;
  Grammar grammar;
  
  public Composition(){
    detaillimit=FRCG.instance.config.getDetailSizeLimit();
    scf=FRCG.instance.config.getSymmetryControlFunction();
    grammar=FRCG.instance.config.getGrammar();
    build();}

  private void build(){
    Grid grid=new Grid();
    setRootGrid(grid);
    BubbleModel rootbubblemodel=FRCG.instance.config.getRootBubbleModel();
    Bubble rootbubble=new Bubble(
      grid,
      rootbubblemodel);
    scf.mindetailsize=detaillimit;
    scf.maxdetailsize=rootbubble.getDetailSize();
    boolean cultivationhappened=true;
    while(cultivationhappened){
      Log.m1("[cultivating]");
      cultivationhappened=cultivate();}}
  
  //DETAIL SIZE LIMITED CHORUSSED RANDOM JIG SELECTION CULTIVATION CYCLE
  //returns true if cultivation happened
  private boolean cultivate(){
    boolean cultivationhappened=false;
    Jig j;
    Random random=new Random();
    Map<BubbleSignature,Jig> sigjigs=new Hashtable<BubbleSignature,Jig>();
    int bcount=0;
    for(Bubble bubble:getLeaves()){
      //progress feed
      bcount++;
      if(bcount%4096==0)
        Log.m1(".");
      //
      if(bubble.getDetailSize()>detaillimit){
        j=getJig(bubble,grammar,sigjigs,random);
        if(j!=null){
          cultivationhappened=true;
          j.create(bubble);}}}
    return cultivationhappened;}

  private Jig getJig(Bubble bubble,Grammar grammar,Map<BubbleSignature,Jig> sigjigs,Random random){
    Jig jig=null;
    //if the symmetry control function says to do symmetry then attempt symmetry
    if(scf.doSymmetry(bubble)){
      //check the table to see if a bubble with this bubble's signature has already been addressed
      //if so then we get a copy of the jig that was picked for that foregone bubble. Thus symmetric treatment.
      jig=sigjigs.get(bubble.getSignature());}
    //if we got a jig then we're done, otherwise get one this other way
    if(jig==null){
      if(doABoiler(bubble)){
        jig=getBoilerJig(bubble,grammar,random);
        if(jig==null)jig=getSplitterJig(bubble,grammar,random);
      }else{
        jig=getSplitterJig(bubble,grammar,random);
        if(jig==null)jig=getBoilerJig(bubble,grammar,random);}
      if(jig==null)return null;
      sigjigs.put(bubble.getSignature(),jig);}
    return jig;}
  
  //our little logic for determining whether to do a boiler or a splitter. This could be a param of course.
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
  
}

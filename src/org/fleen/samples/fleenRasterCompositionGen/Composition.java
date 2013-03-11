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

public class Composition extends DGComposition{
  
  public Composition(){
    detaillimit=FRCG.instance.params.getDetailSizeLimit();
    init();}

  double detaillimit;

  private void init(){
    Grid grid=new Grid();
    setRootGrid(grid);
    Grammar grammar=FRCG.instance.params.getGrammar();
    BubbleModel bubblemodel=FRCG.instance.params.getRootBubbleModel();
    new Bubble(
      grid,
      bubblemodel);
    boolean cultivationhappened=true;
    while(cultivationhappened){
      FRCG.instance.postMessage("cultivating");
      cultivationhappened=doDSLimitedChorussedRandomJigSelectionCultivationCycle(grammar,detaillimit);}}
  
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
  
}

package org.fleen.core.diamondGrammar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/*
 * create child grid for target bubble
 *   origin is arbitrary vertex on target bubble polygon specified by originindex
 *   foreward is target bubble foreward + forewardoffset
 *   fish is parentbubble.basicinterval*fishscale 
 *
 * create child bubbles for that child grid
 *   for each prospective child bubble specify
 *     bubblemodel
 *     v0 and v1
 *     twist
 *  
 * create parentgrid-childbubble associations
 *   obvious
 * 
 * create foam-bubble associations
 *   create a table foams keyed by integer. 
 *   0 is parentbubble foam
 *   create new foams for arbitrary indices as necessary
 *   assign foams to children, specify by index
 *  
 * set chorus indices
 *   arbitrary integers
 *   
 */
public class Jig implements Serializable{
  
  /*
   * ################################
   * FIELDS
   * ################################
   */
  
  private static final long serialVersionUID=-4123490306018406400L;
  
  public Grammar grammar;
  //this Jig's unique id within it's Grammar
  public String id;
  //the unique id of the BubbleModel with which this Jig is compatible
  public String targetbubblemodelid;
  //we have 2 types presently
  //BOILER and SPLITTER. We might have CHIPPER later.
  public static final int 
    TYPE_UNDEFINED=-1,
    TYPE_SPLITTER=0,
    TYPE_BOILER=1;
  public int type=TYPE_UNDEFINED;
  //defines the grid's fish value. It's factored by parentbubble.fish to get local fish.
  //this gives us a local grid with custom scale. 
  //The new grid fits into the parent shape, or rather the parent shape fits onto the new grid, nicely - and
  //we get freedom of scale and, because the diamond grid is irregular, a nice range of geometry options
//  public double gridfishfactor;
  //definitions for each bubble
  public List<JigBubbleDef> bubbledefs=new ArrayList<JigBubbleDef>();
  
  /*
   * ################################
   * CONSTRUCTORS
   * ################################
   */
  
  public Jig(Grammar grammar,String id,String bubblemodelid,
    int gridscale,List<JigBubbleDef> bubbledefs){
    this.grammar=grammar;
    this.id=id;
    this.targetbubblemodelid=bubblemodelid;
    this.griddensity=gridscale;
    this.bubbledefs=bubbledefs;}
  
  public Jig(){}
  
  /*
   * ################################
   * GRIDDENSITY AND FISHFACTOR
   * Grid density is the density of this jig's grid relative to that of it's parent grid (this.parentbubble.parentgrid)
   * Fish factore is the reciprocal of grid density. It is the value of fish (the fundamental 
   * grid unit of length) for this jig's grid in terms of the parentbubble's value for fish (derived from baselength and
   * dis(v0,v1).
   * ################################
   */
  
  public int griddensity=1;
  
  public double getFishFactor(){
    return 1.0/(double)griddensity;}
  
  public int getGridDensity(){
    return griddensity;}
  
  /*
   * ################################
   * BUBBLE CREATION PROCESS
   * ################################
   */
  
  public List<Bubble> create(Bubble target){
    if(type==TYPE_SPLITTER){
      return createForSplitter(target);
    }else if(type==TYPE_BOILER){
      return createForBoiler(target);
    }else{
      throw new IllegalArgumentException("JIG TYPE UNDEFINED");}}
  
  private List<Bubble> createForSplitter(Bubble target){
    //define grid
    Grid grid=new Grid(
      target,
      getFishFactor());
    //create bubbles from bubbledefs
    List<Bubble> newbubbles=new ArrayList<Bubble>();
    for(JigBubbleDef bd:bubbledefs){
      newbubbles.add(new Bubble(
        grid,
        grammar.getBubbleModel(bd.bubblemodelid),
        Bubble.TYPE_SHARD,
        bd.v0,
        bd.v1,
        bd.twist,
        target.foam,
        bd.chorusindex));}
    return newbubbles;}
  
  private List<Bubble> createForBoiler(Bubble target){
    //define grid
    Grid grid=new Grid(
      target,
      getFishFactor());
    //define bubbles
    Map<Integer,Foam> foams=new Hashtable<Integer,Foam>();
    //put index0 foam in the foams table
    foams.put(0,target.foam);
    //create bubbles from bubbledefs
    Foam foam;
    List<Bubble> newbubbles=new ArrayList<Bubble>();
    int btype;
    for(JigBubbleDef bd:bubbledefs){
      //get foam from foams table by foamindex
      //if foamindex==0 then we use the index0 foam : the target bubble's foam
      //if the foam's index is >0 then 
      //  we create it (if we haven't already).
      //  it is a child of the index0 foam
      foam=foams.get(bd.foamindex);
      if(foam==null){
        foam=new Foam();
        foams.put(bd.foamindex,foam);
        //assigning lake and raft
        target.foam.children.add(foam);
        foam.parent=target.foam;}
      //
      if(foam.equals(target.foam)){
        btype=Bubble.TYPE_SHARD;
      }else{
        btype=Bubble.TYPE_RAFT;}
      newbubbles.add(new Bubble(
        grid,
        grammar.getBubbleModel(bd.bubblemodelid),
        btype,
        bd.v0,
        bd.v1,
        bd.twist,
        foam,
        bd.chorusindex));}
    return newbubbles;}

}

package org.fleen.samples.diamondCompositionInspector;

import org.fleen.core.diamondGrammar.Bubble;


public class Util{
  
  public static final BubbleGeometryDataCache getBubbleData(Bubble b){
    if(b.data==null)
      b.data=new BubbleGeometryDataCache(b);
    return (BubbleGeometryDataCache)b.data;}

}

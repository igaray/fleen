package org.fleen.samples.loopingTunnelFlowVFrameSequence;

import org.fleen.core.grammar.GBubble;

public class VFrameCoreBubbleData{
  
  GBubble nextframecorebubble;
  double inradius;
  int index;
  
  VFrameCoreBubbleData(GBubble nextframecorebubble,double inradius,int index){
    this.nextframecorebubble=nextframecorebubble;
    this.inradius=inradius;
    this.index=index;
  }
  
}

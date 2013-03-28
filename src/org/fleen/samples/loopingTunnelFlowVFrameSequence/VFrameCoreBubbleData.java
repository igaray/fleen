package org.fleen.samples.loopingTunnelFlowVFrameSequence;

import org.fleen.core.grammar.Bubble;

public class VFrameCoreBubbleData{
  
  Bubble nextframecorebubble;
  double inradius;
  int index;
  
  VFrameCoreBubbleData(Bubble nextframecorebubble,double inradius,int index){
    this.nextframecorebubble=nextframecorebubble;
    this.inradius=inradius;
    this.index=index;
  }
  
}

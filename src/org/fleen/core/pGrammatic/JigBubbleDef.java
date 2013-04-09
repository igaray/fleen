package org.fleen.core.pGrammatic;

import java.io.Serializable;

import org.fleen.core.gKis.KVertex;

public class JigBubbleDef implements Serializable{
  
  private static final long serialVersionUID=-7934141313458833281L;
  
  public String bubblemodelid;
  public KVertex v0,v1;
  public boolean twist;
  public int foamindex;
  public int chorusindex;
  
  public JigBubbleDef(String bubblemodelid,KVertex v0,KVertex v1,
    boolean twist,int foamindex,int chorusindex){
    this.bubblemodelid=bubblemodelid;
    this.v0=v0;
    this.v1=v1;
    this.twist=twist;
    this.foamindex=foamindex;
    this.chorusindex=chorusindex;}
  
  public JigBubbleDef(){}
  

}




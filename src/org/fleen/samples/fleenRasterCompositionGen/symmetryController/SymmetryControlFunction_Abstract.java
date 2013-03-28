package org.fleen.samples.fleenRasterCompositionGen.symmetryController;

import java.io.Serializable;

import org.fleen.core.grammar.Bubble;

public abstract class SymmetryControlFunction_Abstract implements Serializable{
  
  private static final long serialVersionUID=-8976250155636506474L;
  
  public double maxdetailsize,mindetailsize;
  
  public abstract boolean doSymmetry(Bubble bubble);

  public String toString(){
    return getClass().getSimpleName();}

}

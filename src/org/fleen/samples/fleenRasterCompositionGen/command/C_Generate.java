package org.fleen.samples.fleenRasterCompositionGen.command;

import org.fleen.samples.fleenRasterCompositionGen.Composition;
import org.fleen.samples.fleenRasterCompositionGen.FRCG;

public class C_Generate implements Command{

  public void execute(){
    FRCG.instance.composition=new Composition();
    CQ.renderForViewer();}

  public String getDescription(){
    return "Generate";}

}

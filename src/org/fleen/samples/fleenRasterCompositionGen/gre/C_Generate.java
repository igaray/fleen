package org.fleen.samples.fleenRasterCompositionGen.gre;

import org.fleen.samples.fleenRasterCompositionGen.Composition;
import org.fleen.samples.fleenRasterCompositionGen.FRCG;

public class C_Generate extends GRECommand_Abstract{

  protected void run(){
    FRCG.instance.composition=new Composition();
    GRECommandManager.renderForViewer();}

  protected String getDescription(){
    return "Generate";}

}

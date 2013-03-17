package org.fleen.samples.fleenRasterCompositionGen.command;

import org.fleen.samples.fleenRasterCompositionGen.Composition;
import org.fleen.samples.fleenRasterCompositionGen.FRCG;

public class C_Generate extends Command_Abstract{

  protected void run(){
    FRCG.instance.composition=new Composition();
    Command.renderForViewer();}

  protected String getDescription(){
    return "Generate";}

}

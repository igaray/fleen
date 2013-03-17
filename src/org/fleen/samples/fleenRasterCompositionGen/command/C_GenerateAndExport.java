package org.fleen.samples.fleenRasterCompositionGen.command;

import org.fleen.samples.fleenRasterCompositionGen.FRCG;

public class C_GenerateAndExport extends Command_Abstract{

  protected void run(){
    int c=FRCG.instance.config.getGenExpImageCount();
    for(int i=0;i<c;i++){
      Command.generate();
      Command.renderForViewer();
      Command.renderForExport();
      Command.export();}}

  protected String getDescription(){
    return "Generate and export : "+FRCG.instance.config.getGenExpImageCount();}

}

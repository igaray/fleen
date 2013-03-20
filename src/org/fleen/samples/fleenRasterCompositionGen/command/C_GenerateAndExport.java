package org.fleen.samples.fleenRasterCompositionGen.command;

import org.fleen.samples.fleenRasterCompositionGen.FRCG;

public class C_GenerateAndExport implements Command{

  public void execute(){
    int c=FRCG.instance.config.getGenExpImageCount();
    for(int i=0;i<c;i++){
      CQ.generate();
      CQ.renderForViewer();
      CQ.renderForExport();
      CQ.export();}}

  public String getDescription(){
    return "generating and exporting : "+FRCG.instance.config.getGenExpImageCount();}

}

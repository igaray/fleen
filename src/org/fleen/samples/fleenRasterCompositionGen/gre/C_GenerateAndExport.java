package org.fleen.samples.fleenRasterCompositionGen.gre;

import org.fleen.samples.fleenRasterCompositionGen.FRCG;

public class C_GenerateAndExport extends GRECommand_Abstract{

  protected void run(){
    int c=FRCG.instance.params.getGenExpImageCount();
    for(int i=0;i<c;i++){
      GRECommandManager.generate();
      GRECommandManager.renderForViewer();
      GRECommandManager.renderForExport();
      GRECommandManager.export();}}

  protected String getDescription(){
    return "Generate and export : "+FRCG.instance.params.getGenExpImageCount();}

}

package org.fleen.samples.fleenRasterCompositionGen.command;

import org.fleen.samples.fleenRasterCompositionGen.Exporter;
import org.fleen.samples.fleenRasterCompositionGen.FRCG;

public class C_Export implements Command{

  public void execute(){
    new Exporter().export(FRCG.instance.exportimage,FRCG.instance.config.getExportDir());}

  public String getDescription(){
    return "Export";}

}

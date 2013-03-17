package org.fleen.samples.fleenRasterCompositionGen.command;

import org.fleen.samples.fleenRasterCompositionGen.Exporter;
import org.fleen.samples.fleenRasterCompositionGen.FRCG;

public class C_Export extends Command_Abstract{

  protected void run(){
    new Exporter().export(FRCG.instance.exportimage,FRCG.instance.config.getExportDir());}

  protected String getDescription(){
    return "Export";}

}

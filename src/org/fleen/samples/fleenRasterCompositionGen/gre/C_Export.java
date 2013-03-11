package org.fleen.samples.fleenRasterCompositionGen.gre;

import org.fleen.samples.fleenRasterCompositionGen.Exporter;
import org.fleen.samples.fleenRasterCompositionGen.FRCG;

public class C_Export extends GRECommand_Abstract{

  protected void run(){
    new Exporter().export(FRCG.instance.exportimage,FRCG.instance.params.getExportDir());}

  protected String getDescription(){
    return "Export";}

}

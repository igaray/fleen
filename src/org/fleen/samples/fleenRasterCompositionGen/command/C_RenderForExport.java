package org.fleen.samples.fleenRasterCompositionGen.command;

import org.fleen.samples.fleenRasterCompositionGen.FRCG;
import org.fleen.samples.fleenRasterCompositionGen.renderer.Renderer_Abstract;

public class C_RenderForExport extends Command_Abstract{

  protected void run(){
    Renderer_Abstract r=FRCG.instance.config.getRenderer();
    FRCG.instance.exportimage=
      r.renderForExport(FRCG.instance.composition,FRCG.instance.config.getExportImageScale());}

  protected String getDescription(){
    return "Render for export";}

}

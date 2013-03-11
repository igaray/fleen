package org.fleen.samples.fleenRasterCompositionGen.gre;

import org.fleen.samples.fleenRasterCompositionGen.FRCG;
import org.fleen.samples.fleenRasterCompositionGen.renderer.Renderer_Abstract;

public class C_RenderForExport extends GRECommand_Abstract{

  protected void run(){
    Renderer_Abstract r=FRCG.instance.params.getRenderer();
    FRCG.instance.exportimage=
      r.renderForExport(FRCG.instance.composition,FRCG.instance.params.getExportImageScale());}

  protected String getDescription(){
    return "Render for export";}

}

package org.fleen.samples.fleenRasterCompositionGen.gre;

import org.fleen.samples.fleenRasterCompositionGen.FRCG;
import org.fleen.samples.fleenRasterCompositionGen.renderer.Renderer_Abstract;

public class C_RenderForViewer extends GRECommand_Abstract{

  protected void run(){
    Renderer_Abstract r=FRCG.instance.params.getRenderer();
    FRCG.instance.viewerimage=
      r.renderForViewer(FRCG.instance.composition,FRCG.instance.ui.panViewer);
    FRCG.instance.ui.panViewer.update();}

  protected String getDescription(){
    return "Render for viewer";}

}

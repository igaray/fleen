package org.fleen.samples.fleenRasterCompositionGen.command;

import org.fleen.samples.fleenRasterCompositionGen.FRCG;
import org.fleen.samples.fleenRasterCompositionGen.renderer.Renderer_Abstract;

public class C_RenderForViewer implements Command{

  public void execute(){
    Renderer_Abstract r=FRCG.instance.config.getRenderer();
    FRCG.instance.viewerimage=
      r.renderForViewer(FRCG.instance.composition,FRCG.instance.ui.panView);
    FRCG.instance.ui.panView.update();}

  public String getDescription(){
    return "rendering for viewer";}

}

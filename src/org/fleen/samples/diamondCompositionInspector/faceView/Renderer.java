package org.fleen.samples.diamondCompositionInspector.faceView;

import java.awt.image.BufferedImage;


public interface Renderer{
  
  BufferedImage render();
  
  void setViewer(FaceView v);
  
}

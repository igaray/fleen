package org.fleen.samples.fleenRasterCompositionGen.ui;

import javax.swing.DefaultListModel;

import org.fleen.samples.fleenRasterCompositionGen.FRCG;

@SuppressWarnings("serial")
public class RootBMListModel extends DefaultListModel{

  public int getSize(){
    return FRCG.instance.config.getGrammar().getBubbleModels().size();}

  public Object getElementAt(int index){
    return FRCG.instance.config.getGrammar().getBubbleModels().get(index);}

}

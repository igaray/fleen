package org.fleen.samples.fleenRasterCompositionGen.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import org.fleen.core.diamondGrammar.BubbleModel;

@SuppressWarnings("serial")
class RootBMListCellRenderer extends JLabel implements ListCellRenderer{
  
  RootBMListCellRenderer(){
    setOpaque(true);
    setSize(new Dimension(50,50));
  }

  public Component getListCellRendererComponent(
    JList list,Object value,int index,boolean isselected,boolean cellHasFocus){
    if(isselected){
      setBackground(Color.red);
    }else{
      setBackground(Color.blue);
    }
    
    BubbleModel bm=(BubbleModel)value;
    setText(bm.id);
    return this;
  }
  

}

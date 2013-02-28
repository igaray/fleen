package org.fleen.grammarEditor.util;

import javax.swing.SwingUtilities;

/*
 * all the ui subsystems use show commands so here's our uithread thing
 */
public abstract class C_UIShow_Abstract extends Command_Abstract{

  protected final void run(){
    SwingUtilities.invokeLater(new Runnable(){
      public void run(){
        show();}});}
  
  protected abstract void show();

}

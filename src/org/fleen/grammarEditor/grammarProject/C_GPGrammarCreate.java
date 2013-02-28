package org.fleen.grammarEditor.grammarProject;

import org.fleen.grammarEditor.GE;
import org.fleen.grammarEditor.overview.C_OverviewShow;
import org.fleen.grammarEditor.util.Command_Abstract;

public class C_GPGrammarCreate extends Command_Abstract{

  protected void run(){
    GE.grammarproject=new GrammarProject();
    GE.command(new C_OverviewShow());}

}

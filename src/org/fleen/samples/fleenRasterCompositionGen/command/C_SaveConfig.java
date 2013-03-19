package org.fleen.samples.fleenRasterCompositionGen.command;

import org.fleen.samples.fleenRasterCompositionGen.FRCG;

public class C_SaveConfig implements Command{

  public void execute(){
    FRCG.saveConfig();}

  public String getDescription(){
    return "Save Config.";}

}

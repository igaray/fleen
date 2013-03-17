package org.fleen.samples.fleenRasterCompositionGen.command;

import org.fleen.samples.fleenRasterCompositionGen.FRCG;

public class C_SaveConfig extends Command_Abstract{

  protected void run(){
    FRCG.saveConfig();}

  protected String getDescription(){
    return "Save Config.";}

}

package org.fleen.samples.fleenRasterCompositionGen;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class Log{
  
  private static final String PROGRESSCHAR=".";
  private static LogBox logbox=null;
  private static boolean lastlogwasprogressincrement=false;
  
  public static final void init(LogBox b){
    logbox=b;
    if(!FRCG.debug)
      redirectSystemStreams();}
  
  private static final void redirectSystemStreams() {
    final OutputStream out = new OutputStream(){
      //
      public void write(final int b) throws IOException {
        m2(String.valueOf((char) b));}
      //
      public void write(byte[] b, int off, int len) throws IOException {
        m2(new String(b, off, len));}
      //
      public void write(byte[] b) throws IOException {
        write(b, 0, b.length);}};
    System.setOut(new PrintStream(out,true));
    System.setErr(new PrintStream(out,true));}
  
  public static interface LogBox{
    void appendText(String s);}
  
  /*
   * PROGRESS INCREMENT
   * a dot among dots.
   */
  public static final void mp(){
    if(!lastlogwasprogressincrement){
      logbox.appendText("\n"+PROGRESSCHAR);
    }else{
      logbox.appendText(PROGRESSCHAR);}
    lastlogwasprogressincrement=true;}
  
  /*
   * MESSAGE LEVEL 0
   * Trivial stuff
   */
  public static final void m0(String s){
    //append color info TODO
    if(lastlogwasprogressincrement)s="\n"+s;
    logbox.appendText(s+"\n");
    lastlogwasprogressincrement=false;}
  
  /*
   * MESSAGE LEVEL 1
   * Relevent to whatever process we're in the midst of
   */
  public static final void m1(String s){
    //append color info TODO
    if(lastlogwasprogressincrement)s="\n"+s;
    logbox.appendText(s+"\n");
    lastlogwasprogressincrement=false;}
  
  /*
   * MESSAGE LEVEL 2
   * Dire warnings and death threats
   * Exception messages, stack traces and other System.out stuff also gets piped here
   */
  public static final void m2(String s){
    //append color info TODO
    if(lastlogwasprogressincrement)s="\n"+s;
    logbox.appendText("########\n"+s+"\n########\n");
    lastlogwasprogressincrement=false;}
  
  
}

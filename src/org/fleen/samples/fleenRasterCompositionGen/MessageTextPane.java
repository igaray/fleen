package org.fleen.samples.fleenRasterCompositionGen;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

@SuppressWarnings("serial")
public class MessageTextPane extends JTextArea{
  
  JTextArea thistextpane;
  
  MessageTextPane(){
    thistextpane=this;
    setLineWrap(true);
    setDocument(new PlainDocument());
//    redirectSystemStreams();
    }
  
//  private void updateTextPane(final String text) {
//    SwingUtilities.invokeLater(new Runnable(){
//      public void run(){
//        Document doc=thistextpane.getDocument();
//        doConditionalTrim(doc,text.length());
//        try {
//          doc.insertString(0,text+"\n",null);
//        }catch(BadLocationException e) {}
//        thistextpane.setCaretPosition(0);}});}
  
  public void postMessage(final String text) {
    SwingUtilities.invokeLater(new Runnable(){
      public void run(){
        Document doc=thistextpane.getDocument();
        try {
          doc.insertString(0,text+"\n",null);
          doConditionalTrim(doc);
        }catch(BadLocationException e) {}
        thistextpane.setCaretPosition(0);}});}
  
  /*
   * ################################
   * TRIM
   * for our little loggy thing
   * ################################
   */
  
  private static final int MAX_LOG_CHARS=1000;
  
  private void doConditionalTrim(Document doc){
    int a=doc.getLength();
    if(a>MAX_LOG_CHARS)
      try{
        doc.remove(MAX_LOG_CHARS,a-MAX_LOG_CHARS);
      }catch(Exception x){
        System.out.println("BAD REMOIVE");}}
   
//  /*
//   * ################################
//   * SYSTEM STREAM CONTROL
//   * ################################
//   */
//  
//  private void redirectSystemStreams() {
//    final OutputStream out = new OutputStream(){
//      //
//      public void write(final int b) throws IOException {
//        updateTextPane(String.valueOf((char) b));}
//      //
//      public void write(byte[] b, int off, int len) throws IOException {
//        updateTextPane(new String(b, off, len));}
//      //
//      public void write(byte[] b) throws IOException {
//        write(b, 0, b.length);}};
//    System.setOut(new PrintStream(out,true));
//    System.setErr(new PrintStream(out,true));}

}

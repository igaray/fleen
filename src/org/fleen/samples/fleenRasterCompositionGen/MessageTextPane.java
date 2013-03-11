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
        doConditionalTrim(doc,text.length()*2);
        try {
          doc.insertString(0,text+"\n",null);
        }catch(BadLocationException e) {}
        thistextpane.setCaretPosition(0);}});}
  
  /*
   * ################################
   * TRIM
   * it isn't pretty but it works
   * ################################
   */
  
  private void doConditionalTrim(Document doc,int trimlength){
    if(getPreferredSize().getHeight()>(getParent().getHeight()*1.5)){
      try{
        doc.remove(doc.getLength()-trimlength,trimlength);
      }catch(Exception x){}}}
   
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

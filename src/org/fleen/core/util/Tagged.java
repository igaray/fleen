package org.fleen.core.util;

import java.util.List;

public interface Tagged{
  
  boolean hasTag(String t);
  
  void setTags(List<String> t);
  
  List<String> getTags();

}

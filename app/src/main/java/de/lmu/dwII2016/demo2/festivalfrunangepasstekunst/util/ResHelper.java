package de.lmu.dwII2016.demo2.festivalfrunangepasstekunst.util;

import java.lang.reflect.Field;

public class ResHelper {

   public static int getResId(String resName, Class resType) {
      try {
         Field resourceField = resType.getDeclaredField(resName);
         return resourceField.getInt(resourceField);
      } catch (Exception e) {
         return -1;
      }
   }
}

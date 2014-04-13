package org.modstats;

import java.util.HashMap;
import java.util.Map;

public class ModVersionData {

   public String prefix;
   public String name;
   public String version;
   public String downloadUrl;
   public String changeLogUrl;
   public Map extraFields;


   public ModVersionData() {
      this.extraFields = new HashMap();
   }

   public ModVersionData(String prefix, String name, String version) {
      this.prefix = prefix;
      this.name = name;
      this.version = version;
      this.extraFields = new HashMap();
   }

   public int hashCode() {
      boolean prime = true;
      byte result = 1;
      int result1 = 31 * result + (this.changeLogUrl == null?0:this.changeLogUrl.hashCode());
      result1 = 31 * result1 + (this.downloadUrl == null?0:this.downloadUrl.hashCode());
      result1 = 31 * result1 + (this.name == null?0:this.name.hashCode());
      result1 = 31 * result1 + (this.prefix == null?0:this.prefix.hashCode());
      result1 = 31 * result1 + (this.version == null?0:this.version.hashCode());
      return result1;
   }

   public boolean equals(Object obj) {
      if(this == obj) {
         return true;
      } else if(obj == null) {
         return false;
      } else if(this.getClass() != obj.getClass()) {
         return false;
      } else {
         ModVersionData other = (ModVersionData)obj;
         if(this.changeLogUrl == null) {
            if(other.changeLogUrl != null) {
               return false;
            }
         } else if(!this.changeLogUrl.equals(other.changeLogUrl)) {
            return false;
         }

         if(this.downloadUrl == null) {
            if(other.downloadUrl != null) {
               return false;
            }
         } else if(!this.downloadUrl.equals(other.downloadUrl)) {
            return false;
         }

         if(this.name == null) {
            if(other.name != null) {
               return false;
            }
         } else if(!this.name.equals(other.name)) {
            return false;
         }

         if(this.prefix == null) {
            if(other.prefix != null) {
               return false;
            }
         } else if(!this.prefix.equals(other.prefix)) {
            return false;
         }

         if(this.version == null) {
            if(other.version != null) {
               return false;
            }
         } else if(!this.version.equals(other.version)) {
            return false;
         }

         return true;
      }
   }
}

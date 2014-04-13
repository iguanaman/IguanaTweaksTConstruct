package org.modstats;

import cpw.mods.fml.common.FMLLog;
import org.modstats.IModstatsReporter;

public class Modstats {

   private static final Modstats INSTANCE = new Modstats();
   private static final String CLASS_TEMPLATE = "org.modstats.reporter.v%d.Reporter";
   private IModstatsReporter reporter = this.locateReporter();


   public IModstatsReporter getReporter() {
      return this.reporter;
   }

   private IModstatsReporter locateReporter() {
      int i = 1;

      Class latest;
      for(latest = null; i < 100; ++i) {
         try {
            Class e = Class.forName(String.format("org.modstats.reporter.v%d.Reporter", new Object[]{Integer.valueOf(i)}));
            if(IModstatsReporter.class.isAssignableFrom(e)) {
               latest = e;
            }
         } catch (Exception var5) {
            break;
         }
      }

      if(latest == null) {
         FMLLog.warning("Modstats reporter class not found.", new Object[0]);
      } else {
         try {
            return (IModstatsReporter)latest.newInstance();
         } catch (Exception var4) {
            FMLLog.warning("Modstats reporter class can\'t be instantiated.", new Object[0]);
         }
      }

      return null;
   }

   public static Modstats instance() {
      return INSTANCE;
   }

}

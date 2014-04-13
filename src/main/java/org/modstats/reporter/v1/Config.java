package org.modstats.reporter.v1;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import java.io.File;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

public class Config {

   private static final String CONFIG_NAME = "modstats.cfg";
   public boolean allowUpdates;
   public boolean betaNotifications;
   public boolean forCurrentMinecraftVersion;
   public boolean logOnly;


   public Config() {
      File configLocation = new File(Loader.instance().getConfigDir(), "modstats.cfg");
      Configuration configuration = new Configuration(configLocation);
      configuration.load();
      Property prop = configuration.get("updates", "AllowUpdates", true);
      prop.comment = "Allow to send current mod versions to the server and check for updates.\nIt allows to mod authors to see mod\'s popularity. Please don\'t disable it without necessity";
      this.allowUpdates = prop.getBoolean(true);
      prop = configuration.get("updates", "LogOnly", false);
      prop.comment = "Don\'t display chat message, just add message to the log.";
      this.logOnly = prop.getBoolean(false);
      prop = configuration.get("updates", "BetaNotifications", false);
      prop.comment = "Set true to receive notifications about beta versions. Otherwise you will only receive information about stable versions";
      this.betaNotifications = prop.getBoolean(false);
      prop = configuration.get("updates", "ForCurrentMinecraftVersion", false);
      prop.comment = "Check for updates only for current MC version.\nEx:if you have MC 1.4.2 and ForCurrentMinecraftVersion is true, then you wouldn\'t receive notifications about versions for MC 1.4.5";
      this.forCurrentMinecraftVersion = prop.getBoolean(false);
      configuration.save();
      FMLLog.info("[Modstats] Config loaded. allowUpdates: %b,  betaNotification: %b, strict: %b", new Object[]{Boolean.valueOf(this.allowUpdates), Boolean.valueOf(this.betaNotifications), Boolean.valueOf(this.forCurrentMinecraftVersion)});
   }
}

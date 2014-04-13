package org.modstats.reporter.v1;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.WorldEvent.Load;
import org.modstats.IModstatsReporter;
import org.modstats.ModVersionData;
import org.modstats.ModstatInfo;
import org.modstats.reporter.v1.Config;
import org.modstats.reporter.v1.DataSender;

public class Reporter implements IModstatsReporter {

   public Map registeredMods = new ConcurrentHashMap(2, 0.9F, 1);
   private DataSender sender;
   public Config config;
   private boolean checkedAuto = false;


   public Reporter() {
      MinecraftForge.EVENT_BUS.register(this);
      this.config = new Config();
   }

   private void startCheck(boolean manual) {
      if(this.config.allowUpdates) {
         if(FMLCommonHandler.instance().getSide().isClient() || manual) {
            if(!this.registeredMods.isEmpty()) {
               DataSender currentSender = this.sender;
               if(manual || !this.checkedAuto) {
                  if(currentSender == null || currentSender.manual && !manual) {
                     currentSender = new DataSender(this, manual);
                     currentSender.start();
                     this.sender = currentSender;
                  }
               }
            }
         }
      }
   }

   @ForgeSubscribe
   public void worldLoad(Load event) {
      this.startCheck(false);
   }

   public void registerMod(Object mod) {
      if(this.config.allowUpdates) {
         if(mod == null) {
            FMLLog.warning("[Modstats] Can\'t register null mod.", new Object[0]);
         } else {
            ModstatInfo info = (ModstatInfo)mod.getClass().getAnnotation(ModstatInfo.class);
            if(info == null) {
               FMLLog.warning("[Modstats] ModstatsInfo annotation not found for given mod.", new Object[0]);
            } else if(info.prefix() != null && !info.prefix().equals("")) {
               Mod modData = (Mod)mod.getClass().getAnnotation(Mod.class);
               ModVersionData data;
               if(modData == null) {
                  if(info.name() == null || info.name().equals("")) {
                     FMLLog.warning("[Modstats] Mod name can\'t be empty.", new Object[0]);
                     return;
                  }

                  if(info.version() == null || info.version().equals("")) {
                     FMLLog.warning("[Modstats] Mod version can\'t be empty.", new Object[0]);
                     return;
                  }

                  data = new ModVersionData(info.prefix(), info.name(), info.version());
               } else {
                  data = new ModVersionData(info.prefix(), modData.name(), modData.version());
               }

               this.registeredMods.put(info.prefix(), data);
            } else {
               FMLLog.warning("[Modstats] Mod prefix can\'t be empty.", new Object[0]);
            }
         }
      }
   }

   public void doManualCheck() {
      this.startCheck(true);
   }
}

package org.modstats;

import cpw.mods.fml.common.FMLLog;
import java.util.LinkedList;
import java.util.List;
import net.minecraftforge.event.Cancelable;
import net.minecraftforge.event.Event;
import org.modstats.ModVersionData;

@Cancelable
public class ModsUpdateEvent extends Event {

   private List updatedMods = new LinkedList();


   public void add(ModVersionData data) {
      if(!this.updatedMods.contains(data)) {
         this.updatedMods.add(data);
      } else {
         FMLLog.info("ModsUpdateEvent shouldn\'t have same mods data", new Object[]{data});
      }

   }

   public List getUpdatedMods() {
      return this.updatedMods;
   }
}

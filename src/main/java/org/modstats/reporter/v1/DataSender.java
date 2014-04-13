package org.modstats.reporter.v1;

import argo.jdom.JdomParser;
import argo.jdom.JsonNode;
import argo.jdom.JsonRootNode;
import argo.jdom.JsonStringNode;
import argo.saj.InvalidSyntaxException;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.versioning.ComparableVersion;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;
import net.minecraft.crash.CallableMinecraftVersion;
import net.minecraft.crash.CrashReport;
import net.minecraftforge.common.MinecraftForge;
import org.modstats.ModVersionData;
import org.modstats.ModsUpdateEvent;
import org.modstats.reporter.v1.Reporter;

class DataSender extends Thread {

   private static final String urlAutoTemplate = "http://modstats.org/api/v1/report?mc=%s&user=%s&data=%s&sign=%s&beta=%b&strict=%b";
   private static final String urlManualTemplate = "http://modstats.org/api/v1/check?mc=%s&user=%s&data=%s&sign=%s&beta=%b&strict=%b";
   private final Reporter reporter;
   public final boolean manual;


   public DataSender(Reporter reporter, boolean manual) {
      this.reporter = reporter;
      this.manual = manual;
   }

   private String toHexString(byte[] bytes) {
      char[] hexArray = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
      char[] hexChars = new char[bytes.length * 2];

      for(int j = 0; j < bytes.length; ++j) {
         int v = bytes[j] & 255;
         hexChars[j * 2] = hexArray[v / 16];
         hexChars[j * 2 + 1] = hexArray[v % 16];
      }

      return new String(hexChars);
   }

   private String getPlayerId() throws IOException {
      File statDir = new File(FMLClientHandler.instance().getClient().mcDataDir, "stats");
      if(!statDir.exists()) {
         statDir.mkdirs();
      }

      String mac = "";

      try {
         InetAddress uidFile = InetAddress.getLocalHost();
         NetworkInterface uid = NetworkInterface.getByInetAddress(uidFile);
         byte[] output = uid.getHardwareAddress();
         if(output != null) {
            mac = this.toHexString(output);
         }
      } catch (Exception var6) {
         ;
      }

      File uidFile1 = new File(statDir, "player.uid");
      String uid1;
      if(uidFile1.exists() && uidFile1.canRead() && uidFile1.length() == (long)(32 + mac.length())) {
         uid1 = Files.toString(uidFile1, Charsets.US_ASCII);
         String output1 = uid1.substring(32);
         if(output1.equalsIgnoreCase(mac)) {
            return uid1.substring(0, 32);
         }
      }

      uidFile1.createNewFile();
      if(uidFile1.canWrite()) {
         uid1 = UUID.randomUUID().toString().replace("-", "");
         FileOutputStream output2 = new FileOutputStream(uidFile1);
         output2.write((uid1 + mac).getBytes());
         output2.close();
         return uid1;
      } else {
         return "";
      }
   }

   private String getSignature(String data) {
      return Hashing.md5().hashString(data).toString();
   }

   private String getData() {
      StringBuilder b = new StringBuilder();
      Iterator i$ = this.reporter.registeredMods.entrySet().iterator();

      while(i$.hasNext()) {
         Entry item = (Entry)i$.next();
         b.append((String)item.getKey()).append("+").append(((ModVersionData)item.getValue()).version).append("$");
      }

      return b.toString();
   }

   private boolean checkIsNewer(String current, String received) {
      return (new ComparableVersion(received)).compareTo(new ComparableVersion(current)) > 0;
   }

   private void parseResponse(String response) {
      try {
         JsonRootNode e = (new JdomParser()).parse(response);
         if(!e.isNode(new Object[]{"mods"})) {
            FMLLog.info("[Modstats] Empty result", new Object[0]);
            return;
         }

         List modList = e.getArrayNode(new Object[]{"mods"});
         ModsUpdateEvent event = new ModsUpdateEvent();
         Iterator updatedModsToOutput = modList.iterator();

         while(updatedModsToOutput.hasNext()) {
            JsonNode builder = (JsonNode)updatedModsToOutput.next();
            String iterator = builder.getStringValue(new Object[]{"code"});
            if(!this.reporter.registeredMods.containsKey(iterator)) {
               FMLLog.warning("[Modstats] Extra mod \'%s\' in service response", new Object[]{iterator});
            } else {
               String mc = builder.getStringValue(new Object[]{"ver"});
               if(mc != null && !mc.equals(((ModVersionData)this.reporter.registeredMods.get(iterator)).version) && this.checkIsNewer(((ModVersionData)this.reporter.registeredMods.get(iterator)).version, mc)) {
                  ModVersionData maxTries = new ModVersionData(iterator, ((ModVersionData)this.reporter.registeredMods.get(iterator)).name, mc);
                  Map e1 = builder.getFields();
                  Iterator i$ = e1.entrySet().iterator();

                  while(i$.hasNext()) {
                     Entry entry = (Entry)i$.next();
                     String fieldName = ((JsonStringNode)entry.getKey()).getText();
                     if(!fieldName.equals("code") && !fieldName.equals("ver")) {
                        if(!(entry.getValue() instanceof JsonStringNode)) {
                           FMLLog.warning(String.format("[Modstats] Too complex data in response for field \'%s\'.", new Object[]{fieldName}), new Object[0]);
                        } else {
                           String value = ((JsonStringNode)entry.getValue()).getText();
                           if(fieldName.equals("chlog")) {
                              maxTries.changeLogUrl = value;
                           } else if(fieldName.equals("link")) {
                              maxTries.downloadUrl = value;
                           } else {
                              maxTries.extraFields.put(fieldName, value);
                           }
                        }
                     }
                  }

                  event.add(maxTries);
               }
            }
         }

         if(event.getUpdatedMods().size() > 0) {
            MinecraftForge.EVENT_BUS.post(event);
         }

         if(!event.isCanceled() && event.getUpdatedMods().size() > 0) {
            List var17 = event.getUpdatedMods();
            StringBuilder var18 = new StringBuilder("Updates found: ");
            Iterator var19 = var17.iterator();

            while(var19.hasNext()) {
               ModVersionData var21 = (ModVersionData)var19.next();
               var18.append(var21.name).append(" (").append(var21.version).append(")").append(var19.hasNext()?",":".");
            }

            FMLLog.info("[Modstats] %s", new Object[]{var18.toString()});
            if(!this.reporter.config.logOnly && FMLCommonHandler.instance().getSide().isClient()) {
               Minecraft var20 = FMLClientHandler.instance().getClient();

               for(int var22 = 30; var20.thePlayer == null && var22 > 0; --var22) {
                  try {
                     sleep(1000L);
                  } catch (InterruptedException var15) {
                     ;
                  }
               }

               if(var20.thePlayer != null) {
                  var20.thePlayer.addChatMessage(var18.toString());
               }
            }
         }
      } catch (InvalidSyntaxException var16) {
         FMLLog.warning("[Modstats] Can\'t parse response: \'%s\'.", new Object[]{var16.getMessage()});
      }

   }

   public void run() {
      try {
         String e = this.getData();
         String playerId = this.getPlayerId();
         String hash = this.getSignature(playerId + "!" + e);
         String template = this.manual?"http://modstats.org/api/v1/check?mc=%s&user=%s&data=%s&sign=%s&beta=%b&strict=%b":"http://modstats.org/api/v1/report?mc=%s&user=%s&data=%s&sign=%s&beta=%b&strict=%b";
         String mcVersion = (new CallableMinecraftVersion((CrashReport)null)).minecraftVersion();
         URL url = new URL(String.format(template, new Object[]{mcVersion, playerId, e, hash, Boolean.valueOf(this.reporter.config.betaNotifications), Boolean.valueOf(this.reporter.config.forCurrentMinecraftVersion)}));
         HttpURLConnection connection = (HttpURLConnection)url.openConnection();
         connection.setConnectTimeout(5000);
         connection.setReadTimeout(5000);
         BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

         String line;
         String out;
         for(out = ""; (line = reader.readLine()) != null; out = out + line) {
            ;
         }

         reader.close();
         this.parseResponse(out);
      } catch (MalformedURLException var11) {
         FMLLog.warning("[Modstats] Invalid stat report url", new Object[0]);
      } catch (IOException var12) {
         FMLLog.info("[Modstats] Stat wasn\'t reported \'" + var12.getMessage() + "\'", new Object[0]);
      } catch (Exception var13) {
         FMLLog.warning("[Modstats] Something wrong: " + var13.toString(), new Object[0]);
      }

   }
}

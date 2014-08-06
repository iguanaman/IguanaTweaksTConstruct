package iguanaman.iguanatweakstconstruct.restriction;

import iguanaman.iguanatweakstconstruct.util.Log;
import net.minecraftforge.common.config.Configuration;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.tools.ToolMaterial;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RestrictionConfig {
    private Configuration configfile;
//    public static Map<String, Set<Integer>> restrictedParts = new HashMap<String, Set<Integer>>();

    public void init(File file) {
        configfile = new Configuration(file);
        configfile.load();

        loadRestrictedParts();
        configfile.save();
    }

    // todo: move this to an extra config and load it in postInit so everything is registered
    public void loadRestrictedParts()
    {
        Log.info("Applying Tool Part restrictions");
        configfile.setCategoryComment("Restrictions", "Tweak Module: Allows to blacklist certain things from being created.");
        // construct the comment containing all the info needed :I
        StringBuilder comment = new StringBuilder();
        comment.append("Prevents the creation of listed Material-Tool combinations.\n");
        comment.append("The format is <materialname>:<partname>\n");
        // material names
        comment.append("materialnames are: ");
        ToolMaterial[] mats = TConstructRegistry.toolMaterials.values().toArray(new ToolMaterial[TConstructRegistry.toolMaterials.size()]);
        for(int i = 0; i < mats.length; i++) {
            comment.append(mats[i].materialName);
            if(i < mats.length-1)
                comment.append(", ");
        }
        comment.append('\n');
        // part names
        comment.append("partnames are: ");
        // patterns
        for(String name : RestrictionHelper.configNameToPattern.keySet()) {
            comment.append(name);
            comment.append(", ");
        }
        comment.append("all\n");

        // load the actual config after creating this long information comment ._.
        String[] input = configfile.getStringList("toolParts", "Restrictions", RestrictionHelper.defaultRestrictions, comment.toString());

        // work through the entries
        for(String str : input)
        {
            if(str.isEmpty())
                continue;
            String[] restriction = str.split(":");
            ToolMaterial material = null;

            if(restriction.length != 2)
            {
                Log.error(String.format("Found invalid restriction entry: %s", str));
                continue;
            }

            // check if valid material
            boolean valid = false;
            for(ToolMaterial mat : mats)
                if(mat.materialName.equals(restriction[0])) {
                    valid = true;
                    material = mat;
                }

            if(!valid)
            {
                Log.error(String.format("Found invalid material %s in restriction entry: %s", restriction[0], str));
                continue;
            }

            // find material id
            Integer matID = -1;
            for(Map.Entry<Integer, ToolMaterial> entry : TConstructRegistry.toolMaterials.entrySet())
                if(entry.getValue() == material)
                    matID = entry.getKey();

            if(matID == -1)
            {
                Log.error(String.format("Couldn't find Material ID for %s", material.materialName));
            }

            // check if we have to add all
            if("all".equals(restriction[1]))
            {
                for(RestrictionHelper.ItemMeta key : RestrictionHelper.configNameToPattern.values())
                    RestrictionHelper.addRestriction(key, matID);

                continue;
            }

            // check if valid part
            valid = RestrictionHelper.configNameToPattern.keySet().contains(restriction[1]);

            if(!valid)
            {
                Log.error(String.format("Found invalid part %s in restriction entry: %s", restriction[1], str));
                continue;
            }

            // add restriction :)
            RestrictionHelper.addRestriction(RestrictionHelper.configNameToPattern.get(restriction[1]), matID);
        }
    }
}

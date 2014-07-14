package iguanaman.iguanatweakstconstruct.util;

import cpw.mods.fml.common.FMLLog;
import iguanaman.iguanatweakstconstruct.reference.IguanaReference;
import org.apache.logging.log4j.Level;

public class IguanaLog {
	public static void log(Level level, Object obj)
	{
        FMLLog.log(IguanaReference.MOD_NAME, level, String.valueOf(obj));
	}

    public static void info(Object obj) { log(Level.INFO, obj); }
    public static void warn(Object obj) { log(Level.WARN, obj); }
    public static void error(Object obj) { log(Level.ERROR, obj); }
    public static void debug(Object obj) { log(Level.DEBUG, obj); }
    public static void trace(Object obj) { log(Level.TRACE, obj); }
}

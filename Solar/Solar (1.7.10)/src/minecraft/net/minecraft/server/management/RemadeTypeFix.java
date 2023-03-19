package net.minecraft.server.management;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class RemadeTypeFix implements ParameterizedType {

	private static final String __OBFID = "CL_00001886";
    public Type[] getActualTypeArguments()
    {
        return new Type[] {PlayerProfileCache.ProfileEntry.class};
    }
    public Type getRawType()
    {
        return List.class;
    }
    public Type getOwnerType()
    {
        return null;
    }
}

package cn.superiormc.economylimit.configs;

import cn.superiormc.economylimit.EconomyLimit;
import cn.superiormc.economylimit.utils.ColorParser;

public class Messages {
    public static String GetMessages(String textName){
        String textValue = EconomyLimit.instance.getConfig().getString("messages." + textName);

        if (textValue == null)
            return "§x§9§8§F§B§9§8[EconomyLimit] §cThere is something wrong in your config file!";
        else {
            textValue = ColorParser.parse(textValue);
            return textValue;
        }
    }
    public static String GetActionMessages(String textName){
        textName = ColorParser.parse(textName);
        return textName;
    }
}

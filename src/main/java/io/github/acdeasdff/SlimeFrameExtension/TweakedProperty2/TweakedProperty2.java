package io.github.acdeasdff.SlimeFrameExtension.TweakedProperty2;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TweakedProperty2 extends Properties {

    public String getReplacedProperty(String key) {
        return getReplacedProperty(key, 0);
    }

    /**
     * replace ${XXXXXX}
     **/
    public String getReplacedProperty(String key, int times) {
        String sval = getProperty(key);
        String regex = "\\$\\{[^}]+}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(sval);
        if (times <= 30){
            while (true) {
                if (!matcher.find()) {
                    break;
                }
                sval = matcher.replaceFirst(
                        getReplacedProperty(matcher.group().substring(2, matcher.group().length() - 1)
                                , times + 1)
                );
                matcher = pattern.matcher(sval);
//            logger.log(Level.WARNING,sval);
            }
        }else{
//            logger.log(Level.SEVERE, "trying to avoid StackOverFlowError");
//            logger.log(Level.SEVERE, sval);
        }
        return sval;
    }

    public List<String> getReplacedProperties(String key, ChatColor color) {
        return getReplacedProperties(key, 0, color);
    }
    /**
     * replace ${XXXXXX} and %{XXXXXX}
     * Strings in %{XXXXXX} will be added to next line
     * (only the first works)
     **/
    public List<String> getReplacedProperties(String key, int times, ChatColor color) {
        List<String> svals = new ArrayList<>();
        String sval = getProperty(key);
        String regex = "\\$\\{[^}]+}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(sval);
        if (times <= 30){
            while (true) {
                if (!matcher.find()) {
                    break;
                }
                sval = matcher.replaceFirst(
                        getReplacedProperty(matcher.group().substring(2, matcher.group().length() - 1)
                                , times + 1)
                );
                matcher = pattern.matcher(sval);
//            logger.log(Level.WARNING,sval);
            }
        }else{
//            logger.log(Level.SEVERE, "trying to avoid StackOverFlowError");
//            logger.log(Level.SEVERE, sval);
        }
        regex = "%+\\{[^}]+}";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(sval);
        if (!matcher.find()){
            svals.add(sval);
            return svals;
        }
        sval = matcher.replaceFirst("");
        if (color != null){
            sval = color + sval;
        }
        svals.add(sval);
//        System.out.println(sval);
        svals.addAll(getReplacedProperties(matcher.group().substring(2, matcher.group().length() - 1)
                , times + 1, color));
        return svals;
    }
}

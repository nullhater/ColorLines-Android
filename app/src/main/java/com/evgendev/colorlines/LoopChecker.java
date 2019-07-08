package com.evgendev.colorlines;

import java.util.HashMap;
import java.util.Map;

public class LoopChecker {
    private static Map<String, Integer> map = new HashMap<>();

    public static void add(String status, int deep){
        map.put(status,deep);
    }

    public static boolean isLoop(String status, int deep){
        if (map.containsKey(status)){
            int otherDeep = map.get(status);
            if (otherDeep <= deep) return true;
            else return false;
        }else {
            return false;
        }
    }

    public static void init(){
        map = new HashMap<>();
    }
}

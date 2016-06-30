package com.yuchengtech.mrtn.base;

import java.util.HashMap;
import java.util.Map;

/**
 * 机具型号
 *
 * @author luo
 */
public enum MachineType {
    分体POS("分体POS", "1"), 移动POS("移动POS", "2"), 一体POS("一体POS", "3"), 电话POS(
            "电话POS", "4"), 简易POS("简易POS", "5"), mPOS("mPOS", "6"), aPOS("aPOS",
            "7");

    private String name;
    private String index;

    private MachineType(String name, String index) {
        this.name = name;
        this.index = index;
    }

    /**
     * 根据定义的编号查找对应的字母
     *
     * @param index
     * @return
     * @author luojr Created on 2015年9月29日 下午3:43:47
     */
    public static String getName(String index) {
        for (MachineType c : MachineType.values()) {
            if (c.getIndex().equals(index)) {
                return c.getName();
            }
        }
        return null;
    }

    public static String getIndex(String name) {
        for (MachineType c : MachineType.values()) {
            if (c.getName().equals(name)) {
                return c.getIndex();
            }
        }
        return null;
    }

    /**
     * 返回map
     *
     * @return
     */
    public static Map<String, String> getMap() {
        Map<String, String> map = new HashMap<String, String>();
        for (MachineType c : MachineType.values()) {
            map.put(c.getIndex(), c.getName());
        }
        return map;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}

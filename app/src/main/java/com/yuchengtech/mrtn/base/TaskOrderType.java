package com.yuchengtech.mrtn.base;

public class TaskOrderType {

    public static final String 培训 = "001";

    public static final String 走访回访单 = "002";

    public static final String 风险调查单 = "003";

    public static final String 发卡行调单 = "004";

    public static final String 耗材配送 = "006";

    public static final String 故障报修 = "007";

	/*
     * public static final String 装机任务单 = "008";
	 * 
	 * public static final String 装机申请任务单 = "009";
	 */

    public static final String 补充进件材料 = "010";

    public static String ConvertStatus(String item) {
        if (TaskOrderType.培训.equals(item))
            return "培训";
        else if (TaskOrderType.走访回访单.equals(item))
            return "走访回访单";
        else if (TaskOrderType.风险调查单.equals(item))
            return "风险调查单";
        else if (TaskOrderType.发卡行调单.equals(item))
            return "发卡行调单";
        else if (TaskOrderType.耗材配送.equals(item))
            return "耗材配送";
        else if (TaskOrderType.故障报修.equals(item))
            return "故障报修";
		/*
		 * else if (TaskOrderType.装机任务单.equals(item)) return "装机任务单"; else if
		 * (TaskOrderType.装机申请任务单.equals(item)) return "装机申请任务单";
		 */
        else if (TaskOrderType.补充进件材料.equals(item))
            return "补充进件材料";
        else
            return item;
    }
}

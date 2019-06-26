package com.dsc.common;

import com.dsc.common.data.constant.BaseConstan;
import com.dsc.common.data.vo.LogFileInfoVo;
import com.dsc.common.util.LogFileUtil;

public class Application {

    public static void main(String[] args) throws Exception {
        if (args == null || args.length < 3) {
            System.out.println("入参错误！");
            return;
        }
        String logCreateTimeStr = args[0] + " " + args[1];
        String logSrcDirPath = args[2];
        String logFileExt = ""; // xxx.txt 你需要输入 .txt
        if (args.length > 3) {
            logFileExt = args[3];
        }
        LogFileInfoVo logInfo = LogFileUtil.getLogFileListByLogCreateTime(logSrcDirPath, logFileExt, logCreateTimeStr, BaseConstan.UTF_8);
        System.out.println("================================= 分析结果 =================================");
        if (logInfo == null) {
            System.out.println("未发现符合要求的日志文件！");
            return;
        }
        System.out.println("查找到符合要求的文件：");
        System.out.println("------------------------------------------------------------------------");
        System.out.println("文件路径：" + logInfo.getFileAbsolutePath());
        System.out.println("文件大小：" + logInfo.getSize() + "MB");
        System.out.println("创建时间：" + logInfo.getFirstLineCreateTimeStr());
        System.out.println("------------------------------------------------------------------------");
    }
}
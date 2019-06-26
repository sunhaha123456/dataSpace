package com.dsc.common.util;

import com.dsc.common.data.vo.LogFileInfoVo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Date;

public class LogFileUtil {

	private final static int lineCount = 100;

	/**
	 * 功能：获取指定日志目录下，符合日志生成时间包含指定时间的文件信息
	 * @param logFileParentDirPath 日志文件目录
	 * @param logCreateTimeStr 日志创建时间
	 * @param charSet 指定文件内容读取格式
	 * @return
	 * @throws Exception
	 */
	public static LogFileInfoVo getLogFileListByLogCreateTime(String logFileParentDirPath, String logFileExt, String logCreateTimeStr, String charSet) throws Exception {
		File parentFileDir = new File(logFileParentDirPath);
		if (!parentFileDir.isDirectory()) {
			throw new Exception("日志父级目录路径非目录路径！");
		}
		Date logTimeDate = DateUtil.formatStr2Time(logCreateTimeStr);
		if (logTimeDate == null) {
			throw new Exception("日志创建时间错误！");
		}
		long logCreateTimeLong = logTimeDate.getTime();
		File[] fileArr = parentFileDir.listFiles();
		if (fileArr == null || fileArr.length == 0) {
			throw new Exception("日志父级目录下无文件！");
		}
		LogFileInfoVo logFileInfoRes = null;
		LogFileInfoVo logFileInfoTemp = null;
		String fileName = null;
		String fileExt = null;
		for (File file : fileArr) {
			if (file.isFile() && file.canRead()) {
				if (StringUtil.isNotEmpty(logFileExt)) {
					fileName = file.getName();
					fileExt = fileName.substring(fileName.lastIndexOf("."));
					if (!logFileExt.equals(fileExt)) {
						continue;
					}
				}
				logFileInfoTemp = getLogFileInfo(file, charSet, lineCount);
				if (logFileInfoTemp == null) {
					System.out.println("文件：" + file.getAbsoluteFile() + "，未获取到日志开始创建时间！");
					continue;
				}
				if (logFileInfoTemp.getFirstLineCreateTimeLong().longValue() <= logCreateTimeLong) {
					if (logFileInfoRes == null || logFileInfoRes.getFirstLineCreateTimeLong().longValue() < logFileInfoTemp.getFirstLineCreateTimeLong().longValue()) {
						logFileInfoRes = logFileInfoTemp;
					}
				}
			}
		}
		return logFileInfoRes;
	}

	public static LogFileInfoVo getLogFileInfo(File file, String charSet, int lineCount) throws Exception {
		LogFileInfoVo logFileInfo = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), charSet));
		String line = null;
		String timeStr = null;
		int c = 1;
		while ((line = br.readLine()) != null) {
			timeStr = DateUtil.getLineFirstTime(line);
			if (StringUtil.isNotEmpty(timeStr)) {
				logFileInfo = new LogFileInfoVo();
				logFileInfo.setFileAbsolutePath(file.getAbsolutePath());
				logFileInfo.setFirstLine(line);
				logFileInfo.setFirstLineCreateTimeStr(timeStr);
				logFileInfo.setFirstLineCreateTimeLong(DateUtil.getTimeMills(timeStr));
				logFileInfo.setSize(file.length() / (1024 * 1024));
				break;
			}
			c++;
			if (c >= lineCount) {
				break;
			}
		}
		br.close();
		return logFileInfo;
	}
}
package com.dsc.common.data.vo;

import lombok.Data;

@Data
public class LogFileInfoVo {
    private String fileAbsolutePath;
    private String firstLine;
    private String firstLineCreateTimeStr;
    private Long firstLineCreateTimeLong = 0L;
    private Long size; // 单位：MB
}
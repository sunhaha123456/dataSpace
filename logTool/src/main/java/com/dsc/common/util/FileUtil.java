package com.dsc.common.util;

import com.dsc.common.data.vo.FileLineInfoVo;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

//@Slf4j
public class FileUtil {

	public static FileLineInfoVo getFileFristLineAndEndLine(String filePath) {
		FileLineInfoVo fileInfo = new FileLineInfoVo();
		RandomAccessFile raf;
		try {
			raf = new RandomAccessFile(filePath, "r");
			fileInfo.setFirstLine(raf.readLine());
			long len = raf.length();
			if (len != 0L) {
				long pos = len - 1;
				while (pos > 0) {
					pos--;
					raf.seek(pos);
					if (raf.readByte() == '\n') {
						fileInfo.setEndLine(raf.readLine());
						break;
					}
				}
			}
			raf.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return fileInfo;
	}

	public static String getFileLastLine(String filePath){
		RandomAccessFile raf;
		String lastLine = "";
		try {
			raf = new RandomAccessFile(filePath, "r");
			long len = raf.length();
			if (len != 0L) {
				long pos = len - 1;
				while (pos > 0) {
					pos--;
					raf.seek(pos);
					if (raf.readByte() == '\n') {
						lastLine = raf.readLine();
						break;
					}
				}
			}
			raf.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return lastLine;
	}

	public static String getFileFirstLine(String filePath){
		RandomAccessFile raf;
		String firstLine = "";
		try {
			raf = new RandomAccessFile(filePath, "r");
			firstLine=raf.readLine();
		} catch (Exception e) {
			System.out.println(e);
		}
		return firstLine;
	}

	public <T> void exportFile(HttpServletResponse response, String filename, String fileAbsolutePath) throws Exception {
		// reponse init
		response.setContentType("octets/stream");
		response.addHeader("Content-Type", "octets/stream; charset=utf-8");
		filename = new String(filename.getBytes("UTF-8"), "iso8859-1");
		response.addHeader("Content-Disposition", "attachment;filename=" + filename);

		BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(fileAbsolutePath)));
		try {
			byte[] buf = new byte[1024];
			int len = 0;
			while((len = bis.read(buf)) != -1){
				bos.write(buf,0,len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			bis.close();
			bos.close();
		}
	}
}
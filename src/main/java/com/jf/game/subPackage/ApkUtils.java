package com.jf.game.subPackage;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.zip.ZipFile;

/**   
 * @Title: Apk.java 
 * @Description: 解压Apk、往apk中增加文件的 实现
 */
public class ApkUtils {

	private void copy(InputStream inputStream, OutputStream outputStream) throws IOException{
		byte[] b = new byte[2048];
		int len = 0;
		while((len = inputStream.read(b)) != -1){
			outputStream.write(b, 0, len);
		}
	}
	/**
	 * 往Apk中byte[]
	 * @param apkPath
	 * @param outputPath
	 * @param addFilePath 文件路径
	 * @param entryName 希望在apk中显示的路径、名称
	 */
	public void addByteToApkByZip(String parentPackagePath,String outputPath,
			String subPackageName,String subBasePath,
			byte[] content,String InZipFilePath){
		File file = new File(parentPackagePath);
		if(!file.exists()){
			System.out.println("not found "+parentPackagePath);
			return;
		}
		try {
			System.out.println(" ------------ 复制文件----------------");
		    FileUtil.copyFile(parentPackagePath, outputPath, true);
		    
		    System.out.println(" ------------ 解压文件----------------");
		    String zipPath = subBasePath+SubConfig.ZIP_FILE_PATH;
		    ZipUtil.unZip(outputPath, zipPath+"/");
	 
		    System.out.println(" ------------ 生产(写入文件)----------------");
			String filePath="";
			if(subPackageName.contains("ipa")){
				String findIosMidPath = findTargetPath(zipPath); // ios特殊路基查找
				filePath = zipPath+findIosMidPath+SubConfig.APP_FILE_PATH_IOS;
			}else{
				filePath = zipPath+SubConfig.APP_FILE_PATH_ANDROID;
			}
			System.out.println("  file path: "+ filePath);
			
			FileUtil.createIfNotExsit(filePath.replace("sdkInfo.txt", "")); 
			
			File myConfig = new File(filePath);
			FileOutputStream fos = new FileOutputStream(myConfig);  
			fos.write(content);
			fos.flush();
			fos.close();
			System.out.println(" ------------ 压缩目录----------------");
			File subPackage = new File(outputPath);
			subPackage.delete();
			ZipUtil.compress(zipPath,subBasePath+subPackageName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 查询 .app 文件的 路劲
	 * 需要加  前  / 斜杠
	 * @param zipPath
	 * @return
	 * @author sun
	 * Sep 21, 2017
	 */
	String findTargetPath(String zipPath) {
		File file = new File(zipPath+"/Payload");
		if (file.isDirectory()) {
			String[] dirs = file.list(new MyFilenameFilter());
			if(dirs!=null && dirs.length==1){
				 return "/Payload/"+dirs[0];
			}
		}
		throw new RuntimeException("  sorry  没有找到  .app 目录 ");
	}
	/**
	 * 
	 * @param apkPath  母包路劲
	 * @param outputPath  最终路劲
	 * @param subBasePath : 子包基础路劲
	 * @param subPackageName :子包的名称
	 * @param content ：写入的文件内容
	 * @param filePath ：写入的文件路劲
	 * @author sun
	 * Jul 26, 2017
	 */
	public void addStrToApk(String parentPackagePath,String outputPath,String subBasePath,String subPackageName,String content,String filePath){
		try {
			addByteToApkByZip(parentPackagePath, outputPath,subBasePath,subPackageName,content.getBytes("utf-8"), filePath);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 新的分包方式，直接写入的 备注信息。
	 * @param apkPath  母包路劲
	 * @param outputPath  最终路劲
	 * @param subBasePath : 子包基础路劲
	 * @param subPackageName :子包的名称
	 * @param content ：写入的文件内容
	 * @param filePath ：写入的文件路劲
	 * @author sun
	 * Jul 26, 2017
	 */
	public void addStrToApkByComment(String parentPackagePath,String outputPath,String content){
		try {
			System.out.println(" ------------ addStrToApkByComment 复制文件---------★★★★★★★ -------");
			FileUtil.copyFile(parentPackagePath, outputPath, true);
			ZipFile zipFile = null;
			ByteArrayOutputStream outputStream = null;
			RandomAccessFile accessFile = null;
			try {
				File file = new File(outputPath);
				zipFile = new ZipFile(outputPath);
				String zipComment = zipFile.getComment();
				if (zipComment != null) {
					System.out.println(" ★★★★★★★ ----- 当前文件以及存在备注信息了，不能分包---------★★★★★★★ -------zipComment="+zipComment);
					return;
				}
				System.out.println(" ------------ 写入的内容-------★★★★★★★ -------content="+content);
				byte[] byteComment = content.getBytes();
				outputStream = new ByteArrayOutputStream();

				outputStream.write(byteComment);
				outputStream.write(short2Stream((short) byteComment.length));

				byte[] data = outputStream.toByteArray();
				accessFile = new RandomAccessFile(file, "rw");
				accessFile.seek(file.length() - 2);
				accessFile.write(short2Stream((short) data.length));
				accessFile.write(data);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (zipFile != null) {
						zipFile.close();
					}
					if (outputStream != null) {
						outputStream.close();
					}
					if (accessFile != null) {
						accessFile.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static byte[] short2Stream(short data) {
		ByteBuffer buffer = ByteBuffer.allocate(2);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		buffer.putShort(data);
		buffer.flip();
		return buffer.array();
	}


	public static void main(String[] args) throws Exception {
	    String outputPath = "I:\\chromeDownload\\694_csmbsc_800400003_201807231443.apk";
        ZipFile zipFile = new ZipFile(outputPath);
        String zipComment = zipFile.getComment();
        System.out.println(zipComment);
	}
}

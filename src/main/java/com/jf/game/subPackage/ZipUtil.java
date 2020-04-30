package com.jf.game.subPackage;


import org.apache.commons.lang.StringUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipOutputStream;

/**
 * 2012-9-28 上午11:03:36 
 * explain : 文件解压/打包工具
 */
public class ZipUtil {
	private static final int BUFFER = 1024; 
	private static final String BASE_DIR = "";
	/**符号"/"用来作为目录标识判断符*/
	private static final String PATH = "/";
	/**解压源文件目录*/
	private static final String SOURCE_PATH_NAME = "\\source\\";	
	/**打包目录*/
	private static final String TARGET_PATH_NAME = "\\target\\";
	
	/** 
    * 解压缩zip文件  
    * @param fileName 要解压的文件名 包含路径 如："c:\\test.zip" 
    * @param filePath 解压后存放文件的路径 如："c:\\temp" 
    * @throws Exception 
    */  
    @SuppressWarnings("rawtypes")
	public static void unZip(String fileName, String filePath) throws Exception{  
       ZipFile zipFile = new ZipFile(fileName);   
       Enumeration emu = zipFile.getEntries();
       File file_parent = new File(filePath);
       if(file_parent.exists()){
    	  delDir(file_parent);  
       	  System.out.println("当前目录存在，清理完毕。 filePath="+filePath);    
       }
       while(emu.hasMoreElements()){  
            ZipEntry entry = (ZipEntry) emu.nextElement();  
            if (entry.isDirectory()){  
                new File(filePath+entry.getName()).mkdirs();  
                continue;  
            }  
            BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(entry));  
           
            File file = new File(filePath + entry.getName());  
            File parent = file.getParentFile();  
            if(parent != null && (!parent.exists())){  
                parent.mkdirs();  
            }  
            FileOutputStream fos = new FileOutputStream(file);  
            BufferedOutputStream bos = new BufferedOutputStream(fos,BUFFER);  
      
            byte [] buf = new byte[BUFFER];  
            int len = 0;  
            while((len=bis.read(buf,0,BUFFER))!=-1){  
                fos.write(buf,0,len);  
            }  
            bos.flush();  
            bos.close();  
            bis.close();  
           }  
           zipFile.close();  
    }  
    /**
     * 删除目录
     * @param f
     * @author sun
     * Oct 27, 2017
     */
    public static void delDir(File f) {
	      try {
			// 判断是否是一个目录, 不是的话跳过, 直接删除; 如果是一个目录, 先将其内容清空.
			  if(f.isDirectory()) {
			      // 获取子文件/目录
			      File[] subFiles = f.listFiles();
			      // 遍历该目录
			      for (File subFile : subFiles) {
			          // 递归调用删除该文件: 如果这是一个空目录或文件, 一次递归就可删除. 如果这是一个非空目录, 多次
			          // 递归清空其内容后再删除
			         delDir(subFile);
			     }
			 }
			 // 删除空目录或文件
			 f.delete();
		} catch (Exception e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
	/**
	 * 压缩文件
	 * 
	 * @param srcFile
	 * @param destPath
	 * @throws Exception
	 */
	public static void compress(String srcFile, String destPath) throws Exception {
		compress(new File(srcFile), new File(destPath));
	}
	
	/**
	 * 压缩
	 * 
	 * @param srcFile
	 *            源路径
	 * @param destPath
	 *            目标路径
	 * @throws Exception
	 */
	public static void compress(File srcFile, File destFile) throws Exception {
		// 对输出文件做CRC32校验
		CheckedOutputStream cos = new CheckedOutputStream(new FileOutputStream(
				destFile), new CRC32());

		ZipOutputStream zos = new ZipOutputStream(cos);
		compress(srcFile, zos, BASE_DIR);

		zos.flush();
		zos.close();
	}
	
	/**
	 * 压缩
	 * 
	 * @param srcFile
	 *            源路径
	 * @param zos
	 *            ZipOutputStream
	 * @param basePath
	 *            压缩包内相对路径
	 * @throws Exception
	 */
	private static void compress(File srcFile, ZipOutputStream zos,
			String basePath) throws Exception {
		if (srcFile.isDirectory()) {
			compressDir(srcFile, zos, basePath);
		} else {
			compressFile(srcFile, zos, basePath);
		}
	}
	
	/**
	 * 压缩目录
	 * 
	 * @param dir
	 * @param zos
	 * @param basePath
	 * @throws Exception
	 */
	private static void compressDir(File dir, ZipOutputStream zos,
			String basePath) throws Exception {
		File[] files = dir.listFiles();
		// 构建空目录
		if (files.length < 1) {
			ZipEntry entry = new ZipEntry(basePath + dir.getName() + PATH);

			zos.putNextEntry(entry);
			zos.closeEntry();
		}
		
		String dirName = "";
		String path = "";
		for (File file : files) {
			//当父文件包名为空时，则不把包名添加至路径中（主要是解决压缩时会把父目录文件也打包进去）
			if(basePath!=null && !"".equals(basePath)){
				dirName=dir.getName(); 
			}
			path = basePath + dirName + PATH;
			// 递归压缩
			compress(file, zos, path);
		}
	}

	/**
	 * 文件压缩
	 * 
	 * @param file
	 *            待压缩文件
	 * @param zos
	 *            ZipOutputStream
	 * @param dir
	 *            压缩文件中的当前路径
	 * @throws Exception
	 */
	private static void compressFile(File file, ZipOutputStream zos, String dir)
			throws Exception {
		/**
		 * 压缩包内文件名定义
		 * 
		 * <pre>
		 * 如果有多级目录，那么这里就需要给出包含目录的文件名
		 * 如果用WinRAR打开压缩包，中文名将显示为乱码
		 * </pre>
		 */
		if("/".equals(dir))dir="";
		else if(dir.startsWith("/"))dir=dir.substring(1,dir.length());
		
		ZipEntry entry = new ZipEntry(dir + file.getName());
		zos.setMethod(ZipOutputStream.DEFLATED); 
		if(file!=null && isUnCompressByFileName(file.getName())){ //不压缩
			entry.setSize(file.length());  //size
			zos.setMethod(ZipOutputStream.STORED);
			CRC32 crc = new CRC32();
			crc.reset();
			crc.update(fileToBetyArray(file));
			entry.setCrc(crc.getValue());  
		} 
		zos.putNextEntry(entry);
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		int count;
		byte data[] = new byte[BUFFER];
		while ((count = bis.read(data, 0, BUFFER)) != -1) {
			zos.write(data, 0, count);
		}
		bis.close();

		zos.closeEntry();
	}

	/**
	 * 过滤不需要压缩的文件名称
	 * @param fileName
	 * @return default false
	 */
	private static boolean isUnCompressByFileName(String fileName) {
		if(StringUtils.isNotBlank(fileName)){
			List<String> suffixs = new ArrayList<>();
			fileName = fileName.toLowerCase();
			suffixs.add(".mp3");
			suffixs.add(".mp4");
			for (String suffix:suffixs) {
				if(fileName.endsWith(suffix)){
					return  true;
				}
			}
		}
		return false;
	}


	public  static byte[] fileToBetyArray(File file ){
		FileInputStream fileInputStream = null;
		byte[] bFile = null;
		try {
			bFile = new byte[(int) file.length()];
			fileInputStream = new FileInputStream(file);
			fileInputStream.read(bFile);
			fileInputStream.close();
			System.out.println(" 读取文件完成");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fileInputStream.close();
				bFile.clone();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bFile;
	}
	
	public static void main(String[] args)throws Exception{
		try {
			StringBuffer buffer = new StringBuffer();
			String srcPath = "D:\\data\\llk4_60057.apk";
			File srcFile = new File(srcPath);
			String parentPath = srcFile.getParent();	//源文件目录
			String fileName = srcFile.getName();		//源文件名称
			String prefixName = fileName.substring(0, fileName.lastIndexOf("."));
			//解压源文件保存路径
			String sourcePath = buffer.append(parentPath).append(SOURCE_PATH_NAME).
									append(prefixName).append("\\").toString();
			
			//------解压
			unZip(srcPath, sourcePath);
			buffer.setLength(0);
			//------ 写入文件
			String srcFileName ="D:\\data\\666.txt";
			String destFileName = buffer.append(parentPath).append(SOURCE_PATH_NAME)
					.append(prefixName).append("\\META-INF\\123123.txt").toString();
			FileUtil.copyFile(srcFileName, destFileName, true); 	
			//------打包
			String targetPath = parentPath+TARGET_PATH_NAME;
			//判断创建文件夹
			File targetFile = new File(targetPath);
			if(!targetFile.exists()){
				targetFile.mkdir();
			}
			compress(parentPath+SOURCE_PATH_NAME+prefixName,targetPath+fileName);
			
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}

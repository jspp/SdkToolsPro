package com.jf.game.subPackage;

import java.io.*;

/**
 * 文件工具类
 */
public final class FileUtil {

    /**
     * 复制单个文件
     *
     * @param srcFileName 待复制的文件名
     * @param overlay     如果目标文件存在，是否覆盖
     * @return 如果复制成功返回true，否则返回false
     */
    public static boolean copyFile(String srcFileName, String destFileName, boolean overlay) {
        File srcFile = new File(srcFileName);

        // 判断源文件是否存在
        if (!srcFile.exists()) {
            System.out.println("源文件：" + srcFileName + "不存在！");
            return false;
        } else if (!srcFile.isFile()) {
            System.out.println("复制文件失败，源文件：" + srcFileName + "不是一个文件！");
            return false;
        }

        // 判断目标文件是否存在
        File destFile = new File(destFileName);
        if (destFile.exists()) {
            // 如果目标文件存在并允许覆盖
            if (overlay) {
                // 删除已经存在的目标文件，无论目标文件是目录还是单个文件
                new File(destFileName).delete();
            }
        } else {
            // 如果目标文件所在目录不存在，则创建目录
            if (!destFile.getParentFile().exists()) {
                // 目标文件所在目录不存在
                if (!destFile.getParentFile().mkdirs()) {
                    // 复制文件失败：创建目标文件所在目录失败
                    return false;
                }
            }
        }

        // 复制文件
        int byteread = 0; // 读取的字节数
        InputStream in = null;
        OutputStream out = null;

        try {
            in = new FileInputStream(srcFile);
            out = new FileOutputStream(destFile);
            byte[] buffer = new byte[1024];

            while ((byteread = in.read(buffer)) != -1) {
                out.write(buffer, 0, byteread);
            }
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (out != null)
                    out.close();
                if (in != null)
                    in.close();
            } catch (IOException e) {
            }
        }
    }
    /**
     * 复制文件夹，如果资源文件夹存在
     * @User: jspp@qq.com
     * @Date: 2020/4/28 13:09
     */
    public static boolean copyDirIfResourceExsit(String srcDirName, String destDirName, boolean overlay) {
          if(isExsit(srcDirName)){
              return copyDir( srcDirName,  destDirName,  overlay);
          }else {
              System.out.println("copyDirIfResourceExsit "+ srcDirName + " 不存在，无法替换  跳过");
          }
          return false;
    }
    /**
     *如果目标不存在 则继续
     * @User: jspp@qq.com
     * @Date: 2018/9/12 15:34
     * @Desc  
     * @Param 
     */
    public static boolean copyDirIfResourceExsitAndDestNoExsit(String srcDirName, String destDirName, boolean overlay) {
        if(isExsit(destDirName)){
            System.out.println(destDirName + " 目标文件夹已经存在，不需要覆盖 ");
            return false;
        }
        if(isExsit(srcDirName)){
            return copyDir( srcDirName,  destDirName,  overlay);
        }else {
            System.out.println(srcDirName + " 源文件加不存在，跳过，无法替换。");
        }
        return false;
    }
    /**
     * 复制整个目录的内容
     *
     * @param srcDirName  待复制目录的目录名
     * @param destDirName 目标目录名
     * @param overlay     如果目标目录存在，是否覆盖
     * @return 如果复制成功返回true，否则返回false
     */
    public static boolean copyDir(String srcDirName, String destDirName, boolean overlay) {
        // 判断源目录是否存在
        File srcDir = new File(srcDirName);
        if (!srcDir.exists()) {
            System.out.println("复制目录失败：源目录" + srcDirName + "不存在！");
            return false;
        }
        if (!srcDir.isDirectory()) {
            System.out.println("复制目录失败：" + srcDirName + "不是目录！");
            return false;
        }
        // 如果目标目录名不是以文件分隔符结尾，则加上文件分隔符
        if (!destDirName.endsWith(File.separator)) {
            destDirName = destDirName + File.separator;
        }
        File destDir = new File(destDirName);
        // 如果目标文件夹存在
        if (destDir.exists()) {
            // 如果允许覆盖则删除已存在的目标目录
            if (overlay) {
                boolean rss = new File(destDirName).delete();
                System.out.println("当前文件标记为删除........result--->"+rss);
            } else {
                System.out.println("复制目录失败：目的目录" + destDirName + "已存在！");
                return false;
            }
        } else {
            // 创建目的目录
            System.out.println("目的目录不存在，准备创建。。。"+destDirName);
            if (!destDir.mkdirs()) {
                System.out.println("复制目录失败：创建目的目录失败！");
                return false;
            }
        }

        boolean copyResult = true;
        File[] files = srcDir.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 复制文件
            if (files[i].isFile()) {
                System.out.println("start copy file........>"+files[i].getAbsolutePath());
                copyResult = FileUtil.copyFile(files[i].getAbsolutePath(), destDirName + files[i].getName(), overlay);
                if (!copyResult)
                    break;
            } else if (files[i].isDirectory()) {
                copyResult = FileUtil.copyDir(files[i].getAbsolutePath(), destDirName + files[i].getName(), overlay);
                if (!copyResult)
                    break;
            }
        }
        if (!copyResult) {
            System.out.println("复制目录" + srcDirName + "至" + destDirName + "失败！");
            return false;
        } else {
            return true;
        }
    }



    /**
     * 如果不存在就创建 目录
     *
     * @param path
     * @return
     * @author sun
     * Jul 18, 2017
     */
    public static boolean createIfNotExsit(String path) {
        File destDir = new File(path);
        if (destDir.exists()) {
            return true;
        }
        if (destDir.mkdirs()) {
            System.out.println("开始创建当前目录:" + path);
            return true;
        }
        return false;
    }

    /**
     * 当前文件是否存在
     * @param path
     * @return
     */
    public static boolean isExsit(String path) {
        File destDir = new File(path);
        if (destDir.exists()) {
            return true;
        }
        return false;
    }


    /**
     * 删除文件 或者指定的目录
     *
     * @author ouyang 创建时间：Apr 18, 2012 10:28:17 AM
     */
    public static boolean deleteFile(String fileFullPath) {
        // 判断目标文件是否存在
        File destFile = new File(fileFullPath);
        if (destFile.exists()) {
            // 删除已经存在的目标文件，无论目标文件是目录还是单个文件
            return new File(fileFullPath).delete();
        }
        return true;
    }
    /**
     * 删除文件夹 下面的全部文件
     * @param path
     * @author jspp@qq.com
     * Feb 2, 2018
     */
    public static void deleteAllFilesOfDir(File path) {
        if (!path.exists())
            return;
        if (path.isFile()) {
            path.delete();
            return;
        }
        File[] files = path.listFiles();
        for (int i = 0; i < files.length; i++) {
            deleteAllFilesOfDir(files[i]);
        }
        path.delete();
    }

}

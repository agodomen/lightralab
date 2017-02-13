/**
 *
 */
package edu.muc.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Calendar;

import edu.muc.platform.plugin.tools.SystemTools;

/**
 * @author 龚文东
 */
public class PlatformService {

    public static boolean dataBackUp() {
        String backupPath = SystemTools.getXmlDataBackupPath() + "\\" + Calendar.YEAR + "" + Calendar.getInstance().MONTH;
        String dataPath = SystemTools.getXmlDataPath();

        File origindirectory = new File(dataPath);   //源路径File实例
        File targetdirectory = new File(backupPath);  //目标路径File实例
        File[] fileList = origindirectory.listFiles();  //目录中的所有文件
        for (File file : fileList) {
            if (!file.isFile())   //判断是不是文件
                dataBACKUP(file, targetdirectory);
            //   System.out.println(file.getName());
            try {
                FileInputStream fin = new FileInputStream(file);
                BufferedInputStream bin = new BufferedInputStream(fin);
                PrintStream pout = new PrintStream(targetdirectory.getAbsolutePath() + "/" + file.getName());
                BufferedOutputStream bout = new BufferedOutputStream(pout);
                int total = bin.available();  //文件的总大小
                int percent = total / 100;    //文件总量的百分之一
                int count;
                while ((count = bin.available()) != 0) {
                    int c = bin.read();  //从输入流中读一个字节
                    bout.write((char) c);  //将字节（字符）写到输出流中

                    if (((total - count) % percent) == 0) {
                        double d = (double) (total - count) / total; //必须强制转换成double
                        System.out.println(Math.round(d * 100) + "%"); //输出百分比进度
                    }
                }
                bout.close();
                pout.close();
                bin.close();
                fin.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return true;
    }


    public static boolean dataBACKUP(File target, File targetdirectory) {
        File origindirectory = target;   //源路径File实例
        //目标路径File实例
        File[] fileList = origindirectory.listFiles();  //目录中的所有文件
        for (File file : fileList) {
            if (file.isFile())   //判断是不是文件
                try {
                    FileInputStream fin = new FileInputStream(file);
                    BufferedInputStream bin = new BufferedInputStream(fin);
                    PrintStream pout = new PrintStream(targetdirectory.getAbsolutePath() + "/" + file.getName());
                    BufferedOutputStream bout = new BufferedOutputStream(pout);
                    int total = bin.available();  //文件的总大小
                    int percent = total / 100;    //文件总量的百分之一
                    int count;
                    while ((count = bin.available()) != 0) {
                        int c = bin.read();  //从输入流中读一个字节
                        bout.write((char) c);  //将字节（字符）写到输出流中

                        if (((total - count) % percent) == 0) {
                            double d = (double) (total - count) / total; //必须强制转换成double
                            System.out.println(Math.round(d * 100) + "%"); //输出百分比进度
                        }
                    }
                    bout.close();
                    pout.close();
                    bin.close();
                    fin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            else {
                dataBACKUP(file, targetdirectory);
            }
        }

        return false;

    }
}

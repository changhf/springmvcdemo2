package com.changhf.utils;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;

/**
 * CommandUtil
 *
 * @author <a href="mailto:longlin.ll@alibaba-inc.com">根宝</a>
 * @version V1.0.0
 * @since 2017/12/4
 */
public class CommandUtil {
    private static final Logger LOG = LoggerFactory.getLogger(CommandUtil.class);

    /**
     * 执行命令行
     *
     * @param commandStr
     * @return
     * @throws IOException
     */
    public static Long executeShell(String commandStr) throws Exception {
        Long pid = null;
        String[] commandArr = new String[]{"C:/cygwin64/bin/sh", "-c", commandStr};
//        String[] commandArr = new String[]{"sh", "-c", commandStr};
        Process process = Runtime.getRuntime().exec(commandArr);
        if (process != null) {
            //获取进程id
            Field f = process.getClass().getDeclaredField("handle");
            f.setAccessible(true);
            long handl = f.getLong(process);
            Kernel32 kernel = Kernel32.INSTANCE;
            WinNT.HANDLE handle = new WinNT.HANDLE();
            handle.setPointer(Pointer.createConstant(handl));
            pid = Long.valueOf(kernel.GetProcessId(handle));
            process.waitFor();
//            process.destroy();
        }
        return pid;
    }


    /**
     * 根据pid判断进程是否存在，如果存在，杀掉进程，并清空文件夹
     *
     * @param pid
     * @return 0表示不存在，1表示存在，因为进程号不可能重复，所以输出肯定非0即1
     */
    public static boolean isExistsProcess(String pid) {
        boolean result = false;
        BufferedReader bufferedReader = null;
        try {
            String commandStr = "ps --no-heading " + pid + " | wc -l";
            String[] commandArr = new String[]{"sh", "-c", commandStr};
//            String[] commandArr = new String[]{"C:/cygwin64/bin/sh", "-c", commandStr};
            Process process = Runtime.getRuntime().exec(commandArr);
            if (process != null) {
                //bufferedReader用于读取Shell的输出内容
                bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "gbk"), 1024);
                process.waitFor();
            }
            String line = null;
            //读取Shell的输出内容，并添加到stringBuffer中
            StringBuffer outputStr = new StringBuffer();
            while (bufferedReader != null && (line = bufferedReader.readLine()) != null) {
                outputStr.append(line).append("\n");
            }
            int outputNum = Integer.valueOf(outputStr.toString());
            result = outputNum == 1;
        } catch (Exception ioe) {
            LOG.info("执行Shell命令时发生异常：\n" + ioe.getMessage());
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 杀掉进程
     *
     * @param pid
     * @return true, 命令执行成功
     */
    public static boolean killByPid(String pid) {
        boolean result = false;
        String commandStr = "kill -9 " + pid;
        String[] commandArr = new String[]{"sh", "-c", commandStr};
//        String[] commandArr = new String[]{"C:/cygwin64/bin/sh", "-c", commandStr};
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(commandArr);
            if (process != null) {
                result = true;
            }
        } catch (IOException e) {
            LOG.info("执行kill命令时发生异常：\n" + e.getMessage());
        }
        return result;
    }

    public static void main(String[] args) {
        try {
            System.out.println(CommandUtil.executeShell("touch /home/changhf/image/abc.txt"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

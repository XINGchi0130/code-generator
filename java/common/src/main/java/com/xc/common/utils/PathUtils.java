package com.xc.common.utils;

import java.util.regex.Pattern;


public class PathUtils {
    private static final String ILLEGAL_WINDOWS_CHARS_REGEX = "[\"*/:<>?\\\\|]";
    private static final String ILLEGAL_LINUX_CHARS_REGEX = "[\\\\]";
    private static final int MAX_WINDOWS_PATH_LENGTH = 260;

    /**
     * 自动根据操作系统校验文件路径
     * @param path 待校验的文件路径
     * @return 校验结果
     * @throws IllegalArgumentException 当路径为空或格式不正确时
     */
    public static boolean validatePath(String path) {
        if (SystemUtils.isWindows()) {
            return validateWindowsPath(path);
        } else if (SystemUtils.isLinux()) {
            return validateLinuxPath(path);
        } else {
            // 不支持的操作系统
            return false;
        }
    }

    /**
     * 自动判断当前操作系统下，文件路径是否为相对路径
     * @param path 待校验的文件路径
     * @return 如果是相对路径，返回true；否则返回false
     */
    public static boolean isRelativePath(String path) {
        if (SystemUtils.isWindows()) {
            return isWindowsRelativePath(path);
        } else if (SystemUtils.isLinux()) {
            return isLinuxRelativePath(path);
        } else {
            // 不支持的操作系统
            return false;
        }
    }

    /**
     * 校验Windows文件路径是否规范
     * @param path 待校验的文件路径
     * @return 校验结果
     * @throws IllegalArgumentException 当路径为空或格式不正确时
     */
    public static boolean validateWindowsPath(String path) {
        // 文件路径不能为空
        if (path == null || path.isEmpty()) {
            return false;
        }

        // Windows文件路径应使用反斜杠 (\) 作为路径分隔符
        if (!path.contains("\\")) {
            return false;
        }

        // Windows文件路径包含非法字符
        if (Pattern.compile(ILLEGAL_WINDOWS_CHARS_REGEX).matcher(path).find()) {
            return false;
        }

        if (path.startsWith("C:\\", 0) || path.startsWith("D:\\", 0) || path.startsWith("E:\\", 0) || path.startsWith("F:\\", 0)) {
            // 分区符正确
        } else {
            // Windows绝对路径应以盘符开头
            return false;
        }

        return true;
    }

    /**
     * 校验Linux文件路径是否规范
     * @param path 待校验的文件路径
     * @return 校验结果
     * @throws IllegalArgumentException 当路径为空或格式不正确时
     */
    public static boolean validateLinuxPath(String path) {
        // 文件路径不能为空
        if (path == null || path.isEmpty()) {
            return false;
        }

        // Linux文件路径应使用正斜杠 (/) 作为路径分隔符
        if (!path.contains("/") || path.contains("\\")) {
            return false;
        }

        // Linux文件路径包含非法字符
        if (Pattern.compile(ILLEGAL_LINUX_CHARS_REGEX).matcher(path).find()) {
            return false;
        }

        // Linux应使用绝对路径
        if (!path.startsWith("/")) {
            return false;
        }

        return true;
    }

    /**
     * 校验Windows路径是否为相对路径
     * @param path 待校验的文件路径
     * @return 如果是相对路径，返回true；否则返回false
     */
    public static boolean isWindowsRelativePath(String path) {
        // 文件路径不能为空
        if (path == null || path.isEmpty()) {
            return false;
        }
        return !(path.startsWith("C:\\", 0) || path.startsWith("D:\\", 0) || path.startsWith("E:\\", 0) || path.startsWith("F:\\", 0));
    }

    /**
     * 校验Linux路径是否为相对路径
     * @param path 待校验的文件路径
     * @return 如果是相对路径，返回true；否则返回false
     */
    public static boolean isLinuxRelativePath(String path) {
        // 文件路径不能为空
        if (path == null || path.isEmpty()) {
            return false;
        }
        return !path.startsWith("/");
    }
}

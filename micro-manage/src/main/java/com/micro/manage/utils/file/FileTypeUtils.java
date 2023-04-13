package com.micro.manage.utils.file;

import org.apache.commons.lang3.StringUtils;

import java.io.File;

/**
 * @author -
 * 文件类型工具类
 */
public class FileTypeUtils {

    public final static  int SEVEN_ZERO = 70;
    public final static  int SEVEN_ONE = 71;
    public final static  int SEVEN_THREE = 73;
    public final static  int THREE = 3;
    public final static  int FIVE_SIX = 56;
    public final static  int TWO = 2;
    public final static  int SIX = 6;
    public final static  int SEVEN_FOUR = 74;
    public final static  int NINE = 9;
    public final static  int SEVEN = 7;
    public final static  int EIGHT = 8;
    public final static  int SEVEN_SEVEN = 77;
    public final static  int SIX_SIX = 66;
    public final static  int SEVEN_EIGHT = 78;
    public final static  int EIGHT_ZERO = 80;
    public final static  int FOUR = 4;
    public final static  int FIVE = 5;
    public final static  int FIVE_FIVE = 55;
    public final static  int FIVE_SEVEN = 57;
    public final static  int NINE_SEVEN = 97;

    /**
     * 获取文件类型
     * <p>
     * 例如: ruoyi.txt, 返回: txt
     *
     * @param file 文件名
     * @return 后缀（不含".")
     */
    public static String getFileType(File file) {
        if (null == file) {
            return StringUtils.EMPTY;
        }
        return getFileType(file.getName());
    }

    /**
     * 获取文件类型
     * <p>
     * 例如: ruoyi.txt, 返回: txt
     *
     * @param fileName 文件名
     * @return 后缀（不含".")
     */
    public static String getFileType(String fileName) {
        int separatorIndex = fileName.lastIndexOf(".");
        if (separatorIndex < 0) {
            return "";
        }
        return fileName.substring(separatorIndex + 1).toLowerCase();
    }

    /**
     * 获取文件类型
     *
     * @param photoByte 文件字节码
     * @return 后缀（不含".")
     */
    public static String getFileExtendName(byte[] photoByte) {
        String strFileExtendName = "JPG";
        boolean flag = (photoByte[0] == SEVEN_ONE) && (photoByte[1] == SEVEN_THREE) && (photoByte[TWO] == SEVEN_ZERO) && (photoByte[THREE] == FIVE_SIX)
                && ((photoByte[FOUR] == FIVE_FIVE) || (photoByte[FOUR] == FIVE_SEVEN)) && (photoByte[FIVE] == NINE_SEVEN);
        if (flag) {
            strFileExtendName = "GIF";
        } else if ((photoByte[SIX] == SEVEN_FOUR) && (photoByte[SEVEN] == SEVEN_ZERO) && (photoByte[EIGHT] == SEVEN_THREE) && (photoByte[NINE] == SEVEN_ZERO)) {
            strFileExtendName = "JPG";
        } else if ((photoByte[0] == SIX_SIX) && (photoByte[1] == SEVEN_SEVEN)) {
            strFileExtendName = "BMP";
        } else if ((photoByte[1] == EIGHT_ZERO) && (photoByte[TWO] == SEVEN_EIGHT) && (photoByte[THREE] == SEVEN_ONE)) {
            strFileExtendName = "PNG";
        }
        return strFileExtendName;
    }
}
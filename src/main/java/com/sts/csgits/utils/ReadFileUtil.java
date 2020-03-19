package com.sts.csgits.utils;

import java.io.*;

/**
 * 读取文件或文件夹内容
 */
public class ReadFileUtil {

    /**
     * 读取文件或文件夹内容。
     * @param fileName 文件或文件夹路径。
     * @return 当参数为文件的路径时，返回文件内容的String串；当参数为文件夹路径时，返回文件夹下各文件的列表清单。
     */
    @SuppressWarnings("resource")
    public static String readFile(String fileName) {
        String output = "";
        File file = new File(fileName);
        if (file.exists()) {
            if (file.isFile()) {
                try {
                    InputStreamReader read = new InputStreamReader(new FileInputStream(fileName), "UTF-8");
                    BufferedReader input = new BufferedReader(read);
                    StringBuffer buffer = new StringBuffer();
                    String text;

                    while ((text = input.readLine()) != null) {
                        buffer.append(text);
                    }
                    output = buffer.toString();
                } catch (IOException ioException) {
                    System.err.println("File Error!");
                }
            } else if (file.isDirectory()) {
                String[] dir = file.list();
                output += "Directory contents:\n";

                for (int i = 0; i < dir.length; i++) {
                    output += dir[i] + "\n";
                }
            }
        } else {
            System.err.println("The file does not exist!");
        }
        return output;
    }
}

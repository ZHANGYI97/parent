package com.ziyi.common.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;

public class IoUtils {

    private static final Logger logger = LoggerFactory.getLogger(IoUtils.class);

    /**
     * 读取文件中的全部内容，并以string返回，换行使用各自系统的lineSeparator()
     * @param file
     * @param encoding
     * @return
     * @throws Exception
     */
    public static String readAsText(File file, String encoding) throws Exception {
        return readAsText(new FileInputStream(file), encoding);
    }

    /**
     * 读取文件中的全部内容，并以string返回，换行使用各自系统的lineSeparator()
     * 注意，读取完，流会关闭
     * @param in
     * @param encoding
     * @return
     * @throws Exception
     */
    public static String readAsText(InputStream in, String encoding) throws Exception {
        return readAsText(in, encoding, System.lineSeparator());
    }

    /**
     * 读取文件中的全部内容，并以string返回，换行使用各自系统的lineSeparator()
     * 注意，读取完，流会关闭
     * @param in
     * @param encoding
     * @param lineSeparetor
     * @return
     * @throws Exception
     */
    public static String readAsText(InputStream in, String encoding, String lineSeparetor) throws Exception{
        final StringBuilder builder = new StringBuilder();
        travelAsText(in, encoding, (idx, line)->{
            if (builder.length() > 0){
                builder.append(lineSeparetor);
            }
            builder.append(line);
        });
        return builder.toString();
    }

    /**
     * 读取文件内容，返回一个字符数组，每一行对应数组中的一个坐标
     * @param file
     * @param encoding
     * @return
     * @throws Exception
     */
    public static String[] readLinesAsText(File file, String encoding) throws Exception{
        final List<String> buff = new LinkedList<>();
        travelAsText(new FileInputStream(file), encoding, (idx, line)->{
            buff.add(line);
        });
        return buff.toArray(new String[0]);
    }

    /**
     * 将输入流转成byte数组
     * @param in
     * @return
     * @throws Exception
     */
    public static byte[] readToEnd(InputStream in) throws Exception {
        int bufferSize = 1023*10;
        byte[] buffer = new byte[bufferSize];
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int read;
        while ((read = in.read()) != -1){
            outputStream.write(buffer, 0, read);
        }
        return outputStream.toByteArray();
    }

    /**
     * 读取输入全部内容，每读取一行，调用一次lineHandler处理
     * 读取完，输入流会关闭
     * @param in
     * @param encoding
     * @param lineHandler
     * @throws Exception
     */
    public static void travelAsText(InputStream in, String encoding, BiConsumer<Long, String> lineHandler) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, encoding));
        String line = null;
        try {
            long lineIndex = 0;
            while ((line = reader.readLine()) != null){
                lineHandler.accept(lineIndex, line);
            }
        } catch (Exception e){
            logger.error("文件读取失败");
            throw new Exception("travelAsText文件读取失败", e);
        } finally {
            reader.close();
            in.close();
        }
    }

    /**
     * 关闭流
     * @param stream
     */
    public static void closeQuietly(Closeable stream) {
        if (stream != null){
            try {
                stream.close();
            } catch (Exception e) {
                logger.error("close stream {} error", stream);
            }
        }
    }


}

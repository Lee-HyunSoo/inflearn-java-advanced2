package io.file.copy;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileCopyMainV2 {

    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        FileInputStream fis = new FileInputStream("temp/copy.dat");
        FileOutputStream fos = new FileOutputStream("temp/copy_new.dat");

        // InputStream을 읽어서 OutputStream 으로 보내라.
        fis.transferTo(fos); // 176ms

        fis.close();
        fos.close();
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken: " + (endTime - startTime) + "ms");

        // transferTo() 는 성능 최적화가 많이 되어있어서,
        // 직접 읽어서 쓰는 것보다 비교적 좀 더 빠르다.
    }
}

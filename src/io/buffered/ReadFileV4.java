package io.buffered;

import java.io.FileInputStream;
import java.io.IOException;

import static io.buffered.BufferedConst.FILE_NAME;

public class ReadFileV4 {

    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream(FILE_NAME);
        long startTime = System.currentTimeMillis();

        byte[] bytes = fis.readAllBytes();
        fis.close();

        long endTime = System.currentTimeMillis();
        System.out.println("File name: " + FILE_NAME);
        System.out.println("File size: " + bytes.length / 1024 / 1024 + "MB");
        System.out.println("Time taken: " + (endTime - startTime) + "ms");

        // readAllByte()는 자바 버전에 따라 조금씩 다르지만,
        // 보통 4KB, 8KB, 16KB 단위로 데이터를 읽어들인다.
        // 때문에 8KB 버퍼를 사용했던 코드와 실행 시간 차이가 크게 나지 않는다.
    }
}

package io.buffered;

import java.io.FileOutputStream;
import java.io.IOException;

import static io.buffered.BufferedConst.*;

public class CreateFileV2 {

    public static void main(String[] args) throws IOException {
        FileOutputStream fos = new FileOutputStream(FILE_NAME);
        long startTime = System.currentTimeMillis();

        byte[] buffer = new byte[BUFFER_SIZE];
        int bufferIndex = 0;

        for (int i = 0; i < FILE_SIZE; i++) {
            buffer[bufferIndex++] = 1;
            // 버퍼가 가득 차면 쓰고, 버퍼를 비운다.
            if (bufferIndex == BUFFER_SIZE) {
                fos.write(buffer);
                bufferIndex = 0;
            }
        }

        // 끝 부분에 오면 버퍼가 가득차지 않고, 남아있을 수 있다.
        // 버퍼에 남은 부분 쓰기
        if (bufferIndex > 0) {
            fos.write(buffer, 0, bufferIndex);
        }
        fos.close();

        long endTime = System.currentTimeMillis();
        System.out.println("File created: " + FILE_NAME);
        System.out.println("File size: " + FILE_SIZE / 1024 / 1024 + "MB");
        System.out.println("Time taken: " + (endTime - startTime) + "ms");

        // 약 13ms 소모. 이전엔 18000ms 정도?
        // 이전보다 약 1000배 이상 빨라졌다.

        // 버퍼를 통해 많은 데이터를 한번에 전달하면 성능 최적화가 가능하다.
        // 그렇지만 버퍼의 크기가 커진다고 속도가 계속 줄어들지는 않는다.
        // 왜냐하면 디스크나 파일 시스템에서 데이터를 읽고 쓰는 기본 단위가
        // 보통 4KB 또는 8KB 이기 때문이다.
        // 즉, 버퍼에 많은 데이터를 담아 보내도 디스크나 파일 시스템에서
        // 4KB 나 8KB로 나눠 저장하기 때문에 효율에는 한계가 있다.
        // 따라서 버퍼의 크기는 보통 4KB, 8KB 정도로 잡는 것이 효율적이다.
    }
}

package io.buffered;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static io.buffered.BufferedConst.*;

public class CreateFileV3 {

    public static void main(String[] args) throws IOException {
        FileOutputStream fos = new FileOutputStream(FILE_NAME);
        // 내부에 버퍼 기능을 가지고 있는 OutputStream
        BufferedOutputStream bos = new BufferedOutputStream(fos, BUFFER_SIZE);

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < FILE_SIZE; i++) {
            bos.write(1);
        }
        bos.close();

        long endTime = System.currentTimeMillis();
        System.out.println("File created: " + FILE_NAME);
        System.out.println("File size: " + FILE_SIZE / 1024 / 1024 + "MB");
        System.out.println("Time taken: " + (endTime - startTime) + "ms");

        // BufferedOutputStream은 내부에서 byte[] 버퍼를 가지고 있다.
        // write() 메서드로 스트림에 byte를 전달하면 버퍼에 보관한다.
        // 버퍼가 가득차면 FileOutputStream.write(byte[]) 메서드를 호출한다.
        // 이후 버퍼를 비우고, 다시 채우고 보내고 반복한다.

        // 버퍼가 다 차지 않아도 버퍼에 남아있는 데이터를 보내려면 flush() 메서드 사용
        // 이러면 남아있는 데이터를 보내고 비운다.
        // close()를 호출하면 내부에서 flush()를 호출해준다.

        // 또한, 연결된 stream의 close()도 호출해준다.
        // 때문에 FileOutputStream의 자원도 정리된다.
        // 이 때 꼭 마지막에 연결한 stream을 닫아야한다.

        // FileOutputStream을 먼저 닫으면 BufferedOutputStream의 close()는 호출되지 않는다.
        // 때문에 BufferedOutputStream의 flush()도 호출되지 않는다.
        // 즉, 자원 정리도 안되고 버퍼에 남아있는 데이터가 파일에 저장되지 않는다.
        // 따라서 스트림을 연결해서 사용할 땐 반드시 마지막에 연결한 스트림을 닫아줘야한다.
    }

    // 기본 스트림, 보조 스트림?
    // FileOutputStream과 같이 단독으로 사용할 수 있는 스트림을 기본 스트림
    // BufferedOutputStream과 같이 단독으로 사용할 수 없고, 보조 기능을 제공하는 스트림을 보조 스트림
}

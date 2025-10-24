package io.start;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class StreamStartMain4 {

    public static void main(String[] args) throws IOException {
        FileOutputStream fos = new FileOutputStream("temp/hello.dat");
        byte[] input = {65, 66, 67, 68};
        fos.write(input);
        fos.close();

        FileInputStream fis = new FileInputStream("temp/hello.dat");
        // 스트림이 끝날 때 까지 (파일의 끝까지 모든 데이터를 읽어온다)
        byte[] readBytes = fis.readAllBytes();
        System.out.println(Arrays.toString(readBytes));
        fis.close();
        
        // 한번에 다 읽어오면 되는데 왜 귀찮게 나눠서 읽는 것일까?
        // 1. 부분적으로 읽거나, 읽은 내용을 처리하며 계속 읽어야하는 경우
        // 2. 메모리의 사용량을 제어 가능 (대용량 파일 처리 시)
        // 예) 100M 파일을 1M 단위로 나눠 읽고 처리하면 한번에 최대 1M 메모리만 사용
        
        // readAllBytes()는 편리하지만, 메모리 사용량을 제어할 수 없어
        // 파일이 너무 큰 경우 OutOfMemoryError 발생 가능성 존재
    }

    // 현대의 컴퓨터는 bit 단위는 너무 작아서 기본 단위로 byte 단위를 사용
    // 데이터를 주고 받는 것을 input, output 이라고 한다.

    // 자바 내부의 데이터를 외부의 파일에 저장하거나, 네트워크를 통해 전송하거나, 콘솔에 출력할 때
    // 모두 byte 단위로 데이터를 주고 받는다.

    // 만약 파일, 네트워크, 콘솔 모두 데이터를 주고 받는 방식이 다르다면 불편할 것
    // 이를 위해 자바는 InputStream, OutputStream 이라는 기본 추상 클래스 제공
}

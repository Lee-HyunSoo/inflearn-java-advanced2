package io.start;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class StreamStartMain2 {

    // 참고: stream은 단방향
    public static void main(String[] args) throws IOException {
        // 자바 밖으로 내보낼 때
        FileOutputStream fos = new FileOutputStream("temp/hello.dat");
        fos.write(65);
        fos.write(66);
        fos.write(67);
        // 자바 외부의 자원을 사용할 때는 반드시 닫아줘야한다.
        // 자바 내부 객체는 GC가 처리하지만 외부 자원은 안되기 때문
        fos.close();

        // 자바 안으로 읽어올 때
        FileInputStream fis = new FileInputStream("temp/hello.dat");
        int data;
        while ((data = fis.read()) != -1) {
            // fis.read() = 65
            // data = 65 대입
            System.out.println(data);
        }
        fis.close();

        // fis.read() 가 byte가 아니라 int를 반환하는 이유?
        // 1. 부호 없는 바이트 표현
        // 자바에서 byte는 부호 있는 8비트 값(-128~127) 이다.
        // int로 반환함으로써 0에서 255까지의 모든 가능한 바이트 값을 부호 없이 표현할 수 있다.

        // 2. EOF 표시
        // byte를 표현하려면 256 종류의 값을 모두 사용해야한다.
        // 하지만 byte로 표현하려면 -128~127로 표현해야하는데, EOF를 위한 특별한 값을 할당하기 어렵다.
    }
}

package io.start;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class StreamStartMain1 {

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
        System.out.println(fis.read());
        System.out.println(fis.read());
        System.out.println(fis.read());
        System.out.println(fis.read()); // -1, 파일의 끝(EOF) 도달
        fis.close();
    }
}

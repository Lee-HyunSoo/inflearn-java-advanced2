package io.start;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static java.nio.charset.StandardCharsets.*;

public class PrintStreamMain {

    public static void main(String[] args) throws IOException {
        // System.out 은 사실 PrintStream 이다.
        PrintStream printStream = System.out;

        byte[] bytes = "Hello!\n".getBytes(UTF_8);
        printStream.write(bytes); // OutputStream 부모 클래스 기능
        printStream.println("Print!"); // PrintStream 자체 기능

        // PrintStream은 자바가 시작될 때 자동응로 만들어진다.
        // println은 내가 입력한 문자열을 byte 배열로 바꿔 스트림에 넣어주는 역할
    }
}

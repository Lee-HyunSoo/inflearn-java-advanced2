package io.text;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static io.text.TextConst.FILE_NAME;
import static java.nio.charset.StandardCharsets.*;

public class ReaderWriterMainV2 {

    public static void main(String[] args) throws IOException {
        String writeString = "ABC";
        System.out.println("write String: " + writeString);

        // 파일에 쓰기
        FileOutputStream fos = new FileOutputStream(FILE_NAME);
        // 자바는 문자를 쓸 때 writer 라고 한다.
        // 즉, 문자를 write 하면 byte로 인코딩해 스트림에 넣어준다.
        OutputStreamWriter osw = new OutputStreamWriter(fos, UTF_8);
        osw.write(writeString);
        osw.close();

        // 파일에서 읽기
        FileInputStream fis = new FileInputStream(FILE_NAME);
        // 자바는 문자를 읽을 때 reader 라고 한다.
        // 즉, 파일을 읽을 때 읽은 내용을 문자로 디코딩해준다.
        InputStreamReader isr = new InputStreamReader(fis, UTF_8);

        StringBuilder content = new StringBuilder();
        int ch;
        while ((ch = isr.read()) != -1) {
            // 기존 read()와 다른게, InputStreamReader의 read()는
            // 문자에 해당하는 char 값을 반환한다. (타입은 int로)
            // int 값을 char로 캐스팅하면 자바가 알아서 글자로 바꿔줌

            // 왜 int로 반환?
            // 1. 자바의 char 형은 양수만 표현가능, -1 표현 불가능
            // 2. char은 2byte 라서 문자를 쓸 때 다 써야함
            content.append((char) ch);
        }
        isr.close();

        System.out.println("read String: " + content);

        // OutputStreamWriter의 부모는 OutputStream이 아니라 Writer 이다.
        // InputStreamReader의 부모도 InputStream이 아니라 Reader 이다.
        // 자바는 byte를 다루는 I/O 클래스와 문자를 다루는 I/O 클래스를 둘로 나눠뒀기 때문.

        // byte를 다루는 클래스는 OutputStream, InputStream의 자식
        // 클래스 이름 마지막에 보통 OutputStream, InputStream이 붙어있다.

        // 문자를 다루는 클래스는 Writer, Reader의 자식
        // 클래스 이름 마지막에 보통 Writer, Reader가 붙어있다.
        // 때문에 write(String) 이 가능했고, OutputStreamWriter는 문자를 받아서
        // byte로 변경한 다음에 byte를 다루는 OutputStream으로 데이터를 전달

        // 중요 !
        // 모든 데이터는 byte 단위(숫자)로 저장된다.
        // 따라서 Writer가 아무리 문자를 다룬다고해도 문자를 바로 저장할 수는 없다.
        // 이 클래스에 문자를 전달하면 결과적으로 내부에서 byte로 인코딩 후 저장한다.
    }
}

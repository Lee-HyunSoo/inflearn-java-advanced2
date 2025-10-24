package io.text;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static io.text.TextConst.FILE_NAME;
import static java.nio.charset.StandardCharsets.UTF_8;

public class ReaderWriterMainV3 {

    public static void main(String[] args) throws IOException {
        String writeString = "ABC";
        System.out.println("write String: " + writeString);

        // 파일에 쓰기
        FileWriter fw = new FileWriter(FILE_NAME, UTF_8);
        fw.write(writeString);
        fw.close();

        // 파일에서 읽기
        StringBuilder content = new StringBuilder();
        FileReader fr = new FileReader(FILE_NAME, UTF_8);
        int ch;
        while ((ch = fr.read()) != -1) {
            content.append((char) ch);
        }
        fr.close();

        System.out.println("read String = " + content);

        // FileWriter는 사실 내부에서 스스로 FileOutputStream을 하나 생성해서 사용
        // 또한, FileWriter는 OutputStreamWriter를 상속.
        // 때문에 문자를 쓰면 FileWriter 내부에서 인코딩 셋을 사용해 문자를 byte로 변환,
        // FileOutputStream을 사용해 파일에 저장

        // FileReader도 마찬가지로 내부에서 FileInputStream을 하나 생성해서 사용
        // 데이터를 읽을때도 내부에서는 FileInputStream을 사용해 데이터를 byte로 읽음
        // 그리고 문자집합을 사용해 byte[]를 char로 디코딩
    }
}

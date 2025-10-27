package io.file.copy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileCopyMainV3 {

    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();

        Path source = Path.of("temp/copy.dat");
        Path target = Path.of("temp/copy_new.dat");
        Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING); // 87ms

        long endTime = System.currentTimeMillis();
        System.out.println("Time taken: " + (endTime - startTime) + "ms");

        // 앞의 예제들은 파일 -> 자바 메모리 -> 파일의 과정
        // Files.copy()는 자바에 파일 데이터를 안불러오고 OS의 파일 복사 명령어를 사용한다.
        // 때문에 파일 -> 파일로 간다.
    }
}

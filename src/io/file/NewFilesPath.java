package io.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class NewFilesPath {

    public static void main(String[] args) throws IOException {
        Path path = Path.of("temp/..");
        System.out.println("path = " + path);

        // 절대 경로 (프로그램 시작부터 쭉 오는 것)
        // C:\workspace\inflearn\java-adv2\temp\..
        System.out.println("Absolute path = " + path.toAbsolutePath());

        // 정규 경로 (뒤에 .. 이런 것 까지 다 계산한 것)
        // C:\workspace\inflearn\java-adv2
        System.out.println("Canonical path = " + path.toRealPath());

        Stream<Path> pathStream = Files.list(path);
        List<Path> list = pathStream.toList();
        pathStream.close();

        for (Path p : list) {
            System.out.println( (Files.isRegularFile(p) ? "F" : "D") + " | " + p.getFileName());
        }
    }
}

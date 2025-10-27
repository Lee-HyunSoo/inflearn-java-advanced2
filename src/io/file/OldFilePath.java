package io.file;

import java.io.File;
import java.io.IOException;

public class OldFilePath {

    public static void main(String[] args) throws IOException {
        // temp 위치에서 cd ..
        File file = new File("temp/..");
        System.out.println("path = " + file.getPath());

        // 절대 경로 (프로그램 시작부터 쭉 오는 것)
        // C:\workspace\inflearn\java-adv2\temp\..
        System.out.println("Absolute path = " + file.getAbsolutePath());

        // 정규 경로 (뒤에 .. 이런 것 까지 다 계산한 것)
        // C:\workspace\inflearn\java-adv2
        System.out.println("Canonical path = " + file.getCanonicalPath());

        File[] files = file.listFiles();
        for (File f : files) {
            System.out.println( (f.isFile() ? "F" : "D") + " | " + f.getName());
        }
    }
}

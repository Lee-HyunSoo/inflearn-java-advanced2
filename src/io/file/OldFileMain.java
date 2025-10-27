package io.file;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class OldFileMain {

    public static void main(String[] args) throws IOException {
        File file = new File("temp/example.txt");
        File directory = new File("temp/exampleDir");

        System.out.println("File exists: " + file.exists());

        boolean created = file.createNewFile();
        System.out.println("File created: " + created);

        boolean dirCreated = directory.mkdir();
        System.out.println("Directory created: " + dirCreated);

//        boolean deleted = file.delete();
//        System.out.println("File deleted: " + deleted);

        System.out.println("Is file: " + file.isFile());
        System.out.println("Is directory: " + directory.isDirectory());
        System.out.println("File Name: " + file.getName());
        System.out.println("File size: " + file.length() + " bytes");

        File newFile = new File("temp/newExample.txt");
        boolean renamed = file.renameTo(newFile);
        System.out.println("File renamed: " + renamed);

        long lastModified = newFile.lastModified();
        System.out.println("Last modified: " + new Date(lastModified));

        // File은 파일과 디렉토리를 둘다 다룬다.
        // File 객체를 생성했다고 파일과 디렉토리가 바로 만들어지는 것은 아니다.
        // 메서드를 호출해 생성해야한다.
        // 그리고 과거 호환을 위해 남아있는거지, Files 로 대체 가능
    }
}

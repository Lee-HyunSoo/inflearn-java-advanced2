package io.buffered;

import java.io.FileInputStream;
import java.io.IOException;

import static io.buffered.BufferedConst.FILE_NAME;

public class ReadFileV1 {

    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream(FILE_NAME);
        long startTime = System.currentTimeMillis();

        int fileSize = 0;
        int data;
        while ((data = fis.read()) != -1) {
            // 파일 크기가 10MB 이기 때문에 fis.read() 메서드를 10MB(약 1000만번) 호출
            fileSize++;
        }
        fis.close();

        long endTime = System.currentTimeMillis();
        System.out.println("File name: " + FILE_NAME);
        System.out.println("File size: " + fileSize / 1024 / 1024 + "MB");
        System.out.println("Time taken: " + (endTime - startTime) + "ms");

        // 10MB 파일 쓰기에 15초, 읽는데 10초정도 걸렸다.
        // 왜 이렇게 오래 걸릴까?
        // 자바에서 1byte씩 디스크에 데이터를 전달하기 때문
        // 디스크는 1byte의 데이터를 받아서 1byte의 데이터를 쓰는 과정을 1000만번 반복

        // write() 나 read() 를 호출할 때마다 OS의 시스템 콜을 통해 파일을 읽거나 쓰는 명령어 전달
        // 이러한 시스템 콜은 상대적으로 무거운 작업

        // HDD, SSD 같은 장치들도 하나의 데이터를 읽고 쓸 때마다 필요한 시간 존재
        // HDD의 경우 더 느린데, 물리적으로 디스크의 회전이 필요
        
        // 즉, 창고에서 마트까지 상품을 전달하는데 화물차 한번에 하나의 물건만 가지고 이동한 것
        // 화물차가 1000만번 이동해야한다. 이동에 시간을 대부분 써버린 것
        // 화물차에 더 많은 상품을 담아 보낼 수 있다면 성능을 극적으로 개선 할 수 있다.

        // 참고!
        // 자바에서 OS를 통해 디스크에 1byte씩 전달하면, OS나 하드웨어 레벨에서 여러 최적화 발생
        // 즉, 실제로 디스크에 1byte씩 계속 쓰는 것은 아니다. (그랬다면 더 느림)
        // 하지만 자바에서 1byte씩 write()나 read()를 호출하면 OS 로의 시스템 콜이 발생하고,
        // 이 시스템 콜 자체가 상당한 오버헤드 유발

        // OS와 하드웨어가 어느정도 최적화를 제공하더라도,
        // 자주 발생하는 시스템 콜로 인한 성능저하는 피할 수 없다.
        // 결국 자바에서 read(), write() 호출 횟수를 최대한 줄여야한다.
    }
}

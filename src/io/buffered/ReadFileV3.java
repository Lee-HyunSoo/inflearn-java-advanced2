package io.buffered;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import static io.buffered.BufferedConst.BUFFER_SIZE;
import static io.buffered.BufferedConst.FILE_NAME;

public class ReadFileV3 {

    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream(FILE_NAME);
        BufferedInputStream bis = new BufferedInputStream(fis, BUFFER_SIZE);
        long startTime = System.currentTimeMillis();

        int fileSize = 0;
        int data;
        while ((data = bis.read()) != -1) {
            // 파일 크기가 10MB 이기 때문에 fis.read() 메서드를 10MB(약 1000만번) 호출
            fileSize++;
        }
        bis.close();

        long endTime = System.currentTimeMillis();
        System.out.println("File name: " + FILE_NAME);
        System.out.println("File size: " + fileSize / 1024 / 1024 + "MB");
        System.out.println("Time taken: " + (endTime - startTime) + "ms");

        // bis.read()를 호출하면, 먼저 현재 버퍼 상태를 확인한다.
        // 1. FileInputStream.read(byte[])를 사용해 버퍼 크기만큼 데이터를 불러온다.
        // 2. 버퍼에 보관한 데이터 중 1byte를 반환한다.
        // 3. 이후 read()가 또 호출되면, 버퍼에 있는 데이터 중 1byte를 반환한다.
        // 4. 버퍼가 비어있으면 1로 돌아간다.
    }
    
    // 버퍼 직접 쓴 경우 (13ms~) | BufferedXxx의 경우 (100ms~)
    // 버퍼를 직접 다루는 것보다 BufferedXxx의 성능이 떨어지는 이유?
    // 동기화 코드가 들어가 있기 때문. BufferedXxx 내부에는 lock()이 다 걸려있다.
    // 락을 걸고 푸는 코드도 1000만번 호출되기 때문에 좀 더 느린 것

    // BufferedXxx 클래스의 특징?
    // BufferedXxx 클래스는 멀티스레드를 고려해서 만든 클래스
    // 따라서 멀티스레드 상황에 안전하지만, 성능이 약간 저하된다.
    // 하지만 일반적인 상황이라면 이정도 성능 하락은 싱글스레드여도 크게 문제되지않는다.
    // 다만, 매우 큰 데이터를 다뤄야하고 성능 최적화가 중요한 상황이면 직접 버퍼를 다뤄야한다.
    // 동기화 락이 없는 BufferedXxx 클래스는 없기 때문.
    // 필요하면 BufferedXxx 클래스를 그대로 가져와 동기화 코드만 지워도 된다.
}

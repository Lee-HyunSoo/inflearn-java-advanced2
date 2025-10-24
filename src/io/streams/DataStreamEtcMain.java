package io.streams;

import java.io.*;

public class DataStreamEtcMain {

    public static void main(String[] args) throws IOException {
        FileOutputStream fos = new FileOutputStream("temp/data.dat");
        DataOutputStream dos = new DataOutputStream(fos);


        dos.writeUTF("회원A"); // UTF-8로 인코딩
        dos.writeInt(20); // 4byte, 자바의 byte를 그대로 저장
        dos.writeDouble(10.5); // 8byte, 자바의 byte를 그대로 저장
        dos.writeBoolean(true); // 1byte, 자바의 byte를 그대로 저장
        dos.close();

        FileInputStream fis = new FileInputStream("temp/data.dat");
        DataInputStream dis = new DataInputStream(fis);

        // 반드시 저장한 순서대로 읽어야한다!
        // 커서가 움직이는 느낌
        System.out.println(dis.readUTF());
        System.out.println(dis.readInt());
        System.out.println(dis.readDouble());
        System.out.println(dis.readBoolean());
    }
}

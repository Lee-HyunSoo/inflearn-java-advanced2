package network.tcp.v1;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressMain {

    public static void main(String[] args) throws UnknownHostException {
        InetAddress localhost = InetAddress.getByName("localhost");
        System.out.println(localhost); // localhost/127.0.0.1

        InetAddress google = InetAddress.getByName("google.com");
        // 구글의 IP 경우 지역이나 위치에 따라 조금씩 달라질 수 있다.
        System.out.println(google); // google.com/142.250.207.110

        // 자바는 InetAddress.getByName("호스트명") 메서드를 통해 해당하는 IP 주소를 조회한다.
        // 1. 먼저 시스템의 호스트 파일을 확인한다.
        // - /etc/hosts (리눅스)
        // - c:/windows/system32/drivers/etc/hosts (윈도우)
        // 2. 호스트 파일에 정의되어 있지 않다면, DNS 서버에 요청해 IP 주소를 얻는다.
    }

    // TCP/IP 통신에서는 통신할 대상 서버를 찾을 때 호스트 이름이 아니라 IP 주소가 필요하다.
}

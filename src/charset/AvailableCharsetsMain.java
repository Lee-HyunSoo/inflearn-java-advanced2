package charset;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.SortedMap;

public class AvailableCharsetsMain {

    public static void main(String[] args) {
        // 이용 가능한 모든 Charset 자바 + OS
        SortedMap<String, Charset> charsets = Charset.availableCharsets();
        for (String charsetName : charsets.keySet()) {
            System.out.println("charsetName = " + charsetName);
        }

        System.out.println("=====");
        // 문자로 조회 (대소문자 구분x)
        Charset charset1 = Charset.forName("MS949");
        System.out.println("charset1 = " + charset1);

        // 별칭
        Set<String> aliases = charset1.aliases();
        for (String alias : aliases) {
            System.out.println("alias = " + alias);
        }

        // UTF-8 문자로 조회
        Charset charset2 = Charset.forName("UTF-8");
        System.out.println("charset2 = " + charset2);

        // UTF-8 상수로 조회
        Charset charset3 = StandardCharsets.UTF_8;
        System.out.println("charset3 = " + charset3);

        // 시스템의 기본 Charset 조회
        // 컴퓨터에 텍스트로 된 파일을 저장할 때는 항상 문자 집합을 지정해줘야함.
        // 생략해도 될 때가 있는데, 생략할 때는 시스템에서 사용하는 기본 문자집합을 반환함.
        Charset defaultCharset = Charset.defaultCharset();
        System.out.println("defaultCharset = " + defaultCharset);
    }
}

package charset;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static java.nio.charset.StandardCharsets.*;

public class EncodingMain1 {

    private static final Charset EUC_KR = Charset.forName("EUC-KR");
    private static final Charset MS_949 = Charset.forName("MS949");

    public static void main(String[] args) {
        System.out.println("== ASCII 영문 처리 ==");
        encoding("A", US_ASCII);
        encoding("A", ISO_8859_1); // [65] : ASCII + 서유럽 문자
        encoding("A", EUC_KR); // [65] : ASCII + 한글
        encoding("A", UTF_8); // [65]
        encoding("A", UTF_16BE); // [0, 65] : 2byte 단위로 끊어서 저장하기 때문

        System.out.println("== 한글 지원 ==");
        encoding("가", EUC_KR); // [-80, -95] : 한글에 2byte 사용
        encoding("가", MS_949); // [-80, -95]
        encoding("가", UTF_8); // [-22, -80, -128] : UTF-8은 한글에 3byte 사용
        encoding("가", UTF_16BE); // [-84, -0]

        // UTF_16? UTF_16BE? UTF_16LE?
        // BE, LE는 순서의 차이 [-84, 0] / [0, -84]
        // 그냥 UTF_16은 인코딩한 문자가 BE, LE 중 어떤 것인지 알려주는 2byte가 앞에 추가
        // [-2, -1, -84, 0] 이런식으로.

        String str = "hello";
        // 이렇게 지정해주지 않은 경우 시스템의 기본 문자 집합(Charset.defaultCharset())을 사용
        byte[] bytes = str.getBytes();
        System.out.println("bytes = " + Arrays.toString(bytes));

        // byte 출력에 마이너스 숫자가 보이는 이유?
        // 1byte는 8개의 bit로 구성 -> 256가지 경우 표현 가능

        // 한글 '가' 의 경우 EUC-KR에서 인코딩하면?
        // 2byte로 표현, [10110000, 10100001] == 10진수로 [176, 161]

        // 자바에서의 byte 표현?
        // 자바의 byte 타입은 양수와 음수를 모두 표현 가능.
        // 자바의 byte는 첫 bit가 0이면 양수, 1이면 음수로 간주
        // 즉, 자바의 byte는 256가지 값을 표현하지만, 표현 가능한 숫자 범위는 -128~127이다.
        // [10110000, 10100001] -> 첫 비트가 1이므로 음수, 2의 보수 계산
    }

    private static void encoding(String text, Charset charset) {
        // 문자를 byte로 변경하려면 문자 집합이 꼭 필요하다.
        byte[] bytes = text.getBytes(charset);
        System.out.printf("%s -> [%s] 인코딩 -> %s %sbyte\n", text, charset, Arrays.toString(bytes), bytes.length);
    }
}

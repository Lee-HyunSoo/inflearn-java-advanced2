package charset;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static java.nio.charset.StandardCharsets.*;

public class EncodingMain2 {

    private static final Charset EUC_KR = Charset.forName("EUC-KR");
    private static final Charset MS_949 = Charset.forName("MS949");

    public static void main(String[] args) {
        System.out.println("== 영문 ASCII 인코딩 ==");
        test("A", US_ASCII, US_ASCII);
        test("A", US_ASCII, ISO_8859_1); // ASCII 확장 (LATIN-1)
        test("A", US_ASCII, EUC_KR); // ASCII 포함
        test("A", US_ASCII, MS_949); // ASCII 포함
        test("A", US_ASCII, UTF_8); // ASCII 포함
        test("A", US_ASCII, UTF_16BE); // UTF16은 ASCII가 2byte로 구성, 디코딩 실패

        System.out.println("== 한글 인코딩 - 기본 ==");
        // 인코딩을 하긴 하는데 뭔지 모르면 ?(63) 을 넣어버린다.
        test("가", US_ASCII, US_ASCII); // 63 -> ?
        test("가", US_ASCII, ISO_8859_1); // 63 -> ?
        test("가", EUC_KR, EUC_KR); // [-80, -95]
        test("가", MS_949, EUC_KR); // [-80, -95]
        test("가", UTF_8, UTF_8); // [-22, -80, -128]
        test("가", UTF_16BE, UTF_16BE); // [-84, 0]

        System.out.println("== 한글 인코딩 - 복잡한 문자 ==");
        test("뷁", EUC_KR, EUC_KR); // 63 -> ?
        test("뷁", MS_949, MS_949); // [-108, -18]
        test("뷁", UTF_8, UTF_8); // [-21, -73, -127]
        test("뷁", UTF_16BE, UTF_16BE); // [-67, -63]

        System.out.println("== 한글 인코딩 - 디코딩이 다른 경우 ==");
        test("가", EUC_KR, MS_949); // [-80, -95] : MS949는 EUC-KR의 확장판이라 가능
        test("뷁", MS_949, EUC_KR); // [-108, -18]로 인코딩은 가능한데 디코딩은 불가능
        test("가", EUC_KR, UTF_8); // [-80, -95]로 인코딩 되지만 UTF-8은 한글을 3byte로 쓰기에 디코딩 불가능
        test("가", MS_949, UTF_8); // [-80, -95]로 인코딩 되지만 UTF-8은 한글을 3byte로 쓰기에 디코딩 불가능
        test("가", UTF_8, MS_949); // [-22, -80, -128]로 인코딩 되지만 MS949는 한글을 2byte로 쓰기에 디코딩 불가능

        System.out.println("== 영문 인코딩 - 디코딩이 다른 경우 ==");
        test("A", EUC_KR, UTF_8); // [65]
        test("A", MS_949, UTF_8); // [65]
        test("A", UTF_8, MS_949); // [65]
        test("A", UTF_8, UTF_16BE); // [65]로 인코딩은 되지만 UTF16은 2byte라 디코딩 불가능

        // UTF-8이 현대의 사실상 표준이 된 이유?
        // 1. 저장 공간 절약과 네트워크 효율성
        // UTF-8은 ASCII 문자를 포함한 많은 서양 언어의 문자를 1byte로 사용
        // 반면 UTF-16은 최소 2byte 사용
        // 웹에 있는 문서의 80% 이상은 영문 문서 -> UTF-8이 압도적 효율

        // 2. ASCII 와의 호환성
        // 많은 레거시 시스템들은 ASCII 기반으로 구축되어있기 때문
        
        // 참고: 한글 윈도우의 경우 기존 윈도우와의 호환성을 위해 기본 인코딩을 MS949로 유지 중
    }

    private static void test(String text, Charset encodingCharset, Charset decodingCharset) {
        // 항상 문자 -> byte, byte -> 문자 변환을 위해선 문자집합(Charset)이 있어야한다.
        byte[] encoded = text.getBytes(encodingCharset);
        String decoded = new String(encoded, decodingCharset);
        System.out.printf("%s -> [%s] 인코딩 -> %s %sbyte -> [%s] 디코딩 -> %s\n",
                text, encodingCharset, Arrays.toString(encoded), encoded.length, decodingCharset, decoded);
    }
}

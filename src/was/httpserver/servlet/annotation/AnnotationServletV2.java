package was.httpserver.servlet.annotation;

import was.httpserver.HttpRequest;
import was.httpserver.HttpResponse;
import was.httpserver.HttpServlet;
import was.httpserver.PageNotFoundException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class AnnotationServletV2 implements HttpServlet {

    private final List<Object> controllers;

    public AnnotationServletV2(List<Object> controllers) {
        this.controllers = controllers;
    }

    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException {
        String path = request.getPath();

        for (Object controller : controllers) {
            Method[] methods = controller.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(Mapping.class)) {
                    Mapping annotation = method.getAnnotation(Mapping.class);
//                    String value = annotation.value(); // 배열받도록 개인적인 변경
                    String[] values = annotation.value();
                    for (String value : values) {
                        if (value.equals(path)) {
                            invoke(controller, method, request, response);
                            return;
                        }
                    }
                }
            }
        }
        throw new PageNotFoundException("request = " + path);
    }

    private static void invoke(Object controller, Method method, HttpRequest request, HttpResponse response) {
        // 현재 메서드들은 request, response를 안써도 모두 받아와야하는 점을 개선
        Class<?>[] parameterTypes = method.getParameterTypes();

        // request, response 중 하나를 받을 수도 있고 둘다 받을 수도 있다.
        Object[] args = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            if (parameterTypes[i] == HttpRequest.class) {
                args[i] = request;
            } else if (parameterTypes[i] == HttpResponse.class) {
                args[i] = response;
            } else {
                throw new IllegalArgumentException("Unsupported parameter type: " + parameterTypes[i]);
            }
        }

        try {
            method.invoke(controller, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    // 현재 서블릿까지 와도 존재하는 문제
    // 1. 성능 최적화: 이중 for 문으로 컨트롤러를 순회 + 메서드들을 순회해서 메서드를 찾음

    // 2. 중복 매핑 문제: 개발자가 실수로 @Mapping에 같은 value를 넣어버린다면?
    // 뭐가 먼저 호출될까? 너무 모호함
    // 개발할 땐 모호하게 하면 안된다. 둘다 실행이 안되야한다.
}

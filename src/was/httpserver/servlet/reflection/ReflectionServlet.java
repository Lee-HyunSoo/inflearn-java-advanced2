package was.httpserver.servlet.reflection;

import was.httpserver.HttpRequest;
import was.httpserver.HttpResponse;
import was.httpserver.HttpServlet;
import was.httpserver.PageNotFoundException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class ReflectionServlet implements HttpServlet {

    private final List<Object> controllers;

    public ReflectionServlet(List<Object> controllers) {
        this.controllers = controllers;
    }

    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException {
        String path = request.getPath();

        for (Object controller : controllers) {
            Class<?> aClass = controller.getClass();

            Method[] methods = aClass.getDeclaredMethods();
            for (Method method : methods) {
                String methodName = method.getName();
                if (path.equals("/" + methodName)) {
                    invoke(controller, method, request, response);
                    return;
                }
            }

//            // for문 없이 메서드 명으로 바로 찾기
//            try {
//                Method declaredMethod = aClass.getDeclaredMethod(
//                        path.substring(1), request.getClass(), response.getClass());
//                invoke(controller, declaredMethod, request, response);
//                return;
//            } catch (NoSuchMethodException e) {
//                // 예외 시 다른 클래스에 있는 메서드 일 수 있기 때문에 아무 처리 안함
//            }
        }
        throw new PageNotFoundException("request = " + path);
    }

    private static void invoke(Object controller, Method method, HttpRequest request, HttpResponse response) {
        try {
            method.invoke(controller, request, response);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    // 문제점
    // 1. URL path 이름과 메서드 이름을 다르게 하고싶다면?
    // 2. URL은 주로 - (dash)를 구분자로 사용한다. /add-member 같은 URL은 어떻게 해결?
}

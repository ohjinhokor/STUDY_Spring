package hello.springmvc.basic.request;

// 강의에 앞서 꼭 기억해야 할 것
// 클라이언트에서 서버로 요청 데이터를 전달할 때는 주로 3가지의 방법을 사용한다.
// 1. Get으로 요청하면서 query parameter(query string) 사용
// 2. Post로 요청하면서 HTML Form데이터 사용 (content - type을 application/x-www-form-urlencoded로 한다)
// 3. HTTP message body에 데이터를 직접 담아서 요청(요즘은 주로 json 형식을 이용)

// request.getParameter()를 사용하면 1번과 2번을 다 조회할 수 있음

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Controller
public class RequestParamController {

    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username = {}, age = {}", username, age);

        response.getWriter().write("ok");

    }
}

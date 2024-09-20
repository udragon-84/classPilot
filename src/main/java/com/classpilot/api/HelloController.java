package com.classpilot.api;
import com.classpilot.api.response.ClassPilotResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HelloController {

    @Value("${server.port}")
    private int port;

    @GetMapping("/hello")
    public ClassPilotResponse<String> hello() {
        return new ClassPilotResponse<>(String.format("ClassPilot Hello ServerPort %s", this.port));
    }
}

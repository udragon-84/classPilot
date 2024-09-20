package com.classpilot.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
@Tag(name = "수강신청 관리 Api", description = "월급쟁이부자들 수강신청 관리 Api 명세")
public class ClassPilotController {

}

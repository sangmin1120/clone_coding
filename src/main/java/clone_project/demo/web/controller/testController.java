package clone_project.demo.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class testController {

    @GetMapping("/")
    public String index() {
        log.info("[testController] index()");
        return "index";
    }

    @GetMapping("/test")
    public String test() {
        log.info("[testController] test()");
        return "test";
    }
}

package com.ocp.pipeline.pipeline.poc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping(path = "/answer")
    public String getAnswer(){
        return "42";
    }
}

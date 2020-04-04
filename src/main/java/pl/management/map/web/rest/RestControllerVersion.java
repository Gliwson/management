package pl.management.map.web.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/version")
public class RestControllerVersion {

    @Value("${envname}")
    private String envname;

    @GetMapping("/version")
    public String version() {
        return envname;
    }
}

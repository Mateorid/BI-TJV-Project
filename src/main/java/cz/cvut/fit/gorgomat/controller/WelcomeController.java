package cz.cvut.fit.gorgomat.controller;

import cz.cvut.fit.gorgomat.Welcome;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class WelcomeController {
    private static final String welcomeTxt = "Welcome to %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/welcome")
    public Welcome Welcome(@RequestParam(value = "text", defaultValue = "Ski-Strahov") String text) {
        return new Welcome(
                counter.incrementAndGet(),
                String.format(welcomeTxt, text));
    }
}

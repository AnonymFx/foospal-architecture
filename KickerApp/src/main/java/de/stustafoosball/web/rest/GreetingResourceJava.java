package de.stustafoosball.web.rest;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import de.stustafoosball.web.rest.dto.Greeting;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("/api")
public class GreetingResourceJava {

    @RequestMapping(value = "/greetingJava",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public Greeting greetingGET(@RequestParam(value = "name", defaultValue = "World") String name ) {
        return new Greeting("Hello "+name);
    }
}

package de.stustafoosball.web.rest

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import de.stustafoosball.web.rest.dto.Greeting
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMethod
import javax.servlet.http.HttpSession

@RestController
@RequestMapping("/api")
// see GreetingResourceJava for equivalent in Java
// class needs to be open (not mentioned in Kotlin documentation),
// otherwise Spring throws Exception
open class GreetingResourceKotlin {

    @RequestMapping(value = "/greetingKotlin",
        method = arrayOf(RequestMethod.GET),
        produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun greetingGET(@RequestParam(value = "name", defaultValue = "World") name: String): Greeting {
        return Greeting("Hello, $name")
    }
}


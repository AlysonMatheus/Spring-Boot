package br.com.Alyson.Controllers;

import br.com.Alyson.services.PersonServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;




@RestController
public class TestLogController {

    private Logger logger = LoggerFactory.getLogger(PersonServices.class.getName());

    @GetMapping("/test")
    public String testLog() {
        logger.debug("This is an INFO debug");
        logger.info("This is an INFO log");
        logger.warn("This is an INFO warn");
        logger.error("This is an INFO error");
        return "Logs generated succcessfully!";
    }


}

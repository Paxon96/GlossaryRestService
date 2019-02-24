package pl.paxon96.glossary.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("learning")
public class LearnController {

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> getLearningPage(){
        return new ResponseEntity<>("Not implemented yet", HttpStatus.NOT_FOUND);
    }
}

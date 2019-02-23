package pl.paxon96.glossary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.paxon96.glossary.entity.GlossaryWord;
import pl.paxon96.glossary.service.GlossaryService;

import java.util.List;

@Controller
@RequestMapping("glossary")
public class GlossaryController {

    @Autowired
    private GlossaryService glossaryService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<GlossaryWord>> getAllWords(){
        return new ResponseEntity<>(glossaryService.getAllWordsFromDatabase(), HttpStatus.OK);
    }
}

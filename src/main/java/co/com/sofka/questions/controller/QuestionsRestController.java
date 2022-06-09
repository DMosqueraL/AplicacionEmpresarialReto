package co.com.sofka.questions.controller;

import co.com.sofka.questions.reposioties.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping
public class QuestionsRestController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/countQuestions")
    public Mono<Long> countQuestions(){
        return questionRepository.findAll().count();
    }
}

package co.com.sofka.questions.controller;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
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

    @GetMapping("/totalPages")
    public Mono<Long> totalPages(){
        return questionRepository
                .findAll()
                .count()
                .map(questions -> (questions/10)+1);
    }

    @GetMapping("/pagination/{page}")
    public Flux<Question> pagination(@PathVariable Long page){
        return questionRepository
                .findAll()
                .skip((page)*10)
                .take(10);

    }




}

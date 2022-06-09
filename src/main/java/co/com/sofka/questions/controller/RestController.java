package co.com.sofka.questions.controller;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.reposioties.AnswerRepository;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@org.springframework.web.bind.annotation.RestController
@RequestMapping
public class RestController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;




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

    @GetMapping("/get/{id}")
    public Flux<Answer> getAnswer(@PathVariable String id){
        return answerRepository.
                findAllByQuestionId(id);
    }




}

package co.com.sofka.questions.controller;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.reposioties.AnswerRepository;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    public Mono<Question> getQuestion(@PathVariable String id){
        return questionRepository
                .findById(id);
    }

    @PostMapping("/create")
    public Mono<Question> saveQuestion(@RequestBody Question question) {
        return questionRepository.save(question);
    }

    @PostMapping("/add")
    public Mono<Answer> saveAnswer(@RequestBody Answer answer) {
        return answerRepository.save(answer);
    }

    @PutMapping("/update")
    public Mono<Question> updateQuestion(@RequestBody Question question) {
        return questionRepository.save(question);
    }

    @PutMapping("/answer")
    public Mono<Answer> updateAnswer(@RequestBody Answer answer) {
        return answerRepository.save(answer);
    }

}

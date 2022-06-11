package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.reposioties.AnswerRepository;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static reactor.core.publisher.Mono.when;

class GetUseCaseTest {

    QuestionRepository questionRepository;
    AnswerRepository answerRepository;
    GetUseCase getUseCase;

    @BeforeEach
    public void setup() {
        MapperUtils mapperUtils = new MapperUtils();
        questionRepository = mock(QuestionRepository.class);
        answerRepository = mock(AnswerRepository.class);
        getUseCase = new GetUseCase(mapperUtils, questionRepository, answerRepository);
    }

    @Test
    void getValidationTest() {
        var question = new Question();
        question.setId("Q-00001");
        question.setUserId("ABCD1234");
        question.setQuestion("¿Que te parece el Training");
        question.setType("Aula Invertida");
        question.setCategory("Educación");

        var answer = new Answer();
        answer.setId("A-00001");
        answer.setUserId("ABCD1234");
        answer.setQuestionId("Q-00001");
        answer.setAnswer("Una pesadilla");
        answer.setPosition(5);

        when(questionRepository.findById(question.getId())).thenReturn(Mono.just(question));
        when(answerRepository.findAllByQuestionId(question.getId())).thenReturn(Flux.just(answer));

        StepVerifier.create(getUseCase.apply(question.getId()))
                .expectNextMatches(questionTest -> {
                    assert questionTest.getId().equals(question.getId());
                    assert questionTest.getUserId().equals(question.getUserId());
                    assert questionTest.getCategory().equals(question.getCategory());
                    assert questionTest.getQuestion().equals(question.getQuestion());
                    assert questionTest.getType().equals(question.getType());
                    return true;
                }).verifyComplete();

        verify(questionRepository).findById(question.getId());
        verify(answerRepository).findAllByQuestionId(question.getId());
    }
}
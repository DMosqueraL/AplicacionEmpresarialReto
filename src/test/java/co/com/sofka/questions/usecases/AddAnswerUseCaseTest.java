package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.reposioties.AnswerRepository;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;


class AddAnswerUseCaseTest {

    QuestionRepository questionRepository;
    AnswerRepository answerRepository;
    AddAnswerUseCase addAnswerUseCase;
    GetUseCase getUseCase;
    MapperUtils mapper = new MapperUtils();

    @BeforeEach
    public void setup() {
        MapperUtils mapperUtils = new MapperUtils();
        questionRepository = mock(QuestionRepository.class);
        answerRepository = mock(AnswerRepository.class);
        getUseCase = new GetUseCase(mapperUtils, questionRepository, answerRepository);
        addAnswerUseCase = new AddAnswerUseCase(mapperUtils, getUseCase, answerRepository);
    }

    @Test
    void addAnswerValidationTest() {

        var answer = new Answer();
        answer.setId("A-00001");
        answer.setUserId("ABCD1234");
        answer.setQuestionId("Q-00001");
        answer.setAnswer("Una pesadilla");
        answer.setPosition(0);

        var answerDTO = new AnswerDTO();
        answerDTO.setUserId(answer.getUserId());
        answerDTO.setQuestionId(answer.getQuestionId());
        answerDTO.setAnswer(answer.getAnswer());
        answerDTO.setPosition(answer.getPosition());

        var question = new Question();
        question.setId("Q-00001");
        question.setUserId("ABCD1234");
        question.setQuestion("¿Que te parece el Training");
        question.setType("Aula Invertida");
        question.setCategory("Educación");

        when(questionRepository.findById(any(String.class))).thenReturn(Mono.just(question));
        when(answerRepository.save(any(Answer.class))).thenReturn(Mono.just(answer));
        when(answerRepository.findAllByQuestionId(any(String.class))).thenReturn(Flux.empty());

        StepVerifier.create(addAnswerUseCase.apply(answerDTO))
                .expectNextMatches(questionDTO -> {
                    assert questionDTO.getId().equals("Q-00001");
                    assert questionDTO.getUserId().equals("ABCD1234");
                    assert questionDTO.getQuestion().equals("¿Que te parece el Training");
                    assert questionDTO.getType().equals("Aula Invertida");
                    assert questionDTO.getCategory().equals("Educación");
                    assert questionDTO.getAnswers().contains(answerDTO);
                    return true;
                })
                .verifyComplete();

        verify(questionRepository).findById(any(String.class));

    }


}
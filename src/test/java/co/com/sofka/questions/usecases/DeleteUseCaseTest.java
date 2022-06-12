package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.reposioties.AnswerRepository;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class DeleteUseCaseTest {

    QuestionRepository questionRepository;
    AnswerRepository answerRepository;
    DeleteUseCase deleteUseCase;

    @BeforeEach
    public void setup() {
        questionRepository = mock(QuestionRepository.class);
        answerRepository = mock(AnswerRepository.class);
        deleteUseCase = new DeleteUseCase(answerRepository, questionRepository);
    }

    @Test
    void getValidationDeleteTest() {
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

        Mono.just(question).flatMap(questionRepository::save).subscribe();

        when(questionRepository.deleteById("Q-00001")).thenReturn(Mono.empty());

        StepVerifier.create(deleteUseCase.apply("Q-00001"))
                .expectNextMatches(pregunta->{
                    assert pregunta.equals("Q-00001");
                    return true;
                }).expectComplete();

        verify(questionRepository).deleteById("Q-00001");

    }



}
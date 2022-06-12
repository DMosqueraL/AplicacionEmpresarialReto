package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class UpdateUseCaseTest {

    MapperUtils mapperUtils;
    QuestionRepository questionRepository;
    UpdateUseCase updateUseCase;

    @BeforeEach
    public void setUp(){
        questionRepository = mock(QuestionRepository.class);
        mapperUtils = new MapperUtils();
        updateUseCase = new UpdateUseCase(mapperUtils,questionRepository);

    }

    @Test
    void validationUpdateUseCase() {
        var question = new Question();
        question.setId("Q-00001");
        question.setUserId("ABCD1234");
        question.setQuestion("¿Que te parece el Training");
        question.setType("Aula Invertida");
        question.setCategory("Educación");

        var questionDTO = new QuestionDTO();
        questionDTO.setId(question.getId());
        questionDTO.setUserId(question.getUserId());
        questionDTO.setQuestion("¿Eres autodidacta?");
        questionDTO.setType(question.getType());
        questionDTO.setCategory(question.getCategory());


        when(questionRepository.save(Mockito.any(Question.class))).thenReturn(Mono.just(question));

        StepVerifier.create(updateUseCase.apply(questionDTO))

                .expectNextMatches(pregunta -> {

                    assert questionDTO.getId().equalsIgnoreCase(question.getId());
                    assert questionDTO.getUserId().equalsIgnoreCase("ABCD1234");
                    assert questionDTO.getQuestion().equalsIgnoreCase("¿Eres autodidacta?");
                    assert questionDTO.getType().equalsIgnoreCase("Aula Invertida");
                    assert questionDTO.getCategory().equalsIgnoreCase("Educación");

                    return true;
                }).verifyComplete();

        verify(questionRepository).save(Mockito.any(Question.class));
    }

}
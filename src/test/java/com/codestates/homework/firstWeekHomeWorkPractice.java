package com.codestates.homework;

import com.codestates.homework.common.TestDescription;
import com.codestates.homework.model.Person;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class firstWeekHomeWorkPractice {

    /**
     * ["Blenders", "Old", "Johnnie"] 와 "[Pride", "Monk", "Walker”] 를 순서대로 하나의 스트림으로 처리되는 로직 검증
     */
    @Test
    @TestDescription("Seuquce를 순서대로 하나의 스트림으로 처리되는 로직 검증")
    public void firstQuestion() {
        Flux<String> name1 = Flux.just("Blenders", "Old", "Johnnie")
                .delayElements(Duration.ofSeconds(1));
        Flux<String> name2 = Flux.just("Pride", "Monk", "Walker")
                .delayElements(Duration.ofSeconds(1));
        Flux<String> names = Flux.concat(name1, name2)
                .log();

        StepVerifier.create(names)
                .expectSubscription()
                .expectNext("Blenders", "Old", "Johnnie", "Pride", "Monk", "Walker")
                .verifyComplete();
    }

    /**
     * 1~100 까지의 자연수 중 짝수만 출력하는 로직 검증
     */
    @Test
    @TestDescription("1~100 까지의 자연수 중 짝수만 출력하는 로직 검증")
    public void secondQuestion() {
        Flux<Integer> flux = Flux.range(1, 100)
                .filter(x -> x % 2 == 0)
                .log();

        List<Integer> evenList = new ArrayList<>();
        for (int i = 2; i <= 100; i += 2) {
            evenList.add(Integer.valueOf(i));
        }

        StepVerifier.create(flux)
                .expectNextSequence(evenList)
                .verifyComplete();
    }

    /**
     * “hello”, “there” 를 순차적으로 publish하여 순서대로 나오는지 검증
     */
    @Test
    @TestDescription("순차적으로 publish하여 순서대로 나오는지 검증")
    public void thirdQuestion() {
        Flux<String> flux = Flux.just("hello", "there")
                .delayElements(Duration.ofSeconds(1))
                .log();

        StepVerifier.create(flux)
                .expectSubscription()
                .expectNext("hello", "there")
                .verifyComplete();
    }

    /**
     * 아래와 같은 객체가 전달될 때 “JOHN”, “JACK” 등 이름이 대문자로 변환되어 출력되는 로직 검증
     * <p>
     * Person("John", "[john@gmail.com](mailto:john@gmail.com)", "12345678")
     * Person("Jack", "[jack@gmail.com](mailto:jack@gmail.com)", "12345678")
     */
    @Test
    @TestDescription("대문자로 변환되어 출력되는 로직 검증")
    public void fourthQuestion() {
        Person firstPerson = new Person("John", "[john@gmail.com](mailto:john@gmail.com)", "12345678");
        Person secondPerson = new Person("Jack", "[jack@gmail.com](mailto:jack@gmail.com)", "12345678");

        Flux<Person> flux = Flux.just(firstPerson, secondPerson)
                .map(person -> {
                    person.changeToUpperName();
                    return person;
                })
                .log();

        StepVerifier.create(flux)
                .assertNext(person -> assertEquals(person.getName(), "JOHN"))
                .assertNext(person -> assertEquals(person.getName(), "JACK"))
                .verifyComplete();
    }

    /**
     * ["Blenders", "Old", "Johnnie"] 와 "[Pride", "Monk", "Walker”]를 압축하여 스트림으로 처리 검증
     * 예상되는 스트림 결과값 ["Blenders Pride", "Old Monk", "Johnnie Walker”]
     */
    @Test
    @TestDescription("Sequence를 압축하여 스트림으로 처리 검증")
    public void fifthQuestion() {
        Flux<String> firstNames = Flux.just("Blenders", "Old", "Johnnie");
        Flux<String> secondNames = Flux.just("Pride", "Monk", "Walker");

        Flux<String> zippedNames = firstNames.zipWith(secondNames, (first, second) -> first + " " + second)
                                    .log();

        StepVerifier.create(zippedNames)
                .expectSubscription()
                .expectNext("Blenders Pride", "Old Monk", "Johnnie Walker")
                .verifyComplete()
        ;
    }

    /**
     * ["google", "abc", "fb", "stackoverflow”] 의 문자열 중 5자 이상 되는 문자열만 대문자로 비동기로 치환하여 1번 반복하는 스트림으로 처리하는 로직 검증
     * 예상되는 스트림 결과값 ["GOOGLE", "STACKOVERFLOW", "GOOGLE", "STACKOVERFLOW"]
     */
    @Test
    @TestDescription("문자열 중 5자 이상 되는 문자열만 대문자로 비동기로 치환하여 1번 반복하는 스트림으로 처리하는 로직 검증")
    public void sixthQuestion() {
        Flux<String> stringFlux = Flux.just("google", "abc", "fb", "stackoverflow")
                .filter(s -> s.length() >= 5)
                .flatMap(s -> Mono.just(s.toUpperCase()))
                .log()
                .subscribeOn(Schedulers.boundedElastic())
                .repeat(1);

        StepVerifier.create(stringFlux)
                .expectSubscription()
                .expectNext("GOOGLE", "STACKOVERFLOW", "GOOGLE", "STACKOVERFLOW")
                .verifyComplete();
    }
}

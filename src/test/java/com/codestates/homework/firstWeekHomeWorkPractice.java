package com.codestates.homework;

import com.codestates.homework.model.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class firstWeekHomeWorkPractice {
    @Test
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
                .verifyComplete()
        ;
    }

    @Test
    public void secondQuestion() {
        Flux<Integer> flux = Flux.range(1, 100).filter(x -> x % 2 == 0);
        List<Integer> evenList = new ArrayList<>();
        for (int i = 2; i <= 100; i += 2) {
            evenList.add(Integer.valueOf(i));
        }

        StepVerifier.create(flux)
                .expectNextSequence(evenList)
                .verifyComplete()
        ;
    }

    @Test
    public void thirdQuestion() {
        Flux<String> flux = Flux.just("hello", "there")
                .delayElements(Duration.ofSeconds(1))
                .log();

        StepVerifier.create(flux)
                .expectSubscription()
                .expectNext("hello", "there")
                .verifyComplete();
    }

    @Test
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
                .assertNext(person -> Assertions.assertEquals(person.getName(), "JOHN"))
                .assertNext(person -> Assertions.assertEquals(person.getName(), "JACK"))
                .verifyComplete();
    }

    @Test
    public void fifthQuestion() {
        Flux<String> firstNames = Flux.just("Blenders", "Old", "Johnnie");
        Flux<String> secondNames = Flux.just("Pride", "Monk", "Walker");

        Flux<String> zippedNames = firstNames.zipWith(secondNames, (first, second) -> first + " " + second).log();

        StepVerifier.create(zippedNames)
                .expectSubscription()
                .expectNext("Blenders Pride", "Old Monk", "Johnnie Walker")
                .verifyComplete()
        ;
    }

    @Test
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

package com.havving;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by HAVVING on 2021-04-13.
 */
public class JacksonTest {

    @Test
    @Ignore
    public void convertObjectToJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Person person = new Person("Jung", 20);

        // 객체를 JSON 타입의 String으로 변환
        String personJson = mapper.writeValueAsString(person);
        System.out.println("Json 변환: " + personJson);

        // 객체를 JSON 타입의 String으로 변환 및 정렬
        String personJson2 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(personJson);
        System.out.println("Json 변환 및 정렬: " + personJson2);
    }


    @Test
    @Ignore
    public void convertJsonToObject() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Person person = new Person("Jung", 20);

        String personJson = mapper.writeValueAsString(person);

        Person jsonToPerson = mapper.readValue(personJson, Person.class);
        System.out.println("Name : " + jsonToPerson.getName());
        System.out.println("Age : " + jsonToPerson.getAge());
    }
}


@Getter
class Person {
    String name;
    int age;

    public Person() {}

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
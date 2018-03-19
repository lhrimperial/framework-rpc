package com.github.framework.simple.rpc.api;

import com.github.framework.simple.rpc.domain.Person;

import java.util.List;

/**
 * Created by luxiaoxun on 2016-03-10.
 */
public interface PersonService {
    List<Person> findPerson(String name, int num);
}

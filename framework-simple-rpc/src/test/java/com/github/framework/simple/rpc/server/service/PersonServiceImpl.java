package com.github.framework.simple.rpc.server.service;

import com.github.framework.simple.rpc.api.PersonService;
import com.github.framework.simple.rpc.api.PersonService;
import com.github.framework.simple.rpc.domain.Person;
import com.github.framework.simple.rpc.server.RpcService;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@RpcService(PersonService.class)
public class PersonServiceImpl implements PersonService {

    @Override
    public List<Person> findPerson(String name, int num) {
        List<Person> persons = new ArrayList<>(num);
        for (int i = 0; i < num; ++i) {
            persons.add(new Person(Integer.toString(i), name));
        }
        return persons;
    }
}
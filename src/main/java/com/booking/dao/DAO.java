package com.booking.dao;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;


public interface DAO<A> {

    Optional<A> get(int id);

    Collection<A> getAll();

    Collection<A> getAllBy(Predicate<A> p);

    void create(A data);

    void delete(int id);

}

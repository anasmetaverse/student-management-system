package com.ytseries.sms.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CURD <E, ID> {
    E save (E entity);
    E findById (ID id);
    boolean existById (ID id);
    void deletedById (ID id);
    List<E> findAll ();
}

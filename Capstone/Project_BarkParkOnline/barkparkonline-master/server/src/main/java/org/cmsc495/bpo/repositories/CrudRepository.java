package org.cmsc495.bpo.repositories;

import com.mongodb.client.result.DeleteResult;

public interface CrudRepository<E> {
    public Object create(E document);
    public E retrieve(String index);
    public void update(E document);
    public DeleteResult delete(E document);
    public DeleteResult delete(String index);
}

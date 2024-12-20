package com.vijay.book_managment.service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface iCrudService<Req,Res,ID> {
    CompletableFuture<Res> create(Req request);
    CompletableFuture<Res> getById(ID id);
    CompletableFuture<Set<Res>> getAll();
    CompletableFuture<Res> update(ID id, Req request);
    CompletableFuture<Void> delete(ID id);

}

package com.vini.library.services;

import com.vini.library.entity.Book;

import java.util.List;

/**
 * Created by vin on 7/14/16.
 */
public interface BookService {

    public List<Book> findAllBooks();

    public Book addBook(Book book);

    public Book editBook(Book book);

    public List<Book> searchBook(String key);
    
    public Book findById(int id);
}

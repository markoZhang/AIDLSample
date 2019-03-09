// BookController.aidl
package com.example.marko.aidlserver;

import com.example.marko.aidlserver.Book;

// Declare any non-default types here with import statements

interface BookController {

    List<Book> getBookList();
    void addBookInout(inout Book book);
    void addBookIn(in Book book);
    void addBookOut(out Book book);
}

package com.api.Library.service;

import com.api.Library.model.Book;
import com.api.Library.model.Library;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryService {

    public List<Book> getBooks() {
        return Library.getBooks();
    }

    // other methods...
}

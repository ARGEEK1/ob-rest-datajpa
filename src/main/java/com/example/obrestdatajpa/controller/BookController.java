package com.example.obrestdatajpa.controller;

import com.example.obrestdatajpa.entities.Book;
import com.example.obrestdatajpa.repository.BookRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    // Atributos
    private BookRepository bookRepository;

    // Constructores
    public BookController(BookRepository bookRepository) {
       this.bookRepository = bookRepository;
    }

    // CRUD sobre la entidad Book
    // Buscar todos los libros en base de datos

    /**
     * http://localhost:8081/api/books
     * @return
     */
    @GetMapping("/api/books")
    public List<Book> findAll() {
        // recuperar y devolver los libros en BD
        return bookRepository.findAll();
    }

    // Buscar un solo libro en base de datos, según su ID
    @GetMapping(path = "/api/books/{id}")
    public Optional<Book> findById(@PathVariable("id") Long id) {
        return  bookRepository.findById(id);
    }

    // Crear un nuevo libro en base de datos
    @PostMapping("/api/books")
    public Book save(@RequestBody Book book){
        return bookRepository.save(book);
    }

    // Actualizar un libro en base de datos

    // Borrar un libro en base de datos

}

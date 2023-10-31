package com.example.obrestdatajpa.controller;

import com.example.obrestdatajpa.entities.Book;
import com.example.obrestdatajpa.repository.BookRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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
     *
     * @return
     */
    @GetMapping("/api/books")
    public List<Book> findAll() {
        // recuperar y devolver los libros en BD
        return bookRepository.findAll();
    }

    // Buscar un solo libro en base de datos, según su ID
    @GetMapping("/api/books/{id}")
    public ResponseEntity<Book> findById(@PathVariable Long id) {

        Optional<Book> bookOpt = bookRepository.findById(id);

        // opción 1
             if (bookOpt.isPresent()) {
                 return ResponseEntity.ok(bookOpt.get());
            } else {
                return ResponseEntity.notFound().build();
            }

        /*
         opción 2
         return bookOpt.orElse(null);
         return bookOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        */
    }


    // Crear un nuevo libro en base de datos
    @PostMapping("/api/books")
    public Book save(@RequestBody Book book, @RequestHeader HttpHeaders headers) {

        System.out.println( headers.get("User-Agent"));

        return bookRepository.save(book);
    }

    // Actualizar un libro en base de datos
    @PutMapping("/api/books/{id}")
    public Book update(@RequestBody Book request, @PathVariable Long id) {
        Book book = bookRepository.findById(id).orElse(null);

        if (book != null) {
            book.setTitle(request.getTitle());
            book.setAuthor(request.getAuthor());
            book.setPages(request.getPages());
            book.setPrice(request.getPrice());
            book.setReleaseDate(request.getReleaseDate());
            book.setOnline(request.getOnline());

            bookRepository.save(book);
        }

        return book;
    }

    // Borrar un libro en base de datos

}

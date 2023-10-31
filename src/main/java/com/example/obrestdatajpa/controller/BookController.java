package com.example.obrestdatajpa.controller;

import com.example.obrestdatajpa.entities.Book;
import com.example.obrestdatajpa.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {
    private final Logger log = LoggerFactory.getLogger(BookController.class);

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

    /**
     * Método POST, no colisiona con findAll porque son diferentes métodos HTTP.
     *
     * @param book
     * @param headers
     * @return
     */
    @PostMapping("/api/books")
    public ResponseEntity<Book> save(@RequestBody Book book, @RequestHeader HttpHeaders headers) {

        System.out.println(headers.get("User-Agent"));

        if (book.getId() != null) { // quiere decir que existe el id, por lo tanto no es una creación.
            log.warn("trying to create a book with id");
            System.out.println("trying to create a book with id");

            return ResponseEntity.badRequest().build();
        }

        Book result = bookRepository.save(book);
        return ResponseEntity.ok(result);
    }

    // Actualizar un libro en base de datos
    @PutMapping("/api/books/{id}")
    public ResponseEntity<Book> update(@RequestBody Book request, @PathVariable Long id) {

        Book book = bookRepository.findById(id).orElse(null);

        if (book != null) {
            book.setTitle(request.getTitle());
            book.setAuthor(request.getAuthor());
            book.setPages(request.getPages());
            book.setPrice(request.getPrice());
            book.setReleaseDate(request.getReleaseDate());
            book.setOnline(request.getOnline());

            bookRepository.save(book);

            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    // Borrar un libro en base de datos
    @DeleteMapping("/api/books/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        Optional<Book> book = bookRepository.findById(id);

        try {
            if (book.isPresent()) {
                bookRepository.delete(book.get());
                return ResponseEntity.status(200).body("Book deleted");
            } else {
                return ResponseEntity.badRequest().body("ID not found");
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Borrar todos los libros de la base de datos
    @DeleteMapping("/api/books")
    public ResponseEntity<String> deleteAll() {
        long caunt = bookRepository.count();

        if (caunt == 0) {
            return ResponseEntity.ok().body("There are no books to delete.");
        } else {
            bookRepository.deleteAll();
            return ResponseEntity.ok("All books deleted successfully.");
        }
    }
}

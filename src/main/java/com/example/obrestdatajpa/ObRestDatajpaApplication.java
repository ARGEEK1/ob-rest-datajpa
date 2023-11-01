package com.example.obrestdatajpa;

import com.example.obrestdatajpa.entities.Book;
import com.example.obrestdatajpa.repository.BookRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;

@SpringBootApplication
public class ObRestDatajpaApplication {

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(ObRestDatajpaApplication.class, args);
        BookRepository bookRepository = context.getBean(BookRepository.class);

        System.out.println(bookRepository.count());

        //crear un libro
        Book book1 = new Book(null, "Java", "ames Gosling", 600, 20.50, LocalDate.of(1996, 1, 23), true);
        Book book2 = new Book(null, "Java Avanzado", "ames Gosling", 700, 20.50, LocalDate.of(1996, 1, 23), true);
        // almacenar un libro
        bookRepository.save(book1);
        bookRepository.save(book2);
        System.out.println("Cantidad de libros en DB es: " + bookRepository.count());

        // recuperar todos los libros
        System.out.println(bookRepository.findAll().get(0));

        // borrar un libro
        //bookRepository.deleteById(1L);
        System.out.println(bookRepository.count());

    }

}

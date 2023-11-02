package com.example.obrestdatajpa.service;

import com.example.obrestdatajpa.entities.Book;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BookPriceCalculatorTest {

    @Test
    void calculatePriceTest() {

        //configuración de la prueba
        Book book = new Book(1L,
                "Hola Mundo",
                "José R.",
                300,
                22.50,
                LocalDate.of(2002, 12, 26),
                true);
        BookPriceCalculator calculator = new BookPriceCalculator();

        // se ejecuta el comportamiento bajo testing
        double price = calculator.calculatePrice(book);
        System.out.println(price);
        // comprobaciones aserciones
        assertTrue(price > 0);
        assertEquals(25.490000000000002, price);

    }
}
package com.alura.liter_alura.Commands;

import com.alura.liter_alura.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.command.annotation.Command;

@Command(command = "book", description = "Gerencia o acesso a livros, seja no banco de dados ou na API externa.", group = "Book Commands")
public class BookCommands {

    @Autowired
    private BookService bookService;


}

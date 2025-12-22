package com.alura.liter_alura;

import com.alura.liter_alura.service.GutenbergService;
import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;

@Command(group = "Main Commands", description = "Primary commands for Liter Alura CLI")
public class MainCommands {

    @Autowired
    private LineReader lineReader;

    @Autowired
    private GutenbergService gutenbergService;

    @Autowired
    private Terminal terminal;

    // TODO: Adicionar comando que puxe os livros da Gutenberg, transforme numa entidade e salve no banco de dados.
    // TODO: Adicionar comando que liste os livros salvos no banco de dados.
}

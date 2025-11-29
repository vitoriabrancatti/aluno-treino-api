package com.academia;

import com.academia.controller.AlunoController;
import com.academia.controller.TreinoController;
import com.academia.controller.mapper.AlunoMapper;
import com.academia.controller.mapper.TreinoMapper;
import com.academia.http.AlunoHandler;
import com.academia.http.TreinoHandler;
import com.academia.persistence.AlunoRepository;
import com.academia.persistence.DatabaseFactory;
import com.academia.persistence.TreinoRepository;
import com.academia.service.AlunoService;
import com.academia.service.TreinoService;
import com.academia.service.validation.AlunoValidator;
import com.academia.service.validation.TreinoValidator;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws IOException {
        DatabaseFactory databaseFactory = new DatabaseFactory();
        AlunoRepository alunoRepository = new AlunoRepository(databaseFactory);
        TreinoRepository treinoRepository = new TreinoRepository(databaseFactory);

        AlunoValidator alunoValidator = new AlunoValidator();
        TreinoValidator treinoValidator = new TreinoValidator();

        AlunoService alunoService = new AlunoService(alunoRepository, treinoRepository, alunoValidator);
        TreinoService treinoService = new TreinoService(treinoRepository, alunoRepository, treinoValidator);

        TreinoMapper treinoMapper = new TreinoMapper();
        AlunoMapper alunoMapper = new AlunoMapper(treinoMapper);

        AlunoController alunoController = new AlunoController(alunoService, alunoMapper);
        TreinoController treinoController = new TreinoController(treinoService, treinoMapper);

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/alunos", new AlunoHandler(alunoController));
        server.createContext("/treinos", new TreinoHandler(treinoController));
        server.setExecutor(Executors.newCachedThreadPool());
        server.start();
        System.out.println("Servidor ativo em http://localhost:8080");
    }
}

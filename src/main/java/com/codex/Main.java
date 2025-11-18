package com.codex;

import com.codex.http.AlunoHandler;
import com.codex.http.TreinoHandler;
import com.codex.persistence.AlunoRepository;
import com.codex.persistence.TreinoRepository;
import com.codex.service.AlunoService;
import com.codex.service.TreinoService;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws IOException {
        AlunoRepository alunoRepository = new AlunoRepository();
        TreinoRepository treinoRepository = new TreinoRepository();
        AlunoService alunoService = new AlunoService(alunoRepository, treinoRepository);
        TreinoService treinoService = new TreinoService(treinoRepository, alunoRepository);
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/alunos", new AlunoHandler(alunoService));
        server.createContext("/treinos", new TreinoHandler(treinoService));
        server.setExecutor(Executors.newCachedThreadPool());
        server.start();
        System.out.println("Servidor ativo em http://localhost:8080");
    }
}

package com.skillnez.tennis_scoreboard.controller;

import com.skillnez.tennis_scoreboard.service.OngoingMatchService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/new_match")
public class NewMatch extends HttpServlet {

    @Inject
    OngoingMatchService ongoingMatchService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}

package me.lucasmarques.popcorn.api;

import static spark.Spark.*;

public class Api {

    public static void main(String[] args) {
        get("/hello", (req, res) -> "Hello World");
    }

}

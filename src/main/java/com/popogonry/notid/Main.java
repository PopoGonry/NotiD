package com.popogonry.notid;

import com.popogonry.notid.cli.Authentication;
import com.popogonry.notid.cli.AuthenticationImpl;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        Config config = new Config();
        Authentication authentication = config.authentication();
        authentication.authenticate();
    }
}
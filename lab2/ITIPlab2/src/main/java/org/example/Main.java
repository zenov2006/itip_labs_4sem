package org.example;

import org.example.StringProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Программа запущена");

        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("build-passport.properties")) {
            if (input != null) {
                Properties props = new Properties();
                props.load(input);
                logger.info("=== Паспорт сборки ===");
                logger.info("Пользователь: {}", props.getProperty("build.user"));
                logger.info("ОС: {}", props.getProperty("build.os"));
                logger.info("Java: {}", props.getProperty("build.java.version"));
                logger.info("Время сборки: {}", props.getProperty("build.time"));
                logger.info("Сообщение: {}", props.getProperty("build.message"));
                logger.info("======================");
            } else {
                logger.warn("Файл build-passport.properties не найден");
            }
        } catch (Exception e) {
            logger.error("Ошибка при чтении паспорта сборки", e);
        }

        System.out.println("Добро пожаловать в лабораторную работу по Gradle!");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите строку: ");
        String input = scanner.nextLine();

        String reversed = StringProcessor.reverse(input);
        logger.info("Введённая строка: '{}', реверсированная: '{}'", input, reversed);
        System.out.println("Результат: " + reversed);

        logger.info("Программа завершена");
        scanner.close();
    }
}
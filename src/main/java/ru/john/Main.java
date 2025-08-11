package ru.john;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UserDAO dao = new UserDAO();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("""
                    1. Создать пользователя
                    2. Показать всех
                    3. Найти по ID
                    4. Обновить пользователя
                    5. Удалить пользователя
                    0. Выход
                    """);
            switch (sc.nextInt()) {
                case 1 -> {
                    sc.nextLine();
                    System.out.print("Имя: ");
                    String name = sc.nextLine();
                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    System.out.print("Возраст: ");
                    int age = sc.nextInt();
                    dao.save(new User(name, email, age));
                }
                case 2 -> dao.getAll().forEach(System.out::println);
                case 3 -> {
                    System.out.print("ID: ");
                    System.out.println(dao.getById(sc.nextLong()));
                }
                case 4 -> {
                    System.out.print("ID: ");
                    Long id = sc.nextLong();
                    sc.nextLine();
                    User u = dao.getById(id);
                    if (u != null) {
                        System.out.print("Новое имя: ");
                        u.setName(sc.nextLine());
                        System.out.print("Новый email: ");
                        u.setEmail(sc.nextLine());
                        System.out.print("Новый возраст: ");
                        u.setAge(sc.nextInt());
                        dao.update(u);
                    }
                }
                case 5 -> {
                    System.out.print("ID: ");
                    User u = dao.getById(sc.nextLong());
                    if (u != null) dao.delete(u);
                }
                case 0 -> {
                    HibernateUtil.getSessionFactory().close();
                    return;
                }
                default -> System.out.println("Неверный выбор.");
            }
        }
    }
}
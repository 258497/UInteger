package org.example;

public class Main {
    public static void main(String[] args) {
        UInteger min = UInteger.valueOf(0);
        UInteger max = UInteger.valueOf(UInteger.MAX_VALUE);

        System.out.println("MIN_VALUE: " + min);
        System.out.println("MAX_VALUE: " + max);

        UInteger color = UInteger.valueOf(255);

        System.out.println("Liczba 255 dziesiętnie: " + color.toString());
        System.out.println("Liczba 255 szesnastkowo: " + color.toString(16));
        System.out.println("MAX_VALUE szesnastkowo: " + max.toString(16));

        UInteger a = UInteger.valueOf(3_000_000_000L);
        UInteger b = UInteger.valueOf(1_000_000_000L);

        System.out.println("a = " + a);
        System.out.println("b = " + b);
        System.out.println("a + b = " + a.add(b));
        System.out.println("a - b = " + a.subtract(b));
        System.out.println("a / b = " + a.divide(b));

        System.out.print("Próba dodania 1 do MAX_VALUE: ");

        try {
            max.add(UInteger.valueOf(1));
            System.out.println("Błąd: Przepełnienie nie zostało wykryte!");
        }
        catch (ArithmeticException e) {
            System.out.println("Złapano oczekiwany wyjątek -> " + e.getMessage());
        }

        System.out.print("Próba odjęcia 1 od 0 (MIN_VALUE): ");

        try {
            min.subtract(UInteger.valueOf(1));
            System.out.println("Błąd: Niedomiar nie został wykryty!");
        }
        catch (ArithmeticException e) {
            System.out.println("Złapano oczekiwany wyjątek -> " + e.getMessage());
        }
    }
}
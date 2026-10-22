# Klasa UInteger - 32-bitowa liczba całkowita bez znaku

Klasa `UInteger` dostarcza obiektową obudowę dla 32-bitowej liczby całkowitej bez znaku, rozszerzając standardową klasę `Number` z biblioteki Javy.

## Wnioski z analizy pliku Integer.java
Analiza źródeł JDK wykazała, że klasa `Integer` od wersji Java 8 posiada wiele wbudowanych metod statycznych operujących na liczbach ze znakiem jak na liczbach bez znaku (np. `compareUnsigned`, `divideUnsigned`, `parseUnsignedInt`). Mechanizmy te opierają się głównie na operacjach bitowych oraz rzutowaniu zmiennych 32-bitowych na 64-bitowy typ `long` w miejscach krytycznych (np. w celu bezpiecznego porównania bez ryzyka przekłamań wynikających z najstarszego bitu znaku).

## Reprezentacja wewnętrzna
Jako reprezentację wewnętrzną przyjęto **jeden prywatny atrybut typu `int`**.
**Uzasadnienie:**
Mimo że standardowy `int` w Javie jest typem ze znakiem (w kodowaniu U2), posiada on dokładnie 32 bity pamięci. Interpretacja najstarszego bitu jako bitu wartości, a nie znaku, leży wyłącznie po stronie logiki (co automatycznie realizują wbudowane metody `Integer.*Unsigned`). Wybranie typu `long` do reprezentacji wewnętrznej ułatwiłoby operacje arytmetyczne bez obaw o przepełnienia po drodze, ale marnowałoby dodatkowe 32 bity (4 bajty) pamięci operacyjnej na każdy obiekt klasy `UInteger`, co w aplikacjach przetwarzających duże struktury danych byłoby nieoptymalne.

## Sposób wykrywania przepełnień
Aby bezpiecznie wykryć przepełnienie w metodach takich jak dodawanie (`add`) i mnożenie (`multiply`), wewnętrzne wartości rzutowane są bezpiecznie na typ `long` przy użyciu `Integer.toUnsignedLong()`.
Zaletą tego rozwiązania jest to, że operacja arytmetyczna jest wykonywana w obszarze 64-bitowym, więc naturalnie nie dochodzi do utraty precyzji. Następnie uzyskany 64-bitowy wynik jest sprawdzany względem zdefiniowanych limitów `MAX_VALUE` oraz `MIN_VALUE`. Jeśli wartość wykracza poza ramy dopuszczalne dla 32-bitowego unsigned, rzucany jest wyjątek `ArithmeticException`.

## Sposób porównywania wartości bez znaku
Porównywanie realizowane jest przy użyciu metody pomocniczej `Integer.compareUnsigned(int x, int y)`. Tradycyjne operatory relacyjne (`>`, `<`) na zwykłych intach traktują najstarszy bit jako znak (wartości ujemne), co powodowałoby, że w Javie maksymalne wartości Unsigned (np. 4 miliardy) byłyby mniejsze od zera. Metoda ta dodaje przesunięcie bitowe o wartości `MIN_VALUE` do obu liczb, skutecznie mapując je na układ odwracający problem bitu znaku i realizując szybkie, bezbłędne porównanie.

## Różnice względem języka C++ (Zachowanie w przypadku przepełnienia)
W implementacji klasy `UInteger` wybrano politykę ścisłego (strict) rzucania wyjątków (`ArithmeticException` / `IllegalArgumentException`) na wszelkie operacje wykraczające poza 32-bitowy zakres bez znaku.
W języku C++ typ wbudowany `unsigned int` zachowuje się inaczej. Standard C++ gwarantuje, że przy przepełnieniu na operacjach bez znaku następuje **ciche zawijanie wartości (modulo 2^N)**. Przykładowo, maksymalna wartość + 1 w C++ da wynik `0`, bez żadnego powiadomienia czy wyjątku. W `UInteger` zrezygnowano z cichego zawijania na rzecz bezpieczeństwa biznesowego (fail-fast), typowego dla paradygmatów programowania w Javie, aby programista został jasno poinformowany o błędzie matematycznym, co ułatwia debugowanie.
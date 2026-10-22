package org.example;

/**
 * Klasa reprezentująca 32-bitową liczbę całkowitą bez znaku (unsigned integer).
 * Wartości mieszczą się w zakresie od 0 do 4294967295 (2^32 - 1).
 * @author Anton Sidarovich 258497
 * @version 1.0
 * @since 18-10-2026
 */
public final class UInteger extends Number implements Comparable<UInteger> {

    /** Najmniejsza reprezentowana wartość (0). */
    public static final long MIN_VALUE = 0L;

    /** Największa reprezentowana wartość (2^32 - 1). */
    public static final long MAX_VALUE = 4294967295L;

    /** Liczba bitów używana do reprezentacji wartości bez znaku. */
    public static final int SIZE = 32;

    /** Liczba bajtów używana do reprezentacji wartości bez znaku. */
    public static final int BYTES = 4;

    private final int value;

    private UInteger(int value) {
        this.value = value;
    }

    /**
     * Zwraca instancję UInteger reprezentującą podaną wartość typu long.
     *
     * @param value wartość do przekonwertowania
     * @return obiekt UInteger
     * @throws IllegalArgumentException jeśli wartość jest mniejsza niż MIN_VALUE lub większa niż MAX_VALUE
     */
    public static UInteger valueOf(long value) {
        if (value < MIN_VALUE || value > MAX_VALUE) {
            throw new IllegalArgumentException("Wartość poza dopuszczalnym zakresem dla UInteger: " + value);
        }
        return new UInteger((int) value);
    }

    /**
     * Zwraca instancję UInteger na podstawie podanego łańcucha znaków.
     *
     * @param value łańcuch znaków do sparsowania
     * @return obiekt UInteger
     * @throws NumberFormatException jeśli łańcuch nie zawiera poprawnej wartości całkowitej bez znaku
     */
    public static UInteger valueOf(String value) throws NumberFormatException {
        return parseUnsignedInt(value);
    }

    /**
     * Parsuje łańcuch znaków jako 32-bitową liczbę całkowitą bez znaku.
     *
     * @param value łańcuch znaków
     * @return obiekt UInteger
     * @throws NumberFormatException jeśli łańcuch nie jest poprawną liczbą
     */
    public static UInteger parseUnsignedInt(String value) throws NumberFormatException {
        int parsed = Integer.parseUnsignedInt(value);
        return new UInteger(parsed);
    }

    /**
     * Dodaje podaną wartość do obecnej.
     *
     * @param other wartość dodawana
     * @return nowy obiekt UInteger będący sumą
     * @throws ArithmeticException w przypadku przepełnienia wyniki większego niż MAX_VALUE
     */
    public UInteger add(UInteger other) {
        long result = this.longValue() + other.longValue();
        if (result > MAX_VALUE) {
            throw new ArithmeticException("Przepełnienie podczas dodawania: " + this.longValue() + " + " + other.longValue());
        }
        return new UInteger((int) result);
    }

    /**
     * Odejmuje podaną wartość od obecnej.
     *
     * @param other odjemnik
     * @return nowy obiekt UInteger będący różnicą
     * @throws ArithmeticException w przypadku wyniku mniejszego niż MIN_VALUE
     */
    public UInteger subtract(UInteger other) {
        long result = this.longValue() - other.longValue();
        if (result < MIN_VALUE) {
            throw new ArithmeticException("Niedomiar podczas odejmowania: " + this.longValue() + " - " + other.longValue());
        }
        return new UInteger((int) result);
    }

    /**
     * Mnoży obecną wartość przez podaną.
     *
     * @param other mnożnik
     * @return nowy obiekt UInteger będący iloczynem
     * @throws ArithmeticException w przypadku przepełnienia (wynik większy niż MAX_VALUE)
     */
    public UInteger multiply(UInteger other) {
        long result = this.longValue() * other.longValue();
        if (result > MAX_VALUE) {
            throw new ArithmeticException("Przepełnienie podczas mnożenia: " + this.longValue() + " * " + other.longValue());
        }
        return new UInteger((int) result);
    }

    /**
     * Dzieli obecną wartość przez podaną.
     *
     * @param other dzielnik
     * @return nowy obiekt UInteger będący ilorazem
     * @throws ArithmeticException jeśli dzielnik jest równy 0
     */
    public UInteger divide(UInteger other) {
        if (other.value == 0) {
            throw new ArithmeticException("/ by zero");
        }
        int result = Integer.divideUnsigned(this.value, other.value);
        return new UInteger(result);
    }

    /**
     * Zwraca resztę z dzielenia obecnej wartości przez podaną.
     *
     * @param other dzielnik
     * @return nowy obiekt UInteger będący resztą z dzielenia
     * @throws ArithmeticException jeśli dzielnik jest równy 0
     */
    public UInteger remainder(UInteger other) {
        if (other.value == 0) {
            throw new ArithmeticException("/ by zero");
        }
        int result = Integer.remainderUnsigned(this.value, other.value);
        return new UInteger(result);
    }

    @Override
    public int intValue() {
        return value;
    }

    @Override
    public long longValue() {
        return Integer.toUnsignedLong(value);
    }

    @Override
    public float floatValue() {
        return (float) longValue();
    }

    @Override
    public double doubleValue() {
        return (double) longValue();
    }

    @Override
    public byte byteValue() {
        return (byte) value;
    }

    @Override
    public short shortValue() {
        return (short) value;
    }

    @Override
    public int compareTo(UInteger other) {
        return Integer.compareUnsigned(this.value, other.value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        UInteger uInteger = (UInteger) obj;
        return value == uInteger.value;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }

    @Override
    public String toString() {
        return Integer.toUnsignedString(value);
    }

    /**
     * Zwraca reprezentację tekstową liczby w zadanym systemie liczbowym.
     *
     * @param radix podstawa systemu liczbowego
     * @return tekstowa reprezentacja liczby
     */
    public String toString(int radix) {
        return Integer.toUnsignedString(value, radix);
    }
}
package com.epam.izh.rd.online.service;

import java.math.BigDecimal;
import java.math.BigInteger;

public class SimpleBigNumbersService implements BigNumbersService {

    /**
     * Метод делит первое число на второе с заданной точностью
     * Например 1/3 с точностью 2 = 0.33
     *
     * @param range точность
     * @return результат
     */
    @Override
    public BigDecimal getPrecisionNumber(int a, int b, int range) {
        return new BigDecimal(a).divide(new BigDecimal(b), range, 5);
    }

    /**
     * Метод находит простое число по номеру
     *
     * @param range номер числа, считая с числа 2
     * @return простое число
     */
    @Override
    public BigInteger getPrimaryNumber(int range) {
        int count = range;
        long result = 2;
        while (count >= 0) {
            boolean simple = true;
            for (int i = 2; i < result; i++) {
                if (result % i == 0) {
                    simple = false;
                    break;
                }
            }
            if (simple) {
                count--;
            }
            result++;
        }
        return BigInteger.valueOf(result - 1);
    }
}

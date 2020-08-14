package com.epam.izh.rd.online.service;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleRegExpService implements RegExpService {

    /**
     * Метод должен читать файл sensitive_data.txt (из директории resources) и маскировать в нем конфиденциальную информацию.
     * Номер счета должен содержать только первые 4 и последние 4 цифры (1234 **** **** 5678). Метод должен содержать регулярное
     * выражение для поиска счета.
     *
     * @return обработанный текст
     */
    @Override
    public String maskSensitiveData() {

        StringBuffer buffer = new StringBuffer();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(
                System.getProperty("user.dir") + "/src/main/resources/sensitive_data.txt"))) {
            Pattern pattern = Pattern.compile("((\\d{4}\\s*)(\\d{4}\\s*){2}(\\d{4}\\s*))");
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    matcher.appendReplacement(buffer, matcher.group(2) + "**** **** " + matcher.group(4));
                }
                matcher.appendTail(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    /**
     * Метод должен считыввать файл sensitive_data.txt (из директории resources) и заменять плейсхолдер ${payment_amount} и ${balance} на заданные числа. Метод должен
     * содержать регулярное выражение для поиска плейсхолдеров
     *
     * @return обработанный текст
     */
    @Override
    public String replacePlaceholders(double paymentAmount, double balance) {
        String line;
        StringBuilder buffer = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(
                new FileReader(System.getProperty("user.dir") + "/src/main/resources/sensitive_data.txt"))) {
            while ((line = bufferedReader.readLine()) != null) {
                line = line.replaceAll("\\$\\{(payment_amount)\\}", String.valueOf((int) paymentAmount));
                line = line.replaceAll("\\$\\{(balance)\\}", String.valueOf((int) balance));
                buffer.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }
}

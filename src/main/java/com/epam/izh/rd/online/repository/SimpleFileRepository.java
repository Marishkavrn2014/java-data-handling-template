package com.epam.izh.rd.online.repository;

import java.io.*;
import java.nio.file.Files;

public class SimpleFileRepository implements FileRepository {

    /**
     * Метод рекурсивно подсчитывает количество файлов в директории
     *
     * @param path путь до директори
     * @return файлов, в том числе скрытых
     */
    @Override
    public long countFilesInDirectory(String path) {
        long count = 0;
        File file = new File(System.getProperty("user.dir") + "/src/main/resources/" + path);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (f.isFile()) {
                        count++;
                    } else {
                        count += countFilesInDirectory(path + "/" + f.getName());
                    }
                }
            }
        }
        return count;
    }

    /**
     * Метод рекурсивно подсчитывает количество папок в директории, считая корень
     *
     * @param path путь до директории
     * @return число папок
     */
    @Override
    public long countDirsInDirectory(String path) {
        long count = 0;
        File file = new File(System.getProperty("user.dir") + "/src/main/resources/" + path);
        if (file.isDirectory()) {
            count++;
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (f.isDirectory()) {
                        count += countDirsInDirectory(path + "/" + f.getName());
                    }
                }
            }
        }
        return count;
    }

    /**
     * Метод копирует все файлы с расширением .txt
     *
     * @param from путь откуда
     * @param to   путь куда
     */
    @Override
    public void copyTXTFiles(String from, String to) {
        File pathFile = new File(from);
        File[] files = pathFile.listFiles((file, name) -> {
            boolean result = false;
            if (name.endsWith(".txt")) {
                result = true;
            }
            return result;
        });
        if (files != null) {
            for (File value : files) {
                try {
                    Files.copy(value.toPath(), new File(
                            to + File.separator + value.getName()).toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Метод создает файл на диске с расширением txt
     *
     * @param path путь до нового файла
     * @param name имя файла
     * @return был ли создан файл
     */
    @Override
    public boolean createFile(String path, String name) {
        boolean result = false;
        File dir = new File(System.getProperty("user.dir") + "/target/classes/" + path);
        dir.mkdir();
        if (dir.exists()) {
            File file = new File(dir.getPath(), name);
            if (file.exists()) {
                result = true;
            } else {
                try {
                    result = file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * Метод считывает тело файла .txt из папки src/main/resources
     *
     * @param fileName имя файла
     * @return контент
     */
    @Override
    public String readFileFromResources(String fileName) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(
                System.getProperty("user.dir") + "/src/main/resources/" + fileName))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}

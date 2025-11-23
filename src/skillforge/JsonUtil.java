/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skillforge;

/**
 *
 * @author hp
 */

import com.google.gson.*;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.util.*;

public class JsonUtil {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
   
    public static <T> List<T> readList(String path, Type typeOfList) {
        try {
            Path p = Paths.get(path);
            if (!Files.exists(p)) {
                Files.createFile(p);
                Files.write(p, "[]".getBytes());
            }
            String content = new String(Files.readAllBytes(p));
            return gson.fromJson(content, typeOfList);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static <T> void writeList(String path, List<T> list) {
        try (Writer writer = new FileWriter(path)) {
            gson.toJson(list, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package dev.micah.powertools.io;

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

public class FileManager {

    private final static Gson GSON = new Gson();
    private File rootDir = null;

    public FileManager(File rootDir) {
        this.rootDir = rootDir;
        if (rootDir != null) init();
    }

    private void init() {
        if (!rootDir.exists()) { rootDir.mkdirs(); }
    }

    public boolean write(File file, Object obj) {
        try {
            if (!file.exists()) { file.createNewFile(); }
            FileOutputStream out = new FileOutputStream(file);
            out.write(GSON.toJson(obj).getBytes());
            out.flush(); out.close(); return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public <T> List<T> writeList(File file, Type listType, List<T> list) {
        if (listType == null || list.isEmpty() || list == null) { Bukkit.getLogger().info("Failed to write list"); return null; }
        try {
            if (!file.exists()) { file.createNewFile(); }
            FileOutputStream out = new FileOutputStream(file);
            Bukkit.getLogger().info(GSON.toJson(list, listType));
            out.write(GSON.toJson(list, listType).getBytes());
            out.flush(); out.close(); return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T extends Object> T read(@NotNull File fileIn, Class<T> type) {
        try {
            FileInputStream in = new FileInputStream(fileIn);
            InputStreamReader readerIn = new InputStreamReader(in);
            BufferedReader reader = new BufferedReader(readerIn);
            StringBuilder stringBuilder = new StringBuilder();
            String line; while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            return GSON.fromJson(stringBuilder.toString(), type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> List<T> readList(@NotNull File fileIn, Type listType) {
        try {
            FileInputStream in = new FileInputStream(fileIn);
            InputStreamReader readerIn = new InputStreamReader(in);
            BufferedReader reader = new BufferedReader(readerIn);
            StringBuilder stringBuilder = new StringBuilder();
            String line; while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            return GSON.fromJson(stringBuilder.toString(), listType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}

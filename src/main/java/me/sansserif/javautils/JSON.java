package me.sansserif.javautils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;

public class JSON {
    public enum FileStatus {
        NON_EXISTENT,
        UNREADABLE,
        TOO_BIG,
        INVALID,
        VALID
    }
    private final File file;
    private final Console console;
    private JSONObject json = null;
    public JSON(File jsonfile) throws Exception {
        this.file = jsonfile;
        this.console = new Console("JSON(" + file.getName() + ")");

        // extract json from file
        if (validate().equals(FileStatus.VALID)) {
            try {
                console.log("Valid JSON supplied.");
                json = new JSONObject(Files.readString(jsonfile.toPath()));
            } catch (IOException ignored) {}
        } else {
            // fixers and errors
            if (validate().equals(FileStatus.NON_EXISTENT)) {
                console.warning("JSON file does not exist, creating one ...");
                try {
                    file.createNewFile();
                } catch (IOException err) {
                    throw new Exception("Could not create the file. Check write permissions?");
                }
            }
            if (validate().equals(FileStatus.UNREADABLE)) {
                throw new Exception("JSON file is unreadable. Check read permissions?");
            }
            if (validate().equals(FileStatus.TOO_BIG)) {
                throw new Exception("JSON file is too big. Consider adding more memory.");
            }
            if (validate().equals(FileStatus.INVALID)) {
                console.warning("JSON file is corrupted (invalid). Working with a new one.");
                json = new JSONObject();
            }
        }
    }
    public JSONObject get() {
        if (json == null)
            console.error("JSON object was not initialized correctly!");
        return json;
    }
    public boolean save() {
        try {
            if (!file.exists()) file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(json);
            printWriter.close();
            fileWriter.close();
            console.success("Saved.");
            return true;
        } catch (IOException err) {
            console.error("File is not writable. Check write permissions?");
            return false;
        }
    }
    public boolean checkKey(String key) {
        if (json.has(key))
            if (json.getString(key) != null)
                if (!json.getString(key).isEmpty())
                    return true;
        return false;
    }
    public FileStatus validate() {
        return validate(file);
    }
    public static FileStatus validate(File jsonfile) {
        if (!jsonfile.exists())
            return FileStatus.NON_EXISTENT;
        String json = "";
        try {
            json = Files.readString(jsonfile.toPath());
        } catch (IOException e) {
            return FileStatus.UNREADABLE;
        } catch (OutOfMemoryError e) {
            return FileStatus.TOO_BIG;
        }
        try {
            JSONObject object = new JSONObject(json);
        } catch (JSONException e) {
            return FileStatus.INVALID;
        }
        return FileStatus.VALID;
    }
}

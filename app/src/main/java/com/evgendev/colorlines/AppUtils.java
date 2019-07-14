package com.evgendev.colorlines;



import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class AppUtils {
    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_SAVEGAME = "savegame";
    public static final String FILESAVE = "fsave"; //Путь до файла с сохранением игры


    public static void objToFile(String filepath, Object object, Context context){ //Сохранение объект в файл
        try {
            FileOutputStream fos = context.openFileOutput(filepath, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(object);
            os.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object FileToObj(String filepath, Context context){ //Из файла в объект
        try {
            FileInputStream fis = context.openFileInput(filepath);
            ObjectInputStream is = new ObjectInputStream(fis);
            Object simpleClass = is.readObject();
            is.close();
            fis.close();
            return simpleClass;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}

package com.google.ar.core.examples.java.helloar;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.google.atap.tangoservice.SupportedDevices;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Created by ipcjs on 2017/8/30.
 */

public class ReflectUtil {
    public static final String TAG = ReflectUtil.class.getSimpleName();
    public static final String SUPPORTED_FINGERPRINT = "sailfish:7";

    public static void setFinalStatic(Field field, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field modifiersField = Field.class.getDeclaredField("accessFlags");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.setAccessible(true);
        field.set(null, value);
    }

    public static void ensureSupportARCore(Context context) {
        Log.i(TAG, Build.FINGERPRINT);
        if (!SupportedDevices.isSupported(context)) {
            try {
                setFinalStatic(Build.class.getDeclaredField("FINGERPRINT"), Build.FINGERPRINT + "/" + SUPPORTED_FINGERPRINT);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        Log.i(TAG, Build.FINGERPRINT);
    }
}

package org.lntu.online.util;

import android.content.Context;
import android.support.annotation.RawRes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class ResRawUtils {

    private ResRawUtils() {}

    public static String getString(Context context, @RawRes int rawId) {
        try {
            InputStream is = context.getResources().openRawResource(rawId);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            sb.deleteCharAt(sb.length() - 1);
            reader.close();
            return sb.toString();
        } catch (IOException e) {
            return "资源读取失败。";
        }
    }

}

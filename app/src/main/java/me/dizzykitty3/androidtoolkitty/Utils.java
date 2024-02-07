package me.dizzykitty3.androidtoolkitty;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class Utils {
    private static Toast currentToast;
    private static Snackbar currentSnackbar;

    private Utils() {
        // Empty
    }

    public static void debugLog(@NonNull String logEvent) {
        Log.d("me.dizzykitty3.androidtoolkitty", logEvent);
    }

    public static void showToast(@NonNull Context context, @NonNull String toastText) {
        if (currentToast != null) {
            currentToast.cancel();
        }
        currentToast = Toast.makeText(context, toastText, Toast.LENGTH_SHORT);
        currentToast.show();
    }

    public static void showSnackbar(@NonNull View view, @NonNull String snackbarText) {
        if (Objects.nonNull(currentSnackbar) && currentSnackbar.isShown()) {
            currentSnackbar.dismiss();
        }
        currentSnackbar = Snackbar.make(view, snackbarText, BaseTransientBottomBar.LENGTH_SHORT);
        currentSnackbar.show();
    }

    public static String convertUnicodeToCharacter(String unicode) {
        var stringBuilder = new StringBuilder();
        for (int i = 0; i < unicode.length(); i += 4) {
            var hexValue = unicode.substring(i, i + 4);
            var decimalValue = Integer.parseInt(hexValue, 16);
            stringBuilder.append((char) decimalValue);
        }
        return stringBuilder.toString();
    }
}
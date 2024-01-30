package me.dizzykitty3.androidtoolkitty;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class CommonUtils {
    private static Toast currentToast;
    private static Snackbar currentSnackbar;

    private CommonUtils() {
        // Empty
    }

    public static void debugLog(@NonNull String logEvent) {
        Log.d("com.example.myapplication", logEvent);
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
        currentSnackbar = Snackbar.make(view, snackbarText, Snackbar.LENGTH_SHORT);
        currentSnackbar.show();
    }
}
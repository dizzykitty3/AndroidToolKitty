package me.dizzykitty3.androidtoolkitty;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.Objects;

public class Utils {
    private static Toast currentToast;

    private Utils() {
        // Empty
    }

    public static String convertUnicodeToCharacter(String unicode) throws Exception {
        final var length = unicode.length();
        if (length % 4 != 0) throw new Exception("The length of the input is not a multiple of 4");
        try {
            final var stringBuilder = new StringBuilder();
            for (int i = 0; i < length; i += 4) {
                final var hexValue = unicode.substring(i, i + 4);
                final var decimalValue = Integer.parseInt(hexValue, 16);
                stringBuilder.append((char) decimalValue);
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            throw new Exception("Invalid input");
        }
    }

    public static void openGoogleMaps(@NonNull Context context, @NonNull final String latitude, @NonNull final String longitude) {
        final var coordinates = latitude + "," + longitude;
        final var gmmIntentUri = Uri.parse("geo:" + coordinates + "?q=" + coordinates);

        final var mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        if (Objects.nonNull(mapIntent.resolveActivity(context.getPackageManager()))) {
            context.startActivity(mapIntent);
            return;
        }
        Utils.showToastAndRecordLog(context, "Google Maps app is not installed");
        openCertainAppOnPlayStore(context, "com.google.android.apps.maps");
    }

    public static void showToastAndRecordLog(@NonNull Context context, @NonNull String event) {
        debugLog(event);
        showToast(context, event);
    }

    private static void openCertainAppOnPlayStore(@NonNull Context context, @NonNull final String packageName) {
        final var playStoreUri = Uri.parse("market://details?id=" + packageName);
        final var playStoreIntent = new Intent(Intent.ACTION_VIEW, playStoreUri);

        if (Objects.nonNull(playStoreIntent.resolveActivity(context.getPackageManager()))) {
            context.startActivity(playStoreIntent);
            return;
        }
        Utils.showToastAndRecordLog(context, "Google Play Store app is not installed");
    }

    public static void debugLog(@NonNull String logEvent) {
        Log.d("me.dizzykitty3.androidtoolkitty", logEvent);
    }

    public static void showToast(@NonNull Context context, @NonNull String toastText) {
        if (Objects.nonNull(currentToast)) {
            currentToast.cancel();
        }
        currentToast = Toast.makeText(context, toastText, Toast.LENGTH_SHORT);
        currentToast.show();
    }
}
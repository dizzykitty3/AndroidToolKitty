package me.dizzykitty3.androidtoolkitty;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import androidx.annotation.NonNull;

public class ClipboardUtils {
    private final Context context;

    public ClipboardUtils(@NonNull Context context) {
        this.context = context;
    }

    public void clearClipboard() {
        final var clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard.hasPrimaryClip()) {
            clipboard.clearPrimaryClip();
        }
    }

    public void copyTextToClipboard(String text) {
        final var clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("label", text);
        clipboard.setPrimaryClip(clip);
    }
}
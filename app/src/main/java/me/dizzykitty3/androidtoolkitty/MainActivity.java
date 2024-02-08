package me.dizzykitty3.androidtoolkitty;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import java.util.Objects;

import me.dizzykitty3.androidtoolkitty.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final String CLIPBOARD_CLEARED = "clipboard cleared";
    private static final String AUTO_CLIPBOARD_CLEARED = "clipboard cleared automatically";
    private ActivityMainBinding binding;
    private ClipboardUtils clipboardUtils;
    private boolean isAutoClearClipboard;
    private boolean isUseToast;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Core
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);
        sharedPreferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);
        final var editor = sharedPreferences.edit();
        clipboardUtils = new ClipboardUtils(this);
        getSharedPreferencesValues();

        // Clipboard group
        final var clearClipboardButton = binding.clearClipboardButton;
        clearClipboardButton.setOnClickListener(v -> {
            clipboardUtils.clearClipboard();
            Utils.debugLog(CLIPBOARD_CLEARED);
            if (isUseToast) {
                Utils.showToast(this, CLIPBOARD_CLEARED);
            } else {
                Utils.showSnackbar(binding.getRoot(), CLIPBOARD_CLEARED);
            }
        });

        final var autoClearClipboardSettingSwitch = binding.autoClearClipboardSettingSwitch;
        autoClearClipboardSettingSwitch.setChecked(isAutoClearClipboard);
        autoClearClipboardSettingSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isAutoClearClipboard = isChecked;
            editor.putBoolean(String.valueOf(R.string.key001_auto_clear_clipboard_switch_state), isChecked);
            editor.apply();

            if (isChecked) {
                Utils.debugLog("auto clear clipboard setting switch is now: on");
            } else {
                Utils.debugLog("auto clear clipboard setting switch is now: off");
            }
        });

        // Google Maps group
        final var latInputEditText = binding.inputLat;
        final var lngInputEditText = binding.inputLng;
        final var openGoogleMapsButton = binding.openGoogleMapsButton;
        openGoogleMapsButton.setOnClickListener(v -> {
            String latitude = Objects.requireNonNull(latInputEditText.getText()).toString();
            String longitude = Objects.requireNonNull(lngInputEditText.getText()).toString();
            openGoogleMaps("".equals(latitude) ? "0" : latitude, "".equals(longitude) ? "0" : longitude);
        });

        // Unicode group
        final var unicodeInputEditText = binding.inputUnicode;
        final var convertUnicodeButton = binding.unicodeConvertButton;
        final var unicodeOutputTextView = binding.outputUnicode;
        convertUnicodeButton.setOnClickListener(v -> {
            final var unicode = Objects.requireNonNull(unicodeInputEditText.getText()).toString();
            if (unicode.length() == 0) {
                return;
            }
            String result;
            try {
                result = Utils.convertUnicodeToCharacter(unicode);
            } catch (Exception e) {
                Utils.showToast(this, e.getMessage() != null ? e.getMessage() : "Unknown error occurred");
                return;
            }
            unicodeOutputTextView.setText(result);
            clipboardUtils.copyTextToClipboard(result);
            Utils.showToast(this, result + " copied to clipboard");
        });

        // Settings group
        final var useToastSettingSwitch = binding.useToastSettingSwitch;
        useToastSettingSwitch.setChecked(isUseToast);
        useToastSettingSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isUseToast = isChecked;
            editor.putBoolean(String.valueOf(R.string.key002_use_toast_switch_state), isChecked);
            editor.apply();

            if (isChecked) {
                Utils.showToast(this, "toast would look like this");
                Utils.debugLog("use toast setting switch is now: on");
            } else {
                Utils.showSnackbar(binding.getRoot(), "snackbar would look like this");
                Utils.debugLog("use toast setting switch is now: off");
            }
        });
    }

    private void openGoogleMaps(String latitude, String longitude) {
        final var coordinates = latitude + "," + longitude;
        final var gmmIntentUri = Uri.parse("geo:" + coordinates + "?q=" + coordinates);

        final var mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            Utils.showToast(this, "Google Maps app is not installed");
            Utils.debugLog("Google Maps app is not installed");
            final var playStoreUri = Uri.parse("market://details?id=com.google.android.apps.maps");
            final var playStoreIntent = new Intent(Intent.ACTION_VIEW, playStoreUri);

            if (playStoreIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(playStoreIntent);
            } else {
                Utils.showToast(this, "Google Play Store app is not installed");
                Utils.debugLog("Google Play Store app is not installed");
            }
        }
    }

    private void getSharedPreferencesValues() {
        isAutoClearClipboard = sharedPreferences.getBoolean(String.valueOf(R.string.key001_auto_clear_clipboard_switch_state), false);
        isUseToast = sharedPreferences.getBoolean(String.valueOf(R.string.key002_use_toast_switch_state), false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSharedPreferencesValues();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && (isAutoClearClipboard)) {
            clipboardUtils.clearClipboard();
            Utils.debugLog(AUTO_CLIPBOARD_CLEARED);
            if (isUseToast) {
                Utils.showToast(this, AUTO_CLIPBOARD_CLEARED);
            } else {
                Utils.showSnackbar(binding.getRoot(), AUTO_CLIPBOARD_CLEARED);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (binding != null) {
            binding.unbind();
        }
    }
}
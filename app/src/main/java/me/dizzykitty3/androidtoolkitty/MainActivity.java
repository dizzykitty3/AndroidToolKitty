package me.dizzykitty3.androidtoolkitty;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

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
    private TextView unicodeOutputTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Core
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);
        sharedPreferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);
        var editor = sharedPreferences.edit();
        clipboardUtils = new ClipboardUtils(this);
        getSharedPreferencesValues();

        // Clipboard group
        var clearClipboardButton = binding.clearClipboardButton;
        clearClipboardButton.setOnClickListener(v -> {
            clipboardUtils.clearClipboard();
            CommonUtils.debugLog(CLIPBOARD_CLEARED);
            if (isUseToast) {
                CommonUtils.showToast(this, CLIPBOARD_CLEARED);
            } else {
                CommonUtils.showSnackbar(binding.getRoot(), CLIPBOARD_CLEARED);
            }
        });

        var autoClearClipboardSettingSwitch = binding.autoClearClipboardSettingSwitch;
        autoClearClipboardSettingSwitch.setChecked(isAutoClearClipboard);
        autoClearClipboardSettingSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isAutoClearClipboard = isChecked;
            editor.putBoolean(String.valueOf(R.string.key001_auto_clear_clipboard_switch_state), isChecked);
            editor.apply();

            if (isChecked) {
                CommonUtils.debugLog("auto clear clipboard setting switch is now: on");
            } else {
                CommonUtils.debugLog("auto clear clipboard setting switch is now: off");
            }
        });

        // Google Maps group
        var latInputEditText = binding.inputLat;
        var lngInputEditText = binding.inputLng;
        var openGoogleMapsButton = binding.openGoogleMapsButton;
        openGoogleMapsButton.setOnClickListener(v -> {
            String latitude = Objects.requireNonNull(latInputEditText.getText()).toString();
            String longitude = Objects.requireNonNull(lngInputEditText.getText()).toString();
            openGoogleMaps("".equals(latitude) ? "0" : latitude, "".equals(longitude) ? "0" : longitude);
        });

        // Unicode group
        var unicodeInputEditText = binding.inputUnicode;
        var convertUnicodeButton = binding.unicodeConvertButton;
        unicodeOutputTextView = binding.outputUnicode;
        convertUnicodeButton.setOnClickListener(v -> convertUnicode(Objects.requireNonNull(unicodeInputEditText.getText()).toString()));

        // Settings group
        var useToastSettingSwitch = binding.useToastSettingSwitch;
        useToastSettingSwitch.setChecked(isUseToast);
        useToastSettingSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isUseToast = isChecked;
            editor.putBoolean(String.valueOf(R.string.key002_use_toast_switch_state), isChecked);
            editor.apply();

            if (isChecked) {
                CommonUtils.showToast(this, "toast would look like this");
                CommonUtils.debugLog("use toast setting switch is now: on");
            } else {
                CommonUtils.showSnackbar(binding.getRoot(), "snackbar would look like this");
                CommonUtils.debugLog("use toast setting switch is now: off");
            }
        });
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
            CommonUtils.debugLog(AUTO_CLIPBOARD_CLEARED);
            if (isUseToast) {
                CommonUtils.showToast(this, AUTO_CLIPBOARD_CLEARED);
            } else {
                CommonUtils.showSnackbar(binding.getRoot(), AUTO_CLIPBOARD_CLEARED);
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

    private void openGoogleMaps(String latitude, String longitude) {
        var coordinates = latitude + "," + longitude;
        var gmmIntentUri = Uri.parse("geo:" + coordinates + "?q=" + coordinates);

        var mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            CommonUtils.showToast(this, "Google Maps app is not installed");
            CommonUtils.debugLog("Google Maps app is not installed");
            var playStoreUri = Uri.parse("market://details?id=com.google.android.apps.maps");
            var playStoreIntent = new Intent(Intent.ACTION_VIEW, playStoreUri);

            if (playStoreIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(playStoreIntent);
            } else {
                CommonUtils.showToast(this, "Google Play Store app is not installed");
                CommonUtils.debugLog("Google Play Store app is not installed");
            }
        }
    }

    private void convertUnicode(String unicode) {
        var result = UnicodeUtils.convertUnicodeToCharacter(unicode);
        unicodeOutputTextView.setText(result);
        clipboardUtils.copyTextToClipboard(result);
        CommonUtils.showToast(this, result + "copied to clipboard");
    }
}
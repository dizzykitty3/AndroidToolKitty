package me.dizzykitty3.androidtoolkitty;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        initCore();
        getSharedPreferencesValues();
        initClipboardGroup();
        initGoogleMapsGroup();
        initUnicodeGroup();
        initSettingsGroup();
    }

    private void initCore() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);
        sharedPreferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);
        editor = sharedPreferences.edit();
        clipboardUtils = new ClipboardUtils(this);
        getSharedPreferencesValues();
    }

    private void getSharedPreferencesValues() {
        isAutoClearClipboard = sharedPreferences.getBoolean(String.valueOf(R.string.key001_auto_clear_clipboard_switch_state), false);
        isUseToast = sharedPreferences.getBoolean(String.valueOf(R.string.key002_use_toast_switch_state), false);
    }

    private void initClipboardGroup() {
        binding.clearClipboardButton.setOnClickListener(v -> {
            clipboardUtils.clearClipboard();
            Utils.debugLog(CLIPBOARD_CLEARED);
            if (isUseToast) {
                Utils.showToast(this, CLIPBOARD_CLEARED);
            } else {
                Utils.showSnackbar(binding.getRoot(), CLIPBOARD_CLEARED);
            }
        });

        binding.autoClearClipboardSettingSwitch.setChecked(isAutoClearClipboard);
        binding.autoClearClipboardSettingSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isAutoClearClipboard = isChecked;
            editor.putBoolean(String.valueOf(R.string.key001_auto_clear_clipboard_switch_state), isChecked);
            editor.apply();

            if (isChecked) {
                Utils.debugLog("auto clear clipboard setting switch is now: on");
            } else {
                Utils.debugLog("auto clear clipboard setting switch is now: off");
            }
        });
    }

    private void initGoogleMapsGroup() {
        binding.openGoogleMapsButton.setOnClickListener(v -> {
            String latitude = Objects.requireNonNull(binding.latitudeTextInput.getText()).toString();
            String longitude = Objects.requireNonNull(binding.longitudeTextInput.getText()).toString();
            openGoogleMaps("".equals(latitude) ? "0" : latitude, "".equals(longitude) ? "0" : longitude);
        });
    }

    private void initUnicodeGroup() {
        binding.convertToCharacterButton.setOnClickListener(v -> {
            final var unicode = Objects.requireNonNull(binding.unicodeTextInput.getText()).toString();
            if (unicode.length() == 0) {
                return;
            }
            try {
                var result = Utils.convertUnicodeToCharacter(unicode);
                binding.unicodeOutputTextView.setText(result);
                clipboardUtils.copyTextToClipboard(result);
                Utils.showToast(this, result + " copied to clipboard");
            } catch (Exception e) {
                Utils.showToast(this, e.getMessage() != null ? e.getMessage() : "Unknown error occurred");
            }
        });
    }

    private void initSettingsGroup() {
        binding.useToastSettingSwitch.setChecked(isUseToast);
        binding.useToastSettingSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
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

    private void openGoogleMaps(@NonNull final String latitude, @NonNull final String longitude) {
        final var coordinates = latitude + "," + longitude;
        final var gmmIntentUri = Uri.parse("geo:" + coordinates + "?q=" + coordinates);

        final var mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        if (Objects.nonNull(mapIntent.resolveActivity(getPackageManager()))) {
            startActivity(mapIntent);
            return;
        }
        Utils.showToastAndRecordLog(this, "Google Maps app is not installed");
        openCertainAppOnPlayStore("com.google.android.apps.maps");
    }

    private void openCertainAppOnPlayStore(@NonNull final String packageName) {
        final var playStoreUri = Uri.parse("market://details?id=" + packageName);
        final var playStoreIntent = new Intent(Intent.ACTION_VIEW, playStoreUri);

        if (Objects.nonNull(playStoreIntent.resolveActivity(getPackageManager()))) {
            startActivity(playStoreIntent);
            return;
        }
        Utils.showToastAndRecordLog(this, "Google Play Store app is not installed");
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
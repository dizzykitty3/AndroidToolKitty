package me.dizzykitty3.androidtoolkitty;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import java.util.Objects;

import me.dizzykitty3.androidtoolkitty.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final String CLIPBOARD_CLEARED = "clipboard cleared";
    private static final String AUTO_CLIPBOARD_CLEARED = "clipboard cleared automatically";
    private static final String KEY_AUTO_CLEAR_CLIPBOARD = "key_auto_clear_clipboard";
    private static final String KEY_IS_USE_TOAST = "key_is_use_toast";
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
        sharedPreferences = getSharedPreferences("Android ToolKitty", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        clipboardUtils = new ClipboardUtils(this);
    }

    private void getSharedPreferencesValues() {
        isAutoClearClipboard = sharedPreferences.getBoolean(KEY_AUTO_CLEAR_CLIPBOARD, false);
        isUseToast = sharedPreferences.getBoolean(KEY_IS_USE_TOAST, false);
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
            editor.putBoolean(KEY_AUTO_CLEAR_CLIPBOARD, isChecked);
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
            Utils.openGoogleMaps(this, "".equals(latitude) ? "0" : latitude, "".equals(longitude) ? "0" : longitude);
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
            editor.putBoolean(KEY_IS_USE_TOAST, isChecked);
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

    @Override
    protected void onResume() {
        super.onResume();
        getSharedPreferencesValues();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && isAutoClearClipboard) {
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
        if (Objects.nonNull(binding)) {
            binding.unbind();
        }
    }
}
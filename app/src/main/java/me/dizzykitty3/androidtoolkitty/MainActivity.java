package me.dizzykitty3.androidtoolkitty;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import java.util.Objects;

import me.dizzykitty3.androidtoolkitty.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final String CLIPBOARD_CLEARED = "clipboard cleared";
    private static final String KEY_AUTO_CLEAR_CLIPBOARD = "key_auto_clear_clipboard";
    private ActivityMainBinding binding;
    private ClipboardUtils clipboardUtils;
    private boolean isAutoClearClipboard;
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
        initTestGroup();
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
    }

    private void initClipboardGroup() {
        binding.clearClipboardButton.setOnClickListener(v -> {
            clipboardUtils.clearClipboard();
            Utils.showToastAndRecordLog(this, CLIPBOARD_CLEARED);
        });

        binding.autoClearClipboardSettingSwitch.setChecked(isAutoClearClipboard);
        binding.autoClearClipboardSettingSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isAutoClearClipboard = isChecked;
            editor.putBoolean(KEY_AUTO_CLEAR_CLIPBOARD, isChecked);
            editor.apply();
            Utils.debugLog(isChecked ? "auto clear clipboard is now: on" : "auto clear clipboard is now: off");
        });
    }

    private void initGoogleMapsGroup() {
        binding.openGoogleMapsButton.setOnClickListener(v -> {
            String latitude = Objects.requireNonNull(binding.latitudeTextInput.getText()).toString();
            String longitude = Objects.requireNonNull(binding.longitudeTextInput.getText()).toString();
            Utils.openGoogleMaps(this, latitude.isEmpty() ? "0" : latitude, longitude.isEmpty() ? "0" : longitude);
        });
    }

    private void initUnicodeGroup() {
        binding.convertToCharacterButton.setOnClickListener(v -> {
            final var unicode = Objects.requireNonNull(binding.unicodeTextInput.getText()).toString();
            if (unicode.isEmpty()) {
                return;
            }
            try {
                var result = Utils.convertUnicodeToCharacter(unicode);
                binding.unicodeOutputTextView.setText(result);
                clipboardUtils.copyTextToClipboard(result);
                Utils.showToast(this, result + " copied");
            } catch (Exception e) {
                Utils.showToast(this, e.getMessage() != null ? e.getMessage() : "Unknown error occurred");
            }
        });
    }

    private void initTestGroup() {
        binding.testButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, TestActivity.class);
            startActivity(intent);
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
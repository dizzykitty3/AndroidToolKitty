package me.dizzykitty3.androidtoolkitty;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.materialswitch.MaterialSwitch;

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

        // Core
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);
        sharedPreferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);
        editor = sharedPreferences.edit();
        clipboardUtils = new ClipboardUtils(this);
        getSharedPreferencesValues();

        // Clipboard group
        Button clearClipboardButton = binding.clearClipboardButton;
        clearClipboardButton.setOnClickListener(v -> {
            clipboardUtils.clearClipboard();
            CommonUtils.debugLog(CLIPBOARD_CLEARED);
            if (isUseToast) {
                CommonUtils.showToast(this, CLIPBOARD_CLEARED);
            } else {
                CommonUtils.showSnackbar(binding.getRoot(), CLIPBOARD_CLEARED);
            }
        });
        MaterialSwitch autoClearClipboardSettingSwitch = binding.autoClearClipboardSettingSwitch;
        autoClearClipboardSettingSwitch.setChecked(isAutoClearClipboard);
        autoClearClipboardSettingSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isAutoClearClipboard = isChecked;
            editor.putBoolean(String.valueOf(R.string.key001_auto_clear_clipboard_switch_state), isChecked);
            editor.apply();
        });

        // Settings group
        MaterialSwitch useToastSettingSwitch = binding.useToastSettingSwitch;
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
}
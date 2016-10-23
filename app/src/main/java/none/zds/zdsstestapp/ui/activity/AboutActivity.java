package none.zds.zdsstestapp.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import none.zds.zdsstestapp.BuildConfig;
import none.zds.zdsstestapp.R;
import none.zds.zdsstestapp.databinding.ActivityAboutBinding;

public class AboutActivity extends AppCompatActivity {
    private ActivityAboutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_about);
        String versionInfo = getString(R.string.version_info, BuildConfig.VERSION_NAME);
        binding.versionInfo.setText(versionInfo);
    }

}

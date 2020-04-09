package mi.song.weekand.farm.ui.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import mi.song.weekand.farm.R;
import mi.song.weekand.farm.databinding.ActivityCorpDiaryBinding;

public class CorpDiaryActivity extends AppCompatActivity {
    private ActivityCorpDiaryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corp_diary);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_corp_diary);
        binding.setCorpDiary(this);
    }
}

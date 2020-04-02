package mi.song.weekand.farm.ui.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import mi.song.weekand.farm.R;
import mi.song.weekand.farm.databinding.ActivityCorpListBinding;


public class CorpListActivity extends AppCompatActivity {
    private ActivityCorpListBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corp_list);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_corp_list);
        binding.setCorp(this);
    }
}

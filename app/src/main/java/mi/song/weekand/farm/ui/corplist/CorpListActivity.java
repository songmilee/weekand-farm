package mi.song.weekand.farm.ui.corplist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import mi.song.weekand.farm.R;
import mi.song.weekand.farm.databinding.ActivityCorpListBinding;
import mi.song.weekand.farm.model.Corp;
import mi.song.weekand.farm.ui.corplist.add.CorpAddActivity;


public class CorpListActivity extends AppCompatActivity {
    private ActivityCorpListBinding binding;
    private CorpListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corp_list);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_corp_list);
        binding.setCorp(this);

        init();
    }

    private void init(){
        adapter = new CorpListAdapter(this);
        binding.corpList.setAdapter(adapter);
        binding.corpList.setLayoutManager(new LinearLayoutManager(this));
        adapter.addItem(new Corp("Test Corp", null, "test memo"));

        binding.corpListAddBtn.setOnClickListener(v ->{
            startActivity(new Intent(CorpListActivity.this, CorpAddActivity.class));
        });
    }
}

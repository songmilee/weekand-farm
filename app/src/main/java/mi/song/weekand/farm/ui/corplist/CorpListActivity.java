package mi.song.weekand.farm.ui.corplist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseUser;

import mi.song.weekand.farm.R;
import mi.song.weekand.farm.databinding.ActivityCorpListBinding;
import mi.song.weekand.farm.model.Corp;
import mi.song.weekand.farm.ui.corplist.add.CorpAddActivity;


public class CorpListActivity extends AppCompatActivity {
    private final String TAG = CorpListActivity.class.getSimpleName();

    private ActivityCorpListBinding binding;
    private CorpListAdapter adapter;
    private String uuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corp_list);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_corp_list);
        binding.setCorp(this);

        getUser();
        init();
    }

    private void getUser(){
        Intent intent = getIntent();
        FirebaseUser user = (FirebaseUser)intent.getParcelableExtra("user");
        uuid = user.getUid();

        Log.d(TAG, "user uuid : " + uuid);
    }

    private void init(){
        adapter = new CorpListAdapter(this);
        binding.corpList.setAdapter(adapter);
        binding.corpList.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        adapter.addItem(new Corp("Test Corp", null, "test memo"));

        binding.corpListAddBtn.setOnClickListener(v ->{   startActivity(new Intent(CorpListActivity.this, CorpAddActivity.class));     });
    }
}
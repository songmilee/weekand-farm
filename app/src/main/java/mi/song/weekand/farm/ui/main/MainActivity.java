package mi.song.weekand.farm.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import mi.song.weekand.farm.R;
import mi.song.weekand.farm.databinding.ActivityMainBinding;
import mi.song.weekand.farm.ui.menu.adddiary.AddFragment;
import mi.song.weekand.farm.ui.menu.home.HomeFragment;
import mi.song.weekand.farm.ui.menu.setting.SettingFragment;
import mi.song.weekand.farm.ui.menu.user.UserFragment;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FragmentManager manager;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        init();
    }

    private void init(){
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.add(R.id.main_frame_layout, HomeFragment.newInstance()).commit();

        binding.mainBottomNavigation.setOnNavigationItemSelectedListener(selectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()){
                case R.id.main_menu_home:
                    replaceFragment(HomeFragment.newInstance());
                    break;

                case R.id.main_menu_user:
                    replaceFragment(UserFragment.newInstance());
                    break;

                case R.id.main_menu_setting:
                    replaceFragment(SettingFragment.newInstance());
                    break;

                case R.id.main_menu_add_diary:
                    replaceFragment(AddFragment.newInstance());
                    break;
            }

            return true;
        }
    };

    private void replaceFragment(Fragment fragment){
        transaction = manager.beginTransaction();
        transaction.replace(R.id.main_frame_layout, fragment).commit();
    }
}

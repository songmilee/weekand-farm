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

import java.util.ArrayList;

import mi.song.weekand.farm.R;
import mi.song.weekand.farm.databinding.ActivityMainBinding;
import mi.song.weekand.farm.ui.menu.adddiary.AddFragment;
import mi.song.weekand.farm.ui.menu.home.HomeFragment;
import mi.song.weekand.farm.ui.menu.setting.SettingFragment;
import mi.song.weekand.farm.util.ProgressUtil;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private ArrayList<Fragment> fragmentList;
    private Fragment curFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        init();
    }

    private void init(){
        setFragmentList();

        ProgressUtil.getInstance(this); //activity로 progress dialog 초기화 (window bad token 문제)

        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        curFragment = fragmentList.get(1);

        transaction.add(R.id.main_frame_layout, curFragment).commit();

        binding.mainBottomNavigation.setOnNavigationItemSelectedListener(selectedListener);
    }

    private void setFragmentList(){
        fragmentList = new ArrayList<>();

        fragmentList.add(AddFragment.newInstance());
        fragmentList.add(HomeFragment.newInstance());
        fragmentList.add(SettingFragment.newInstance());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()){
                case R.id.main_menu_add_diary:
                    replaceFragment(fragmentList.get(0));
                    break;

                case R.id.main_menu_home:
                    replaceFragment(fragmentList.get(1));
                    break;

                case R.id.main_menu_setting:
                    replaceFragment(fragmentList.get(2));
                    break;
            }

            return true;
        }
    };

    private void replaceFragment(Fragment fragment){
        transaction = manager.beginTransaction();
        transaction.addToBackStack(curFragment.getClass().getName());
        transaction.replace(R.id.main_frame_layout, fragment).commit();

        curFragment = fragment;
    }
}

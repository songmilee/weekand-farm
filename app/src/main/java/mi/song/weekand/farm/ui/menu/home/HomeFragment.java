package mi.song.weekand.farm.ui.menu.home;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Map;

import mi.song.weekand.farm.R;
import mi.song.weekand.farm.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment implements HomeInterface.View {
    FragmentHomeBinding binding;
    HomeItemAdapter itemAdapter;
    HomeInterface.Presenter presenter;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        init();

        return binding.getRoot();
    }

    private void init(){
        presenter = new HomePresenter(this);

        itemAdapter = new HomeItemAdapter();
        binding.homeItemList.setAdapter(itemAdapter);
        binding.homeItemList.setLayoutManager(new GridLayoutManager(getContext(), 2));

        presenter.getCorpItemList();
    }

    @Override
    public void sendMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setCorpItemData(Map<String, Object> data) {
        binding.homeItemList.setVisibility(View.VISIBLE);
        binding.noItemMsg.setVisibility(View.INVISIBLE);
        itemAdapter.addData(data);
    }
}

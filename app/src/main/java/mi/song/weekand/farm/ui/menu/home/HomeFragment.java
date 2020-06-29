package mi.song.weekand.farm.ui.menu.home;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import mi.song.weekand.farm.R;
import mi.song.weekand.farm.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment implements HomeInterface.View, SwipeRefreshLayout.OnRefreshListener {
    FragmentHomeBinding binding;
    HomeItemAdapter itemAdapter;
    HomeInterface.Presenter presenter;
    Set<Map<String, Object>> corpList;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new HomePresenter(this);
        corpList = new HashSet<>();
        itemAdapter = new HomeItemAdapter();

        presenter.getCorpItemList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        init();

        return binding.getRoot();
    }

    private void init(){
        binding.homeRefreshLayout.setOnRefreshListener(this);

        binding.homeItemList.setAdapter(itemAdapter);
        binding.homeItemList.setLayoutManager(new GridLayoutManager(getContext(), 2));

        itemAdapter.setData(corpList);
        presenter.getCorpItemList();

        if(corpList.size() > 0){
            binding.homeItemList.setVisibility(View.VISIBLE);
            binding.noItemMsg.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void sendMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setCorpItemData(ArrayList<Map<String, Object>> dataList) {
        binding.homeItemList.setVisibility(View.VISIBLE);
        binding.noItemMsg.setVisibility(View.INVISIBLE);

        for(Map<String, Object> data : dataList)
            corpList.add(data);

        itemAdapter.setData(corpList);
    }

    @Override
    public void onRefresh() {

        binding.homeRefreshLayout.setRefreshing(false);
    }
}

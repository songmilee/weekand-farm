package mi.song.weekand.farm.ui.menu.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.Map;

import mi.song.weekand.farm.R;
import mi.song.weekand.farm.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment implements HomeInterface.View, SwipeRefreshLayout.OnRefreshListener {
    FragmentHomeBinding binding;
    HomeItemAdapter itemAdapter;
    HomeInterface.Presenter presenter;
    Long curTime = 0L;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new HomePresenter(this);
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

        if(itemAdapter.getItemCount() > 0) {
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
        if(dataList.size() > 0) {
            binding.homeItemList.setVisibility(View.VISIBLE);
            binding.noItemMsg.setVisibility(View.INVISIBLE);

            for (Map<String, Object> data : dataList) {
                curTime = (Long) data.get("createdat");
                itemAdapter.addData(data);
            }
        } else {
            sendMessage(getString(R.string.no_update));
        }
    }

    @Override
    public void onRefresh() {
        presenter.getCorpItemList(curTime);
        binding.homeRefreshLayout.setRefreshing(false);
    }
}

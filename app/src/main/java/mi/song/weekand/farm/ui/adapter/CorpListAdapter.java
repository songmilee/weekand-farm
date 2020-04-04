package mi.song.weekand.farm.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import mi.song.weekand.farm.R;
import mi.song.weekand.farm.databinding.CorpItemBinding;
import mi.song.weekand.farm.model.Corp;
import mi.song.weekand.farm.util.TimeUtils;

public class CorpListAdapter extends RecyclerView.Adapter<CorpListAdapter.CorpVH> {
    private ArrayList<Corp> corpList = new ArrayList<>();
    private Activity activity;

    public CorpListAdapter(Activity activity) { this.activity = activity; }
    public void addItem(Corp item) { corpList.add(item);}

    @NonNull
    @Override
    public CorpVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CorpVH(LayoutInflater.from(activity).inflate(R.layout.corp_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CorpVH holder, int position) {
        holder.bindView(corpList.get(position));
    }

    @Override
    public int getItemCount() {
        return corpList.size();
    }

    public class CorpVH extends RecyclerView.ViewHolder {
        CorpItemBinding corpItemBinding;

        public CorpVH(@NonNull View itemView) {
            super(itemView);
            corpItemBinding = DataBindingUtil.setContentView(activity, R.layout.corp_item);
        }

        public void bindView(Corp item){
            corpItemBinding.corpItemCreate.setText(TimeUtils.parseLongTime(item.getStartDate()));
            corpItemBinding.corpItemName.setText(item.getName());
        }
    }
}

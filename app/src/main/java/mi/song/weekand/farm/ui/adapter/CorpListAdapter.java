package mi.song.weekand.farm.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import mi.song.weekand.farm.databinding.CorpItemBinding;
import mi.song.weekand.farm.model.Corp;
import mi.song.weekand.farm.ui.view.CorpDiaryActivity;
import mi.song.weekand.farm.util.TimeUtils;

public class CorpListAdapter extends RecyclerView.Adapter<CorpListAdapter.CorpVH> {
    private ArrayList<Corp> corpList;
    private Activity activity;

    public CorpListAdapter(Activity activity) {
        this.activity = activity;
        corpList = new ArrayList<>();
    }

    public void addItem(Corp item) {
        corpList.add(item);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CorpVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CorpItemBinding corpItemBinding = CorpItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CorpVH(corpItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CorpVH holder, int position) {
        holder.bindView(corpList.get(position));
    }

    @Override
    public int getItemCount() {
        return corpList.size();
    }

    public class CorpVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        CorpItemBinding binding;
        Long id = -1L;

        public CorpVH(@NonNull CorpItemBinding corpItemBinding){
            super(corpItemBinding.getRoot());
            binding = corpItemBinding;
            itemView.setOnClickListener(this);
        }

        public void bindView(Corp item){
            binding.corpItemCreate.setText(TimeUtils.parseLongTime(item.getStartDate()));
            binding.corpItemName.setText(item.getName());

            id = item.getId();
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(activity, CorpDiaryActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("corp_id", id);
            intent.putExtra("corp_name", binding.corpItemName.getText());
            activity.startActivity(intent);
        }
    }
}

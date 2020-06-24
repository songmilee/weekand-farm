package mi.song.weekand.farm.ui.menu.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.Map;

import mi.song.weekand.farm.R;

public class HomeItemAdapter extends RecyclerView.Adapter<HomeItemAdapter.HomeVH> {
    private ArrayList<Map<String, Object>> itemList;

    public HomeItemAdapter(){
        itemList = new ArrayList<>();
    }

    public void addData(Map<String, Object> item){
        itemList.add(item);
        notifyItemInserted(itemList.size() - 1);
    }

    @NonNull
    @Override
    public HomeVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_corp_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeVH holder, int position) {
        Map<String, Object> data = itemList.get(position);
        holder.bindItem(data);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class HomeVH extends RecyclerView.ViewHolder{
        private TextView title, contents;
        private ImageView imgView;

        public HomeVH(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.corp_item_title);
            contents = itemView.findViewById(R.id.corp_item_contents);
            imgView = itemView.findViewById(R.id.corp_item_img);
        }

        public void bindItem(Map<String, Object> data){
            title.setText(data.get("title").toString());
            contents.setText(data.get("contents").toString());

            Glide.with(itemView)
                    .load(data.get("url").toString())
                    .placeholder(R.drawable.img_placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(imgView);
        }
    }
}

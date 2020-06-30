package mi.song.weekand.farm.ui.menu.home;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HomeScrollListener extends RecyclerView.OnScrollListener {
    private HomeInterface.Presenter presenter;

    public HomeScrollListener(HomeInterface.Presenter presenter){
        this.presenter = presenter;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if(!recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN)){
            presenter.getCorpItemList(true);
        }
    }

}

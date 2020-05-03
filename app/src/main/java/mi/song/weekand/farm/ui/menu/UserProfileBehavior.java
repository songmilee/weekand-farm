package mi.song.weekand.farm.ui.menu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

public class UserProfileBehavior extends CoordinatorLayout.Behavior<ImageView> {
    Context context;

    public UserProfileBehavior(Context context, AttributeSet attrs){
        this.context = context;
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull ImageView child, @NonNull View dependency) {
        return super.layoutDependsOn(parent, child, dependency);
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull ImageView child, @NonNull View dependency) {
        return super.onDependentViewChanged(parent, child, dependency);
    }
}

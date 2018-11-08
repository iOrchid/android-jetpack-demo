package in.zhiwei.jetpack.navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import in.zhiwei.jetpack.R;

/**
 * Author: zhiwei.
 * Date: 2018/11/5 0005,20:35.
 */
public class FragmentTwo extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_two, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //返回
        view.findViewById(R.id.btn_back1).setOnClickListener(v -> Navigation.findNavController(v).navigateUp());
        //跳 3
        view.findViewById(R.id.btn_go3).setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.go_fragment3));
    }
}

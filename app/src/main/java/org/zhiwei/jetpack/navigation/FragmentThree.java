package org.zhiwei.jetpack.navigation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import org.zhiwei.jetpack.R;

/**
 * Author: zhiwei.
 * Date: 2018/11/5 0005,20:35.
 */
public class FragmentThree extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.w("Fragment", "FragmentThree onCreate");

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.w("Fragment", "FragmentThree onCreateView");
        return inflater.inflate(R.layout.fragment_three, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.w("Fragment", "FragmentThree onViewCreate");
        //返回
        view.findViewById(R.id.btn_back2).setOnClickListener(v -> Navigation.findNavController(v).navigateUp());
        //跳 1
        view.findViewById(R.id.btn_go1).setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.go_fragment1));
    }
}

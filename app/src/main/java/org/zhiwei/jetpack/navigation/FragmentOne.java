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
public class FragmentOne extends Fragment {

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("Fragment", "FragmentOne onCreate");
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		Log.d("Fragment", "FragmentOne onCreateView");
		return inflater.inflate(R.layout.fragment_one, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Log.d("Fragment", "FragmentOne onViewCreated");
		view.findViewById(R.id.btn_go2).setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.go_fragment2));
	}
}

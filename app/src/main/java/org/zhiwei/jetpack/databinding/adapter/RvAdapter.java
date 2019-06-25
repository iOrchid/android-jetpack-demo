package org.zhiwei.jetpack.databinding.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import org.zhiwei.jetpack.BR;
import org.zhiwei.jetpack.R;
import org.zhiwei.jetpack.databinding.entity.CommonUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: zhiwei.
 * Date: 2018/11/2 0002,17:09.
 */
public class RvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<CommonUser> users = new ArrayList<>();

    public RvAdapter() {
        CommonUser user;
        for (int i = 0; i < 4; i++) {
            user = new CommonUser(20 + i, "RV Name " + i, i % 2 == 0);
            users.add(user);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_rv, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            MyViewHolder holder1 = (MyViewHolder) holder;
            holder1.getBinding().setVariable(BR.rvusr, users.get(position));
            holder1.getBinding().executePendingBindings();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding binding;

        public MyViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ViewDataBinding getBinding() {
            return this.binding;
        }
    }
}

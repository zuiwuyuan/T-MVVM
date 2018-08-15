package com.code.mvvm.core.view.live;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.code.mvvm.base.BaseListFragment;
import com.code.mvvm.core.data.pojo.live.LiveListVo;
import com.code.mvvm.core.viewmodel.LiveViewModel;
import com.code.mvvm.util.AdapterPool;
import com.trecyclerview.multitype.MultiTypeAdapter;

/**
 * @author：zhangtianqiu on 18/6/30 18:36
 */
public class LiveListFragment extends BaseListFragment<LiveViewModel> {


    public static LiveListFragment newInstance() {
        return new LiveListFragment();
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
    }

    @Override
    protected void dataObserver() {
        mViewModel.getLiveList().observe(this, new Observer<LiveListVo>() {
            @Override
            public void onChanged(@Nullable LiveListVo liveListVo) {
                if (liveListVo != null && liveListVo.data!= null) {
                    lastId = liveListVo.data.get(liveListVo.data.size() - 1).liveid;
                    setData(liveListVo.data);
                }
            }
        });
    }

    @Override
    protected LiveViewModel createViewModelProviders() {
        return ViewModelProviders.of(this).get(LiveViewModel.class);
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    @Override
    protected MultiTypeAdapter createAdapter() {
        return AdapterPool.newInstance().getLiveAdapter(getActivity());
    }


    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
        getRemoteData();
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        getRemoteData();
    }

    @Override
    protected void onRefreshAction() {
        super.onRefreshAction();
        getRemoteData();
    }

    @Override
    protected void getRemoteData() {
        mViewModel.getLiveList(getArguments().getString("f_catalog_id"), lastId, "20");
    }
}

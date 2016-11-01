package net.levinh.ytmusic.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.levinh.ytmusic.R;
import net.levinh.ytmusic.presenter.SearchVideoPresenter;
import net.levinh.ytmusic.view.adapter.RecyclerViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchFragment extends Fragment implements SearchView, RecyclerViewAdapter.OnItemClickListener {
    private static final String TAG = SearchFragment.class.getSimpleName();
    private static final String ARG_KEYWORD = "keyword";
    private static final String ARG_CONTEXT = "context";

    Context mContext;
    @BindView(R.id.rcvSearchList)
    RecyclerView rcvSearchList;
    private RecyclerViewAdapter mAdapter;
    SearchVideoPresenter presenter;

    public SearchFragment() {
    }

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);
        this.mContext = container.getContext();
        initView();
        presenter = new SearchVideoPresenter(this);
        presenter.getMostPopularVideo();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    private void initView() {
        rcvSearchList.setLayoutManager(new LinearLayoutManager(mContext));
        rcvSearchList.setHasFixedSize(true);
        mAdapter = new RecyclerViewAdapter(mContext, R.layout.list_item,this);
        rcvSearchList.setAdapter(mAdapter);
    }

    public void searchVideo(final String keyword) {
        presenter.searchVideo(keyword);
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return mAdapter;
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void onItemClick(int position) {
        presenter.onItemClick(position);
    }
}

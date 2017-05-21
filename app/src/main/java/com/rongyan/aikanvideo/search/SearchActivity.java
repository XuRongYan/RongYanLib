package com.rongyan.aikanvideo.search;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.rongyan.aikanvideo.OnSearchCompleteListener;
import com.rongyan.aikanvideo.R;
import com.rongyan.aikanvideo.adapter.MainViewPagerAdapter;
import com.rongyan.rongyanlibrary.base.BaseActivity;
import com.rongyan.rongyanlibrary.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import br.com.mauker.materialsearchview.MaterialSearchView;
import butterknife.Bind;

public class SearchActivity extends BaseActivity implements MaterialSearchView.OnQueryTextListener, OnSearchCompleteListener {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.search_view)
    MaterialSearchView searchView;
    @Bind(R.id.search_tab)
    TabLayout tabLayout;
    @Bind(R.id.search_pager)
    ViewPager viewPager;


    private List<BaseFragment> fgmList = new ArrayList<>();
    private MainViewPagerAdapter adapter;
    private String[] titles = new String[]{"电视剧", "电影", "综艺"};
    private String query;
    private TextView footView;
    private SearchPresenter tvPresenter;
    private SearchPresenter moviePresenter;
    private SearchPresenter varietyPresenter;
    private SearchFragment tvPlayFgm;
    private SearchFragment movieFgm;
    private SearchFragment variety;

    @Override
    protected int getContentView() {
        return R.layout.activity_search;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        toolbar.setTitle(R.string.search_cn);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        searchView.setOnQueryTextListener(this);

        List<String> titleList = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            titleList.add(titles[i]);
        }
        tvPlayFgm = SearchFragment.newInstance(titles[0]);
        movieFgm = SearchFragment.newInstance(titles[1]);
        variety = SearchFragment.newInstance(titles[2]);
        fgmList.add(tvPlayFgm);
        fgmList.add(movieFgm);
        fgmList.add(variety);
        adapter = new MainViewPagerAdapter(getSupportFragmentManager(), fgmList, titleList);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tvPresenter = new SearchPresenter(this, (tvPlayFgm), titles[0], lifeCycleSubject);
        moviePresenter = new SearchPresenter(this, (movieFgm), titles[1], lifeCycleSubject);
        varietyPresenter = new SearchPresenter(this, (variety), titles[2], lifeCycleSubject);
        tvPresenter.setOnSearchCompleteListener(this);
        moviePresenter.setOnSearchCompleteListener(this);
        varietyPresenter.setOnSearchCompleteListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.search:
                searchView.openSearch();
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        searchView.activityResumed();
    }

    @Override
    public void onBackPressed() {
        if (searchView.isOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        this.query = query;
        tvPlayFgm.setQurey(query);
        movieFgm.setQurey(query);
        variety.setQurey(query);

        tvPresenter.submitQuery(query);
        moviePresenter.submitQuery(query);
        varietyPresenter.submitQuery(query);


        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onComplete() {
        if (searchView != null && searchView.isOpen()) {
            searchView.closeSearch();
        }
    }


//
}

package com.lijianping.jiandan.Fragment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lijianping.jiandan.R;
import com.lijianping.jiandan.activity.MainActivity;
import com.lijianping.jiandan.base.BaseFragment;
import com.lijianping.jiandan.model.MenuItem;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @ fileName: MainMenuFragment
 * @ Author: Li Jianping
 * @ Date: 2016/9/21 16:16
 * @ Description:
 */
public class MainMenuFragment extends BaseFragment{

    @InjectView(R.id.rv_fragment_drawer)
    RecyclerView mRecyclerView;

    @InjectView(R.id.rl_fragment_drawer_container)
    RelativeLayout mContainer;

    private MainActivity mainActivity;

    private LinearLayoutManager mLayoutManger;

    private MenuAdapter mAdapter;

    private MenuItem.FragmentType currentFragment = MenuItem.FragmentType.FreshNews;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof MainActivity){
            mainActivity = (MainActivity) activity;
        }else {
            throw new IllegalArgumentException("The activity must be a MainActivity!");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drawer, container, false);
        ButterKnife.inject(this, view);

        mLayoutManger = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManger);

        mContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new MenuAdapter();
        addAllMenuItems(mAdapter);
        mRecyclerView.setAdapter(mAdapter);
    }

    private class MenuAdapter extends RecyclerView.Adapter<ViewHolder>{
        private ArrayList<MenuItem> menuItems;

        public MenuAdapter(){
            menuItems = new ArrayList<>();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_drawer,
                    parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final MenuItem menuItem = menuItems.get(position);

            holder.title.setText(menuItem.getTitle());
            holder.imageView.setImageResource(menuItem.getResourceId());
            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (currentFragment != menuItem.getType()){
                            Fragment fragment = (Fragment) Class.forName(menuItem.getFragment().getName()).newInstance();
                            mainActivity.replaceFragment(R.id.fl_main_container, fragment);
                            currentFragment = menuItem.getType();
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    mainActivity.closeDrawers();
                }
            });
        }

        @Override
        public int getItemCount() {
            return menuItems.size();
        }
    }
    private static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView title;
        private RelativeLayout container;

        public ViewHolder(View itemView){
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_item_drawer);
            title = (TextView) itemView.findViewById(R.id.tv_item_drawer_title);
            container = (RelativeLayout) itemView.findViewById(R.id.rl_item_drawer_container);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (sp.getBoolean(SettingFragment.ENABLE_GIRLS, false) && mAdapter.menuItems.size() == 4){
            addAllMenuItems(mAdapter);
            mAdapter.notifyDataSetChanged();
        }else if (!sp.getBoolean(SettingFragment.ENABLE_GIRLS, false) && mAdapter.menuItems.size() == 5){
            addMenuItemsNoGirls(mAdapter);
            mAdapter.notifyDataSetChanged();
        }

    }

    private void addAllMenuItems(MenuAdapter mAdapter) {
        mAdapter.menuItems.clear();
        mAdapter.menuItems.add(new MenuItem("新鲜事", R.drawable.ic_explore_white_24dp, MenuItem.FragmentType.FreshNews,
                FreshNewsFragment.class));
        mAdapter.menuItems.add(new MenuItem("无聊图", R.drawable.ic_mood_white_24dp, MenuItem.FragmentType.BoringPicture,
                PictureFragment.class));
        mAdapter.menuItems.add(new MenuItem("妹子图", R.drawable.ic_local_florist_white_24dp, MenuItem.FragmentType.Sister,
                GirlsFragment.class));
        mAdapter.menuItems.add(new MenuItem("段子", R.drawable.ic_chat_white_24dp, MenuItem.FragmentType.Joke,
                JokeFragment.class));
        mAdapter.menuItems.add(new MenuItem("小电影", R.drawable.ic_movie_white_24dp, MenuItem.FragmentType.Video,
                VideoFragment.class));
    }

    private void addMenuItemsNoGirls(MenuAdapter mAdapter){
        mAdapter.menuItems.clear();
        mAdapter.menuItems.add(new MenuItem("新鲜事", R.drawable.ic_explore_white_24dp, MenuItem.FragmentType.FreshNews,
                FreshNewsFragment.class));
        mAdapter.menuItems.add(new MenuItem("无聊图", R.drawable.ic_mood_white_24dp, MenuItem.FragmentType.BoringPicture,
                PictureFragment.class));
        mAdapter.menuItems.add(new MenuItem("段子", R.drawable.ic_chat_white_24dp, MenuItem.FragmentType.Joke,
                JokeFragment.class));
        mAdapter.menuItems.add(new MenuItem("小电影", R.drawable.ic_movie_white_24dp, MenuItem.FragmentType.Video,
                VideoFragment.class));
    }
}

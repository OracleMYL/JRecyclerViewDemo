package com.jtech.jrecyclerview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * jrecyclerview
 *
 * @author wuxubaiyang
 */
@SuppressWarnings("rawtypes")
public class JRecyclerView extends RecyclerView {
    /**
     * 加载状态-正常状态
     */
    public static final int LOAD_STATE_NORMAL = 0x9527;
    /**
     * 加载状态-加载中
     */
    public static final int LOAD_STATE_LOADING = 0x9528;
    /**
     * 加载状态-加载失败
     */
    public static final int LOAD_STATE_FAIL = 0x9529;
    /**
     * 加载状态-无更多数据
     */
    public static final int LOAD_STATE_NOMORE = 0x9530;
    /**
     * 加载状态标志位
     */
    private int loadState = LOAD_STATE_NORMAL;
    /**
     * layoutmanager状态-线性布局
     */
    private static final int LAYOUT_STATE_LINEAR = 0x9531;
    /**
     * layoutmanager状态-表格布局
     */
    private static final int LAYOUT_STATE_GRID = 0x9532;
    /**
     * layoutmanager状态-瀑布流布局
     */
    private static final int LAYOUT_STATE_STAGGERED = 0x9533;
    /**
     * layoutmanager标志位
     */
    private int layout_state = LAYOUT_STATE_LINEAR;
    /**
     * 是否显示足部（加载更多）
     */
    private boolean loadMore = false;
    /**
     * 外部包裹的适配器
     */
    private mAdapter mAdapter;
    /**
     * 是否向下滚动
     */
    private boolean isScrollDown = false;
    /**
     * 加载更多监听
     */
    private OnLoadListener onLoadListener;
    /**
     * item点击事件监听
     */
    private OnItemClickListener onItemClickListener;
    /**
     * item长点击事件监听
     */
    private OnItemLongClickListener onItemLongClickListener;

    /**
     * 主构造
     *
     * @param context
     */
    public JRecyclerView(Context context) {
        this(context, null);
    }

    /**
     * 主构造
     *
     * @param context
     * @param attrs
     */
    public JRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 主构造
     *
     * @param context
     * @param attrs
     * @param arg2
     */
    public JRecyclerView(Context context, AttributeSet attrs, int arg2) {
        super(context, attrs, arg2);
    }

    /**
     * 是否正在加载中
     *
     * @return 是否正在加载
     */
    public boolean isLoading() {
        return this.loadState == LOAD_STATE_LOADING;
    }

    /**
     * 设置加载状态
     *
     * @param loadState 加载状态
     */
    public void setLoadState(int loadState) {
        this.loadState = loadState;
        if (null != mAdapter) {
            mAdapter.modifyState(loadState);
        }
    }

    /**
     * 设置状态为正在加载
     */
    public void setLoadingState() {
        setLoadState(LOAD_STATE_LOADING);
    }

    /**
     * 设置状态为加载失败
     */
    public void setLoadFailState() {
        setLoadState(LOAD_STATE_FAIL);
    }

    /**
     * 设置状态为无更多数据
     */
    public void setLoadFinishState() {
        setLoadState(LOAD_STATE_NOMORE);
    }

    /**
     * 设置状态为加载完成
     */
    public void setLoadCompleteState() {
        setLoadState(LOAD_STATE_NORMAL);
    }

    /**
     * 是否开启加载更多功能
     *
     * @param loadMore 是否加载更多
     */
    public void setLoadMore(boolean loadMore) {
        this.loadMore = loadMore;
    }

    /**
     * 是否加载更多
     *
     * @return 是否加载更多
     */
    public boolean isLoadMore() {
        return loadMore;
    }

    /**
     * 设置加载更多监听
     *
     * @param onLoadListener 加载更多监听
     */
    public void setOnLoadListener(OnLoadListener onLoadListener) {
        this.onLoadListener = onLoadListener;
    }

    /**
     * 设置item点击事件监听
     *
     * @param onItemClickListener item点击事件监听
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 设置item长点击事件监听
     *
     * @param onItemLongClickListener item长点击事件监听
     */
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    /**
     * 设置适配器
     *
     * @param adapter 适配器
     */
    @Override
    public void setAdapter(Adapter adapter) {
        setAdapter(adapter, new SimpleLoadFooterAdapter(getContext()));
    }

    /**
     * 设置适配器以及足部适配
     *
     * @param adapter        适配器
     * @param loadMoreFooter 足部适配
     */
    public void setAdapter(Adapter adapter, LoadFooterAdapter loadMoreFooter) {
        mAdapter = new mAdapter(adapter, loadMoreFooter);
        super.setAdapter(mAdapter);
    }

    /**
     * 设置layoutmanager
     *
     * @param layout layoutmanager
     */
    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);
        if (layout instanceof GridLayoutManager) {
            layout_state = LAYOUT_STATE_GRID;
        } else if (layout instanceof LinearLayoutManager) {
            layout_state = LAYOUT_STATE_LINEAR;
        } else if (layout instanceof StaggeredGridLayoutManager) {
            layout_state = LAYOUT_STATE_STAGGERED;
        }
    }

    /**
     * 滚动
     *
     * @param dx X轴滚动距离
     * @param dy Y轴滚动距离
     */
    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        this.isScrollDown = dy > 0;
        if (!isScrollDown && loadState == LOAD_STATE_FAIL) {
            loadState = LOAD_STATE_NORMAL;
            mAdapter.modifyState(loadState);
        }
    }

    /**
     * 滚动状态
     *
     * @param state 滚动状态
     */
    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        if (null != mAdapter && mAdapter.getItemCount() > 0 && loadMore && null != onLoadListener && isScrollDown
                && RecyclerView.SCROLL_STATE_IDLE == state && loadState == LOAD_STATE_NORMAL) {
            boolean flag = true;
            if (layout_state == LAYOUT_STATE_LINEAR) {// 线性布局
                int lastPosition = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
                flag = lastPosition == (mAdapter.getItemCount() - 1);
            } else if (layout_state == LAYOUT_STATE_GRID) {// 表格布局
                int lastPosition = ((GridLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
                flag = lastPosition == (mAdapter.getItemCount() - 1);
            } else if (layout_state == LAYOUT_STATE_STAGGERED) {// 交错布局
                int[] lastPositions = ((StaggeredGridLayoutManager) getLayoutManager())
                        .findLastVisibleItemPositions(null);
                int footer = mAdapter.getItemCount() - 1;
                for (int i = 0; i < lastPositions.length; i++) {
                    if (lastPositions[i] != footer) {
                        flag = false;
                        break;
                    }
                }
            }
            loadState = flag ? LOAD_STATE_LOADING : LOAD_STATE_NORMAL;
            if (flag) {// 如果标志状态为加载中，则回调方法
                onLoadListener.loadMore();
                setLoadState(loadState);
            }
        }
    }

    /**
     * 自定义适配器，包裹用户设置的适配器，实现添加足部（加载更多）功能
     *
     * @author JTech
     */
    private class mAdapter extends Adapter {
        private static final int ITEM_FOOTER = 0x9527;
        private LoadFooterAdapter loadFooterAdapter;
        private RecyclerHolder recyclerHolder;
        private Adapter adapter;

        public mAdapter(Adapter adapter, LoadFooterAdapter loadFooterAdapter) {
            this.loadFooterAdapter = loadFooterAdapter;
            this.adapter = adapter;
            //注册适配器的数据观察着
            adapter.registerAdapterDataObserver(new RecyclerDataObserver(this));
        }


        @Override
        public int getItemCount() {
            if (loadMore) {
                return adapter.getItemCount() + 1;
            }
            return adapter.getItemCount();
        }

        @Override
        public int getItemViewType(int position) {
            if (loadMore && position == getItemCount() - 1) {
                return ITEM_FOOTER;
            }
            return adapter.getItemViewType(position);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == ITEM_FOOTER) {
                if (null == recyclerHolder) {
                    //实例化足部视图的viewholder
                    LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                    recyclerHolder = new RecyclerHolder(loadFooterAdapter.getFooterView(layoutInflater, parent));
                    //重置状态
                    modifyState(LOAD_STATE_NORMAL);
                }
                return recyclerHolder;
            }
            return adapter.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            //处理footer
            if (getItemViewType(position) == ITEM_FOOTER) {
                //staggered布局（瀑布流）的足部添加
                if (layout_state == LAYOUT_STATE_STAGGERED) {
                    StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) holder.itemView
                            .getLayoutParams();
                    layoutParams.setFullSpan(true);
                    holder.itemView.setLayoutParams(layoutParams);
                } else if (layout_state == LAYOUT_STATE_GRID) {//grid布局（表格）的足部添加
                    final GridLayoutManager gridLayoutManager = ((GridLayoutManager) getLayoutManager());
                    final GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();
                    gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                        @Override
                        public int getSpanSize(int position) {
                            if (null != onLoadListener && position == mAdapter.getItemCount() - 1) {
                                return gridLayoutManager.getSpanCount();
                            } else if (null != spanSizeLookup) {
                                return spanSizeLookup.getSpanSize(position);
                            }
                            return 1;
                        }
                    });
                }
            } else {
                //调用适配器的bindview方法
                adapter.onBindViewHolder(holder, position);
                //设置item的点击事件
                if (null != onItemClickListener) {
                    holder.itemView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onItemClickListener.onItemClick((RecyclerHolder) holder, v, position);
                        }
                    });
                }
                //设置item的长点击事件
                if (null != onItemLongClickListener) {
                    holder.itemView.setOnLongClickListener(new OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            return onItemLongClickListener.onItemLongClick((RecyclerHolder) holder, v, position);
                        }
                    });
                }
            }
        }

        /**
         * 修改加载状态
         *
         * @param loadState 加载状态
         */
        public void modifyState(int loadState) {
            switch (loadState) {
                case JRecyclerView.LOAD_STATE_FAIL:// 加载失败
                    loadFooterAdapter.loadFailState(recyclerHolder);
                    break;
                case JRecyclerView.LOAD_STATE_LOADING:// 加载中
                    loadFooterAdapter.loadingState(recyclerHolder);
                    break;
                case JRecyclerView.LOAD_STATE_NOMORE:// 无更多数据
                    loadFooterAdapter.noMoreDataState(recyclerHolder);
                    break;
                case JRecyclerView.LOAD_STATE_NORMAL:// 正常状态
                    loadFooterAdapter.normalState(recyclerHolder);
                    break;
            }
        }
    }
}
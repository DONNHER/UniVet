package com.example.uni.adapters;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Utility class for RecyclerView configurations and common operations
 */
public class recycler {
    
    /**
     * Setup RecyclerView with Linear Layout Manager
     */
    public static void setupLinearRecyclerView(RecyclerView recyclerView, Context context, 
                                               RecyclerView.Adapter adapter, int orientation) {
        if (recyclerView == null || context == null) return;
        
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, orientation, false);
        recyclerView.setLayoutManager(layoutManager);
        
        if (adapter != null) {
            recyclerView.setAdapter(adapter);
        }
        
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
    }
    
    /**
     * Setup RecyclerView with Grid Layout Manager
     */
    public static void setupGridRecyclerView(RecyclerView recyclerView, Context context, 
                                             RecyclerView.Adapter adapter, int spanCount) {
        if (recyclerView == null || context == null) return;
        
        GridLayoutManager layoutManager = new GridLayoutManager(context, spanCount);
        recyclerView.setLayoutManager(layoutManager);
        
        if (adapter != null) {
            recyclerView.setAdapter(adapter);
        }
        
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
    }
    
    /**
     * Add spacing between RecyclerView items
     */
    public static class SpacingItemDecoration extends RecyclerView.ItemDecoration {
        private final int spacing;
        private final boolean includeEdge;
        
        public SpacingItemDecoration(int spacing, boolean includeEdge) {
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }
        
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, 
                                   @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            
            if (includeEdge) {
                outRect.left = spacing;
                outRect.right = spacing;
                outRect.top = spacing;
                outRect.bottom = spacing;
            } else {
                outRect.left = spacing / 2;
                outRect.right = spacing / 2;
                outRect.top = spacing / 2;
                outRect.bottom = spacing / 2;
            }
        }
    }
    
    /**
     * Add horizontal divider between items
     */
    public static class HorizontalDividerDecoration extends RecyclerView.ItemDecoration {
        private final int dividerHeight;
        
        public HorizontalDividerDecoration(int dividerHeight) {
            this.dividerHeight = dividerHeight;
        }
        
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, 
                                   @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            outRect.bottom = dividerHeight;
        }
    }
    
    /**
     * Scroll to top of RecyclerView
     */
    public static void scrollToTop(RecyclerView recyclerView) {
        if (recyclerView != null) {
            recyclerView.scrollToPosition(0);
        }
    }
    
    /**
     * Scroll to bottom of RecyclerView
     */
    public static void scrollToBottom(RecyclerView recyclerView) {
        if (recyclerView != null && recyclerView.getAdapter() != null) {
            int itemCount = recyclerView.getAdapter().getItemCount();
            if (itemCount > 0) {
                recyclerView.scrollToPosition(itemCount - 1);
            }
        }
    }
    
    /**
     * Smooth scroll to position
     */
    public static void smoothScrollToPosition(RecyclerView recyclerView, int position) {
        if (recyclerView != null && recyclerView.getAdapter() != null) {
            int itemCount = recyclerView.getAdapter().getItemCount();
            if (position >= 0 && position < itemCount) {
                recyclerView.smoothScrollToPosition(position);
            }
        }
    }
    
    /**
     * Check if RecyclerView is at the top
     */
    public static boolean isAtTop(RecyclerView recyclerView) {
        if (recyclerView == null) return true;
        
        return !recyclerView.canScrollVertically(-1);
    }
    
    /**
     * Check if RecyclerView is at the bottom
     */
    public static boolean isAtBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return true;
        
        return !recyclerView.canScrollVertically(1);
    }
    
    /**
     * Add scroll listener for pagination
     */
    public static void addPaginationScrollListener(RecyclerView recyclerView, 
                                                   OnLoadMoreListener listener) {
        if (recyclerView == null || listener == null) return;
        
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                
                if (isAtBottom(recyclerView) && !listener.isLoading()) {
                    listener.onLoadMore();
                }
            }
        });
    }
    
    /**
     * Interface for pagination
     */
    public interface OnLoadMoreListener {
        void onLoadMore();
        boolean isLoading();
    }
}
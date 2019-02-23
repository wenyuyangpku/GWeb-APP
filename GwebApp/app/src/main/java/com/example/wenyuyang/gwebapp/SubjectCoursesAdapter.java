package com.example.wenyuyang.gwebapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SubjectCoursesAdapter extends RecyclerView.
        Adapter<SubjectCoursesAdapter.SubjectCoursesViewHolder> {

    //tags and listener
    private static final String TAG = CoursesAdapter.class.getSimpleName();
    final private SubjectCoursesAdapter.ListItemListener mOnClickListener;

    //parameters
    private static int viewHolderCount;
    private int mNumberItem;

    public SubjectCoursesAdapter(int numberOfItems, SubjectCoursesAdapter.ListItemListener listener) {
        mNumberItem = numberOfItems;
        mOnClickListener = listener;
        viewHolderCount = 0;
    }

    //Clicked Listener based on the index
    public interface ListItemListener {
        void onListItemClicked(int clickedItemIndex);
    }

    @NonNull
    @Override
    public SubjectCoursesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutForListItem = R.layout.course_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutForListItem, viewGroup, shouldAttachToParentImmediately);
        SubjectCoursesViewHolder viewHolder = new SubjectCoursesViewHolder(view);

        //TODO:change the structure of viewholder
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectCoursesViewHolder subjectCoursesViewHolder, int i) {
        Log.d(TAG, "#" + i);
        subjectCoursesViewHolder.bind(i);
    }

    @Override
    public int getItemCount() {
        return mNumberItem;
    }

    class SubjectCoursesViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        //parameters
        TextView listItemCourseName;
        TextView listItemCourseTime;
        TextView listItemCourseWhere;

        //Constructor
        public SubjectCoursesViewHolder(@NonNull View itemView) {
            super(itemView);
            listItemCourseName = (TextView) itemView.findViewById(R.id.tv_item_name);
            listItemCourseTime = (TextView) itemView.findViewById(R.id.tv_item_time);
            listItemCourseWhere = (TextView) itemView.findViewById(R.id.tv_item_where);
            itemView.setOnClickListener(this);
        }

        //assign value
        void bind(int index) {
            listItemCourseName.setText(MainSelectActivity.mListSelectCourses.get(index).Subject+" "+MainSelectActivity.mListSelectCourses.get(index).Course+" "+MainSelectActivity.mListSelectCourses.get(index).Title);
            listItemCourseTime.setText(MainSelectActivity.mListSelectCourses.get(index).Date+" "+MainSelectActivity.mListSelectCourses.get(index).Time);
            listItemCourseWhere.setText(MainSelectActivity.mListSelectCourses.get(index).Location);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClicked(clickedPosition);
        }
    }


}

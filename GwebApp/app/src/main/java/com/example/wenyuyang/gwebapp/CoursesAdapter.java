package com.example.wenyuyang.gwebapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.CourseViewHolder> {

    //tags and listener
    private static final String TAG = CoursesAdapter.class.getSimpleName();
    final private ListItemListener mOnClickListener;

    //parameters
    private static int viewHolderCount;
    private int mNumberItem;

    //Clicked Listener based on the index
    public interface ListItemListener {
        void onListItemClicked(int clickedItemIndex);
    }

    //Constuctor
    public CoursesAdapter(int numberOfItems, ListItemListener listener) {
        mNumberItem = numberOfItems;
        mOnClickListener = listener;
        viewHolderCount = 0;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        Context context = viewGroup.getContext();
        int layoutForListItem = R.layout.course_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutForListItem, viewGroup, shouldAttachToParentImmediately);
        CourseViewHolder viewHolder = new CourseViewHolder(view);

        //TODO:change the structure of viewholder
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder courseViewHolder, int i) {
        Log.d(TAG, "#" + i);
        courseViewHolder.bind(i);
    }

    @Override
    public int getItemCount() {
        return mNumberItem;
    }

    //Create ViewHolder
    class CourseViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        //parameters
        TextView listItemCourseName;
        TextView listItemCourseTime;
        TextView listItemCourseWhere;

        //Construvctor
        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            listItemCourseName = (TextView) itemView.findViewById(R.id.tv_item_name);
            listItemCourseTime = (TextView) itemView.findViewById(R.id.tv_item_time);
            listItemCourseWhere = (TextView) itemView.findViewById(R.id.tv_item_where);
            itemView.setOnClickListener(this);
        }

        //assign value
        void bind(int index) {
            listItemCourseName.setText(MainActivity.mClassCourses.get(index).name);
            listItemCourseTime.setText(MainActivity.mClassCourses.get(index).day+" "+MainActivity.mClassCourses.get(index).time);
            listItemCourseWhere.setText(MainActivity.mClassCourses.get(index).where);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClicked(clickedPosition);
        }
    }
}

package com.privat.kobbigal.donotdisturb;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by kobbigal on 2/18/18.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private ArrayList<Event> events;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView eventTitleTV;
//        TextView eventDescriptionTV;
        TextView eventStartTimeTV;
        Switch dndSwitch;

        public MyViewHolder(View itemView) {
            super(itemView);

            this.eventTitleTV = itemView.findViewById(R.id.event_title_TV);
//            this.eventDescriptionTV = itemView.findViewById(R.id.even)
            this.eventStartTimeTV = itemView.findViewById(R.id.event_time_TV);
            this.dndSwitch = itemView.findViewById(R.id.dnd_S);

        }
    }


    CustomAdapter(ArrayList<Event> events){
        this.events = events;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_layout, parent, false);
        view.setOnClickListener(MainActivity.mOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        TextView titleTv = holder.eventTitleTV;
        TextView eventStartTv = holder.eventStartTimeTV;
        Switch eswitch = holder.dndSwitch;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM @ HH:mm");
        String formattedDate = simpleDateFormat.format(events.get(position).getEventStartTime());


//        String eventStartText = String.valueOf(events.get(position).getEventStartTime());

        titleTv.setText(events.get(position).getEventTitle());
        eventStartTv.setText(formattedDate);
        eswitch.setChecked(false);

    }

    @Override
    public int getItemCount() {
        return events.size();
    }


}

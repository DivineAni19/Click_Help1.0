package com.Help.Helping_1.click_help_1;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;



public class Donation_List extends ArrayAdapter<Donation> {

    private Activity context;
    private List<Donation> donationlist;

    public Donation_List(Activity context, List<Donation> donationlist) {
        super(context, R.layout.donation_info_row, donationlist);
        this.context = context;
        this.donationlist = donationlist;
    }



    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater layoutInflater=context.getLayoutInflater();

        View listviewitem=layoutInflater.inflate(R.layout.donation_info_row,null,true);

        TextView event_nm=(TextView)listviewitem.findViewById(R.id.event_nm);
        TextView who=(TextView)listviewitem.findViewById(R.id.who);
        TextView where=(TextView)listviewitem.findViewById(R.id.where);
        TextView tk=(TextView)listviewitem.findViewById(R.id.tk);
        TextView date=(TextView)listviewitem.findViewById(R.id.eve_date);

        Donation donation=donationlist.get(position);

        event_nm.setText("Event Name:"+donation.getEvent_hm());
        who.setText("Donor Name:"+donation.getWho());
        where.setText("Donor Address:"+donation.getWhere());
        tk.setText("Donation Amount:"+donation.getTk());
        date.setText("Donation Date:"+donation.getDate());

        return listviewitem;
    }
}

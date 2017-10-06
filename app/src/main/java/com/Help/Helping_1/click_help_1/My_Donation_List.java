package com.Help.Helping_1.click_help_1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by HP on 9/22/2017.
 */

public class My_Donation_List extends ArrayAdapter<Donation_Mine_Class> {

    FragmentActivity con;
    List<Donation_Mine_Class> listitem;

    public My_Donation_List(FragmentActivity context,  List<Donation_Mine_Class> objects) {
        super(context, R.layout.my_donation_info, objects);

        con=context;
        listitem=objects;

    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater layoutInflater=con.getLayoutInflater();

        View listviewitem=layoutInflater.inflate(R.layout.my_donation_info,null,true);

        TextView event_nm=(TextView)listviewitem.findViewById(R.id.event_nm_);
        TextView tk=(TextView)listviewitem.findViewById(R.id.tk_);
        TextView payment_type=(TextView)listviewitem.findViewById(R.id.payment_type);
        TextView payment_Date=(TextView)listviewitem.findViewById(R.id.payment_date_);


        Donation_Mine_Class donation_mine_class=listitem.get(position);

        event_nm.setText("Name:"+donation_mine_class.getEvent_name());
        tk.setText("Amount:"+donation_mine_class.getTk());
        payment_type.setText("Type:"+donation_mine_class.getPayment());
        payment_Date.setText("Date:"+donation_mine_class.getDate());


        return listviewitem;
    }



}

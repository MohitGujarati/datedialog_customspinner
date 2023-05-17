package com.example.interview_api_call.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.interview_api_call.R
import com.example.interview_api_call.model.data_model

class spinner_adapter(val context: Context, var datalist: List<data_model>) : BaseAdapter() {

        private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            val view: View
            val vh: ItemHolder
            if (convertView == null) {
                view = inflater.inflate(R.layout.cus_spinner, parent, false)
                vh = ItemHolder(view)
                view?.tag = vh
            } else {
                view = convertView
                vh = view.tag as ItemHolder
            }

            //we are geting data from api but currently its null
            //so data is retrive
            //but i have added data manually in it so names are displayed

            vh.label.text = datalist.get(position).Name
            vh.img.setBackgroundResource(datalist.get(position).Image)

            return view
        }

        override fun getItem(position: Int): Any? {
            return datalist[position];
        }

        override fun getCount(): Int {
            return datalist.size;
        }

        override fun getItemId(position: Int): Long {
            return position.toLong();
        }

        private class ItemHolder(row: View?) {
            val label: TextView
            val img: ImageView


            init {
                label = row?.findViewById(R.id.text) as TextView
                img = row?.findViewById(R.id.Img) as ImageView

            }
        }


}
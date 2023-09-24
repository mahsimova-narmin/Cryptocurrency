package com.narminmahsimova.cryptocurrency.adapter

import android.graphics.Color
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.narminmahsimova.cryptocurrency.R
import com.narminmahsimova.cryptocurrency.model.CryptoModel

class RecyclerViewAdapter(val cryptoList: ArrayList<CryptoModel>, private val listener: Listener): RecyclerView.Adapter<RecyclerViewAdapter.RowHolder>() {

    interface Listener{
        fun onItemClick(cryptoModel: CryptoModel)
    }

    private val colors: Array<String> = arrayOf("#E6E6FA","#FFF68F","#A9FFCB","#B48EFF","#F8766D","#FFB06D","#FFF26D","#8AC74B")

    class RowHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(cryptoModel: CryptoModel,colors:Array<String>, position: Int,listener: Listener){

            itemView.setOnClickListener{
                listener.onItemClick(cryptoModel)
            }



            val textNameView = itemView.findViewById<View>(R.id.text_name) as TextView
            val textPriceView = itemView.findViewById<View>(R.id.text_price) as TextView

            itemView.setBackgroundColor(Color.parseColor(colors[position%8]))

            textNameView.text = cryptoModel.currency
            textPriceView.text = cryptoModel.price


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout,parent,false)
        return RowHolder(view)
    }

    override fun getItemCount(): Int {
        return cryptoList.count()
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.bind(cryptoList[position],colors,position,listener)
    }
}
package com.s32xlevel.foodtracker.util

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.s32xlevel.foodtracker.R
import com.s32xlevel.foodtracker.repository.UserRepositoryImpl

class RecyclerFoodAdapter(val context: Context) : RecyclerView.Adapter<RecyclerFoodAdapter.ViewHolder>() {

    interface Listener {
        fun onClick(position: Int): Boolean?
    }

    var listener: Listener? = null

    private val user = UserRepositoryImpl(context).findById(1)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var typeName = itemView.findViewById<TextView>(R.id.food_recycler_typeName)
        var time = itemView.findViewById<TextView>(R.id.food_recycler_time)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycler_view_food, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val dishes = FoodUtil.getFoodsForUser(user!!)
        val dish = dishes!![position]

        val addedTime = dish.timeReception!!.plusMinutes(30)
        val time = "${dish.timeReception!!.toLocalTime().toString("HH:mm")} - ${addedTime.toLocalTime().toString("HH:mm")}"

        viewHolder.typeName.text = dish.typeName
        viewHolder.time.text = time

        viewHolder.itemView.setOnClickListener {
            val bol = listener!!.onClick(position)
            if (bol != null) {
                if (bol) {
                    viewHolder.typeName.setTextColor(Color.RED)
                    viewHolder.time.setTextColor(Color.RED)
                } else {
                    viewHolder.typeName.setTextColor(Color.GREEN)
                    viewHolder.time.setTextColor(Color.GREEN)
                }
            } else {
                Toast.makeText(context, context.getString(R.string.time_no_come), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return FoodUtil.getFoodsForUser(user!!)!!.size
    }
}
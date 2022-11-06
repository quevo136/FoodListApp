package com.diva.restofinder.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.diva.restofinder.R
import com.diva.restofinder.model.Food
import com.diva.restofinder.uitel.loadImage
import com.diva.restofinder.view.DetailsActivity

class ListAdapter (var mContext:Context,var foods:ArrayList<Food>):
RecyclerView.Adapter<ListAdapter.ListViewHolder>()
{
    inner class ListViewHolder(var v:View): RecyclerView.ViewHolder(v){

        var imgT = v.findViewById<ImageView>(R.id.foodImageView)
        var nameT = v.findViewById<TextView>(R.id.nameTextView)
        var addressT = v.findViewById<TextView>(R.id.review)
        var phoneT = v.findViewById<TextView>(R.id.phoneNumberTextView)


        var descriT = v.findViewById<TextView>(R.id.descriptionTextView)
        var mMenus:ImageView
        var mReview: TextView

        init {
            mMenus = v.findViewById(R.id.mMenus)
            mMenus.setOnClickListener { popupMenus(it) }

            mReview = v.findViewById(R.id.review)
            //mReview.setOnClickListener{ReviewActivity()}


        }

        private fun popupMenus(v:View) {
            val position = foods[adapterPosition]
            val popupMenus = PopupMenu(mContext,v)
            popupMenus.inflate(R.menu.show_menu)
            popupMenus.setOnMenuItemClickListener {
                when(it.itemId){

                    R.id.edit->{
                        val v = LayoutInflater.from(mContext).inflate(R.layout.activity_upload,null)
                        val name = v.findViewById<EditText>(R.id.nameEditText)
                        val number = v.findViewById<EditText>(R.id.addressEditText)
                        AlertDialog.Builder(mContext)
                            .setView(v)
                            .setPositiveButton("Ok"){
                                    dialog,_->
                                position.name = name.text.toString()
                                position.address = number.text.toString()

                                notifyDataSetChanged()
                                Toast.makeText(mContext,"User Information is Edited",Toast.LENGTH_SHORT).show()
                                dialog.dismiss()

                            }
                            .setNegativeButton("Cancel"){
                                    dialog,_->
                                dialog.dismiss()

                            }
                            .create()
                            .show()

                        true
                    }

                    R.id.delete->{
                        /**set delete*/
                        AlertDialog.Builder(mContext)
                            .setTitle("Delete")
                            .setMessage("Are you sure delete this Information")
                            .setPositiveButton("Yes"){
                                    dialog,_->
                                foods.removeAt(adapterPosition)
                                notifyDataSetChanged()
                                Toast.makeText(mContext,"Deleted this Information", Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                            }
                            .setNegativeButton("No"){
                                    dialog,_->
                                dialog.dismiss()
                            }
                            .create()
                            .show()

                        true
                    }
                    else-> true
                }

            }
            popupMenus.show()
            val popup = PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true
            val menu = popup.get(popupMenus)
            menu.javaClass.getDeclaredMethod("setForceShowIcon",Boolean::class.java)
                .invoke(menu,true)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
       var infalter = LayoutInflater.from(parent.context)
        var v = infalter.inflate(R.layout.row_item,parent,false)
        return ListViewHolder(v)
    }

    override fun getItemCount(): Int =foods.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
       var newList = foods[position]
        holder.nameT.text = newList.name
        holder.descriT.text = newList.description
        holder.imgT.loadImage(newList.imageUrl)
        holder.addressT.text = newList.address


        holder.v.setOnClickListener {

            val name = newList.name
            val address = newList.address
            val phone = newList.phone
            val descrip = newList.description
            val imgUri = newList.imageUrl

            val mIntent = Intent(mContext,DetailsActivity::class.java)
            mIntent.putExtra("NAMET",name)
            mIntent.putExtra("ADDRESST","Address: ${address}")
            mIntent.putExtra("PHONET","Phone: ${phone}")
            mIntent.putExtra("DESCRIT","Review: ${descrip}")
            mIntent.putExtra("IMGURI",imgUri)
            mContext.startActivity(mIntent)
        }
    }
}
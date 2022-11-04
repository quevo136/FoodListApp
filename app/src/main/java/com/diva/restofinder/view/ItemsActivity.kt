package com.diva.restofinder.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.diva.restofinder.R
import com.diva.restofinder.adapter.ListAdapter
import com.diva.restofinder.model.Food
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_items.*

class ItemsActivity : AppCompatActivity() {
    private var mStorage: FirebaseStorage? = null
    private var mDatabaseRef: DatabaseReference? = null
    private var mDBListener: ValueEventListener? = null
    private lateinit var mFoods:List<Food>
    private lateinit var listAdapter: ListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_items)
        /**set adapter*/
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(this@ItemsActivity)
        myDataLoaderProgressBar.visibility = View.VISIBLE
        mFoods = ArrayList()
        listAdapter = ListAdapter(this@ItemsActivity, mFoods as ArrayList<Food>)
        mRecyclerView.adapter = listAdapter
        /**set Firebase Database*/
        mStorage = FirebaseStorage.getInstance()
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("food_uploads")
        mDBListener = mDatabaseRef!!.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ItemsActivity,error.message, Toast.LENGTH_SHORT).show()
                myDataLoaderProgressBar.visibility = View.INVISIBLE

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                (mFoods as ArrayList<Food>).clear()
                for (foodSnapshot in snapshot.children){
                    val upload = foodSnapshot.getValue(Food::class.java)
                    upload!!.key = foodSnapshot.key
                    (mFoods as ArrayList<Food>).add(upload)

                }
                listAdapter.notifyDataSetChanged()
                myDataLoaderProgressBar.visibility = View.GONE

            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        mDatabaseRef!!.removeEventListener(mDBListener!!)
    }
}
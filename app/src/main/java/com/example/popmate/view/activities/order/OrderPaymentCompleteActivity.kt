package com.example.popmate.view.activities.order


import android.os.Bundle
import android.util.Log
import com.example.popmate.R
import com.example.popmate.config.BaseActivity
import com.example.popmate.databinding.ActivityOrderPaymentCompleteBinding
import com.example.popmate.model.data.remote.order.PopupStoreItem

class OrderPaymentCompleteActivity : BaseActivity<ActivityOrderPaymentCompleteBinding>(R.layout.activity_order_payment_complete) {
    private lateinit var data : ArrayList<PopupStoreItem>
    private var totalAmount = 0
    private var orderName = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        data = ArrayList<PopupStoreItem>()
        if (intent.hasExtra("item")) {
            //val storeitem = intent.getSerializableExtra("item") as? ArrayList<PopupStoreItem>
            val storeitem = intent.getParcelableArrayListExtra<PopupStoreItem>("item")

            Log.d("ddd",storeitem.toString())
            if (storeitem != null) {
                for (value in storeitem) {
                    data.add(value)
                    totalAmount += value.amount * value.totalQuantity
                }
                if (data.size == 1) {
                    orderName = data[0].name
                } else {
                    orderName = "${data[0].name} 외 ${data.size - 1}건"
                }
            }
        }

        binding.txtOrderDetailPaymentCompleteTotalprice.text = totalAmount.toString()
        binding.textView13.text = totalAmount.toString()

    }
}
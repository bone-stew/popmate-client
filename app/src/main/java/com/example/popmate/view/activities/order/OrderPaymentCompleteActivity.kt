package com.example.popmate.view.activities.order


import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.popmate.R
import com.example.popmate.config.BaseActivity
import com.example.popmate.databinding.ActivityOrderPaymentCompleteBinding
import com.example.popmate.model.data.remote.order.OrderPlaceDetailResponse
import com.example.popmate.model.data.remote.order.PopupStoreItem
import com.example.popmate.view.activities.MainActivity
import com.example.popmate.view.activities.detail.PopupDetailActivity
import com.example.popmate.view.activities.user.MyPagePurchaseDetailActivity
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class OrderPaymentCompleteActivity : BaseActivity<ActivityOrderPaymentCompleteBinding>(R.layout.activity_order_payment_complete) {
    private lateinit var data : ArrayList<PopupStoreItem>
    private var totalAmount = 0
    private var orderName = ""
    private var placeDetail: OrderPlaceDetailResponse? = null
    private var popupStoreId = 0L
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
                    popupStoreId = value.popupStoreId
                }
                if (data.size == 1) {
                    orderName = data[0].name
                } else {
                    orderName = "${data[0].name} 외 ${data.size - 1}건"
                }
            }
        }
        val orderId = intent.getLongExtra("orderId",0)
        if(intent.hasExtra("placeDetail")){
            placeDetail = intent.getParcelableExtra("placeDetail") as? OrderPlaceDetailResponse
        }

        val dateFormat = SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.getDefault())
        val currentTime = Date()


        val amount = NumberFormat.getNumberInstance(Locale.KOREA).format(totalAmount)
        binding.txtOrderDetailPaymentCompleteTotalprice.text = amount
        binding.textView13.text = amount
        binding.textView14.text = dateFormat.format(currentTime)
        binding.textView15.text = placeDetail?.title

        binding.btnOrderPaymentCompleteCheck.setOnClickListener {
            val intent = Intent(this, PopupDetailActivity::class.java)
            intent.putExtra("id",popupStoreId)
            startActivity(intent)
        }

        binding.imgOrderDetailPaymentCompleteClose.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.layoutOrderPaymentOrderList.setOnClickListener {
            val intent = Intent(this,MyPagePurchaseDetailActivity::class.java)
            intent.putExtra("orderId",orderId)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }
}

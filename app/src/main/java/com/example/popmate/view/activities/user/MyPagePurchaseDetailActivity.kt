package com.example.popmate.view.activities.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.popmate.databinding.ActivityMyPagePurchaseDetailBinding
import com.example.popmate.model.data.remote.user.OrderListItem
import com.example.popmate.model.data.remote.user.Orders
import com.example.popmate.view.adapters.user.MyPageOrderDetailAdapter
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

class MyPagePurchaseDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMyPagePurchaseDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPagePurchaseDetailBinding.inflate(layoutInflater)
        binding.layoutPagePurchaseDetailTitle.imgArrow.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        val clickedItem = intent.getParcelableExtra<Orders>("id")
        if (clickedItem is Orders) {
            val adapter = MyPageOrderDetailAdapter()
            adapter.listData = clickedItem.orderItemList as MutableList<OrderListItem>
            if(clickedItem.status==0){
                binding.txtFragmentMyPagePurchaseDetailPickup.text = "픽업 대기중이에요"
            }else if(clickedItem.status == 1) {
                binding.txtFragmentMyPagePurchaseDetailPickup.text = "픽업 완료"
            }else{
                binding.txtFragmentMyPagePurchaseDetailPickup.text = "주문 취소"
            }
            binding.recyclerViewMyPagePuchaseDetail.adapter = adapter
            binding.recyclerViewMyPagePuchaseDetail.layoutManager = LinearLayoutManager(this)
            binding.txtFragmentMyPagePurchaseDetailStoreName.text = clickedItem.title

            if(clickedItem.orderItemList.size == 1){
                binding.txtFragmentMyPagePurchaseDetailItems.text = clickedItem.orderItemList[0].popupStoreItem!!.name
            }else{
                binding.txtFragmentMyPagePurchaseDetailItems.text = clickedItem.orderItemList[0].popupStoreItem!!.name + " 외 ${clickedItem.orderItemList.size-1}개"
            }
            val koreanLocale = Locale("ko", "KR")
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", koreanLocale)
            val outputFormat = SimpleDateFormat("yyyy년 M월 d일 a hh:mm", koreanLocale)
            val date = inputFormat.parse(clickedItem.createdAt)
            val formattedDate = outputFormat.format(date)
            binding.txtFragmentMyPagePurchaseDetailTime.text = "구매일시 : ${formattedDate}"
            binding.txtFragmentMyPagePurchaseDetailOrderNumber.text = "주문번호 : ${clickedItem.orderTossId}"
            val amount = clickedItem.total_amount
            val totalAmount = NumberFormat.getNumberInstance(Locale.KOREA).format(amount) + "원"
            binding.txtFragmentMyPagePurchaseDetailTotalPrice.text = "${totalAmount}"

            if(clickedItem.easyPay==null){
                binding.txtFragmentMyPagePurchaseDetailPayment.text = "${clickedItem.cardType}${clickedItem.method}"
            }else{

            }

            binding.txtFragmentMyPagePurchaseDetailDepartment.text = "${clickedItem.placeDetail} 매장"

        } else {
        }

        binding.txtMypageQrcode.setOnClickListener {

        }

        setContentView(binding.root)
    }
}
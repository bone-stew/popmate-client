package com.example.popmate.view.activities.user

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.popmate.R
import com.example.popmate.config.BaseActivity
import com.example.popmate.databinding.ActivityMyPagePurchaseDetailBinding
import com.example.popmate.model.data.remote.user.OrderListItem
import com.example.popmate.util.QrDialog
import com.example.popmate.view.adapters.user.MyPageOrderDetailAdapter
import com.example.popmate.viewmodel.user.OrderListDetailViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MyPagePurchaseDetailActivity : BaseActivity<ActivityMyPagePurchaseDetailBinding>(R.layout.activity_my_page_purchase_detail) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPagePurchaseDetailBinding.inflate(layoutInflater)
        binding.layoutPagePurchaseDetailTitle.imgArrow.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        setContentView(binding.root)
        val orderId = intent.getLongExtra("orderId",0)
        val model : OrderListDetailViewModel by viewModels()
        model.loadDetails(orderId)

        model.orderDetailItem.observe(this){
            binding.orderDetailItems = it
            val data = binding.orderDetailItems!!.orderListDetailResponse
            val adapter = MyPageOrderDetailAdapter()
            adapter.listData = data.orderItemList as MutableList<OrderListItem>
            if(data.status==0){
                binding.txtFragmentMyPagePurchaseDetailPickup.text = "픽업 대기중이에요"
            }else if(data.status == 1) {
                binding.txtFragmentMyPagePurchaseDetailPickup.text = "픽업 완료"
            }else{
                binding.txtFragmentMyPagePurchaseDetailPickup.text = "주문 취소"
            }
            binding.recyclerViewMyPagePuchaseDetail.adapter = adapter
            binding.recyclerViewMyPagePuchaseDetail.layoutManager = LinearLayoutManager(this)
            binding.txtFragmentMyPagePurchaseDetailStoreName.text = data.title

            if(data.orderItemList.size==1){
                binding.txtFragmentMyPagePurchaseDetailItems.text = data.orderItemList[0].popupStoreItem!!.name
            }else{
                binding.txtFragmentMyPagePurchaseDetailItems.text = data.orderItemList[0].popupStoreItem!!.name + " 외 ${data.orderItemList.size-1}개"
            }

            val koreanLocale = Locale("ko", "KR")
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", koreanLocale)
            val outputFormat = SimpleDateFormat("yyyy년 M월 d일 a hh:mm", koreanLocale)
            val date = inputFormat.parse(data.createdAt)
            val formattedDate = outputFormat.format(date)

            //pickup 시간
            val currentTime = Calendar.getInstance()
            currentTime.time = date
            currentTime.add(Calendar.MINUTE, 10)
            val pickupDate = outputFormat.format(currentTime.time)
            binding.txtFragmentMyPagePickup.text = "픽업시간 : ${pickupDate}"
            binding.txtFragmentMyPagePurchaseDetailTime.text = "구매일시 : ${formattedDate}"
            binding.txtFragmentMyPagePurchaseDetailOrderNumber.text = "주문번호 : ${data.orderTossId}"
            val amount = data.total_amount
            val totalAmount = NumberFormat.getNumberInstance(Locale.KOREA).format(amount) + "원"
            binding.txtFragmentMyPagePurchaseDetailTotalPrice.text = "${totalAmount}"

            if(data.easyPay==null){
                binding.txtFragmentMyPagePurchaseDetailPayment.text = "${data.cardType}${data.method}"
            }else{
            }
            binding.txtFragmentMyPagePurchaseDetailDepartment.text = "${data.placeDetail} 매장"

            binding.btnMypageQrcode.setOnClickListener {
                val dialog = QrDialog(this,data.qrImgUrl)
                dialog.listener = object:QrDialog.LessonOkDialogClickedListener{
                    override fun onOkClicked() {
                    }
                }
                dialog.start()
            }
        }
    }
}

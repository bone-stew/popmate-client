package com.example.popmate.view.fragments.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.popmate.databinding.FragmentMyPagePurchaseDetailBinding
import com.example.popmate.model.data.remote.user.OrderListItem
import com.example.popmate.model.data.remote.user.Orders
import com.example.popmate.view.adapters.user.MyPageOrderDetailAdapter
import java.text.SimpleDateFormat
import java.util.Locale


class MyPagePurchaseDetailFragment(private val clickedItem: Orders) : Fragment() {
    private lateinit var binding : FragmentMyPagePurchaseDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyPagePurchaseDetailBinding.inflate(layoutInflater)

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
        binding.recyclerViewMyPagePuchaseDetail.layoutManager = LinearLayoutManager(requireContext())
        binding.txtFragmentMyPagePurchaseDetailStoreName.text = clickedItem.title

        if(clickedItem.orderItemList.size == 1){
            binding.txtFragmentMyPagePurchaseDetailItems.text = clickedItem.orderItemList[0].popupStoreItem.name
        }else{
            binding.txtFragmentMyPagePurchaseDetailItems.text = clickedItem.orderItemList[0].popupStoreItem.name + " 외 ${clickedItem.orderItemList.size-1}개"
        }
        val koreanLocale = Locale("ko", "KR")
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", koreanLocale)
        val outputFormat = SimpleDateFormat("yyyy년 M월 d일 a hh:mm", koreanLocale)
        val date = inputFormat.parse(clickedItem.createdAt)
        val formattedDate = outputFormat.format(date)
        binding.txtFragmentMyPagePurchaseDetailTime.text = "구매일시 : ${formattedDate}"
        binding.txtFragmentMyPagePurchaseDetailOrderNumber.text = "주문번호 : ${clickedItem.orderTossId}"

        binding.txtFragmentMyPagePurchaseDetailTotalPrice.text = "${clickedItem.total_amount}원"

        if(clickedItem.easyPay==null){
            binding.txtFragmentMyPagePurchaseDetailPayment.text = "${clickedItem.cardType}${clickedItem.method}"
        }else{

        }

        binding.txtFragmentMyPagePurchaseDetailDepartment.text = "${clickedItem.placeDetail} 매장"
        return binding.root
    }



}
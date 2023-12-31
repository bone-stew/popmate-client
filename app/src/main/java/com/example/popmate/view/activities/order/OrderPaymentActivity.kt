package com.example.popmate.view.activities.order

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.popmate.R
import com.example.popmate.model.data.remote.order.Payment
import com.example.popmate.databinding.ActivityOrderPaymentBinding
import com.example.popmate.model.data.remote.ApiResponse
import com.example.popmate.model.data.remote.order.OrderItemRequest
import com.example.popmate.model.data.remote.order.OrderPlaceDetailResponse
import com.example.popmate.model.data.remote.order.OrderResponse
import com.example.popmate.model.data.remote.order.PopupStoreItem
import com.example.popmate.model.repository.ApiClient
import com.example.popmate.util.ProgressDialog
import com.google.gson.Gson
import com.tosspayments.paymentsdk.PaymentWidget
import com.tosspayments.paymentsdk.model.PaymentCallback
import com.tosspayments.paymentsdk.model.PaymentWidgetStatusListener
import com.tosspayments.paymentsdk.model.TossPaymentResult
import com.tosspayments.paymentsdk.view.PaymentMethod
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.Locale
import java.util.Random

class OrderPaymentActivity : AppCompatActivity() {
    private lateinit var binding : ActivityOrderPaymentBinding
    private lateinit var data : ArrayList<PopupStoreItem>
    private var totalAmount = 0
    private var orderName = ""
    private var placeDetail: OrderPlaceDetailResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityOrderPaymentBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        val progressDialog = ProgressDialog(this)
        progressDialog.start()
        progressDialog.setCancelable(false)

        setContentView(binding.root)
        data = ArrayList<PopupStoreItem>()
        if (intent.hasExtra("item")) {
            //val storeitem = intent.getSerializableExtra("item") as? ArrayList<PopupStoreItem>
            val storeitem = intent.getParcelableArrayListExtra<PopupStoreItem>("item")

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

        if(intent.hasExtra("placeDetail")){
            placeDetail = intent.getParcelableExtra("placeDetail") as? OrderPlaceDetailResponse
        }

        binding.txtOrderDetailPaymentPopupstoreName.text = placeDetail?.title
        binding.txtOrderDetailPaymentDepartment.text = placeDetail?.placeDetail

        val amount = NumberFormat.getNumberInstance(Locale.KOREA).format(totalAmount)
        binding.txtOrderDetailPaymentTotalprice.text = amount

        val orderId = generateOrderId(10)
        val clientKey = getString(R.string.toss_client_key)

        val paymentWidget = PaymentWidget(
            activity = this@OrderPaymentActivity,
            clientKey = clientKey,
            customerKey = "refefafe@kfe_ffae11",
        )

        val paymentMethodWidgetStatusListener = object : PaymentWidgetStatusListener {
            override fun onLoad() {
                val message = "결제위젯 렌더링 완료"
                Log.d("jja", message)
                //loadingSpinner.visibility = View.GONE
                progressDialog.dismiss()
            }
        }

        paymentWidget.run {
            renderPaymentMethods(
                method = binding.paymentMethodWidget,
                amount = PaymentMethod.Rendering.Amount(totalAmount),
                paymentWidgetStatusListener = paymentMethodWidgetStatusListener
            )
            renderAgreement(binding.agreementWidget)
        }

        binding.btnOrderPayment.setOnClickListener {
            // 여기서 orderId는 항상 바꿔줘야 한다.
            paymentWidget.requestPayment(
                paymentInfo = PaymentMethod.PaymentInfo(orderId = orderId, orderName = orderName),
                paymentCallback = object : PaymentCallback {
                    override fun onPaymentSuccess(success: TossPaymentResult.Success) {
                        Log.d("ddd", success.paymentKey)
                        Log.d("ddd", success.orderId)
                        Log.d("ddd", success.amount.toString())

                        val client = OkHttpClient()
                        val mediaType = "application/json".toMediaTypeOrNull()
                        val body = RequestBody.create(mediaType, "{\"paymentKey\":\"${success.paymentKey}\",\"amount\":\"${success.amount}\",\"orderId\":\"${success.orderId}\"}")
                        val request = Request.Builder()
                            .url("https://api.tosspayments.com/v1/payments/confirm")
                            .post(body)
                            .addHeader("Authorization", "Basic dGVzdF9za19Qb3h5MVhRTDhSZzFrNjBNMWpaMzduTzVXbWxnOg==")
                            .addHeader("Content-Type", "application/json")
                            .build()

                        // 백그라운드 스레드에서 실행
                        Thread {
                            val response = client.newCall(request).execute()
                            Log.d("ddd", response.toString())
                            if (response.isSuccessful) {
                                val responseBody = response.body?.string()
                                Log.d("ddd",response.body.toString())
                                // Gson을 사용하여 JSON을 Payment 객체로 변환
                                val gson = Gson()
                                val payment = gson.fromJson(responseBody, Payment::class.java)
                                ordercomplete(payment)
                            } else {
                                Log.e("ddd", "서버 응답 실패: ${response.code}")
                            }

                        }.start()
                    }

                    override fun onPaymentFailed(fail: TossPaymentResult.Fail) {
                        Log.d("ddd",fail.errorMessage)
                    }
                }
            )
        }

        binding.imgArrow.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun generateOrderId(length: Int): String {
        val characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_"
        val random = Random()
        val orderId = StringBuilder()

        for (i in 0 until length) {
            val randomIndex = random.nextInt(characters.length)
            val randomChar = characters[randomIndex]
            orderId.append(randomChar)
        }

        return orderId.toString()
    }

    private fun ordercomplete(payment: Payment) {
        runOnUiThread {
            // ProgressDialog를 시작
            val progressDialog = ProgressDialog(this@OrderPaymentActivity)
            progressDialog.start()
            progressDialog.setCancelable(false)

            Log.d("ddd", "orderComplete")
            Log.d("ddd", payment.toString())
            val orderItemRequest = OrderItemRequest(data, payment.orderId, payment.receipt.url, payment.card.cardType,
                "", payment.method)

            val call: Call<ApiResponse<OrderResponse>> = ApiClient.orderService.orderItems(orderItemRequest)
            call.enqueue(object : Callback<ApiResponse<OrderResponse>> {
                override fun onResponse(
                    call: Call<ApiResponse<OrderResponse>>,
                    response: Response<ApiResponse<OrderResponse>>
                ) {
                    Log.d("ddd", "왔어??")
                    val orderId = response.body()?.data?.orderId
                    val intent = Intent(this@OrderPaymentActivity, OrderPaymentCompleteActivity::class.java)
                    intent.putExtra("item", data)
                    intent.putExtra("placeDetail", placeDetail)
                    intent.putExtra("orderId", orderId)

                    // ProgressDialog를 닫음
                    progressDialog.dismiss()
                    startActivity(intent)
                }

                override fun onFailure(call: Call<ApiResponse<OrderResponse>>, t: Throwable) {
                    Log.d("jja", "서버 실패")

                    // ProgressDialog를 닫음
                    progressDialog.dismiss()
                }
            })
        }
    }
}

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
import com.example.popmate.model.data.remote.order.OrderResponse
import com.example.popmate.model.data.remote.order.PopupStoreItem
import com.example.popmate.model.repository.ApiClient
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
import java.util.Random
import java.util.UUID

class OrderPaymentActivity : AppCompatActivity() {
    private lateinit var binding : ActivityOrderPaymentBinding
    private lateinit var data : ArrayList<PopupStoreItem>
    private var totalAmount = 0
    private var orderName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityOrderPaymentBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
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
        binding.txtOrderDetailPaymentWon.text = totalAmount.toString()
        binding.txtOrderDetailPaymentBottomTotalprice.text = totalAmount.toString()

        val orderId = generateOrderId(10)

        val clientKey = getString(R.string.toss_client_key)
        val uuid = UUID.randomUUID()
        val paymentWidget = PaymentWidget(
            activity = this@OrderPaymentActivity,
            clientKey = clientKey,
            customerKey = "refefafe@kfe_ffae11",
        )

        val paymentMethodWidgetStatusListener = object : PaymentWidgetStatusListener {
            override fun onLoad() {
                val message = "결제위젯 렌더링 완료"
                Log.d("jja", message)
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
                                Log.e("ddd", "서버 응답 실패: ${response.body.toString()}")
                                Log.e("ddd", "서버 응답 실패: ${response.message}")
                                Log.e("ddd", "서버 응답 실패: ${response.toString()}")
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
        val characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_=+"
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
        Log.d("ddd", "orderComplete")
        Log.d("ddd", payment.toString())
        val orderItemRequest = OrderItemRequest(data,payment.orderId, payment.receipt.url, payment.card.cardType,
            "", payment.method) //payment.easyPay as String
        Log.d("ddd",orderItemRequest.orderId)
        Log.d("ddd",orderItemRequest.cardType)
        Log.d("ddd",orderItemRequest.toString())
        val call : Call<ApiResponse<OrderResponse>> = ApiClient.orderService.orderItems(orderItemRequest)
        call.enqueue(object : Callback<ApiResponse<OrderResponse>>{
            override fun onResponse(
                call: Call<ApiResponse<OrderResponse>>,
                response: Response<ApiResponse<OrderResponse>>
            ) {
                Log.d("jja","서버 왔어요")
                val intent = Intent(this@OrderPaymentActivity,OrderPaymentCompleteActivity::class.java)
                intent.putExtra("item",data)
                startActivity(intent)
            }

            override fun onFailure(call: Call<ApiResponse<OrderResponse>>, t: Throwable) {
                Log.d("jja","서버 실패")
            }
        })

    }

}
package com.example.popmate.view.activities.order

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.popmate.R
import com.example.popmate.model.data.remote.order.Payment
import com.example.popmate.databinding.ActivityOrderPaymentBinding
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

class OrderPaymentActivity : AppCompatActivity() {
    private lateinit var binding : ActivityOrderPaymentBinding

//    clientKey : 토스페이먼츠에서 발급하는 연동 키
//    customerKey : 다른 사용자가 이 값을 알면 악의적으로 사용할 수 있어서 (UUID)
//    orderId: 주문을 구분하는 ID입니다. 충분히 무작위한 값을 생성해서 각 주문마다 고유한 값을 넣어주어야 한다.
//    영문 대소문자, 숫자, 특수문자 -, _, =로 이루어진 6자 이상 64자 이하의 문자열이어야 한다
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityOrderPaymentBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val clientLey = getString(R.string.toss_client_key)

        val paymentWidget = PaymentWidget(
            activity = this@OrderPaymentActivity,
            clientKey = clientLey,
            customerKey = "7CP_K-knksQZ966GZAfhmdee",
        )

        val paymentMethodWidgetStatusListener = object : PaymentWidgetStatusListener {
            override fun onLoad() {
                val message = "결제위젯 렌더링 완료"
                Log.d("ddd", message)
            }
        }

        paymentWidget.run {
            renderPaymentMethods(
                method = binding.paymentMethodWidget,
                amount = PaymentMethod.Rendering.Amount(5000),
                paymentWidgetStatusListener = paymentMethodWidgetStatusListener
            )
            renderAgreement(binding.agreementWidget)
        }



        binding.btnOrderPayment.setOnClickListener {
            // 여기서 orderId는 항상 바꿔줘야 한다.
            paymentWidget.requestPayment(
                paymentInfo = PaymentMethod.PaymentInfo(orderId = "cddsedffqwedfdfdwfefedasdss", orderName = "생수잔 외 1개"),
                paymentCallback = object : PaymentCallback {
                    override fun onPaymentSuccess(success: TossPaymentResult.Success) {
                        Log.d("ddd", success.paymentKey)
                        Log.d("ddd", success.orderId)
                        Log.d("ddd", success.amount.toString())

                        val paymentKey = success.paymentKey
                        val amount = success.amount.toString()
                        val orderId = success.orderId

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
                            Log.d("ddd",response.message)
                            if (response.isSuccessful) {
                                val responseBody = response.body?.string()

                                // Gson을 사용하여 JSON을 Payment 객체로 변환
                                val gson = Gson()
                                val payment = gson.fromJson(responseBody, Payment::class.java)

                                // Payment 객체의 내용 출력
//                                Log.d("ddd", "PaymentKey: ${payment.paymentKey}")
//                                Log.d("ddd", "Amount: ${payment.totalAmount}")
//                                Log.d("ddd", "OrderID: ${payment.orderId}")
//                                Log.d("ddd", "OrderID: ${payment.orderName}")
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
    }

    private fun ordercomplete(payment: Payment) {
        val intent = Intent(this,OrderPaymentCompleteActivity::class.java)
        startActivity(intent)
    }

}
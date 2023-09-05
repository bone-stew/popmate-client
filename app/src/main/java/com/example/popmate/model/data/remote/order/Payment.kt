package com.example.popmate.model.data.remote.order

data class Payment(
    val mId: String,
    val lastTransactionKey: String,
    val paymentKey: String,
    val orderId: String,
    val orderName: String,
    val taxExemptionAmount: Int,
    val status: String,
    val requestedAt: String,
    val approvedAt: String,
    val useEscrow: Boolean,
    val cultureExpense: Boolean,
    val card: Card,
    val virtualAccount: Any?, // 이 필드의 유형은 정확한 데이터 유형을 모르는 경우 사용 가능합니다.
    val transfer: Any?, // 이 필드의 유형은 정확한 데이터 유형을 모르는 경우 사용 가능합니다.
    val mobilePhone: Any?, // 이 필드의 유형은 정확한 데이터 유형을 모르는 경우 사용 가능합니다.
    val giftCertificate: Any?, // 이 필드의 유형은 정확한 데이터 유형을 모르는 경우 사용 가능합니다.
    val cashReceipt: Any?, // 이 필드의 유형은 정확한 데이터 유형을 모르는 경우 사용 가능합니다.
    val cashReceipts: Any?, // 이 필드의 유형은 정확한 데이터 유형을 모르는 경우 사용 가능합니다.
    val discount: Any?, // 이 필드의 유형은 정확한 데이터 유형을 모르는 경우 사용 가능합니다.
    val cancels: Any?, // 이 필드의 유형은 정확한 데이터 유형을 모르는 경우 사용 가능합니다.
    val secret: String,
    val type: String,
    val easyPay: Any?, // 이 필드의 유형은 정확한 데이터 유형을 모르는 경우 사용 가능합니다.
    val country: String,
    val isPartialCancelable: Boolean,
    val receipt: Receipt,
    val checkout: Checkout,
    val currency: String,
    val totalAmount: Int,
    val balanceAmount: Int,
    val suppliedAmount: Int,
    val vat: Int,
    val taxFreeAmount: Int,
    val method: String,
    val version: String
)

data class Card(
    val issuerCode: String,
    val acquirerCode: String,
    val number: String,
    val installmentPlanMonths: Int,
    val isInterestFree: Boolean,
    val interestPayer: Any?, // 이 필드의 유형은 정확한 데이터 유형을 모르는 경우 사용 가능합니다.
    val approveNo: String,
    val useCardPoint: Boolean,
    val cardType: String,
    val ownerType: String,
    val acquireStatus: String,
    val amount: Int
)

data class Receipt(
    val url: String
)

data class Checkout(
    val url: String
)


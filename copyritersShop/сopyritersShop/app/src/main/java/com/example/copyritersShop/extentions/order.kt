package com.example.copyritersShop.extentions

import com.example.copyritersShop.model.item.OrderStatus

val OrderStatus.str: String
    get() = when (this) {
        OrderStatus.WAIT_CONFIRM -> "Ожидает подтверждения"
        OrderStatus.CONFIRMED -> "Подтвержден"
        OrderStatus.WAIT_WORKING -> "Ожидает выполнения"
        OrderStatus.IN_WORKING -> "В процессе выполнения"
        OrderStatus.COMPLETE -> "Выполнен"
        OrderStatus.CANCELED -> "Отменен"
    }

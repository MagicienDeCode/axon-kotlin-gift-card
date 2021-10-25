package fr.xiang.giftcardrest.infra.query

import fr.xiang.giftcardapi.query.model.GiftCardView
import fr.xiang.giftcardrest.domain.command.GiftCard

fun GiftCard.toView(): GiftCardView = with(this) {
    GiftCardView(id, amount)
}

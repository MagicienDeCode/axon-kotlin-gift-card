package fr.xiang.giftcardrest.domain.query

import fr.xiang.giftcardapi.query.GetGiftCardQuery
import fr.xiang.giftcardapi.query.model.GiftCardView

interface GiftCardViewProjector {
    fun getGiftCard(query: GetGiftCardQuery): GiftCardView
}

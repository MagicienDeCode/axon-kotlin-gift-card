package fr.xiang.giftcardapi.command

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class RedeemCommand(
    @TargetAggregateIdentifier val id: String,
    val amount: Int
)

package fr.xiang.giftcardapi.command

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class CancelCommand(
    @TargetAggregateIdentifier val id: String
)

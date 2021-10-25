package fr.xiang.giftcardapi.command

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class IssueCommand(
    @TargetAggregateIdentifier val id: String,
    val amount: Int
)

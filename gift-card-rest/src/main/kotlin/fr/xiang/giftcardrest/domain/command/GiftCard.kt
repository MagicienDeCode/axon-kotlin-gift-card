package fr.xiang.giftcardrest.domain.command

import fr.xiang.giftcardapi.command.CancelCommand
import fr.xiang.giftcardapi.command.IssueCommand
import fr.xiang.giftcardapi.command.RedeemCommand
import fr.xiang.giftcardapi.event.CancelEvent
import fr.xiang.giftcardapi.event.IssuedEvent
import fr.xiang.giftcardapi.event.RedeemEvent
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate
import org.springframework.context.annotation.Profile
import kotlin.properties.Delegates

@Profile("command")
@Aggregate
class GiftCard {

    @AggregateIdentifier
    lateinit var id: String
    var amount by Delegates.notNull<Int>()

    constructor()

    @CommandHandler
    constructor(issueCommand: IssueCommand) {
        if (issueCommand.amount <= 0) {
            throw RuntimeException("issueCommand amount <= 0")
        }
        AggregateLifecycle.apply(IssuedEvent(issueCommand.id, issueCommand.amount))
    }

    @CommandHandler
    fun handle(cancelCommand: CancelCommand) {
        AggregateLifecycle.apply(CancelEvent(cancelCommand.id))
    }

    @CommandHandler
    fun handle(redeemCommand: RedeemCommand) {
        if (redeemCommand.amount <= 0) {
            throw RuntimeException("redeemCommand amount <= 0")
        }
        if (redeemCommand.amount > this.amount) {
            throw RuntimeException("redeemCommand amount > remaining amount")
        }
        AggregateLifecycle.apply(RedeemEvent(redeemCommand.id, redeemCommand.amount))
    }

    @EventSourcingHandler
    fun on(issuedEvent: IssuedEvent) {
        this.id = issuedEvent.id
        this.amount = issuedEvent.amount
    }

    @EventSourcingHandler
    fun on(cancelEvent: CancelEvent) {
        this.amount = 0
    }

    @EventSourcingHandler
    fun on(redeemEvent: RedeemEvent) {
        this.amount -= redeemEvent.amount
    }
}

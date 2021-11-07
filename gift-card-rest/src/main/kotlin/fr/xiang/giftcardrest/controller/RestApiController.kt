package fr.xiang.giftcardrest.controller

import fr.xiang.giftcardapi.command.CancelCommand
import fr.xiang.giftcardapi.command.IssueCommand
import fr.xiang.giftcardapi.command.RedeemCommand
import fr.xiang.giftcardapi.query.GetGiftCardQuery
import fr.xiang.giftcardapi.query.model.GiftCardView
import fr.xiang.giftcardrest.utils.lazyLogger
import org.axonframework.commandhandling.CommandMessage
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.eventsourcing.eventstore.EventStore
import org.axonframework.extensions.kotlin.send
import org.axonframework.extensions.reactor.commandhandling.gateway.ReactorCommandGateway
import org.axonframework.extensions.reactor.queryhandling.gateway.ReactorQueryGateway
import org.axonframework.messaging.MetaData
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import kotlin.streams.toList

@RestController
@RequestMapping("/card/")
class RestApiController(
    private val commandGateway: CommandGateway,
    private val reactorCommandGateway: ReactorCommandGateway,
    private val queryGateway: ReactorQueryGateway,
    private val eventStore: EventStore
) {

    private val logger by lazyLogger()

    @GetMapping("create/{id}/{amount}")
    fun createCard(@PathVariable id: String, @PathVariable amount: Int): String {

        commandGateway.send(
            command = IssueCommand(id, amount),
            onSuccess = { commandMessage: CommandMessage<out IssueCommand>, result: Any, metaData: MetaData ->
                logger.info(commandMessage.toString())
                logger.info(result.toString())
                logger.info(metaData.toString())
            },
            onError = { result: Any, exception: Throwable, metaData: MetaData ->
                logger.info(result.toString())
                logger.info(exception.toString())
                logger.info(metaData.toString())
            }
        )

        return "OK"
    }

    @GetMapping("redeem/{id}/{amount}")
    fun redeemCard(@PathVariable id: String, @PathVariable amount: Int): Mono<String> =
        reactorCommandGateway.send(RedeemCommand(id, amount))

    @GetMapping("cancel/{id}")
    fun cancelCard(@PathVariable id: String): Mono<String> =
        reactorCommandGateway.send(CancelCommand(id))

    @GetMapping("{id}")
    fun getCard(@PathVariable id: String): Mono<GiftCardView> =
        queryGateway.query(GetGiftCardQuery(id), GiftCardView::class.java)

    @GetMapping("{id}/events")
    fun getEvents(@PathVariable id: String) =
        eventStore.readEvents(id).asStream().map { it.toString() }.toList()
}

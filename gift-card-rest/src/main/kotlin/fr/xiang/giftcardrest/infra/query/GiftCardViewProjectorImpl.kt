package fr.xiang.giftcardrest.infra.query

import fr.xiang.giftcardapi.query.GetGiftCardQuery
import fr.xiang.giftcardapi.query.model.GiftCardView
import fr.xiang.giftcardrest.domain.command.GiftCard
import fr.xiang.giftcardrest.domain.query.GiftCardViewProjector
import org.axonframework.modelling.command.Repository
import org.axonframework.queryhandling.QueryHandler
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture

@Profile("query")
@Service
class GiftCardViewProjectorImpl(
    val giftCardRepository: Repository<GiftCard>
) : GiftCardViewProjector {

    @QueryHandler
    override fun getGiftCard(query: GetGiftCardQuery): GiftCardView {
        val future = CompletableFuture<GiftCard>()
        giftCardRepository.load(query.id).execute(future::complete)
        return future.get().toView()
    }
}
